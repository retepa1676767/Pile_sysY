package frontend;

import java.io.IOException;
import java.util.HashSet;
import frontend.TokenType;
import error.ErrorType;
import error.FrontError;

import java.util.ArrayList;


import utils.IOhandler;
public class Lexer{

    //private String stringSource;// source string

   // private int curPos; // current position pointer

    private int lineNum;// current line number
    private int curChar;// current character
    private int readCnt;// incremented by every advance(); used to detect a non-consuming addToken()
    //private HashSet<String> reserveWords;// reserved word table
    private ArrayList<Token> tokenList;
    private ArrayList<String> errorList;
    private HashSet<Character> SymbolSet1;
    private HashSet<Character> SymbolSet2;
    public Lexer(){
       // this.stringSource = null;
        this.lineNum = 1;
       //this.reserveWords = new HashSet<String>();// reserved word table
        this.tokenList = new ArrayList<Token>();
        this.errorList = new ArrayList<>();
        initSymList();
    }
    private void initSymList(){
        SymbolSet1 = new HashSet<Character>();
        SymbolSet2 = new HashSet<Character>();
        char[] arr1 = new char[]{',',';','(',')','[',']','{','}','+','-','*',':','%'};
        char[] arr2 = new char[]{'>','<','=','&','|','!'};
        for(char a:arr1)SymbolSet1.add(a);
        for(char b:arr2)SymbolSet2.add(b);

    }
    public ArrayList<String> getErrorList(){
        return errorList;
    }
    

    public ArrayList<Token> getTokenList(){
        return tokenList;
    }

  
    private void advance() throws IOException {
        curChar = IOhandler.read();
        readCnt++;
    }
    private void handleSpace() throws IOException{
        while(curChar == ' '|| curChar == '\t')advance();
    }
    public boolean addSymbol1() throws IOException {
        String name = String.valueOf((char) curChar);
        TokenType type;
        switch (curChar) {
            case ',': type = TokenType.COMMA;   break;
            case ';': type = TokenType.SEMICN;  break;
            case '(': type = TokenType.LPARENT; break;
            case ')': type = TokenType.RPARENT; break;
            case '[': type = TokenType.LBRACK;  break;
            case ']': type = TokenType.RBRACK;  break;
            case '{': type = TokenType.LBRACE;  break;
            case '}': type = TokenType.RBRACE;  break;
            case '+': type = TokenType.PLUS;    break;
            case '-': type = TokenType.MINU;    break;
            case '*': type = TokenType.MULT;    break;
            case ':': type = TokenType.COLON;   break;
            case '%': type = TokenType.MOD; break;
            default:  return false;
        }
        tokenList.add(new Token(name, type, lineNum));
        advance();
        return true;
    }
    public boolean addSymbol2() throws IOException{
        StringBuilder name = new StringBuilder(String.valueOf((char)curChar));
        TokenType type;
        switch(curChar){
            case '>':
                advance();
                if(curChar == '='){
                    name.append((char)curChar);
                    type = TokenType.GEQ;
                }
                else {
                    IOhandler.unread(curChar);
                    type = TokenType.GRE;
                }
                break;
            case '<':
                advance();
                if(curChar == '='){
                    name.append((char)curChar);
                    type = TokenType.LEQ;
                }
                else {
                    IOhandler.unread(curChar);
                    type = TokenType.LSS;
                }
                break;

            case '&':
                advance();
                 if(curChar != '&'){
                    IOhandler.unread(curChar);
                    errorList.add(new FrontError(ErrorType.a,lineNum).toString());
                }
                name.append("&");
                type = TokenType.AND;
                break;

            case '|':
                advance();
                 if(curChar != '|'){
                      IOhandler.unread(curChar);
                      errorList.add(new FrontError(ErrorType.a,lineNum).toString());
                }
                name.append("|");
               type = TokenType.OR;
                break;

            case '!':
                advance();
                 if(curChar == '='){
                    name.append((char)curChar);
                    type = TokenType.NEQ;
                }
                else {
                    IOhandler.unread(curChar);
                    type = TokenType.NOT;
                }
                break;

            case '=':
                advance();
                 if(curChar == '='){
                    name.append((char)curChar);
                    type = TokenType.EQL;
                }
                else {
                    IOhandler.unread(curChar);
                    type = TokenType.ASSIGN;
                }
                break;
           
            default:return false;
        }
 tokenList.add(new Token(name.toString(), type, lineNum));
        advance();
        return true;
    }
    public boolean addLetterToken() throws IOException{
//Character.isLetter(c)
//Character.isDigit(c)
//Character.
        TokenType type;
        StringBuilder name = new StringBuilder();
        if(Character.isLetter(curChar) || curChar == '_'){
              while(curChar !=' ' && (Character.isDigit(curChar)||Character.isLetter(curChar)|| curChar == '_')){
                name.append((char)curChar);
                advance();
              }
              switch(name.toString()){
            case "continue":
                type = TokenType.CONTINUETK;
                break;
            case "default":
                type = TokenType.DEFAULTTK;
                break;
            case "printf":
                type = TokenType.PRINTFTK;
                break;
            case "return":
                type = TokenType.RETURNTK;
                break;
            case "static":
                type = TokenType.STATICTK;
                break;
            case "break":
                type = TokenType.BREAKTK;
                break;
            case "const":
                type = TokenType.CONSTTK;
                break;

            case "while":
                type = TokenType.WHILETK;
                break;
            case "case":
                type = TokenType.CASETK;
                break;
            case "char":
                type = TokenType.CHARTK;
                break;
            case "else":
                type = TokenType.ELSETK;
                break;
            case "main":
                type = TokenType.MAINTK;
                break;
                case "void":
                type = TokenType.VOIDTK;
                break;
                case "int":
                type = TokenType.INTTK;
                break;
                case "if":
                type = TokenType.IFTK;
                break;
                case "switch":
                    type = TokenType.SWITCHTK;
                    break;
                default:
                    type = TokenType.IDENFR;
        }           
tokenList.add(new Token(name.toString(), type, lineNum));
return true;
        }
        return false;

    }

