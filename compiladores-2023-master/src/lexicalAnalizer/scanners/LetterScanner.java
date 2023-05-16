/**
 * Esta clase implementa la interfaz Scanner y realiza el escaneo de un lexema
 * que comienza con una letra.
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */

package src.lexicalAnalizer.scanners;

import src.domain.lexicalAnalizer.LexicalResponse;
import src.domain.lexicalAnalizer.Token;
import src.domain.lexicalAnalizer.Alphabet;

public class LetterScanner implements Scanner {
    private static final String cutCases = ".,;:()[]{}+-*/=<>!&|";

    /**
     * Este metodo realiza el escaneo de un lexema que comienza con una letra.
     * @param offSet: posicion del caracter en el que se encuentra el analizador.
     * @param line: linea en la que se encuentra el analizador.
     * @param column: columna en la que se encuentra el analizador.
     * @param input: cadena de caracteres que contiene el archivo de entrada.
     * @return LexicalResponse: objeto que contiene el token y el resto de la linea.
     */
    @Override
    public LexicalResponse scan(int offSet, int line, int column, String input) {
        Alphabet alphabet = new Alphabet();
        String lexeme = "",tokenType;
        int iterator = offSet;
        Boolean badFormed = false, stopFlag = false;
        Token token = new Token();
        LexicalResponse lexicalResponse;
        boolean restOfLineIs0 = false;

        while (!stopFlag) {

            // Si encontramos un espacio, un tabulador, un salto de linea,
            // si no hay mas nada adelante en la linea
            // o un caracter de corte, entonces el lexema es un identificador
            // y se detiene el analisis.
            if ((restOfLineIs0 || input.charAt(iterator) == ' '
                    || input.charAt(iterator) == '\t'
                    || input.charAt(iterator) == '\n'
                    || cutCases.contains(String.valueOf(input.charAt(iterator)))
                    ) ) {

                // Si el lexema es una palabra reservada, entonces el token es
                // una palabra reservada, de lo contrario es un identificador.
                if (alphabet.isReservedWord(lexeme)) {
                    tokenType = Token.tokenTypes.get(lexeme);

                }
                else {
                    tokenType = Token.tokenTypes.get("identifier");
                }

                token = new Token(lexeme,line,column,tokenType);
                stopFlag = true;
            }

            if (!stopFlag) {
                lexeme += input.charAt(iterator);

                // Si encontramos un caracter que no es una letra o un numero
                // entonces el lexema es mal formado.
                if (!alphabet.isNumber(input.charAt(iterator))
                        && !alphabet.isAlphabet(input.charAt(iterator))) {

                    badFormed = true;
                }

                if (iterator == input.length() - 1) {
                    restOfLineIs0 = true;
                }
                iterator++;
            }
        }

        if (badFormed) {
            throw new RuntimeException("ERROR: LEXICO\n"+
                    "| NUMERO DE LINEA: " +
                    "| NUMERO DE COLUMNA: " +
                    "| DESCRIPCION: |\n"+
                    "| LINEA " + line
                    + " | COLUMNA " + column
                    + " | IDENTIFICADOR MAL FORMADO: "+ lexeme +"  |");
        }

        if (input.length() == 1){
            lexicalResponse = new LexicalResponse(token, "", iterator);
        }

        lexicalResponse = new LexicalResponse(token,
                input.substring(iterator),
                iterator);
        return lexicalResponse;
    }
}
