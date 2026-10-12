package frontend;
import java.io.IOException;
import java.util.ArrayList;
import error.*;
import error.FrontError;
import frontend.lexer.Lexer;
import frontend.lexer.Token;
import utils.IOhandler;

public class FrontEnd{
    private static Lexer lexer= new Lexer();
    public  static ArrayList<Token> lex() throws IOException{
        return lexer.run();
    }
public static void writeErrorList() throws IOException{
      StringBuilder content = new StringBuilder();
      ArrayList<FrontError> l = ErrorRecorder.getErrorList();
    for(FrontError fe: l){
        content.append(fe.toString() + "\n");
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