package ptui;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import model.LasersModel;

/**
 * This class represents the view portion of the plain text UI.  It
 * is initialized first, followed by the controller (ControllerPTUI).
 * You should create the model here, and then implement the update method.
 *
 * @author Sean Strout @ RIT CS
 * @author Jake Bashaw, Oscar Onyeke
 */
public class LasersPTUI implements Observer
{
    /** The UI's connection to the model */
    private LasersModel model;

    /**
     * Construct the PTUI.  Create the model and initialize the view.
     *
     * @param filename the safe file name
     * @throws FileNotFoundException if file not found
     */
    public LasersPTUI(String filename) throws FileNotFoundException
    {
        try
        {
            this.model = new LasersModel(filename);
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println(fnfe.getMessage());
            System.exit(-1);
        }
        this.model.addObserver(this);
    }

    /**
     * Returns the model
     */
    public LasersModel getModel()
    {
        return this.model;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        String s = "  ";
        for (int i = 0; i < model.getCSize(); i++)
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
        for (int i = 0; i < (2*model.getCSize()) - 1; i++)
        {
            s += "-";
        }
        s += "\n";
        for (int i = 0; i < model.getRSize(); i++)
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
            for(int j = 0; j < model.getCSize(); j++)
            {
                s += model.getBoard()[i][j];
                if( j < model.getCSize()-1)
                {
                    s+= " ";
                }
            }
            if(i < model.getRSize()-1)
            {
                s += "\n";
            }
        }
        System.out.println(s);
    }
}
