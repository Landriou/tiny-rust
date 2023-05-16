/**
 * Clase principal del compilador.
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */
package src;

import src.domain.lexicalAnalizer.LexicalResponse;
import src.executor.Executor;
import src.lexicalAnalizer.LexicalAnalizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String fileName,textLine;
        int line, actColumn;
        LexicalAnalizer lexicalAnalizer = new LexicalAnalizer();
        LexicalResponse response;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        BufferedReader reader = null;
        PrintWriter out = null;
        Executor executor = new Executor();

        try {

            // Si se pasa un segundo argumento, se escribe en un archivo.
            if (args.length > 1){
                try {
                    Files.deleteIfExists(Paths.get(args[1]));
                } catch (IOException e) {
                    System.out.println("Error: No se pudo borrar el archivo");
                }
                fileWriter = new FileWriter(args[1], true);
                bufferedWriter = new BufferedWriter(fileWriter);
                out = new PrintWriter(bufferedWriter);
            }

            //fileName = "test/test1";
            // Leemos el argumento que viene por consola.
            fileName = args[0];
            reader = new BufferedReader(new FileReader(fileName));
            textLine = reader.readLine();
            line = 1;
            actColumn = 1;


            while(textLine != null) {
                // Si la linea esta vacia, se lee la siguiente.
                if (textLine.equals("") ||
                        textLine.equals(" ") ||
                        textLine.equals("\t")) {
                    textLine = reader.readLine();
                    line++;

                }
                else {
                    // Se obtiene el token y se ejecuta pasa al ejecutor.
                    response = lexicalAnalizer.getToken(textLine,
                            line,
                            actColumn);

                    if (response.getToken() != null) {
                        executor.getToken(fileWriter,
                                out,
                                response.getToken());
                    }

                    // Si la linea esta vacia, se lee la siguiente.
                    if (response.getRestOfLine().equals("")) {
                        actColumn = 1;
                        textLine = reader.readLine();
                        line++;
                    }
                    else {
                        // Si no, Se actualiza la linea y la columna.
                        if (response.getToken() != null) {
                            actColumn = response.getToken().getColumn()
                                    + response.getToken().getLexeme().length();
                        }
                        else {
                            actColumn = response.getOffSet();
                        }
                        textLine = response.getRestOfLine();
                    }
                }
            }
            // Cuando el comentario multilinea no esta cerrado
            // se lanza una excepción.
            if (lexicalAnalizer.ignoreLines) {
                lexicalAnalizer.multilineCommentError(line,actColumn);
            }

            executor.printResult();

        } catch (FileNotFoundException ex) {
            System.out.println("Error: Fichero no encontrado");
            ex.printStackTrace();
        } catch(Exception ex) {
            System.out.println(ex);
        } finally {
            // Se cierra el archivo.
            try {
                if(reader != null) {
                    reader.close();
                }
                if (fileWriter != null){
                    out.close();
                    bufferedWriter.close();
                    fileWriter.close();
                }
            }

            catch (Exception ex) {
                System.out.println("Error al cerrar el fichero");
                ex.printStackTrace();
            }
        }
    }
}