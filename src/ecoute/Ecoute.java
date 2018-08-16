package ecoute;

import ecoute.gui.ControlBar;
import ecoute.gui.GridBuilder;
import ecoute.synth.Synth;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Ecoute extends Application 
{    
    public static int   buttonSize = 60;
    public static int   colNumber = 16,
                        rowNumber = 4,
                        rowNumberInit = rowNumber,
                        colNumberInit = colNumber,
                        defaultSamples = 4;
    
    public static GridBuilder grid;
    public static Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws URISyntaxException, MalformedURLException 
    {
        this.stage = primaryStage;
        Platform.setImplicitExit(false);
        
        //Build the Control Area of the application
        BorderPane controlArea = ControlBar.build();
        controlArea.setStyle("-fx-background-color:rgba(255,248,220,0.9);");
                
        //Build the Grid area of the application
        grid = new GridBuilder();
        VBox gridArea = grid.build();
        gridArea.setBackground(Background.EMPTY);
        
        Image backgroundImage = new Image(getClass().getClassLoader().getResource("notesBackground.jpg").toURI().toURL().toString());
        
        VBox appArea = new VBox();
        appArea.getChildren().addAll(controlArea, gridArea);
        appArea.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        
        StackPane root = new StackPane(appArea);
        
        Scene scene = new Scene(root);
        
        stage.setTitle("ECOUTE - Music Modelling Software");
        
        stage.setScene(scene);
        stage.sizeToScene();
        
        
        stage.show();
        stage.setOnCloseRequest((event) -> {System.exit(0);});
        
        
        appArea.setMaxSize(stage.getMaxWidth(), stage.getMaxHeight());

        Synth synth = new Synth(scene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
