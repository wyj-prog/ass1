

import org.yaml.snakeyaml.Yaml;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class about extends JFrame {

    static class aboutFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new about();
            }catch (IOException error){
                error.printStackTrace();
            }
        }
    }


    JLabel mainText;

    public about() throws IOException {
        //Read from YAML configure file
        Yaml aboutyaml = new Yaml();
        FileReader yamlreader = new FileReader("YAML//about.yaml");
        BufferedReader yamlbuffer = new BufferedReader(yamlreader);
        Map<String, Object> map = aboutyaml.load(yamlbuffer);

        mainText = new JLabel("<html><body>"+"Beneath this mask there is more than flesh."+"<br>"+
                "Beneath this mask there is an idea, "+ "<br>" +
                "Mr. Creedy, and ideas are bulletproof."+"<br><br>" +
                "Zheng Wang" + "<br>" +
                "William Wu"+"<body></html>", JLabel.CENTER);


        this.setTitle((String) map.get("Title"));
        this.setSize((int) map.get("width"), (int) map.get("height"));
        this.add(mainText, BorderLayout.CENTER);
        this.setVisible(true);

        //screen parameter
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width/2;
        int screenHeight = screenSize.height/2;
        int height = this.getHeight();
        int width = this.getWidth();
        this.setLocation(screenWidth-width/2, screenHeight-height/2);
    }
}
