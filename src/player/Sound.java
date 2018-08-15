package player;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Serializable{
    Clip clip;
    AudioInputStream ais;
    public URL url;
    FloatControl volControl;
    public ArrayList<Boolean> timeMap = new ArrayList<>();

    public Sound(URL url){
        try {
            this.url = url;
            this.clip = AudioSystem.getClip();
            
            
            
            ais = AudioSystem.getAudioInputStream(url);
            clip.open(ais);
            volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        timeMap = new ArrayList<Boolean>(Collections.nCopies(ecoute.Ecoute.colNumber+1, false));
        
    }
    
    public void destroy() throws UnsupportedAudioFileException, IOException
    {
        this.clip.stop();
        this.clip.flush();
        this.ais.close();
        this.clip.close();
    }


    public void play(double volume) throws IOException, LineUnavailableException{     
        
        clip.setFramePosition(0);
        clip.start();
        
        volControl.setValue((float)(20f * (float) Math.log10(volume)));
    }
    
   

}


