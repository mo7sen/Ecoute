package player;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    AudioInputStream ais;
    Clip clip;
    URL url;
    FloatControl volControl;
    public ArrayList<Boolean> lista = new ArrayList<>();

    public Sound(URL url, int colNumber){
        this.url = url;
        try {
            this.ais = AudioSystem.getAudioInputStream(url.toURI().toURL());
        } catch (UnsupportedAudioFileException | IOException | URISyntaxException e) {
        }

        for(int i = 0; i <= colNumber; i++){
            lista.add(false);
        }
    }


    public void play(int beat,double volume){
        if(lista.get(beat)){
            try {
                this.ais = AudioSystem.getAudioInputStream(this.url.toURI().toURL());
                clip = AudioSystem.getClip();
                clip.open(this.ais);
                volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volControl.setValue((float)(20f * (float) Math.log10(volume)));
                clip.start();
            } catch (IOException | UnsupportedAudioFileException | URISyntaxException | LineUnavailableException e) {
            }

        }
    }
}
