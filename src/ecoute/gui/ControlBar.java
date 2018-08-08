package ecoute.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import player.SoundsHolder;

public class ControlBar
{
    static int  minBPM = 10,    //InEdited:
                maxBPM = 120;   //      Change BPM values to more convenient ones
    static Button   playBtn = new Button("\u23F5"),
                    pauseBtn = new Button("\u23F8"),
                    stopBtn = new Button("\u23F9");
    static Slider   bpmSlider = new Slider(minBPM, maxBPM, (maxBPM+minBPM)/2),
                    volSlider = new Slider(0, 100, 50);
    static Label    bpmLabel = new Label(),
                    volLabel = new Label();
    public static SoundsHolder sounds;

    public static FlowPane build()
    {
        //Add core nodes to FlowPane
        FlowPane controlPane = new FlowPane(playBtn, pauseBtn, stopBtn, bpmSlider, bpmLabel, volSlider, volLabel);

        //SoundsHolder sounds;
        sounds = new SoundsHolder();

        //Set up functionality of the Play button
        playBtn.setDisable(false);
        playBtn.setOnAction((event) -> 
        {
            playBtn.setDisable(true);
            pauseBtn.setDisable(false);
            stopBtn.setDisable(false);

            sounds.play();
            
            //InEdited: Play button pressed
        });
        //EndOf: Set up functionality of the Play button
        
        //Set up functionality of the Pause button
        pauseBtn.setDisable(true);
        pauseBtn.setOnAction((event) -> 
        {
            playBtn.setDisable(false);
            pauseBtn.setDisable(true);
            
            //InEdited: Pause button pressed
        });
        //EndOf: Set up functionality of the Pause button
        
        //Set up functionality of the Stop button
        stopBtn.setDisable(true);
        stopBtn.setOnAction((event) -> 
        {
            playBtn.setDisable(false);
            pauseBtn.setDisable(true);
            stopBtn.setDisable(true);
            sounds.stop();
            
            //InEdited: Stop button pressed
        });
        //EndOf: Set up functionality of the Stop button
        
        //Set up functionality of the BPM slider
        bpmSlider.setSnapToTicks(true);
        bpmSlider.setMajorTickUnit(1);
        bpmSlider.setMinorTickCount(0);
        bpmSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            //InEdited:
            //      on BPM value change



            //sounds.setBpm((Integer) newValue);
            //this doesnt work and im too lazy to set up a listener and shit idk how to
        });
        //EndOf: Set up functionality of the BPM slider
        
        //Set up functionality of the Volume slider
        volSlider.setSnapToTicks(true);
        volSlider.setMajorTickUnit(1);
        volSlider.setMinorTickCount(0);
        volSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            //InEdited:
            //      on Volume value change


            //sounds.setVolume((Integer) newValue);
            //same here
        });
        //EndOf: Set up functionality of the Volume slider
        
        //Set up functionality of the BPM label
        bpmLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return String.valueOf((int) bpmSlider.getValue());
        }, bpmSlider.valueProperty()));
        bpmLabel.setTextFill(Color.WHITESMOKE);
        //EndOf: Set up functionality of the BPM label
        
        //Set up functionality of the Volume label
        volLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return String.valueOf((int) volSlider.getValue());
        }, volSlider.valueProperty()));
        volLabel.setTextFill(Color.WHITESMOKE);
        //EndOf: Set up functionality of the Volume label
        
        return controlPane;
    }
}
