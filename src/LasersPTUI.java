import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Jake Bashaw on 4/10/2016.
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
        String[] pc = command.split(" ");
        switch (pc[0].toLowerCase().charAt(0))
        {
            case 'h':
                help();
                break;
            case 'a':
                if(pc.length == 3)
                {
                    add(Integer.parseInt(pc[1]), Integer.parseInt(pc[2]));
                }
                else
                {
                    //Throw some error
                }
                break;
            case 'd':
                toString();
                break;
            case 'r':
                if(pc.length == 3)
                {
                    remove(Integer.parseInt(pc[1]), Integer.parseInt(pc[2]));
                }
                else
                {
                    //Throw some error
                }
                break;
            case 'v':
                verify();
                break;
            case 'q':
                running = false;
                break;
        }
    }

    public void add(int r, int c)
    {
        if(b[r][c] == "." || b[r][c] == "*")
        {
            b[r][c] = "L";
            addLaserBeam(r, c);
        }
        else
        {
            //Throw some error
        }
    }

    public void help()
    {
        System.out.println("    a|add r c: Add laser to (r,c)\n" +
                "    d|display: Display safe\n" +
                "    h|help: Print this help message\n" +
                "    q|quit: Exit program\n" +
                "    r|remove r c: Remove laser from (r,c)\n" +
                "    v|verify: Verify safe correctness");
    }

    public void remove(int r, int c)
    {
        if(b[r][c] == "L" || b[r][c] == "*")
        {
            b[r][c] = ".";
            if(r > 0)
            {
                for(int row = r; r > -1; r--)
                {
                    if(!b[row][c].equals("X") && !b[row][c].matches("[0-9]") && !b[row][c].equals("*"))
                    {
                        b[row][c] = ".";
                    }
                }
            }
            if(r < rsize)
            {
                for(int row = r; r < rsize; r++)
                {
                    if(!b[row][c].equals("X") && !b[row][c].matches("[0-9]") && !b[row][c].equals("*"))
                    {
                        b[row][c] = ".";
                    }
                }
            }
            if(c > 0)
            {
                for(int col = c; c > -1; c--)
                {
                    if(!b[r][col].equals("X") && !b[r][col].matches("[0-9]") && !b[r][col].equals("*"))
                    {
                        b[r][col] = ".";
                    }
                }
            }
            if(r < csize)
            {
                for(int col = c; c < csize; c++)
                {
                    if(!b[r][col].equals("X") && !b[r][col].matches("[0-9]") && !b[r][col].equals("*"))
                    {
                        b[r][col] = ".";
                    }
                }
            }
            for (int row=0; row < b.length; row++)
            {
                for (int col = 0; col < b[row].length; col++)
                {
                    if(b[row][col] == " L")
                    {
                        addLaserBeam(row, col);
                    }
                }
            }
        }
        else
        {
            //Throw some error
        }
    }

    public void verify()
    {
        for (int row = 0; row < b.length; row++)
        {
            for (int col = 0; col < b[row].length; col++)
            {

            }
        }
        System.out.println("Safe is fully verified!");
    }

    public void addLaserBeam(int r, int c)
    {
        if(r > 0)
        {
            for(int row = r; r > -1; r--)
            {
                if(!b[row][c].equals("X") && !b[row][c].matches("[0-9]") && !b[row][c].equals("*"))
                {
                    b[row][c] = "*";
                }
            }
        }
        if(r < rsize)
        {
            for(int row = r; r < rsize; r++)
            {
                if(!b[row][c].equals("X") && !b[row][c].matches("[0-9]") && !b[row][c].equals("*"))
                {
                    b[row][c] = "*";
                }
            }
        }
        if(c > 0)
        {
            for(int col = c; c > -1; c--)
            {
                if(!b[r][col].equals("X") && !b[r][col].matches("[0-9]") && !b[r][col].equals("*"))
                {
                    b[r][col] = "*";
                }
            }
        }
        if(r < csize)
        {
            for(int col = c; c < csize; c++)
            {
                if(!b[r][col].equals("X") && !b[r][col].matches("[0-9]") && !b[r][col].equals("*"))
                {
                    b[r][col] = "*";
                }
            }
        }
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
