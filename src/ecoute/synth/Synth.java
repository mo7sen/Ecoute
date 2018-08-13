package ecoute.synth;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.sound.midi.*;
import java.util.ArrayList;


public class Synth {
    int instrument = 40;
    int velocity = 600;
    ArrayList<KeyCode> keyCodes= new ArrayList();

    public Synth(Scene scene){

        try {
            Synthesizer syn = MidiSystem.getSynthesizer();
            syn.open();

            final MidiChannel[] mc = syn.getChannels();
            syn.loadAllInstruments(syn.getDefaultSoundbank());
            Instrument[] instr = syn.getAvailableInstruments();

            for (Instrument ins:instr
                 ) {
                System.out.println(ins);
            }
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
}

