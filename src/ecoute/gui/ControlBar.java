package ecoute.gui;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import player.SoundsHolder;

public class ControlBar
{
    static int          minBPM = 33,    //InEdited:
                        maxBPM = 200,
                        sliderRadius = 40;   //      Change BPM values to more convenient ones
    static Button       playBtn = new Button("\u23F5"),
                        stopBtn = new Button("\u23F9");
    static Label        bpmLabel = new Label(),
                        volLabel = new Label();
    
    public static RadSlider     bpmSlider = new RadSlider(minBPM, maxBPM, "Bpm", new Color((double)30/255,(double) 20/255,(double) 10/255, 1), sliderRadius, sliderRadius, sliderRadius*0.6),
                                volSlider = new RadSlider(0, 100, "%", new Color((double)30/255,(double) 20/255,(double) 10/255, 1), sliderRadius, sliderRadius, sliderRadius*0.6);
    
    public static SoundsHolder soundPlayer; 
    
    public static ComboBox synthSounds;
    
    public static BorderPane build() throws URISyntaxException, MalformedURLException
    {
        soundPlayer = new SoundsHolder();
        
        
        
        synthSounds = new ComboBox();
        synthSounds.setBackground(Background.EMPTY);
        synthSounds.setStyle("-fx-text-fill:brown;");
        
        HBox radSliders = new HBox(bpmSlider, volSlider);
        bpmSlider.setLayoutX(-3 * sliderRadius);
        FlowPane controlPane = new FlowPane(playBtn, stopBtn);
        BorderPane pane = new BorderPane(synthSounds, null, radSliders, null, controlPane);
//        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        radSliders.setSpacing(50);
        pane.setPadding(new Insets(15));
        
        //Set up functionality of the Play button
        playBtn.setDisable(false);
        playBtn.setBackground(Background.EMPTY);
        playBtn.setFont(Font.font(40));
        playBtn.setTextFill(Color.GREEN);
        playBtn.setOnAction((event) -> 
        {
            playBtn.setDisable(true);
            stopBtn.setDisable(false);
            
            //InEdited: Play button pressed
            if(soundPlayer.isAlive())
                soundPlayer.resume();
            else
                soundPlayer.start();
        });
        //EndOf: Set up functionality of the Play button
        
        //Set up functionality of the Stop button
        stopBtn.setDisable(true);
        stopBtn.setBackground(Background.EMPTY);
        stopBtn.setFont(Font.font(40));
        stopBtn.setTextFill(Color.RED);
        stopBtn.setOnAction((event) -> 
        {
            playBtn.setDisable(false);
            stopBtn.setDisable(true);
            
            //InEdited: Stop button pressed
            soundPlayer.end();
            
        });
        //EndOf: Set up functionality of the Stop button
        
        
        return pane;
    }
}