/**
 * Clase que contiene los alfabetos que se utilizan en el analizador léxico
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */
package src.domain.lexicalAnalizer;

public class Alphabet {
    public static final String AlphabetList = "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ_";

    public static final String NumbersList = "0123456789";

    public static final String SimbolsList = ".,;:()[]{}+-*/=<>!&|\"\'%";

    public static final String[] ReservedWordList = {
            "while",
            "for",
            "if",
            "class",
            "else",
            "not",
            "true",
            "false",
            "new",
            "fn",
            "return",
            "static",
            "self",
            "pub",
            "priv",
            "create",
            "void",
            "I32",
            "Char",
            "Bool",
            "Str",
            "Array",

    };

    public Boolean isAlphabet(char input){
        return AlphabetList.contains(String.valueOf(input));
    }

    public Boolean isNumber(char input){
        return NumbersList.contains(String.valueOf(input));
    }

    public Boolean isSimbol(char input){
        return SimbolsList.contains(String.valueOf(input));
    }

    public Boolean isReservedWord(String input){
        boolean isReservedWord = false;
        for (String reservedWord : ReservedWordList) {
            if (reservedWord.equals(input)) {
                isReservedWord = true;
            }
        }
        return isReservedWord;
    }
}