    public boolean addInt() throws IOException{
        StringBuilder name = new StringBuilder();
        int value;
        if(curChar=='0'){
tokenList.add(new Token("0", TokenType.INTCON, lineNum, 0));
advance();
        return true;
        }
        if(Character.isDigit(curChar)){
        while(Character.isDigit(curChar)){
            name.append((char)curChar);
            advance();
        }
        value =  Integer.parseInt(name.toString());
       tokenList.add(new Token(name.toString(), TokenType.INTCON, lineNum, value));
        return true;
    }
    return false;
}

    public boolean addStr() throws IOException{
         StringBuilder name = new StringBuilder();
        if(curChar == '"'){
            while(true){
                name.append((char)curChar);
                advance();
                if(curChar=='"'){name.append((char)curChar);advance();break;}
                if(curChar == -1)return false;
            }
            tokenList.add(new Token(name.toString(),TokenType.STRCON,lineNum));
            return true;
        }
        return false;
    }
    public boolean addChar() throws IOException{
        StringBuilder name = new StringBuilder();
        int value = 0;
        if(curChar == '\''){
            name.append((char)curChar);
            advance();

            if(curChar !='\\'){
            name.append((char)curChar);
            value = curChar;
            }
            else {//curChar = '\\'
            name.append((char)curChar);
            advance();
                switch(curChar){
                    case 'n':
                        name.append("n");
                        value = '\n';
                        break;
                    case  't':
                        name.append("t");
                        value = '\t';
                        break;
                    case 'r':
                        name.append("r");
                        value = '\r';
                        break;
                    case '0':
                         name.append("0");
                        value = '\0';
                        break;
                    case '\\':
                         name.append("\\");
                        value = '\\';
                        break;
                    case '\'':
                         name.append("'");
                        value = '\'';
                        break;
                }
           
            }
            advance();
            if(curChar == '\''){
                name.append("'");
            tokenList.add(new Token(name.toString(),TokenType.CHARCON,lineNum,value));
            advance();
            return true;
            }
        }
        return false;
    }
    public boolean addDiv() throws IOException{
        if(curChar == '/'){
            advance();
            if(curChar == '/'){
                while(true){
                    advance();
                    if(isNewLine()){
                        lineNum++;
                        advance();
                        return true;
                    }
                    if(curChar ==-1)return true;
                }
            }
            else if(curChar == '*'){
                while(true){
                    advance();
                    if(isNewLine()){
                        lineNum++;
                        advance();
                    }
                    if(curChar == -1)return false;
                    if(curChar == '*'){
                        advance();
                        if(curChar == '/'){
                            advance();
                            return true;
                        }
                        else IOhandler.unread(curChar);
                    }
                }

            }
            else {
            tokenList.add(new Token("/",TokenType.DIV,lineNum));
        return true;}
        }
        return false;
    }

   public boolean addToken() throws IOException,RuntimeException{
    //addSymbol1
    //addSymbol2
    //addLetterToken
    //addInt
    //addStr
    //addChar
    char c = (char)curChar;
    int pre = readCnt;
    if(SymbolSet1.contains(c))addSymbol1();
    if(SymbolSet2.contains(c))addSymbol2();
    if(Character.isLetter(c)||c == '_')addLetterToken();
    if(Character.isDigit(c))addInt();
    if(c == '"'){
        addStr();
    }
    if(c == '\''){
        addChar();
    }
    if(c == '/'){
        addDiv();
    }
    if(readCnt == pre && curChar != -1)
    throw new RuntimeException("char unhandled: " + (char)curChar + ", line " + lineNum);
    return true;
   }
    public boolean isNewLine() throws IOException{

        if(curChar == '\r'){
              advance();
        }
         return curChar == '\n';
    }
    public void run() throws IOException{
        advance();
        while(curChar!=-1){
            handleSpace();
            if(isNewLine()){
                lineNum++;
                advance();
                
                continue;
            }
            addToken();
        }
    }



}
