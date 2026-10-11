package frontend;
public class Token{
    private String name;    // original token text
    private TokenType type;    // token category
    private int line;    // line number
    private int value;     // numeric value
    public Token(String name,TokenType type,int line){
        this.name = name;
        this.type = type;
        this.line = line;

    }
    public Token(String name,TokenType type,int line,int value){
        this.name = name;
        this.type = type;
        this.line = line;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getLine() {
        return line;
    }

    public TokenType getType() {
        return type;
    }
    public int getValue(){
        return value;
    }
}