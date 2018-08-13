package ecoute;

import ecoute.gui.ControlBar;
import ecoute.gui.GridBuilder;
import ecoute.synth.Synth;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Ecoute extends Application 
{    
    public static int   buttonSize = 60;
    public static int   colNumber = 16,
                        rowNumber = 4;
    
    
    
    public static GridBuilder grid;
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) 
    {
        this.stage = primaryStage;
        
        //Build the Control Area of the application
        BorderPane controlArea = ControlBar.build();
                
        //Build the Grid area of the application
        grid = new GridBuilder();
        VBox gridArea = grid.build();
        
        VBox appArea = new VBox();
        appArea.getChildren().addAll(controlArea, gridArea);
        
        Group root = new Group(appArea);
        
        Scene scene = new Scene(root, Color.BLACK);
        
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ECOUTE PROTOTYPE");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.sizeToScene();
        
        stage.show();
        
        stage.setOnCloseRequest((event) -> {System.exit(0);});
        
        appArea.setMaxSize(stage.getWidth(), stage.getHeight());

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
