/**
 * Clase que implementa la interfaz Scanner y realiza el escaneo de un lexema
 * que comienza con un numero.
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */
package src.lexicalAnalizer.scanners;

import src.domain.lexicalAnalizer.LexicalResponse;
import src.domain.lexicalAnalizer.Token;

public class NumberScanner implements Scanner {

    private static final String cutCases = ".,;:()[]{}+-*/=<>!&|";
    /**
     * Este metodo realiza el escaneo de un lexema que comienza con un numero.
     * @param offSet: posicion del caracter en el que
     *             se encuentra el analizador.
     * @param line: linea en la que se encuentra el analizador.
     * @param column: columna en la que se encuentra el analizador.
     * @param input: cadena de caracteres que contiene el archivo de entrada.
     * @return LexicalResponse: objeto que contiene el token
     * y el resto de la linea.
     */
    @Override
    public LexicalResponse scan(int offSet,
                                int line,
                                int column,
                                String input) {
        String tokenType,lexeme = "";
        int iterator = offSet;
        Boolean badFormed = false,stopFlag = false;
        Token token = new Token();
        LexicalResponse lexicalResponse;

        while (!stopFlag) {
            // Si encontramos un espacio, un tabulador, un salto de linea
            // o un caracter de corte, entonces el lexema es un numero
            // y se detiene el analisis.
            if (input.charAt(iterator) == ' '
                    || input.charAt(iterator) == '\t'
                    || input.charAt(iterator) == '\n'
                    || cutCases.contains(String.valueOf(input.charAt(iterator)))
                    || iterator == input.length() - 1 ) {

                tokenType = Token.tokenTypes.get("const");
                token = new Token(lexeme,line,column,tokenType);
                stopFlag = true;
            }

            if (!stopFlag) {
                lexeme += input.charAt(iterator);

                // Si encontramos un caracter que no es un numero
                // entonces el lexema es mal formado.
                if (!Character.isDigit(input.charAt(iterator))) {
                    badFormed = true;
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
                    + " | NUMERO MAL FORMADO: "+ lexeme +"  |");
        }


        // Si el lexema es un numero y es el ultimo caracter de la linea
        // entonces se retorna un LexicalResponse con el token y un
        // string vacio en el resto de la linea.
        if (input.length() == 1){
            lexicalResponse = new LexicalResponse(token, "", iterator);
        }

        lexicalResponse = new LexicalResponse(token,
                input.substring(iterator),
                iterator);
        return lexicalResponse;
    }
}
