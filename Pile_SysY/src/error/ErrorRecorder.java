package error;

import java.util.ArrayList;

public class ErrorRecorder {
private static ArrayList<FrontError> errorList = new ArrayList<>(); 
private ErrorRecorder(){}
public static ArrayList<FrontError> getErrorList(){
    return errorList;
}
public static void record(FrontError fe){
    errorList.add(fe);
}
}
