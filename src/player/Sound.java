package player;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Serializable{
    Clip clip;
//    URL url;
    FloatControl volControl;
    public ArrayList<Boolean> timeMap = new ArrayList<>();

    public Sound(URL url){
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        

        timeMap = new ArrayList<Boolean>(Collections.nCopies(ecoute.Ecoute.colNumber+1, false));
        
    }


    public void play(double volume){
            
                volControl.setValue((float)(20f * (float) Math.log10(volume)));
                
                clip.start();
            

    }
    
    public void stop()
    {
        clip.stop();
        clip.setFramePosition(0);
    }

}


