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
 * @author Jake Bashaw
 * @author Oscar Onyeke
 */
public class LasersModel extends Observable
{
    /**
     * The board that the lasers are placed on
     */
    private String[][] b;

    /**
     * The amount of rows in the board
     */
    private int rsize;

    /**
     * The amount of columns in the board
     */
    private int csize;

    /**
     * The current status message
     */
    private String curMessage;

    /**
     * The current file being used
     */
    private String curFile;

    /**
     * The row of the node that failed to verify
     */
    private int rVer = -1;

    /**
     * The column of the row that failed to verify
     */
    private int cVer = -1;

    /**
     * Creates a board using a given file. Also prints the initial board
     *
     * @param filename The name of the file to be read
     */
    public LasersModel(String filename)
    {
        Scanner in = null;
        File file = new File(filename);
        try
        {
            in = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        curMessage = file.getName()+ " loaded";
        curFile  = filename;
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
     * Returns the current status message
     */
    public String getCurMessage()
    {
        return curMessage;
    }

    /**
     * Returns the current file
     */
    public String getCurFile()
    {
        return curFile;
    }

    /**
     * Returns the row size of the safe
     */
    public int getRSize()
    {
        return rsize;
    }

    /**
     * Sets the row of the bad verification
     *
     * @param num The number that is used to set
     */
    public void setRVer(int num)
    {
        rVer = num;
    }

    /**
     * Sets the column of the bad verification
     *
     * @param num The number that is used to set
     */
    public void setCVer(int num)
    {
        cVer = num;
    }

    /**
     * Returns the column size of the safe
     */
    public int getCSize()
    {
        return csize;
    }

    /**
     * Returns the row of the node that failed to verify
     */
    public int getRVer()
    {
        return rVer;
    }

    /**
     * Returns the column of the node that failed to verify
     */
    public int getCVer()
    {
        return cVer;
    }

    /**
     * Returns the 2D safe configuration
     */
    public String[][] getBoard()
    {
        return b;
    }

    /**
     * Status of the add command is displayed. If the laser was successfully
     * placed, the standard output message, followed by a new line, if the
     * laser could not be placed the prints an error messages. After the status
     * of the add command is displayed, the safe is redisplayed to standard
     * output.
     *
     * @param r The row where the laser is to be added
     * @param c The column where the laser is to be added
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
     * Removes the laser from the safe. If the laser could not be removed, an
     * error message is printed. After the print, the status of the remove
     * command is displayed.
     *
     * @param r The row where the laser is going to be removed
     * @param c The column where the laser is going to be removed
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
     * Removes the laser beams from a specific starting point in the four
     * cardinal directions
     *
     * @param r The row where beam removal begins
     * @param c The column where beam removal begins
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
     * Adds laser beams from a specific starting point in the four cardinal
     * directions
     *
     * @param r The row where beam adding begins
     * @param c The row where column adding begins
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
     * Displays a status message that indicates whether the safe is valid or
     * not
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
                    rVer = row;
                    cVer = col;
                    curMessage = "Error verifying at: (" + row + ", " + col + ")";
                    return;
                }
                else if(point.equals("L"))
                {
                    if(!laserVer(row,col))
                    {
                        rVer = row;
                        cVer = col;
                        curMessage = "Error verifying at: (" + row + ", " + col + ")";
                        return;
                    }
                }
                if(point.matches("[0-9]"))
                {
                    if(!pillarVer(row, col))
                    {
                        rVer = row;
                        cVer = col;
                        curMessage = "Error verifying at: (" + row + ", " + col + ")";
                        return;
                    }
                }
            }
        }
        curMessage = "Safe is fully verified!";
    }

    /**
     * Sees if a laser came in contact with another laser
     *
     * @param r The row where laser verification begins
     * @param c The column where laser verification begins
     */
    public boolean laserVer(int r, int c)
    {
        for(int row = r-1; row > 0; row--)
        {
            if(b[row][c].equals("L"))
            {
                return false;
            }
            else if(b[row][c].equals("*"))
            {
                continue;
            }
            else
            {
                break;
            }
        }
        for(int row = r+1; row < rsize; row++)
        {
            if(b[row][c].equals("L"))
            {
                return false;
            }
            else if(b[row][c].equals("*"))
            {
                continue;
            }
            else
            {
                break;
            }
        }
        for(int col = c-1; col > 0; col--)
        {
            if(b[r][col].equals("L"))
            {
                return false;
            }
            else if(b[r][col].equals("*"))
            {
                continue;
            }
            else
            {
                break;
            }
        }
        for(int col = c+1; col < csize; col++)
        {
            if(b[r][col].equals("L"))
            {
                return false;
            }
            else if(b[r][col].equals("*"))
            {
                continue;
            }
            else
            {
                break;
            }
        }
        return true;
    }

    /**
     * Checks a pillar too see if has the specified number of lasers surrounding
     * it
     *
     * @param r The row where verification begins
     * @param c The column where verification begins
     */
    public boolean pillarVer(int r, int c)
    {
        int count = 0;
        if(r > 0)
        {
            if(b[r-1][c].equals("L"))
            {
                count++;
            }
        }
        if(r < rsize-1)
        {
            if(b[r+1][c].equals("L"))
            {
                count++;
            }
        }
        if(c > 0)
        {
            if(b[r][c-1].equals("L"))
            {
                count++;
            }
        }
        if(c < csize-1)
        {
            if(b[r][c+1].equals("L"))
            {
                count++;
            }
        }
        if(count == Integer.parseInt(b[r][c]))
        {
            return true;
        }
        return false;
    }

    /**
     * Displays the help message to standard output, with no status message
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
