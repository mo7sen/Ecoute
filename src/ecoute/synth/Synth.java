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
    private int instrument = 1;
    private int velocity = 600;
    final int midiChannel = 5;

    private boolean C,Db,D,E,Eb,F,G,Gb,A,Ab,B,Bb =false;
//    ArrayList<KeyCode> keyCodes= new ArrayList();

    public Synth(Scene scene){

        try {
            Synthesizer syn = MidiSystem.getSynthesizer();
            syn.open();

            final MidiChannel[] mc = syn.getChannels();
            syn.loadAllInstruments(syn.getDefaultSoundbank());
            Instrument[] instr = syn.getAvailableInstruments();


            syn.loadInstrument(instr[instrument]);
            mc[midiChannel].programChange(instr[instrument].getPatch().getProgram());


            //=============================
            //     Changing instruments
            //=============================
            ControlBar.synthSounds.getItems().add("Piano"); //InEdited: Add whatever you need
            ControlBar.synthSounds.getItems().add("Violin"); //InEdited: Add whatever you need
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
                        setInstrument(1);
                    }
                    if(ControlBar.synthSounds.getSelectionModel().getSelectedIndex()==1){
                        setInstrument(40);
                    }
                    if(ControlBar.synthSounds.getSelectionModel().getSelectedIndex()==2){
                        setInstrument(33);
                    }
                    if(ControlBar.synthSounds.getSelectionModel().getSelectedIndex()==3){
                        setInstrument(28);
                    }
                    mc[midiChannel].programChange(instr[instrument].getPatch().getProgram());
                }
            });

            ControlBar.synthSounds.getSelectionModel().selectFirst();


           /* for (Instrument hopa:instr
                 ) {
                System.out.println(hopa);
            }*/

            playNote(scene, mc);
            stopNote(scene, mc);


        }
        catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopNote(Scene scene, MidiChannel[] mc) {
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    //Note C
                    case A:{
                        mc[midiChannel].noteOff(60, velocity);
                        C = false;
                    }
                    break;
                    //Note C#
                    case W:{
                        mc[midiChannel].noteOff(61, velocity);
                        Db = false;
                    }
                    break;
                    //Note D
                    case S:{
                        mc[midiChannel].noteOff(62, velocity);
                        D = false;
                    }
                    break;
                    //Note D#
                    case E:{
                        mc[midiChannel].noteOff(63, velocity);
                        Eb = false;
                    }
                    break;
                    //Note E
                    case D:{
                        mc[midiChannel].noteOff(64, velocity);
                        E = false;
                    }
                    break;
                    //Note F
                    case F:{
                        mc[midiChannel].noteOff(65, velocity);
                        F = false;
                    }
                    break;
                    //Note F#
                    case T:{
                        mc[midiChannel].noteOff(66, velocity);
                        Gb = false;
                    }
                    break;
                    //Note G
                    case G:{
                        mc[midiChannel].noteOff(67, velocity);
                        G = false;
                    }
                    break;
                    //Note G#
                    case Y:{
                        mc[midiChannel].noteOff(68, velocity);
                        Ab = false;

                    }
                    break;
                    //Note A
                    case H:{
                        mc[midiChannel].noteOff(69, velocity);
                        A = false;

                    }
                    break;
                    //Note A#
                    case U:{
                        mc[midiChannel].noteOff(70, velocity);
                        Bb = false;
                    }
                    break;
                    //Note B
                    case J:{
                        mc[midiChannel].noteOff(71, velocity);
                        B = false;

                    }
                    break;
                }
            }
        });
    }

    private void playNote(Scene scene, MidiChannel[] mc) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {


                switch (event.getCode()) {
                    //Note C
                    case A:{
                        if(!C) {
                            mc[midiChannel].noteOn(60, velocity);
                            C = true;
                        }

                    }
                        break;
                    //Note C#
                    case W:{
                        if(!Db) {
                            mc[midiChannel].noteOn(61, velocity);
                            Db = true;
                        }
                    }
                        break;
                    //Note D
                    case S:{
                        if(!D) {
                            mc[midiChannel].noteOn(62, velocity);
                            D = true;
                        }
                    }
                        break;
                    //Note D#
                    case E:{
                        if(!Eb) {
                            mc[midiChannel].noteOn(63, velocity);
                            Eb = true;
                        }
                    }
                        break;
                    //Note E
                    case D:{
                        if(!E) {
                            mc[midiChannel].noteOn(64, velocity);
                            E = true;
                        }
                    }
                        break;
                    //Note F
                    case F:{
                        if(!F) {
                            mc[midiChannel].noteOn(65, velocity);
                            F = true;
                        }
                    }
                        break;
                    //Note F#
                    case T:{
                        if(!Gb) {
                            mc[midiChannel].noteOn(66, velocity);
                            Gb = true;
                        }
                    }
                        break;
                    //Note G
                    case G:{
                        if(!G) {
                            mc[midiChannel].noteOn(67, velocity);
                            G = true;
                        }
                    }
                        break;
                    //Note G#
                    case Y:{
                        if(!Ab) {
                            mc[midiChannel].noteOn(68, velocity);
                            Ab = true;
                        }

                    }
                        break;
                    //Note A
                    case H:{
                        if(!A) {
                            mc[midiChannel].noteOn(69, velocity);
                            A = true;
                        }
                    }
                        break;
                    //Note A#
                    case U:{
                        if(!Bb) {
                            mc[midiChannel].noteOn(70, velocity);
                            Bb = true;
                        }
                    }
                        break;
                    //Note B
                    case J:{
                        if(!B) {
                            mc[midiChannel].noteOn(71, velocity);
                            B = true;
                        }
                    }
                        break;
                }
            }
        });
    }


    public int getInstrument() {
        return instrument;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
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

