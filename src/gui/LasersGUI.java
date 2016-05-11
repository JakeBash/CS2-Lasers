package gui;

import backtracking.Backtracker;
import backtracking.Configuration;
import backtracking.SafeConfig;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.stage.Window;
import model.*;

/**
 * The main class that implements the JavaFX UI.   This class represents
 * the view/controller portion of the UI.  It is connected to the model
 * and receives updates from it.
 *
 * @author Sean Strout @ RIT CS
 * @author Jake Bashaw
 * @author Oscar Onyeke
 */
public class LasersGUI extends Application implements Observer
{
    /**
     * The UI's connection to the model
     */
    private LasersModel model;

    /**
     * Where the GUI object will be placed
     */
    private BorderPane mainPane = new BorderPane();

    /**
     *
     */
    private Stage mainStage;

    @Override
    public void init() throws Exception
    {
        // the init method is run before start.  the file name is extracted
        // here and then the model is created.
        Parameters params = getParameters();
        String filename = params.getRaw().get(0);
        this.model = new LasersModel(filename);
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
     * Initializes a GUI
     *
     * @param stage the stage to add UI components into
     */
    private void init(Stage stage)
    {
        mainPane.setTop(status());
        mainPane.setCenter(grid());
        mainPane.setBottom(controls());
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        init(primaryStage);
        primaryStage.setTitle("Lasers");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setResizable(true);
        mainStage = primaryStage;
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
        BorderPane uPane = new BorderPane();
        uPane.setTop(status());
        uPane.setCenter(grid());
        uPane.setBottom(controls());
        Scene uScene = new Scene(uPane);
        mainStage.setScene(uScene);

    }

    /**
     * Creates the status label
     */
    public Pane status()
    {
        GridPane holder = new GridPane();
        Label status = new Label(model.getCurMessage());
        status.setAlignment(Pos.CENTER);
        status.setPadding(new Insets(10,0,10,0));
        holder.add(status,0,0);
        holder.setAlignment(Pos.CENTER);
        return holder;
    }

    /**
     * Creates the grid that represents the safe
     */
    public Pane grid()
    {
        GridPane holder = new GridPane();
        holder.setVgap(10);
        holder.setHgap(10);
        holder.setPadding(new Insets(30,50,30,50));
        holder.setAlignment(Pos.CENTER);
        for(int r = 0; r < model.getRSize(); r++)
        {
            for(int c = 0; c < model.getCSize(); c++)
            {
                String s = model.getBoard()[r][c];
                if (s.matches("[0-9]"))
                {
                    Button b = new Button();
                    if(model.getRVer() == r && model.getCVer() == c)
                    {
                        int row  = r;
                        int col = c;
                        b.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/pillar" + s + ".png"))));
                        setButtonBackground(b, "red.png");
                        model.setRVer(-1);
                        model.setCVer(-1);
                        b.setOnAction(e -> {
                            model.add(row, col);
                            model.announceChange();
                        });
                    }
                    else
                    {
                        int row  = r;
                        int col = c;
                        b.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/pillar" + s + ".png"))));
                        setButtonBackground(b, "white.png");
                        b.setOnAction(e -> {
                            model.add(row, col);
                            model.announceChange();
                        });
                    }
                    holder.add(b, c, r);
                }
                else if(s.equals("X"))
                {
                    int row  = r;
                    int col = c;
                    Button b = new Button();
                    b.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/pillarX.png"))));
                    setButtonBackground(b, "white.png");
                    b.setOnAction(e -> {
                        model.add(row, col);
                        model.announceChange();
                    });
                    holder.add(b, c, r);

                }
                else if(s.equals("."))
                {
                    Button b = createButton(r, c, s);
                    holder.add(b, c, r);
                }
                else if(s.equals("*"))
                {
                    Button b = createButton(r, c, s);
                    holder.add(b, c, r);
                }
                else if(s.equals("L"))
                {
                    Button b = createButton(r, c, s);
                    holder.add(b, c, r);
                }

            }
        }
        return holder;
    }

    /**
     * This creates the clickable buttons in the safe grid.
     */
    private Button createButton(int r, int c, String type)
    {
        Button button = new Button();
        if(type.equals("."))
        {
            if(model.getRVer() == r && model.getCVer() == c)
            {
                button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/red.png"))));
                setButtonBackground(button, "white.png");
                model.setRVer(-1);
                model.setCVer(-1);
            }
            else
            {
                button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/white.png"))));
                setButtonBackground(button, "white.png");
            }
            button.setOnAction(e -> {
                model.add(r,c);
                model.announceChange();
            });
        }
        else if(type.equals("*"))
        {
            button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/beam.png"))));
            setButtonBackground(button, "yellow.png");
            button.setOnAction(e -> {
                model.add(r,c);
                model.announceChange();
            });
        }
        else if(type.equals("L"))
        {
            if(model.getRVer() == r && model.getCVer() == c)
            {
                button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/laser.png"))));
                setButtonBackground(button, "red.png");
                model.setRVer(-1);
                model.setCVer(-1);
            }
            else
            {
                button.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("resources/laser.png"))));
                setButtonBackground(button, "yellow.png");
            }
            button.setOnAction(e -> {
                model.remove(r,c);
                model.announceChange();
            });
        }
        return button;
    }

    /**
     * Creates the buttons that control the GUI
     */
    public Pane controls()
    {
        GridPane holder = new GridPane();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(0,50,15,50));
        hbox.setSpacing(16);


        ArrayList<Button> options = new ArrayList<>();

        Button check = new Button("Check");
        check.setPrefSize(80, 20);
        check.setOnMouseClicked(e -> {
            model.verify();
            model.announceChange();
        });
        options.add(check);

        Button hint = new Button("Hint");
        hint.setPrefSize(80, 20);
        hint.setOnMouseClicked(e -> {
            // TODO
        });
        options.add(hint);

        Button solve = new Button("Solve");
        solve.setPrefSize(80, 20);
        solve.setOnMouseClicked(e -> {
            Configuration init = new SafeConfig(model.getCurFile());
            Backtracker bt = new Backtracker(false);
            Optional<Configuration> sol = bt.solve(init);
            System.out.println(sol);
            for (int row=0; row < model.getRSize(); row++)
            {
                for (int col = 0; col < model.getBoard()[row].length; col++)
                {
                    model.getBoard()[row][col] = sol.get().getBoard()[row][col];
                }
            }
            model.announceChange();
        });
        options.add(solve);

        Button restart = new Button("Restart");
        restart.setPrefSize(80, 20);
        restart.setOnMouseClicked(e -> {
            model = new LasersModel(model.getCurFile());
            model.addObserver(this);
            model.announceChange();
        });
        options.add(restart);

        Button load = new Button("Load");
        load.setPrefSize(80, 20);
        load.setOnMouseClicked(e -> load());
        options.add(load);

        for (int i=0; i< options.size(); i++)
        {
            HBox.setMargin(options.get(i), new Insets(0, 0, 0, 8));
            hbox.getChildren().add(options.get(i));
        }

        holder.add(hbox, 0, 0);
        holder.setAlignment(Pos.CENTER);
        return holder;
    }

    public void load()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File f = fileChooser.showOpenDialog(new Stage());
        if(f != null)
        {
            model = new LasersModel(f.toString());
            model.addObserver(this);
            model.announceChange();
        }
    }
}
