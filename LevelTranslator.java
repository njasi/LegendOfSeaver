import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;
/**
 * Write a description of class LevelTranslator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LevelTranslator
{
    public static void main(String[] args){
        Image img,img2;
        try{
            img = ImageIO.read(new File("D:\\Image\\Sample.jpg"));
            img2 = ImageIO.read(new File("D:\\Image\\Sample.jpg"));
            System.out.println(areImagesEqual(img,img2));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static boolean areImagesEqual(Image image1,Image image2){
        int[][] pixels1 = imageToIntArray((BufferedImage)image1);
        int[][] pixels2 = imageToIntArray((BufferedImage)image2);

        return true;
    }

    private static int[][] imageToIntArray(BufferedImage image){
        int w=image.getWidth(null),h=image.getHeight(null);
        int[][] pixels = new int[w][h];
        for( int i = 0; i < w; i++ ){
            for( int j = 0; j < h; j++ ){
                pixels[i][j] = image.getRGB( i, j );
            }
        }
        return pixels;
    }
}
