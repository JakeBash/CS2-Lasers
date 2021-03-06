package backtracking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import model.LasersModel;

/**
 * The class represents a single configuration of a safe.  It is
 * used by the backtracker to generate successors, check for
 * validity, and eventually find the goal.
 *
 * This class is given to you here, but it will undoubtedly need to
 * communicate with the model.  You are free to move it into the model
 * package and/or incorporate it into another class.
 *
 * @author Sean Strout @ RIT CS
 * @author Jake Bashaw
 * @author Oscar Onyeke
 */
public class SafeConfig implements Configuration
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
     * The current file being used
     */
    private String curFile;

    /**
     * The current row for getSuccessors
     */
    private int curRow;

    /**
     * The current column for getSuccessors
     */
    private int curCol;

    /**
     * Returns the current safe configuration
     */
    private String[][] lasers;

    /**
     *
     * Returns the location of lasers being used by a pillar
     */
    private int currentpillar;

    /**
     *
     *  Returns the value of the current pillar we are on.
     */
    public String[][] getBoard()
    {
        return b;
    }

    /**
     * Creates a new safe configuration from a given file
     *
     * @param filename The name of the file that configuration is created from
     */
    public SafeConfig(String filename)
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
        curFile  = filename;
        rsize = in.nextInt();
        csize = in.nextInt();
        curRow = 0;
        curCol = -1;
        in.nextLine();
        int numberofpillars = 0;
        b = new String[rsize][csize];
        for (int row = 0; row < rsize; row++)
        {
            for (int col = 0; col < csize; col++)
            {
                b[row][col] = in.next();
                if (b[row][col].matches("[0-9]")||b[row][col].matches("[0-9]")){
                    numberofpillars+=1;
                }
            }
        }
        lasers = new String[numberofpillars][8];
        for (int row = 0; row < numberofpillars; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                lasers[row][col]=null;
            }}
        currentpillar = 0;
        in.close();
    }

    /**
     * Deep copy constructor for safeConfig
     *
     * @param other The configuration that you are making a deep copy of
     */
    public SafeConfig(SafeConfig other)
    {
        this.curFile = other.curFile;
        this.rsize = other.rsize;
        this.csize = other.csize;
        this.curRow = other.curRow;
        this.curCol = other.curCol;
        this.b = new String[rsize][csize];
        for (int row = 0; row < rsize; row++)
        {
            for (int col = 0; col < csize; col++)
            {
                b[row][col] = other.b[row][col];
            }
        }
        this.lasers = other.lasers;
        this.currentpillar = other.currentpillar;
    }

    /**
     * Helper method to advance the indexes for getSuccessors()
     */
    public void nexSpot()
    {
        curCol++;
        if(curCol == csize)
        {
            curRow++;
            curCol = 0;
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
     * Get the collection of successors from the current one
     */
    @Override
    public Collection<Configuration> getSuccessors()
    {
        ArrayList<Configuration> config = new ArrayList<>();
        nexSpot();
        if(curRow == rsize || curCol == csize)
        {
            return config;
        }
        else if(curRow == rsize-1 && curCol == csize-1)
        {
            SafeConfig newConfig = new SafeConfig(this);
            newConfig.b[curRow][curCol] = "L";
            newConfig.addLaserBeam(curRow, curCol);
            config.add(newConfig);
        }
        else if(b[curRow][curCol].equals("."))
        {
            SafeConfig newConfig = new SafeConfig(this);
            newConfig.b[curRow][curCol] = "L";
            newConfig.addLaserBeam(curRow, curCol);
            config.add(newConfig);
            SafeConfig blankConfig = new SafeConfig(this);
            config.add(blankConfig);
        }
        else if(b[curRow][curCol].equals("4"))
        {
            SafeConfig newConfig = new SafeConfig(this);
            newConfig.b[curRow+1][curCol] = "L";
            newConfig.b[curRow-1][curCol] = "L";
            newConfig.b[curRow][curCol+1] = "L";
            newConfig.b[curRow][curCol-1] = "L";
            newConfig.addLaserBeam(curRow+1, curCol);
            newConfig.addLaserBeam(curRow-1, curCol);
            newConfig.addLaserBeam(curRow, curCol+1);
            newConfig.addLaserBeam(curRow, curCol-1);
            config.add(newConfig);
        }
        else
        {
            SafeConfig newConfig = new SafeConfig(this);
            config.add(newConfig);
        }
        return config;
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
     * Checks a pillar too see if has <= the number of lasers surrounding
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
        return count <= Integer.parseInt(b[r][c]);
    }

    /**
     * Returns true if the current configuration has all valid laser placements,
     * false otherwise
     */
    @Override
    public boolean isValid()
    {
        String point;
        for (int row = 0; row < rsize; row++)
        {
            for (int col = 0; col < csize; col++)
            {
                point = b[row][col];
                if(point.equals("L"))
                {
                    if(!laserVer(row,col))
                    {
                        return false;
                    }
                }
                else if(point.matches("[0-9]"))
                {
                    if(!pillarVer(row, col))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Return true if the current configuration is a goal configuration,
     * false otherwise
     */
    @Override
    public boolean isGoal()
    {
        String point;
        for (int row = 0; row < rsize; row++)
        {
            for (int col = 0; col < csize; col++)
            {
                point = b[row][col];
                if(point.equals("."))
                {
                    return false;
                }
                else if(point.equals("L"))
                {
                    if(!laserVer(row,col))
                    {
                        return false;
                    }
                }
                if(point.matches("[0-9]"))
                {
                    if(!pillarVer(row, col))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
