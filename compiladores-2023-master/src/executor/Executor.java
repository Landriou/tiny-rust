/**
 * Clase encargada de ejecutar la acción de cada token.
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */

package src.executor;

import src.domain.lexicalAnalizer.Token;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Executor {
    //javadoc comment español

    private ArrayList<String> resultList = new ArrayList<>();
    /**
     * Este método se encarga de ejecutar la acción de cada token.
     * @param writer escritor del archivo de salida.
     * @param out archivo en el cual se va a imprimir el token.
     * @param token token que se va a imprimir.
     */
    public void getToken(FileWriter writer, PrintWriter out, Token token) {

        resultList.add(token.toString()+'\n');

        if (writer != null){
            out.println(token.toString());
        }

    }

    public void printResult(){
        for (String s : resultList) {
            System.out.print(s);
        }
    }
}
