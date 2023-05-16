/**
 * Clase que representa un token
 * @author Sebastian Fuchilieri y Daniel Alexander Silva
 * @version 1.0
 * @since 19/03/2023
 */

package src.domain.lexicalAnalizer;

import java.util.HashMap;
import java.util.Map;

public class Token {

    public static Map<String, String> tokenTypes  =
            new HashMap<String, String>() {{
        //La llaves es el lexema y el valor es el token

        // palabras reservadas
        put("while", "pwhile");
        put("for", "pfor");
        put("if", "pif");
        put("class", "pclass");
        put("else", "pelse");
        put("not", "pnot");
        put("true", "ptrue");
        put("false", "pfalse");
        put("new", "pnew");
        put("fn", "pfn");
        put("return", "preturn");
        put("static", "pstatic");
        put("self", "pself");
        put("pub", "ppub");
        put("priv", "ppriv");
        put("create", "pcreate");

        // simbolos
        put("(", "openParenthesis");
        put(")", "closeParenthesis");
        put("{", "openCurlyBrace");
        put("}", "closeCurlyBrace");
        put("[", "openSquareBracket");
        put("]", "closeSquareBracket");
        put(";", "semicolon");
        put(",", "comma");
        put(".", "dot");
        put(":", "colon");
        put("//", "comment");
        put("/*", "multiLineCommentOpen");
        put("*/", "multiLineCommentClose");
        put("->", "arrow");
        put("\"", "doubleQuote");
        put("'", "singleQuote");
        put(" ", "space");
        put("\t", "tab");
        put("\n", "newLine");
        put("\\", "backslash");

        //operadores
        put("=", "equal");
        put("==", "equalEqual");
        put("!", "exclamation");
        put("!=", "exclamationEqual");
        put("<", "less");
        put("<=", "lessEqual");
        put(">", "greater");
        put(">=", "greaterEqual");
        put("+", "plus");
        put("-", "minus");
        put("*", "multiply");
        put("/", "divide");
        put("%", "module");
        put("&&", "and");
        put("||", "or");

        // tipos y identificadores
        put("identifier", "id");
        put("const", "const");
        put("Str", "tstr");
        put("Char", "tchar");
        put("I32", "tint");
        put("Bool", "tbool");
        put("Array", "tarray");
        put("void", "pvoid");
        put("nil", "pnil");

    }};

    private String lexeme;
    private int line;
    private int column;
    private String tokenType;

    public Token(String lexeme, int line, int column, String tokenType) {
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
        this.tokenType = tokenType;
    }

    public Token() {
        this.lexeme = "";
        this.line = 0;
        this.column = 0;
        this.tokenType = "";
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "CORRECTO: ANALISIS LEXICO\n"+
                "| TOKEN | LEXEMA | NUMERO DE LINEA (NUMERO DE COLUMNA) |\n"+
                "| " + tokenType + " | "
                + lexeme + " | "
                + line + " (" + column + ") |";
    }
}
