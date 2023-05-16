/**
 * Clase que implementa el escaneo de simbolos
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */
package src.lexicalAnalizer.scanners;

import src.domain.lexicalAnalizer.LexicalResponse;
import src.domain.lexicalAnalizer.Token;

public class SimbolScanner implements Scanner {

    /**
     * Este metodo realiza el escaneo de un lexema que comienza con un simbolo.
     * @param offSet: posicion del caracter en el que se
     *              encuentra el analizador.
     * @param line: linea en la que se encuentra el analizador.
     * @param column: columna en la que se encuentra el analizador.
     * @param input: cadena de caracteres que contiene el archivo de entrada.
     * @return LexicalResponse: objeto que contiene el token y
     * el resto de la linea.
     */
    @Override
    public LexicalResponse scan(int offSet,
                                int line,
                                int column,
                                String input) {
        String tokenType, lexeme = "";
        Token token = null;
        int iterator;
        boolean stopFlag, commentFlag = false;
        LexicalResponse lexicalResponse = null;

        // Si estamos al final de la linea, obtenemos ese token
        if (input.length() - 1 == offSet) {

            switch (input.charAt(offSet)) {
                case '&':
                case '|':
                    throw new RuntimeException("ERROR: LEXICO\n"+
                            "| NUMERO DE LINEA: " +
                            "| NUMERO DE COLUMNA: | DESCRIPCION: |\n"
                            + "| LINEA " + line
                            + " | COLUMNA " + column
                            + " | CARACTER INESPERADOR: "+ input.charAt(offSet) +"  |");
            }

            tokenType = Token.tokenTypes.get(
                    String.valueOf(input.charAt(offSet))
            );

            lexeme += input.charAt(offSet);
            token = new Token(lexeme,line,column+offSet,tokenType);
            lexicalResponse = new LexicalResponse(token, "", offSet);

        }
        else{
            // Para los simbolos duales, tenemos que chequear cada caso
            switch (input.charAt(offSet)) {
                case '<':
                case '>':
                case '=':
                case '!':
            // Si es un igual, tenemos que verificar si hay otro adelante
                    if (input.charAt(offSet + 1) == '=') {
                        lexeme += input.charAt(offSet);
                        lexeme += input.charAt(offSet + 1);
                    }
                    else {
                        lexeme += input.charAt(offSet);
                    }
                    tokenType = Token.tokenTypes.get(lexeme);
                    token = new Token(lexeme,line,column,tokenType);
                    break;

                case '&':
            // Si es un ampersand, tenemos que verificar si hay otro adelante
                    if (input.charAt(offSet + 1) == '&') {
                        lexeme += input.charAt(offSet);
                        lexeme += input.charAt(offSet + 1);
                        tokenType = Token.tokenTypes.get(lexeme);
                        token = new Token(lexeme,line,column,tokenType);
                    }else{
                        throw new RuntimeException("ERROR: LEXICO\n"+
                                "| NUMERO DE LINEA: " +
                                "| NUMERO DE COLUMNA: | DESCRIPCION: |\n"
                                + "| LINEA " + line
                                + " | COLUMNA " + column
                                + " | CARACTER INESPERADOR: "+ input.charAt(offSet) +"  |");
                    }
                    break;

                case '|':
            // Si es un pipe, tenemos que verificar si hay otro adelante
                    if (input.charAt(offSet + 1) == '|') {
                        lexeme += input.charAt(offSet);
                        lexeme += input.charAt(offSet + 1);
                        tokenType = Token.tokenTypes.get(lexeme);
                        token = new Token(lexeme,line,column,tokenType);
                    }else{
                        throw new RuntimeException("ERROR: LEXICO\n"+
                                "| NUMERO DE LINEA: " +
                                "| NUMERO DE COLUMNA: | DESCRIPCION: |\n"
                                + "| LINEA " + line
                                + " | COLUMNA " + column
                                + " | CARACTER INESPERADOR: "+ input.charAt(offSet) +"  |");
                    }
                    break;

                case '-':
            // Si es un menos, tenemos que verificar si es una flechas
                    if (input.charAt(offSet + 1) == '>') {
                        lexeme += input.charAt(offSet);
                        lexeme += input.charAt(offSet + 1);
                    }
                    else {
                        lexeme += input.charAt(offSet);
                    }
                    tokenType = Token.tokenTypes.get(lexeme);
                    token = new Token(lexeme,line,column,tokenType);
                    break;

                case '/':
            // Si es un slash, tenemos que verificar si es un divido o un comentario
                    switch (input.charAt(offSet + 1)) {
            // Comentario multilinea, devuelve token vacio al analizador lexico
                        case '/':
                            commentFlag = true;
                            lexicalResponse = new LexicalResponse();
                            break;
            // Comentario multilinea, devuelve null al analizador lexico
                        case '*':
                            commentFlag = true;
                            lexicalResponse = null;
                            break;
                        default:
                        //Si no es comentario, es un slash de division
                                lexeme += input.charAt(offSet);
                                tokenType = Token.tokenTypes.get(lexeme);
                                token = new Token(lexeme,line,column,tokenType);
                    }
                    break;

                    // Si encontramos una comilla, es un caso especial
                    // una constante de cadena
                case '\"':
                    // Buscamos el siguiente simbolo de comilla
                    iterator = offSet + 1;
                    stopFlag = false;
                    while (!stopFlag) {
                        // Si se acaba la linea,
                        // disparamos un error de cadena mal formada,
                        // No se soporta cadenas multilinea
                        if (input.length() == iterator) {
                            throw new RuntimeException("ERROR: LEXICO\n"+
                                    "| NUMERO DE LINEA: " +
                                    "| NUMERO DE COLUMNA: | DESCRIPCION: |\n"
                                    + "| LINEA " + line
                                    + " | COLUMNA " + column
                                    + " | CADENA MAL FORMADA: "+ input +"  |");
                        }

                        if (input.charAt(iterator) == '\"') {
                            if (input.charAt(iterator - 1) == '\\') {
                                iterator++;
                            }
                            else {
                                stopFlag = true;
                            }
                        }
                        else if (input.charAt(iterator) == '\\') {
                            if (input.charAt(iterator + 1) == '0') {
                                throw new RuntimeException("ERROR: LEXICO\n"+
                                        "| NUMERO DE LINEA: | " +
                                        "NUMERO DE COLUMNA: | DESCRIPCION: |\n"
                                        + "| LINEA " + line
                                        + " | COLUMNA " + column
                                        + " | END OF FILE: "+ input +"  |");
                            }
                            else {
                                iterator++;
                            }
                        }
                        else{
                            iterator++;
                        }
                    }

                    lexeme += input.substring(offSet, iterator + 1);
                    tokenType = Token.tokenTypes.get("const");
                    token = new Token(lexeme,line,column ,tokenType);
                    break;

                    // Si es una comilla simple, es un caso especial
                    // una constante de caracter
                case '\'':
                    // Buscamos la siguiente comilla simple
                    iterator = offSet + 1;
                    while (input.charAt(iterator) != '\'') {
                        iterator++;
                    }
                    lexeme += input.substring(offSet, iterator + 1);
                    String tokenType2 = Token.tokenTypes.get("const");
                    token = new Token(lexeme,line,column,tokenType2);
                    break;

                default:
                    // Si no es ningun caso, es un simbolo simple
                    lexeme += input.charAt(offSet);
                    tokenType = Token.tokenTypes.get(lexeme);
                    token = new Token(lexeme,line,column,tokenType);
                    break;
            }

        }

        // Si el token no es nulo, creamos un LexicalResponse
        if (lexicalResponse == null && commentFlag == false && token != null) {
            lexicalResponse = new LexicalResponse(token, input.substring(
                    offSet + lexeme.length()),
                    offSet + lexeme.length()
            );
        }

        return lexicalResponse;
    }
}
