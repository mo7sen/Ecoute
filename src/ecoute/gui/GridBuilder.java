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
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GridBuilder extends GridPane 
{
    Color buttonDefault = new Color((double)40/255,(double) 30/255,(double) 20/255, 1);
    Color buttonActive = Color.NAVAJOWHITE;
    FileChooser fileChooser = new FileChooser(); // Creates a new FileChooser
    FileChooser.ExtensionFilter extFil = new FileChooser.ExtensionFilter("Ecoute Drum Sequence ", "*.eds");//InEdited: Change that pls
    String buttonStyle = "-fx-background-color:cornsilk;"
                       + "-fx-text-fill:#403020;"
                       + "-fx-font-weight:bold;"
                       + "-fx-font-size:20px;";
    String labelStyle = "-fx-background-color:cornsilk;"
                      + "-fx-text-fill:#403020;"
                      + "-fx-font-weight:bold;"
                      + "-fx-font-size:14px;";
    /**
     * @return VBox containing the whole grid and buttons for adding extra
     *         columns and rows.
     */
    public VBox build()
    {   
        this.setPadding(new Insets(15));
        this.fillTheVoid();
        this.setBackground(Background.EMPTY);
        
        AnchorPane gridAnchor = new AnchorPane(this); //Only present for the possibility of adding a pointer
        gridAnchor.setBackground(Background.EMPTY);
        
        
        ScrollPane gridScrollable = new ScrollPane(gridAnchor);
        gridScrollable.setBackground(new Background(new BackgroundFill(Color.VIOLET, CornerRadii.EMPTY, Insets.EMPTY)));
        gridScrollable.setStyle("-fx-background:transparent;"
                              + "-fx-background-color:rgba(255, 248, 220, 0.9);"
        );
//        gridScrollable.setBackground(new Background(new BackgroundFill(Color.MAROON, CornerRadii.EMPTY, Insets.EMPTY)));
        gridScrollable.setFitToHeight(true);
        gridScrollable.setFitToWidth(true);
        gridScrollable.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gridScrollable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        
        Button addBeatBtn = new Button("Add Beat");
        Button addRowBtn = new Button("Add Row");
        addBeatBtn.setStyle(buttonStyle);
        addRowBtn.setStyle(buttonStyle);
        
        
        addBeatBtn.setOnAction((event) -> {
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
                        this.addRow();
                        soundPlayer.samplePaths.add(newSamplePath);
                        soundPlayer.sampleList.add(new Sound(newSamplePath));
                        this.updateLabels();
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
        saveBtn.setStyle(buttonStyle);
        loadBtn.setStyle(buttonStyle);


        saveBtn.setOnAction((event) -> {
            //InEdited: Change the extension to a more suitable one
            if(!fileChooser.getExtensionFilters().contains(extFil))
                fileChooser.getExtensionFilters().add(extFil);
            File fileToSave = fileChooser.showSaveDialog(Ecoute.stage);
            Save.saveSequence(fileToSave);

        });
        
        loadBtn.setOnAction((event) -> {
            if(!fileChooser.getExtensionFilters().contains(extFil))
                fileChooser.getExtensionFilters().add(extFil);
            File fileToLoad = fileChooser.showOpenDialog(Ecoute.stage);
            try {
                Load.loadSequence(fileToLoad);
            } catch (URISyntaxException ex) {
                Logger.getLogger(GridBuilder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GridBuilder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(GridBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Finalizing the gridButtons
        FlowPane editGridBtns = new FlowPane(addRowBtn, addBeatBtn);
        editGridBtns.setAlignment(Pos.BASELINE_RIGHT);
        FlowPane saveLoad = new FlowPane(saveBtn, loadBtn);
        BorderPane gridButtons = new BorderPane(null, null, editGridBtns, null, saveLoad);
        
        //Add all previous nodes into a final VBox for a convenient vertical layout
//        FlowPane gridFlow = new FlowPane(gridScrollable);
//        gridFlow.setAlignment(Pos.CENTER);
        VBox resultingNode = new VBox();
        
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
    }
    
    public void addBeat()
    {
        addColumn();
        addColumn();
        addColumn();
        addColumn();
    }
    
    //Adding an extra row to the Grid for more Samples to be played
    //simultaneously
    public void addRow()
    {
        ecoute.Ecoute.rowNumber++;
        for(int c = 1; c <= ecoute.Ecoute.colNumber; c++)
            this.add(new MusicButton(ecoute.Ecoute.buttonSize, buttonDefault, buttonActive, c, ecoute.Ecoute.rowNumber), c, ecoute.Ecoute.rowNumber);
    }
    
    
    private void fillTheVoid()
    {
        for(int c = 1; c <= ecoute.Ecoute.colNumber; c++)
            for(int r = 1; r <= ecoute.Ecoute.rowNumber; r++)
                this.add(new MusicButton(ecoute.Ecoute.buttonSize, buttonDefault, buttonActive, c, r), c, r);
        this.updateLabels();
    }
    
    public void updateLabels()
    {
        for(int r = 1; r <= ecoute.Ecoute.rowNumber; r++)
        {
            Label label = new Label(ControlBar.soundPlayer.samplePaths.get(r - 1).getFile().substring(ControlBar.soundPlayer.samplePaths.get(r-1).getFile().lastIndexOf("/") + 1, ControlBar.soundPlayer.samplePaths.get(r-1).getFile().lastIndexOf(".")));
            label.setPadding(new Insets(5));
            label.setStyle(labelStyle);
            
            this.add(
                    label,
                    0,
                    r);
        }
    }
    
    public void reset()
    {
        ecoute.Ecoute.rowNumber = ecoute.Ecoute.rowNumberInit;
        ecoute.Ecoute.colNumber = ecoute.Ecoute.colNumberInit;
        this.getChildren().clear();
        this.fillTheVoid();
    }
    
    public void hardReset()
    {
        ecoute.Ecoute.rowNumber = ecoute.Ecoute.rowNumberInit;
        ecoute.Ecoute.colNumber = ecoute.Ecoute.colNumberInit;
        this.getChildren().clear();
    }
    
    private void clear()
    {
        this.getChildren().forEach((t) -> {
            if(t instanceof MusicButton && ((MusicButton) t).active)
                ((MusicButton) t).trigger();
        });
    }
    
    public void updateButtons()
    {
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
