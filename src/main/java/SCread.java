

import java.awt.*;
public class SCread {

    public static String[] orange = {"private", "protected", "public", "try", "catch", "throw", "import", "package", "true", "false", "register", "reinterpret_cast", "namespace"
            , "and", "@author", "@version", "@date", "/**", "*", "*/", "or", "as", "assert", "async",
            "await", "class", "def", "del", "cout", "cin", "endl", "println"};

    public static String[] blue = {"void", "byte", "char", "short", "Short", "integer", "long", "float", "double","String[]","String","File",
            "boolean", "String", "Float", "Double", "Long", "Integer", "Boolean", "bool", "char", "int", "unsigned", "signed",
            "short", "long", "from", "global", "in", "is", "List", "Tuple", "Dictionary", "Set", "Number", "delete", "do", "dynamic_cast", "else", "enum", "explicit", "export",
            "for", "friend", "JFrame", "JPane", "JLabel", "JTextFiled", "JTextPanel", "JButton","JMenu", "JMenuItem", "JPopMenu","JMenuBar","JFileChooser", "JScrollPane"};

    public static String[] cyan = {"abstract", "class", "extends", "final", "implements", "interface", "native", "new", "static", "lambda", "None",
            "nonlocal", "not", "inlines", "mutable", "new", "operator", "System"};

    public static String[] pink = {"break", "continue", "return", "do", "while", "if", "elif", "else", "for", "instanceof", "switch", "case", "default"
            , "try", "typedef", "typeid", "virtual", "void", "volatile", "union"};

    public static String[] magenta = {"super", "this", "void","using", "std",
            "complex", "bool", "pass", "with", "yield", "enum", "explicit", "#include"};


    public static Color keyword(String str) {
        Color color = Color.black;

        for (String s : orange) {
            if (str.trim().equals(s)) {
                color = Color.orange;
                break;
            }
        }

        for (String s : blue) {
            if (str.trim().equals(s)) {
                color = Color.blue;
                break;
            }
        }

        for (String s : cyan) {
            if (str.trim().equals(s)) {
                color = Color.cyan;
                break;
            }
        }

        for (String s : pink) {
            if (str.trim().equals(s)) {
                color = Color.pink;
                break;
            }
        }

        for (String s : magenta) {
            if (str.trim().equals(s)) {
                color = Color.magenta;
                break;
            }
        }
        return color;
    }
}

