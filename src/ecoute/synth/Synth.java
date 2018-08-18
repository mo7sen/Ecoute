package ecoute.synth;

import ecoute.gui.ControlBar;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import javax.sound.midi.*;
import javax.swing.event.HyperlinkEvent;


public class Synth {
    int instrument = 1;
    int velocity = 600;
//    ArrayList<KeyCode> keyCodes= new ArrayList();

    public Synth(Scene scene){

        try {
            Synthesizer syn = MidiSystem.getSynthesizer();
            syn.open();

            final MidiChannel[] mc = syn.getChannels();
            syn.loadAllInstruments(syn.getDefaultSoundbank());
            Instrument[] instr = syn.getAvailableInstruments();
            
            
            

            ControlBar.synthSounds.getItems().add("Piano"); //InEdited: Add whatever you need
            ControlBar.synthSounds.getItems().add("Viola"); //InEdited: Add whatever you need
            ControlBar.synthSounds.getItems().add("Bass"); //InEdited: Add whatever you need
            ControlBar.synthSounds.getItems().add("Guitar"); //InEdited: Add whatever you need

            ControlBar.synthSounds.getSelectionModel().selectedIndexProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {

                    System.out.println(ControlBar.synthSounds.getSelectionModel().getSelectedIndex());//} ->  Pick one of these two
                    //ControlBar.synthSounds.getSelectionModel().getSelectedItem(); //} ->
                    
                    //InEdited:
                    //  Add the logic
                    if(ControlBar.synthSounds.getSelectionModel().getSelectedIndex()==0){
                        instrument = 1;
                    }
                    if(ControlBar.synthSounds.getSelectionModel().getSelectedIndex()==1){
                        instrument = 30;
                    }
                    if(ControlBar.synthSounds.getSelectionModel().getSelectedIndex()==2){
                        instrument = 40;
                    }
                    if(ControlBar.synthSounds.getSelectionModel().getSelectedIndex()==3){
                        instrument = 33;
                    }
                    mc[5].programChange(instr[instrument].getPatch().getProgram());
                }
            });

            ControlBar.synthSounds.getSelectionModel().selectFirst();



            syn.loadInstrument(instr[instrument]);
            mc[5].programChange(instr[instrument].getPatch().getProgram());


            
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    
                        
                    switch (event.getCode()) {
                        //Note C
                        case A:{
                            mc[5].noteOn(60, velocity);
                            
                        }
                            break;
                        case K:{
                            mc[5].noteOn(60, velocity);
                        }
                        break;
                        //Note C#
                        case W:{
                            mc[5].noteOn(61, velocity);
                        }
                            break;
                        //Note D
                        case S:{
                            mc[5].noteOn(62, velocity);
                        }
                            break;
                        //Note D#
                        case E:{
                            mc[5].noteOn(63, velocity);
                        }
                            break;
                        //Note E
                        case D:{
                            mc[5].noteOn(64, velocity);
                        }
                            break;
                        //Note F
                        case F:{
                            mc[5].noteOn(65, velocity);
                        }
                            break;
                        //Note F#
                        case T:{
                            mc[5].noteOn(66, velocity);
                        }
                            break;
                        //Note G
                        case G:{
                            mc[5].noteOn(67, velocity);

                        }
                            break;
                        //Note G#
                        case Y:{
                            mc[5].noteOn(68, velocity);

                        }
                            break;
                        //Note A
                        case H:{
                            mc[5].noteOn(69, velocity);

                        }
                            break;
                        //Note A#
                        case U:{
                            mc[5].noteOn(70, velocity);
                        }
                            break;
                        //Note B
                        case J:{
                            mc[5].noteOn(71, velocity);

                        }
                            break;
                    }
                }
            });
            scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        //Note C
                        case A:{
                            mc[5].noteOff(60, velocity);

                        }
                        break;
                        case K:{
                            mc[5].noteOff(60, velocity);
                        }
                        break;
                        //Note C#
                        case W:{
                            mc[5].noteOff(61, velocity);
                        }
                        break;
                        //Note D
                        case S:{
                            mc[5].noteOff(62, velocity);
                        }
                        break;
                        //Note D#
                        case E:{
                            mc[5].noteOff(63, velocity);
                        }
                        break;
                        //Note E
                        case D:{
                            mc[5].noteOff(64, velocity);
                        }
                        break;
                        //Note F
                        case F:{
                            mc[5].noteOff(65, velocity);
                        }
                        break;
                        //Note F#
                        case T:{
                            mc[5].noteOff(66, velocity);
                        }
                        break;
                        //Note G
                        case G:{
                            mc[5].noteOff(67, velocity);

                        }
                        break;
                        //Note G#
                        case Y:{
                            mc[5].noteOff(68, velocity);

                        }
                        break;
                        //Note A
                        case H:{
                            mc[5].noteOff(69, velocity);

                        }
                        break;
                        //Note A#
                        case U:{
                            mc[5].noteOff(70, velocity);
                        }
                        break;
                        //Note B
                        case J:{
                            mc[5].noteOff(71, velocity);

                        }
                        break;
                    }
                }
            });


        }
        catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static class ChangeListenerImpl implements ChangeListener<String> {

        public ChangeListenerImpl() {
        }

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}

