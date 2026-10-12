import utils.*;
import frontend.*;

public class Compiler{
public static void main(String[] args) {
      //initiation
        try{
               IOhandler.init(); 
        }
        catch(Throwable e0){
              e0.printStackTrace();
               try {
             IOhandler.close();
      } catch (Exception e1) {
            e1.printStackTrace();
      }
              System.exit(0);  
        }

       
        //process
        try {
       FrontEnd.lex();
        FrontEnd.writeTokenList();
        FrontEnd.writeErrorList();
}
catch(Throwable e){
        e.printStackTrace();
}
finally{
      try {
             IOhandler.close();
      } catch (Exception e1) {
            e1.printStackTrace();
      }
}

}}
