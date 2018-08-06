package ecoute.gui;

import ecoute.Ecoute;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class GridBuilder extends GridPane 
{
    int columnCount, rowCount;
    int defaultButtonSize;
    Color buttonDefault = Color.CORNFLOWERBLUE;
    Color buttonActive = Color.CORAL;
    
    /**
     * 
     * @param columns
     * @param rows
     * @param buttonSize
     * @return VBox containing the whole grid and buttons for adding extra
     *         columns and rows.
     */
    public VBox build(int columns, int rows, int buttonSize)
    {
        this.columnCount = columns;
        this.rowCount = rows;
        this.defaultButtonSize = buttonSize;
        this.setPadding(new Insets(15));
        
        
        for(int c = 0; c < columns; c++)
            for(int r = 0; r < rows; r++)
                this.add(new MusicButton(buttonSize, buttonDefault, buttonActive, c, r), c, r);
        
        AnchorPane gridAnchor = new AnchorPane(this); //Only present for the possibility of adding a pointer
        gridAnchor.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        
        ScrollPane gridScrollable = new ScrollPane(gridAnchor);
        gridScrollable.setStyle("-fx-background-color:transparent;");
        
        Button addColumnBtn = new Button("Add Column");
        Button addRowBtn = new Button("Add Row");
        
        FlowPane gridButtons = new FlowPane(addRowBtn, addColumnBtn);
        gridButtons.setAlignment(Pos.BASELINE_RIGHT);
        
        
        addColumnBtn.setOnAction((event) -> {
            this.addColumn();
        });
        addRowBtn.setOnAction((event) -> {
            FileChooser newSamplePicker = new FileChooser(); // Creates a new FileChooser
            File newSample = newSamplePicker.showOpenDialog(Ecoute.stage);
            try {
                if(newSample.getName().endsWith(".wav")) //Checks the format of the file
                {
                    URL newSamplePath = new URL(newSample.getPath());
                    this.addRow();
                    /*
                    InEdited: Add Logic to add the new sample to the array of samples.
                    */
                }
                else
                {
                    // Alert the user that the chosen file format is not supported
                    Alert headsUp = new Alert(Alert.AlertType.NONE, "Only .wav format is supported.", ButtonType.OK);
                    headsUp.setTitle("Format not supported");
                    headsUp.setResizable(false);
                    headsUp.show();
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(Ecoute.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        //Add all previous nodes into a final VBox for a convenient vertical layout
        VBox resultingNode = new VBox();
        resultingNode.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        resultingNode.getChildren().add(gridButtons);
        resultingNode.getChildren().add(gridScrollable);
        
        return resultingNode;
    }
    
    
    //Adding an extra column to the Grid for longer pieces
    public void addColumn()
    {
        for(int r = 0; r < rowCount; r++)
            this.add(new MusicButton(defaultButtonSize, buttonDefault, buttonActive, columnCount, r), columnCount, r);
        this.columnCount++;
        
        /*
        InEdited:
                Here goes the code for editing the Array of Booleans to match
                the new dimensions of the grid.
        */
        
    }
    
    //Adding an extra row to the Grid for more Samples to be player 
    //simultaneously
    public void addRow()
    {
        for(int c = 0; c < columnCount; c++)
            this.add(new MusicButton(defaultButtonSize, buttonDefault, buttonActive, c, rowCount), c, rowCount);
        this.rowCount++;
        
        /*
        InEdited:
                Here goes the code for editing the Array of Booleans to match
                the new dimensions of the grid.
        */
        
    }
}
