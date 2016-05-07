package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.*;

/**
 * The main class that implements the JavaFX UI.   This class represents
 * the view/controller portion of the UI.  It is connected to the model
 * and receives updates from it.
 *
 * @author Sean Strout @ RIT CS
 * @author Jake Bashaw, Oscar Onyeke
 */
public class LasersGUI extends Application implements Observer
{
    /**
     * The UI's connection to the model
     */
    private LasersModel model;

    /**
     * Represents toggling of a button
     */
    private static boolean status = true;

    /**
     * Where the GUI object will be placed
     */
    BorderPane mainPane = new BorderPane();

    @Override
    public void init() throws Exception
    {
        // the init method is run before start.  the file name is extracted
        // here and then the model is created.
        try
        {
            Parameters params = getParameters();
            String filename = params.getRaw().get(0);
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
     * A private utility function for setting the background of a button to
     * an image in the resources subdirectory.
     *
     * @param button the button control
     * @param bgImgName the name of the image file
     */
    private void setButtonBackground(Button button, String bgImgName)
    {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image( getClass().getResource("resources/" + bgImgName).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        button.setBackground(background);
    }

    /**
     * This is a private demo method that shows how to create a button
     * and attach a foreground image with a background image that
     * toggles from yellow to red each time it is pressed.
     */
    private Button createButton()
    {
        Button button = new Button();
        Image laserImg = new Image(getClass().getResourceAsStream("resources/laser.png"));
        ImageView laserIcon = new ImageView(laserImg);
        button.setGraphic(laserIcon);
        setButtonBackground(button, "yellow.png");
        button.setOnAction(e -> {
            if (!status) {
                setButtonBackground(button, "yellow.png");
            } else {
                setButtonBackground(button, "red.png");
            }
            status = !status;
        });
        return button;
    }

    /**
     * Initializes a GUI
     *
     * @param stage the stage to add UI components into
     */
    private void init(Stage stage)
    {
        mainPane.setPrefHeight(600);
        mainPane.setPrefWidth(400);
        mainPane.setTop(new Label("NULL"));
        mainPane.setCenter(grid());
        mainPane.setBottom(controls());
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        init(primaryStage);
        primaryStage.setTitle("Lasers");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Updates the GUI after an action is performed
     *
     * @param o Not uses
     * @param arg Not used
     */
    @Override
    public void update(Observable o, Object arg)
    {
        // TODO
    }

    /**
     * Creates the grid that represents the safe
     */
    public Pane grid()
    {
        GridPane holder = new GridPane();
        holder.setVgap(10);
        holder.setHgap(10);
        holder.setPadding(new Insets(0,50,0,50));
        for(int r = 0; r < model.getRSize(); r++)
        {
            for(int c = 0; c < model.getCSize(); c++)
            {
                Button button = createButton();
                holder.add(button, c, r);
            }
        }
        return holder;
    }

    /**
     * Creates the buttons that control the GUI
     */
    public Pane controls()
    {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(25,25,25,0));
        hbox.setSpacing(16);

        ArrayList<Button> options = new ArrayList<>();

        Button check = new Button("Check");
        check.setPrefSize(80, 20);
        check.setOnMouseClicked(e -> {
            // TODO
        });
        options.add(check);

        Button hint = new Button("Hint");
        hint.setPrefSize(80, 20);
        //hint.setOnMouseClicked(e ->  );
        options.add(hint);

        Button solve = new Button("Solve");
        solve.setPrefSize(80, 20);
        //solve.setOnMouseClicked(e -> );
        options.add(solve);

        Button restart = new Button("Restart");
        restart.setPrefSize(80, 20);
        //restart.setOnMouseClicked(e -> );
        options.add(restart);

        Button load = new Button("Load");
        load.setPrefSize(80, 20);
        //load.setOnMouseClicked(e -> );
        options.add(load);

        for (int i=0; i< options.size(); i++)
        {
            HBox.setMargin(options.get(i), new Insets(0, 0, 0, 8));
            hbox.getChildren().add(options.get(i));
        }
        return hbox;
    }
}
