import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by JakeBashaw on 4/10/2016.
 */
public class LasersPTUI
{
    // PRIVATE VARIABLES
    /** The max row and column size */
    private int size;
    /** The board that is being occupied by tents, grass, and trees */
    private String[][] b;
    /** A string array of the row looking values */
    private int rsize;
    private int csize;

    public static void main(String args[]) throws FileNotFoundException
    {String firstcommand = "";
        if(args.length==2){
            firstcommand = args[1];
        }
        LasersPTUI lasersPTUI = new LasersPTUI(args[0]);
        boolean exit = false;
        while (exit){
            if (firstcommand.equals(" ")){

            }
            else {
                System.out.println(">");
                Scanner keyboard = new Scanner(System.in);

                int myint = keyboard.nextInt();
            }
        }
    }

    public LasersPTUI(String filename) throws FileNotFoundException
    {
        Scanner in = new Scanner(new File(filename));
        rsize = in.nextInt();
        csize = in.nextInt();
        in.nextLine();
        b = new String[rsize][csize];
        for (int row=0; row < b.length; row++)
        {
            for (int col = 0; col < b[row].length; col++)
            {
                b[row][col] = in.next();
            }
        }
        in.close();
        System.out.println(toString());
    }
}
