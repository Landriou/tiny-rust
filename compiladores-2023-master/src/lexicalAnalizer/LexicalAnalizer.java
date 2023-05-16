/**
 * Esta clase es la encargada de realizar el analisis lexico del archivo de entrada.
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */
package src.lexicalAnalizer;

import src.domain.lexicalAnalizer.LexicalResponse;
import src.domain.lexicalAnalizer.Alphabet;
import src.lexicalAnalizer.scanners.LetterScanner;
import src.lexicalAnalizer.scanners.NumberScanner;
import src.lexicalAnalizer.scanners.SimbolScanner;

public class LexicalAnalizer {

    //Esta variables se usa para manejar comentarios multilinea.
    public Boolean ignoreLines = false;
    /**
     * Este metodo es el que realiza la logica para obtener el token en la linea.
     * @param line: linea en la que se encuentra el analizador.
     * @param column: columna en la que se encuentra el analizador.
     * @param input: cadena de caracteres que contiene el archivo de entrada.
     * @return LexicalResponse: objeto que contiene el token y el resto de la linea.
     */
    public LexicalResponse getToken(String input, int line, int column) {
        Boolean stopFlag = false;
        LexicalResponse response = new LexicalResponse();

        SimbolScanner simbolScanner = new SimbolScanner();
        NumberScanner numberScanner = new NumberScanner();
        LetterScanner letterScanner = new LetterScanner();
        Alphabet alphabet = new Alphabet();
        char[] inputArray = input.toCharArray();
        int arrayPosition = 0;

        while (!stopFlag) {
            //Si se encuentra un comentario de linea, se ignora el resto de la linea
            //y todas las lineas que se encuentren despues hasta que esto se ponga en false
            if (ignoreLines) {
                if (inputArray.length - 1 == arrayPosition) {
                    response = new LexicalResponse();
                    response.setRestOfLine("");
                    return response;
                }

                // Cuando encuentro un * verifico si el siguiente es un /, si es asi
                // dejo de ignorar las lineas
                if (inputArray[arrayPosition] == '*') {
                    if (inputArray[arrayPosition + 1] == '/') {
                        ignoreLines = false;
                        column += 2;
                        arrayPosition += 2;
                        response = new LexicalResponse(
                                null,
                                input.substring(column-1),
                                column
                        );

                        return response;
                    } else {
                        arrayPosition++;
                        column++;
                    }
                } else {
                    arrayPosition++;
                    column++;
                }
            }else {

                // Si el caracter es un espacio o un tab, se ignora
                if (inputArray[arrayPosition] == ' ') {
                    column++;
                    inputArray = String.valueOf(inputArray)
                            .substring(1).toCharArray();

                } else if (inputArray[arrayPosition] == '\t') {
                    column++;
                    inputArray = String.valueOf(inputArray)
                            .substring(1).toCharArray();

                } else {
                    stopFlag = true;

                    // Si es un numero, se llama al scanner de numeros
                    if (alphabet.isNumber(inputArray[arrayPosition])) {
                        response = numberScanner.scan(
                                arrayPosition,
                                line,
                                column,
                                String.valueOf(inputArray)
                        );

                        // Si es un simbolo, se llama al scanner de simbolos
                    } else if (alphabet.isSimbol(inputArray[arrayPosition])) {
                        response = simbolScanner.scan(
                                arrayPosition,
                                line,
                                column,
                                String.valueOf(inputArray)
                        );
                        // Si la respuesta es nula significa que es un comentario multilinea
                        if (response == null) {
                            stopFlag = false;
                            ignoreLines = true;
                            column++;
                            inputArray = String.valueOf(inputArray)
                                    .substring(1).toCharArray();

                            // Si el token esta vacio significa que es un comentario de linea
                        } else if (response.getToken() == null) {
                            response.setRestOfLine("");
                            return response;
                        }
                        // Si es una letra, se llama al scanner de letras
                    } else if (alphabet.isAlphabet(inputArray[arrayPosition])) {
                        response = letterScanner.scan(arrayPosition,
                                line,
                                column,
                                String.valueOf(inputArray));
                    } else {
                        // Si no es ninguno de los anteriores, se lanza un error de caracter no valido
                        throw new RuntimeException("ERROR: LEXICO\n" +
                                "| NUMERO DE LINEA: |" +
                                " NUMERO DE COLUMNA: | DESCRIPCION: |\n"
                                + "| LINEA " + line
                                + " | COLUMNA " + column
                                + " | CARRACTER NO VALIDO: "
                                + inputArray[arrayPosition] + " |");
                    }
                }
            }
        }
        return response;
    }

    /**
     * Este metodo se encarga de lanzar un error cuando se encuentra un comentario multilinea sin cierre.
     * @param line: linea en la que se encuentra el analizador.
     * @param i: columna en la que se encuentra el analizador.
     */
    public void multilineCommentError(int line, int i) {
        throw new RuntimeException("ERROR: LEXICO\n" +
                "| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |\n" +
                "| LINEA " + line
                + " | COLUMNA " + i
                + " | COMENTARIO MULTILINEA SIN CIERRE:" + " |");
    }
}
