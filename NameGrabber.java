import java.lang.reflect.Method;
import java.io.*;
public class NameGrabber {
    public static void main(String[] args) {
        System.out.println("Hello there "+grabName());
    }

    public static String grabName(){
        String commandPC = "cmd /c echo %username%";
        String commandMAC = "id -F";
        String name = "";
        String fallbackName = System.getProperty("user.name");
        try{
            String osName = System.getProperty( "os.name" ).toLowerCase();
            if( osName.contains( "windows" ) ){//PC stuff
                String boi=executeCommand(commandPC);
                boi = boi.substring(boi.indexOf("Full Name")+9,boi.indexOf("Comment"));
                for(int i = 0; i< boi.length();i++){
                    if((boi.charAt(i)!=(' '))||(i>0&&i<boi.length()-1&&boi.charAt(i)==' '&&boi.charAt(i-1)!=' '&&boi.charAt(i+1)!=' ')){
                        name+=boi.charAt(i);
                    }
                }
            }
            else if( osName.contains( "mac" ) ){// mac stuff
                name = executeCommand(commandMAC);
            }
            if(name.length()==0){
                throw new Exception("oof");
            }
        }catch(Exception e){
            name=fallbackName;
        }
        return name;
    }

    private static String executeCommand(String command) throws Exception{
        StringBuffer output = new StringBuffer();
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";           
        while ((line = reader.readLine())!= null) {
            output.append(line + "\n");
        }
        return output.toString();
    }
} 