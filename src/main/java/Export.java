

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.yaml.snakeyaml.Yaml;

import javax.swing.*;

public class Export extends JFrame {

    public Export() throws IOException{
        // Read from the YAML file
        Yaml exportyaml = new Yaml();
        FileReader yamlreader = new FileReader("YAML//export.yaml");
        BufferedReader yamlbuffer = new BufferedReader(yamlreader);
        Map<String, Object> map = exportyaml.load(yamlbuffer);
        int margin = (int) map.get("margin");
        int fontSize = (int) map.get("fontSize");

        PDDocument doc = null;
        try
        {
            doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            //PDFont pdfFont = PDType0Font.load(doc, new File("c:/windows/fonts/times.ttf"));
            // If the above font does not cover the characters you typed, you can choose the one below.
            PDFont pdfFont = PDType1Font.TIMES_ROMAN;
            //float fontSize = 9;
            float leading = 1.5f * fontSize;

            PDRectangle mediabox = page.getMediaBox();
            //float margin = 72;
            float width = mediabox.getWidth() - 2*margin;
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin;

            String text = notepad.input.getText();
            List<String> liness = new ArrayList<String>();
            String temp = "";
            for (int i = 0; i < text.length(); i++){
                if (text.charAt(i) == '\n' || text.charAt(i) == '\r' || i + 1 == text.length()){
                    // If this is the end of the document
                    if(i + 1 == text.length()){ // last one
                        liness.add(temp);
                        break;
                    }else
                    // If this is not the end of the document
                    {
                        int count = -1;
                        while(text.charAt(i)== '\n' || text.charAt(i) == '\r'){
                            if (i + 1 < text.length()){
                                i = i + 1;
                                count ++;
                            }else break;
                        }
                        liness.add(temp);
                        for (int newlines = 1; newlines <= (int)count; newlines++){
                            temp = "";
                            liness.add(temp);
                        }
                        if (i + 1 == text.length()) break;
                        else {
                            //if (count > 0) i++;
                            i = i - 1;
                            temp = "";
                        }
                    }
                }else temp += text.charAt(i);
            }

            contentStream.beginText();
            contentStream.setFont(pdfFont, fontSize);
            contentStream.newLineAtOffset(startX, startY);
            int count_liness = 0;
            int need_new_page = 0;
            int current_lines = 0;
            for (int i = 1; i <= liness.size() / 47 + 1 ; i ++){
                while (current_lines < liness.size())
                {
                    //Judging whether the lines is too long
                    if (liness.get(current_lines).length() > 130){
                        int plus_one;
                        plus_one = liness.get(current_lines).length() % 130 == 0 ? 0 : 1;
                        int rows = liness.get(current_lines).length() / 130 + plus_one;
                        int current_position = 0;
                        for (int count = 1; count < rows; count++){
                            contentStream.showText(liness.get(current_lines).substring(current_position, current_position + 130));
                            contentStream.newLine();
                            contentStream.newLineAtOffset(0, -leading);
                            current_position += 131;
                        }
                        contentStream.showText(liness.get(current_lines).substring(current_position, liness.get(current_lines).length()));
                        contentStream.newLine();
                        contentStream.newLineAtOffset(0, -leading);
                    }else {
                        contentStream.showText(liness.get(current_lines));
                        contentStream.newLine();
                        contentStream.newLineAtOffset(0, -leading);
                    }
                    current_lines++;
                    if ((current_lines % 47) == 0) break;
                }
                contentStream.endText();
                contentStream.close();
                if (current_lines != liness.size()) {
                    PDPage newpage = new PDPage();
                    doc.addPage(newpage);
                    contentStream = new PDPageContentStream(doc, newpage);
                    contentStream.beginText();
                    contentStream.setFont(pdfFont, fontSize);
                    contentStream.newLineAtOffset(startX, startY);
                    count_liness = 0;
                }
            }
            doc.save(new File("Print.pdf"));
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (doc != null) {
                doc.close();
                JOptionPane.showMessageDialog(null, "Successfully exported the text file to Print.pdf", "Success", JOptionPane.INFORMATION_MESSAGE);
            }else
                JOptionPane.showMessageDialog(null, "Error occured", "alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void callExport() throws IOException{
        InputStream inputStream = Files.newInputStream(new File("YAML//export.yaml").toPath());
        Yaml yaml = new Yaml();
        Export newexport = yaml.load(inputStream);
        System.out.println(newexport);
    }

}


