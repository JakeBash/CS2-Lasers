package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Scanner;

/**
 * This class handles all of the safe backend commands. Adding, removing, and
 * verifying safe configurations is done in this class.
 * @author Sean Strout @ RIT CS
 * @author Jake Bashaw, Oscar Onyeke
 */
public class LasersModel extends Observable
{
    // PRIVATE VARIABLES
    private String[][] b; // The board that the lasers are placed on
    private int rsize; // The amount of rows in the board
    private int csize; // The amount of columns in the board
    private String curMessage;

    /**
     Creates a board using a given file. Also prints the initial board.
     Parameters: The name of the file to be read.
     */
    public LasersModel(String filename) throws FileNotFoundException
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
        announceChange();
    }

    /**
     Status of the add command is displayed. If the laser was successfully
     placed, the standard output message, followed by a new line, if the
     laser could not be placed the prints an error messages. After the status
     of the add command is displayed, the safe is redisplayed to standard
     output.
     Parameters: The row and column where the laser is to be added.
     */
    public void add(int r, int c)
    {
        if(r >= rsize || r < 0 || c >= csize || c < 0)
        {
            curMessage = "Error adding laser at: (" + r + ", " + c + ")";
        }
        else if(!(b[r][c].matches("[0-9]")) && !(b[r][c].equals("X")))
        {
            b[r][c] = "L";
            addLaserBeam(r, c);
            curMessage = "Laser added at: (" + r + ", " + c + ")";
        }
        else
        {
            curMessage = "Error adding laser at: (" + r + ", " + c + ")";
        }
    }

    /**
     Removes the laser from the safe. If the laser could not be removed, an
     error message is printed. After the print, the status of the remove
     command is displayed.
     Parameters: The row and column where the laser is to be added.
     */
    public void remove(int r, int c)
    {
        if(r >= rsize || r < 0 || c >= csize || c < 0)
        {
            curMessage = "Error removing laser at: (" + r + ", " + c + ")";
        }
        else if(b[r][c].equals("L"))
        {
            b[r][c] = ".";
            removeLaserBeam(r, c);
            for (int row=0; row < b.length; row++)
            {
                for (int col = 0; col < b[row].length; col++)
                {
                    if(b[row][col].equals("L"))
                    {
                        addLaserBeam(row, col);
                    }
                }
            }
            curMessage = "Laser removed at: (" + r + ", " + c + ")";
        }
        else
        {
            curMessage = "Error removing laser at: (" + r + ", " + c + ")";
        }
    }

    /**
     Removes the laser beams from a specific starting point in the four
     cardinal directions.
     Parameters: The row and column where the beam removal begins.
     */
    public void removeLaserBeam(int r, int c)
    {
        if(r > 0)
        {
            for(int row = r - 1; row >= 0; row--)
            {
                if(!b[row][c].equals("X") && !b[row][c].matches("[0-9]") && !b[row][c].equals("L"))
                {
                    b[row][c] = ".";
                }
                else
                {
                    break;
                }
            }
        }
        if(r < rsize-1)
        {
            for(int row = r + 1; row < rsize; row++)
            {
                if(!b[row][c].equals("X") && !b[row][c].matches("[0-9]") && !b[row][c].equals("L"))
                {
                    b[row][c] = ".";
                }
                else
                {
                    break;
                }
            }
        }
        if(c > 0)
        {
            for(int col = c - 1; col >= 0; col--)
            {
                if(!b[r][col].equals("X") && !b[r][col].matches("[0-9]") && !b[r][col].equals("L"))
                {
                    b[r][col] = ".";
                }
                else
                {
                    break;
                }
            }
        }
        if(c < csize-1)
        {
            for(int col = c + 1; col < csize; col++)
            {
                if(!b[r][col].equals("X") && !b[r][col].matches("[0-9]") && !b[r][col].equals("L"))
                {
                    b[r][col] = ".";
                }
                else
                {
                    break;
                }
            }
        }
    }

    /**
     Adds laser beams from a specific starting point in the four cardinal
     directions.
     Parameters: The row and column where the beam adding begins.
     */
    public void addLaserBeam(int r, int c)
    {
        if(r > 0)
        {
            for(int row = r - 1; row >= 0; row--)
            {
                if(!b[row][c].equals("X") && !b[row][c].matches("[0-9]") && !b[row][c].equals("L"))
                {
                    b[row][c] = "*";
                }
                else
                {
                    break;
                }
            }
        }
        if(r < rsize-1)
        {
            for(int row = r + 1; row < rsize; row++)
            {
                if(!b[row][c].equals("X") && !b[row][c].matches("[0-9]") && !b[row][c].equals("L"))
                {
                    b[row][c] = "*";
                }
                else
                {
                    break;
                }
            }
        }
        if(c > 0)
        {
            for(int col = c - 1; col >= 0; col--)
            {
                if(!b[r][col].equals("X") && !b[r][col].matches("[0-9]") && !b[r][col].equals("L"))
                {
                    b[r][col] = "*";
                }
                else
                {
                    break;
                }
            }
        }
        if(c < csize-1)
        {
            for(int col = c + 1; col < csize; col++)
            {
                if(!b[r][col].equals("X") && !b[r][col].matches("[0-9]") && !b[r][col].equals("L"))
                {
                    b[r][col] = "*";
                }
                else
                {
                    break;
                }
            }
        }
    }

    /**
     Displays a status message that indicates whether the safe is valid or
     not.
     Parameters: None
     */
    public void verify()
    {
        String point;
        for (int row = 0; row < b.length; row++)
        {
            for (int col = 0; col < b[row].length; col++)
            {
                point = b[row][col];
                if(point.equals("."))
                {
                    curMessage = "Error verifying at: (" + row + ", " + col + ")";
                    return;
                }
                else if(point.equals("L"))
                {
                    if(!laserVer(row,col))
                    {
                        curMessage = "Error verifying at: (" + row + ", " + col + ")";
                        return;
                    }
                }
                if(point.matches("[0-9]"))
                {
                    if(!pillarVer(row, col))
                    {
                        curMessage = "Error verifying at: (" + row + ", " + col + ")";
                        return;
                    }
                }
            }
        }
        curMessage = "Safe is fully verified!";
    }

    /**
     Sees if a laser came in contact with another laser.
     Parameters: The row and column where the laser verification begins.
     */
    public boolean laserVer(int r, int c)
    {
        String [] pillars = new String[] {"1","2","3","4","X"};
        for(int i=r;i<rsize;i++)
        {
            if(this.b[i][c].equals("L")&&i!=r)
            {
                return false;
            }
            else if(Arrays.asList(pillars).contains(this.b[i][c]))
            {
                i=rsize;
            }
        }
        for(int i=r;i>=0;i--)
        {
            if(this.b[i][c].equals("L")&&i!=r)
            {
                return false;
            }
            else if(Arrays.asList(pillars).contains(this.b[i][c]))
            {
                i=-1;
            }
        }
        for(int i=c;i<csize;i++)
        {
            if(this.b[r][i].equals("L")&&i!=c)
            {
                return false;
            }
            else if(Arrays.asList(pillars).contains(this.b[i][c]))
            {
                i=csize;
            }
        }
        for(int i=c;i>=0;i--)
        {
            if(this.b[r][i].equals("L")&&i!=c)
            {
                return false;
            }
            else if(Arrays.asList(pillars).contains(this.b[i][c]))
            {
                i=-1;
            }
        }
        return true;
    }

    /**
     Checks a pillar too see if has the specified number of lasers surrounding
     it.
     Parameters: The row and column where the pillar verification begins.
     */
    public boolean pillarVer(int r, int c)
    {
        int count = 0;
        if(r > 0)
        {
            if(b[r-1][c].equals("L"))
            {
                // Checks above the spot for a laser
                count++;
            }
        }
        if(r < rsize-1)
        {
            if(b[r+1][c].equals("L"))
            {
                // Checks below for a laser
                count++;
            }
        }
        if(c > 0)
        {
            if(b[r][c-1].equals("L"))
            {
                // Checks to the left for a laser
                count++;
            }
        }
        if(c < csize-1)
        {
            if(b[r][c+1].equals("L"))
            {
                // Checks to the right for a laser
                count++;
            }
        }
        if(count == Integer.parseInt(b[r][c]))
        {
            // Checks if the number of laser equals the number on the pillar
            return true;
        }
        return false;
    }

    /**
     Displays the help message to standard output, with no status message
     Parameters: None
     */
    public void help()
    {
        System.out.println("    a|add r c: Add laser to (r,c)\n" +
                "    d|display: Display safe\n" +
                "    h|help: Print this help message\n" +
                "    q|quit: Exit program\n" +
                "    r|remove r c: Remove laser from (r,c)\n" +
                "    v|verify: Verify safe correctness");
    }

    public String getCurMessage()
    {
        return curMessage;
    }

    public int getRSize()
    {
        return rsize;
    }


    public int getCSize()
    {
        return csize;
    }

    public String[][] getBoard()
    {
        return b;
    }
    /**
     * A utility method that indicates the model has changed and
     * notifies observers
     */
    public void announceChange()
    {
        setChanged();
        notifyObservers();
    }
}
