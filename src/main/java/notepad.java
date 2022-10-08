

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;


public class notepad extends JFrame{

    public static void main(String[] args) {
        notepad notepad = new notepad(true);
        notepad.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static JFrame mainFrame;
    
    //Input Area
    public static JTextPane input;
    public static JScrollPane scroller;
    public static MouseWheelListener sysWheel;

    //The menu bar at the top
    public static JMenuBar topMenu;

    //File
    public static JMenu file;
    public static JMenuItem New;
    public static JMenuItem open;
    public static JMenuItem save;
    public static JMenuItem exit;

    //Search
    public static JMenu search;
    public static JMenuItem searchItem;
    public static JMenuItem previous;
    public static JMenuItem next;

    //Manage
    public static JMenu manage;
    public static JMenuItem selectAll;
    public static JMenuItem copy;
    public static JMenuItem paste;
    public static JMenuItem cut;
    public static JMenuItem print;
    public static JMenuItem export;

    //View
    public static JMenu view;
    public static JMenuItem statusBarInvisibility;
    public static JMenuItem scaleUp;
    public static JMenuItem scaleDown;

    //Help
    public static JMenu help;
    public static JMenuItem about;

    //popMenu
    public static JPopupMenu rClick;
    public static JMenuItem selectAllR;
    public static JMenuItem copyR;
    public static JMenuItem pasteR;
    public static JMenuItem cutR;

    //Status Bar
    public static JPanel statusBar;
    public static JMenu timeAndDate;
    public static JTextField leftPart;




    public notepad(boolean isVisible) {

        mainFrame = new JFrame("Notepad--");
        mainFrame.setSize(600,600);


        //Top menubar
        topMenu = new JMenuBar();


        //File Menu
        file = new JMenu("File");
        New = new JMenuItem("New            ");
        New.addActionListener(new miniFunctions.NewButton());
        open = new JMenuItem("Open");
        open.addActionListener(new miniFunctions.OpenButton());
        save = new JMenuItem("Save");
        save.addActionListener(new miniFunctions.SaveButton());
        exit = new JMenuItem("Exit");
        exit.addActionListener(e -> {
            mainFrame.dispose();
        });

        file.add(New);
        file.add(open);
        file.add(save);
        file.add(exit);

        topMenu.add(file);

        //Search Menu
        search = new JMenu("Search");

        searchItem = new JMenuItem("Search          ");
        searchItem.addActionListener(new search.searchFrame());
        previous = new JMenuItem("Previous");
        previous.addActionListener(new search.searchingUp());
        next = new JMenuItem("Next");
        next.addActionListener(new search.searchingDown());

        search.add(searchItem);
        search.add(previous);
        search.add(next);

        topMenu.add(search);

        //View Menu

        view = new JMenu("View");

        statusBarInvisibility = new JMenuItem("Status Bar             √");
        statusBarInvisibility.addActionListener(new miniFunctions.statusBarInvisibility());
        scaleUp = new JMenuItem("Scale up                Ctrl + wheel-up");
        scaleUp.addActionListener(new miniFunctions.scaleUp());
        scaleDown = new JMenuItem("Scale down           Ctrl + wheel-down");
        scaleDown.addActionListener(new miniFunctions.scaleDown());

        view.add(statusBarInvisibility);
        view.add(scaleUp);
        view.add(scaleDown);

        topMenu.add(view);

        //Manage Menu
        manage = new JMenu("Manage");

        selectAll = new JMenuItem("Select all           ");
        selectAll.addActionListener(new miniFunctions.SelectAllButton());
        copy = new JMenuItem("Copy");
        copy.addActionListener(new miniFunctions.CopyButton());
        paste = new JMenuItem("Paste");
        paste.addActionListener(new miniFunctions.PasteButton());
        cut = new JMenuItem("Cut");
        cut.addActionListener(new miniFunctions.CutButton());
        print = new JMenuItem("Print");
        print.addActionListener(new miniFunctions.PrintButton());
        export = new JMenuItem("Export");
        export.addActionListener(new miniFunctions.ExportButton());

        manage.add(selectAll);
        manage.add(copy);
        manage.add(paste);
        manage.add(cut);
        manage.add(print);
        manage.add(export);

        topMenu.add(manage);


        //Help Menu

        help= new JMenu("Help");

        about = new JMenuItem("About            ");
        about.addActionListener(new about.aboutFrame());

        help.add(about);

        topMenu.add(help);



        //topMenu construct

        mainFrame.add(topMenu, BorderLayout.NORTH);


        //Input area
        input = new JTextPane();

        input.addCaretListener(new miniFunctions.emptyCheck());
//        input.setTransferHandler(new DragAndDrop());


        manage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (miniFunctions.getClipboardString() == null){
                        paste.setEnabled(false);
                    }
                    if (input.getText().equals("")){
                        selectAll.setEnabled(false);
                        copy.setEnabled(false);
                    }
                }
            }
        });

        scroller = new JScrollPane();
        scroller.setBounds(20,20,100,50);
        scroller.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroller.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setViewportView(input);
        sysWheel = scroller.getMouseWheelListeners()[0];
        scroller.removeMouseWheelListener(sysWheel);
        scroller.addMouseWheelListener(new miniFunctions.scale());






        mainFrame.add(scroller);

        //Status Bar
        statusBar = new JPanel();


        //left part of status bar
//        leftPart = new JTextField("Line: 1          Column: 1           Word Count: 0");
        leftPart = new JTextField("Word Count: 0");
        leftPart.setLayout(null);
        leftPart.setEditable(false);
        leftPart.setBackground(new Color(238,238,238));
        leftPart.setBorder(new MatteBorder(0, 0, 0, 0, new Color(238, 238,
                238)));


        //cursor position
        input.addCaretListener( new miniFunctions.wordCount());


        statusBar.setLayout(new FlowLayout());
        statusBar.add(leftPart);

        //time&date
        //Real-time display
        timeAndDate = new JMenu();
        timeAndDate.addActionListener(new miniFunctions.TimeActionListener());
        timeAndDate.setPreferredSize(new Dimension(180,30));
        timeAndDate.setEnabled(false);


        topMenu.add(Box.createHorizontalGlue());
        topMenu.add(timeAndDate);

        mainFrame.add(statusBar, BorderLayout.SOUTH);

        //pop menu
        rClick = new JPopupMenu();

        selectAllR = new JMenuItem("Select all           ");
        copyR = new JMenuItem("Copy");
        pasteR = new JMenuItem("Paste");
        cutR = new JMenuItem("Cut");

        selectAllR.addActionListener(new miniFunctions.SelectAllButton());
        copyR.addActionListener(new miniFunctions.CopyButton());
        pasteR.addActionListener(new miniFunctions.PasteButton());
        cutR.addActionListener(new miniFunctions.CutButton());

        rClick.add(selectAllR);
        rClick.add(copyR);
        rClick.add(pasteR);
        rClick.add(cutR);

        input.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {



                if (e.getButton() == MouseEvent.BUTTON3) {

                    rClick.show(e.getComponent(), e.getX(), e.getY());

                    if (miniFunctions.getClipboardString() == null){
                        pasteR.setEnabled(false);
                    }
                    if (input.getText().equals("")){
                        selectAllR.setEnabled(false);
                    }
                }


            }
        });


        //screen parameter
        int height = mainFrame.getHeight();
        int width = mainFrame.getWidth();
        mainFrame.setLocation(miniFunctions.getScreenWidth()/2-width/2, miniFunctions.getScreenHeight()/2-height/2);
        mainFrame.setVisible(isVisible);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }




}
