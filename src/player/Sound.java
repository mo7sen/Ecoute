package player;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Sound {
    AudioInputStream ais;
    Clip clip;
    URL url;
    public ArrayList<Boolean> lista = new ArrayList<>();

    public Sound(URL url){
        this.url = url;
        try {
            this.ais = AudioSystem.getAudioInputStream(url.toURI().toURL());
            clip = AudioSystem.getClip();

            clip.open(this.ais);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        for(int i =0;i<16l;i++){
            lista.add(false);
        }
    }


    public void play(int beat,int volume){
        if(lista.get(beat)){
            try {
                this.ais = AudioSystem.getAudioInputStream(this.url.toURI().toURL());
                clip = AudioSystem.getClip();
                clip.open(this.ais);
                clip.start();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
