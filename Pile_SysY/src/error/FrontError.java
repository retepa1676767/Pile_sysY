package error;

public class FrontError {
    ErrorType e;
    int lineNum;
    public FrontError(ErrorType e,int lineNum){
        this.e = e;
        this.lineNum = lineNum;
    }

    public String toString(){
        return String.valueOf(lineNum)+" "+e.toString();
    } 
}
