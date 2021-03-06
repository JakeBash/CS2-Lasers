package ptui;

import model.LasersModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class represents the controller portion of the plain text UI.
 * It takes the model from the view (LasersPTUI) so that it can perform
 * the operations that are input in the run method.
 *
 * @author Sean Strout @ RIT CS
 * @author Jake Bashaw
 * @author Oscar Onyeke
 */
public class  ControllerPTUI
{
    /**
     * Boolean to track whether or not the program is running.
     */
    private boolean running;

    /**
     * The UI's connection to the model
     */
    private LasersModel model;

    /**
     * Construct the PTUI.  Create the model and initialize the view.
     *
     * @param model The laser model
     */
    public ControllerPTUI(LasersModel model)
    {
        this.model = model;
        running = true;
    }

    /**
     * Run the main loop.  This is the entry point for the controller
     *
     * @param inputFile The name of the input command file, if specified
     */
    public void run(String inputFile) throws FileNotFoundException
    {
        if (inputFile != null)
        {
            Scanner sc = new Scanner(new File(inputFile));
            while (sc.hasNextLine())
            {
                String command = sc.nextLine();
                System.out.println("> " + command);
                commandPicker(command);
            }
            sc.close();
        }
        Scanner kb = new Scanner(System.in);
        while (running)
        {
            System.out.print("> ");
            String command = kb.nextLine();
            if (command.equals(""))
            {
                command = " ";
            }
            commandPicker(command);
        }
    }

    /**
     * Given a user command, calls the associated function.
     *
     * @param command The command the user typed in, if present.
     */
    public void commandPicker(String command)
    {
        String[] pc = command.split(" ");
        switch (command.toLowerCase().charAt(0))
        {
            case 'h':
                model.help();
                break;
            case 'a':
                if (pc.length == 3)
                {
                    model.add(Integer.parseInt(pc[1]), Integer.parseInt(pc[2]));
                    System.out.println(model.getCurMessage());
                    model.announceChange();
                }
                else
                {
                    System.out.println("Incorrect coordinates");
                }
                break;
            case 'd':
                model.announceChange();
                break;
            case 'r':
                if (pc.length == 3)
                {
                    model.remove(Integer.parseInt(pc[1]), Integer.parseInt(pc[2]));
                    System.out.println(model.getCurMessage());
                    model.announceChange();
                }
                else
                {
                    System.out.println("Incorrect coordinates");
                }
                break;
            case 'v':
                model.verify();
                System.out.println(model.getCurMessage());
                model.announceChange();
                break;
            case 'q':
                running = false;
                break;
            case ' ':
                break;
            default:
                System.out.println("Unrecognized command: " + command);
                break;
        }
    }
}
