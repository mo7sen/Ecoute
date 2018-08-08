package player;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SoundsHolder {
    public ArrayList<Sound> sounds = new ArrayList<>();
    IntegerProperty bpm = new SimpleIntegerProperty();
    IntegerProperty volume = new SimpleIntegerProperty();
    int colNumber = 16;
    Timer timer;
    URL kick = null,
            snare = null,
            clap = null,
            closedHat = null;

    public SoundsHolder(){
        setBpm(120);
        initSounds();
    }

    private void initSounds(){

        try {
            kick = this.getClass().getClassLoader().getResource("Samples/Kick.wav").toURI().toURL();
            closedHat = this.getClass().getClassLoader().getResource("Samples/ClosedHat.wav").toURI().toURL();
            snare = this.getClass().getClassLoader().getResource("Samples/Snare.wav").toURI().toURL();
            clap = this.getClass().getClassLoader().getResource("Samples/Clap.wav").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        if(kick!=null) {
            Sound hopaz = new Sound(kick);
            sounds.add(hopaz);
        }
        if(closedHat!=null) {
            Sound hopaz1 = new Sound(closedHat);
            sounds.add(hopaz1);
        }
        if(snare!=null) {
            Sound hopaz2 = new Sound(snare);
            sounds.add(hopaz2);
        }
        if(clap!=null) {
            Sound hopaz3 = new Sound(clap);
            sounds.add(hopaz3);
        }
    }


    public void play() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                    if(i>=colNumber){
                        i=0;
                    }
                    for (Sound hopa :
                            sounds) {
                        hopa.play(i,getVolume());
                    }
                    System.out.println("Playing sound on beat : " + i);
                    i++;
            }

        };
        timer.schedule(task,0, (long) ((15d/bpm.get())*1000));
    }

    public void stop(){
        timer.cancel();
    }

    public void addColumn(){
        for(Sound hopa: sounds){
            hopa.lista.add(false);
        }
        colNumber++;
    }

    public int getBpm() {
        return bpm.get();
    }

    public IntegerProperty bpmProperty() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm.set(bpm);
    }

    public int getVolume() {
        return volume.get();
    }

    public IntegerProperty volumeProperty() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume.set(volume);
    }
}
