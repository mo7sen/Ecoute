package ecoute.gui;

import ecoute.Ecoute;
import java.io.File;
import java.net.URL;

import files.Load;
import files.Save;
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
import player.Sound;

import static ecoute.gui.ControlBar.soundPlayer;
import java.util.Optional;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

public class GridBuilder extends GridPane 
{
    Color buttonDefault = Color.ORCHID;
    Color buttonActive = Color.OLIVEDRAB;
    FileChooser fileChooser = new FileChooser(); // Creates a new FileChooser
    FileChooser.ExtensionFilter extFil = new FileChooser.ExtensionFilter("Ecoute Drum Sequence ", "*.eds");//InEdited: Change that pls
    
    /**
     * 
     * @param columns
     * @param rows
     * @param buttonSize
     * @return VBox containing the whole grid and buttons for adding extra
     *         columns and rows.
     */
    public VBox build()
    {
        
        String[] coreSamples = {"KICK", "CLOSEDHAT", "SNARE", "CLAPS"};
        
        this.setPadding(new Insets(15));
        
        for(int r = 1; r <= ecoute.Ecoute.rowNumber; r++)
        {
            Label label = new Label(coreSamples[r-1]);
            label.setPadding(new Insets(5));
            label.setTextFill(Color.WHITESMOKE);
            label.setStyle("-fx-font-weight:bold;");
            
            
            this.add(label, 0, r);
        }
        
        for(int c = 1; c <= ecoute.Ecoute.colNumber; c++)
            for(int r = 1; r <= ecoute.Ecoute.rowNumber; r++)
                this.add(new MusicButton(ecoute.Ecoute.buttonSize, buttonDefault, buttonActive, c, r), c, r);
        
        AnchorPane gridAnchor = new AnchorPane(this); //Only present for the possibility of adding a pointer
        gridAnchor.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        ScrollPane gridScrollable = new ScrollPane(gridAnchor);
        gridScrollable.setStyle("-fx-background-color:transparent;");
        
        TextInputDialog sampleNameDialog = new TextInputDialog();
        sampleNameDialog.setHeaderText("Enter a name for your sample");
        sampleNameDialog.setGraphic(null);
        
        Button addColumnBtn = new Button("Add Beat");
        Button addRowBtn = new Button("Add Row");
        
        
        
        addColumnBtn.setOnAction((event) -> {
            this.addBeat();
        });
        addRowBtn.setOnAction((event) -> {
            fileChooser.getExtensionFilters().clear();
            File newSample = fileChooser.showOpenDialog(Ecoute.stage);
            try {
                if(newSample != null) //Checks the format of the file
                {
                    if(newSample.getName().endsWith(".wav"))
                    {
                        URL newSamplePath = new URL("file:///"+newSample.getPath());
                        Optional<String> sampleName = sampleNameDialog.showAndWait();
                        if(sampleName.isPresent())
                        {
                            this.addRow(sampleName.get());
                            soundPlayer.sampleList.add(new Sound(newSamplePath));
                        }
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
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        });
        
        //Adding buttons for the Save/Load functionality
        Button saveBtn = new Button("Save");
        Button loadBtn = new Button("Load");
//        saveBtn.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
//        saveBtn.setTextFill(Color.WHITESMOKE);
//        saveBtn.setStyle("-fx-font-weight:bold;");
        
        saveBtn.setOnAction((event) -> {
            //InEdited: Change the extension to a more suitable one
            if(!fileChooser.getExtensionFilters().contains(extFil))
                fileChooser.getExtensionFilters().add(extFil);
            File fileToSave = fileChooser.showSaveDialog(Ecoute.stage);
            Save.saveSequence(fileToSave);
            
            //InEdited:
            //      Do your thing
            //Mo7sen:
            //      Did my thing
            //InEdited:
            //      Fuck your thing

        });
        
        loadBtn.setOnAction((event) -> {
            if(!fileChooser.getExtensionFilters().contains(extFil))
                fileChooser.getExtensionFilters().add(extFil);
            File fileToLoad = fileChooser.showOpenDialog(Ecoute.stage);
            Load.loadSequence(fileToLoad);
            //InEdited:
            //      FU though
            //Mo7sen :
            //      7bb tslm
        });
        
        //Finalizing the gridButtons
        FlowPane editGridBtns = new FlowPane(addRowBtn, addColumnBtn);
        editGridBtns.setAlignment(Pos.BASELINE_RIGHT);
        FlowPane saveLoad = new FlowPane(saveBtn, loadBtn);
        BorderPane gridButtons = new BorderPane(null, null, editGridBtns, null, saveLoad);
        
        //Add all previous nodes into a final VBox for a convenient vertical layout
        VBox resultingNode = new VBox();
        resultingNode.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        resultingNode.getChildren().add(gridButtons);
        resultingNode.getChildren().add(gridScrollable);
        
        return resultingNode;
    }
    
    
    //Adding an extra column to the Grid for longer pieces
    public void addColumn()
    {
        ecoute.Ecoute.colNumber++;
        for(int r = 1; r <= ecoute.Ecoute.rowNumber; r++)
            this.add(new MusicButton(ecoute.Ecoute.buttonSize, buttonDefault, buttonActive, ecoute.Ecoute.colNumber, r), ecoute.Ecoute.colNumber, r);
        soundPlayer.addColumn();
        /*
        InEdited:
                Here goes the code for editing the Array of Booleans to match
                the new dimensions of the grid.
        */
        
    }
    
    public void addBeat()
    {
        addColumn();
        addColumn();
        addColumn();
        addColumn();
    }
    
    //Adding an extra row to the Grid for more Samples to be player 
    //simultaneously
    public void addRow(String sampleName)
    {
        ecoute.Ecoute.rowNumber++;
        
        Label label = new Label(sampleName);
        label.setTextFill(Color.WHITESMOKE);
        label.setStyle("-fx-font-weight:bold;");
        label.setPadding(new Insets(5));
        this.add(label, 0, ecoute.Ecoute.rowNumber);
        for(int c = 1; c <= ecoute.Ecoute.colNumber; c++)
            this.add(new MusicButton(ecoute.Ecoute.buttonSize, buttonDefault, buttonActive, c, ecoute.Ecoute.rowNumber), c, ecoute.Ecoute.rowNumber);
        /*
        InEdited:
                Here goes the code for editing the Array of Booleans to match
                the new dimensions of the grid.
        */
        
    }
    
    public void clear()
    {
        this.getChildren().forEach((t) -> {
            if(t instanceof MusicButton && ((MusicButton) t).active)
                ((MusicButton) t).trigger();
        });
    }
    
    public void updateButtons()
    {
//        for(int c = 1; c <= ecoute.Ecoute.colNumber; c++)
//            for(int r = 1; r<= ecoute.Ecoute.rowNumber; r++)
//                if(ControlBar.soundPlayer.sampleList.get(r).timeMap.get(c) !=)
        this.getChildren().forEach((node) -> {
            if(node instanceof MusicButton && 
                    ((MusicButton)node).active != 
                    ControlBar.soundPlayer.
                            sampleList.get(((MusicButton)node).rowIndex).
                            timeMap.get(((MusicButton)node).colIndex)
                    )
                ((MusicButton)node).trigger();
        });
    }

    
}
