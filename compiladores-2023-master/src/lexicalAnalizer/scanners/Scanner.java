/**
 * Interfaz que define el comportamiento de un escaner.
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */

package src.lexicalAnalizer.scanners;

import src.domain.lexicalAnalizer.LexicalResponse;

public interface Scanner {

    /**
     * Este metodo realiza el escaneo de un lexema.
     * @param offSet: posicion del caracter en el que
     *              se encuentra el analizador.
     * @param line: linea en la que se encuentra el analizador.
     * @param column: columna en la que se encuentra el analizador.
     * @param input: cadena de caracteres que contiene el archivo de entrada.
     * @return LexicalResponse: objeto que contiene el token y
     * el resto de la linea.
     */
    LexicalResponse scan(int offSet, int line, int column, String input);

}
