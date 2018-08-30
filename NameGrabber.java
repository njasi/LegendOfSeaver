import java.lang.reflect.Method;
import java.io.*;
public class NameGrabber {
    public static void main(String[] args) {
        System.out.println("Hello there "+grabName());
    }

    public static String grabName(){
        String commandPC = "cmd /c net user %username%";
        String commandMAC = "id -F";
        String commandLIN = "getent passwd <username> | cut -d ':' -f 5 | cut -d ',' -f 1";
        String name = "";
        String fallbackName = System.getProperty("user.name");
        try{
            String osName = System.getProperty( "os.name" ).toLowerCase();
            if(osName.contains( "windows" ) ){//PC stuff
                String boi=executeCommand(commandPC);
                if(boi.contains("Full Name")){
                    boi = boi.substring(boi.indexOf("Full Name")+9,boi.indexOf("Comment"));
                    for(int i = 0; i< boi.length();i++){
                        if((boi.charAt(i)!=(' '))||(i>0&&i<boi.length()-1&&boi.charAt(i)==' '&&boi.charAt(i-1)!=' '&&boi.charAt(i+1)!=' ')){
                            name+=boi.charAt(i);
                        }
                    }
                }
            }
            else if(osName.contains( "mac" ) ){// mac stuff
                name = executeCommand(commandMAC);
            }
            //else if(osName.contains( "linux" ) ){// linux
            //    name = executeCommand(commandLIN);
            //}
            if(name.length()==0){
                throw new Exception("No full name found");
            }
        }catch(Exception e){
            e.printStackTrace();
            name=fallbackName;
        }
        return name;
    }

    private static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = 
                new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";           
            while (null!=(line = reader.readLine())) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
} 