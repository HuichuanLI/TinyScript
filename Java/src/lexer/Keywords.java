package lexer;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Author : lihuichuan
 * Time   : 2020/11/29
 */
public class Keywords {
    static String[] keywords = {
            "var",
            "int",
            "float",
            "bool",
            "void",
            "string",
            "if",
            "else",
            "for",
            "while",
            "break",
            "func",
            "return"
    };


    // array 装成hashset
    static HashSet<String> set = new HashSet<>(Arrays.asList(keywords));

    public static boolean isKeyword(String word) {
        return set.contains(word);
    }
}
