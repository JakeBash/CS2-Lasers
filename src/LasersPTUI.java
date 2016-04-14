import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by JakeBashaw on 4/10/2016.
 */
public class LasersPTUI
{
    // PRIVATE VARIABLES
    private String[][] b;
    private int rsize;
    private int csize;
    private static boolean running = true;

    public static void main(String args[]) throws FileNotFoundException
    {
        LasersPTUI ptui = new LasersPTUI("test.txt");
        String fc;
        if(args.length == 2)
        {
            fc = args[1];
        }
        Scanner kb = new Scanner(System.in);
        while(running == true)
        {
            System.out.print("> ");
            String command = kb.next();
            System.out.println();
            ptui.commandPicker(command);
        }
        System.out.println("Program closed");
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

    public void commandPicker(String command)
    {
        
    }

    public void add(String args)
    {

    }

    public void display(String args)
    {

    }

    public void help(String args)
    {

    }

    public void remove(String args)
    {

    }

    public void verify(String args)
    {

    }

    @Override
    public String toString()
    {
        String s = "  ";
        for (int i = 0; i < csize; i++)
        {
            if ( i >= 10)
            {
                s += i%10;
            }
            else
            {
                s += i;
            }
            s += " ";
        }
        s += "\n";
        s += "  ";
        for (int i = 0; i < (2*csize) - 1; i++)
        {
            s += "-";
        }
        s += "\n";
        for (int i = 0; i < rsize; i++)
        {
            if ( i >= 10)
            {
                s += i%10;
            }
            else
            {
                s += i;
            }
            s += "|";
            for(int j = 0; j < csize; j++)
            {
                s += b[i][j];
                if( j < csize-1)
                {
                    s+= " ";
                }
            }
            s += "\n";
        }
        return s;
    }
}
