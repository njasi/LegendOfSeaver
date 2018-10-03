import java.io.*;

public class Test
{
    public static void main(String[] args)
    {
        while(true){
            File desktop = new File("/Users/nick/Desktop");
            File[] directoryListing = desktop.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    if(child.getName().indexOf("unknown")!=-1&&child.getName().indexOf(".download")!=-1)
                    {   
                        if(deleteDirectory(child)){
                            System.out.println(child.getName()+"\twas deleted successfully");
                        }else{
                            System.out.println(child.getName()+"\twas not deleted successfully");
                        }
                    }
                }
            }
        }
    }

    static public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}