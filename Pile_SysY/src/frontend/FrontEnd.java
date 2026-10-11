package frontend;
import java.io.IOException;
import java.util.ArrayList;

import utils.IOhandler;

public class FrontEnd{
    private static Lexer lexer= new Lexer();
    public  static void lex() throws IOException{
        lexer.run();
    }
public static void writeErrorList() throws IOException{
    ArrayList<String> l=lexer.getErrorList();
      StringBuilder content = new StringBuilder();
    for(String str:l){
        content.append(str + "\n");
    };
    IOhandler.write(IOhandler.ERROR_FILE,content.toString());
}
public static void writeTokenList() throws IOException{
    ArrayList<Token> tl = lexer.getTokenList();
    StringBuilder content = new StringBuilder();
    for(Token token:tl){
        content.append(token.getType().name()+" "+token.getName()+ "\n");
    }
    IOhandler.write(IOhandler.LEXER_FILE,content.toString());
}
}