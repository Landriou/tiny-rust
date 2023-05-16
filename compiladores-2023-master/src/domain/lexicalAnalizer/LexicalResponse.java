/**
 * Clase que representa la respuesta del analizador léxico
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */
package src.domain.lexicalAnalizer;

public class LexicalResponse {

    private Token token;

    private String restOfLine;

    private int offSet;

    public LexicalResponse(Token token, String restOfLine, int offSet) {
        this.token = token;
        this.restOfLine = restOfLine;
        this.offSet = offSet;
    }

    public LexicalResponse() {
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getRestOfLine() {
        return restOfLine;
    }

    public void setRestOfLine(String restOfLine) {
        this.restOfLine = restOfLine;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }
}
