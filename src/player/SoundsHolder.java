package player;

import ecoute.gui.ControlBar;
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
    public IntegerProperty bpm = new SimpleIntegerProperty();
    public IntegerProperty volume = new SimpleIntegerProperty();
    int colNumber = ecoute.Ecoute.colNumber;
    Timer timer;
    URL kick = null,
            snare = null,
            clap = null,
            closedHat = null;

    public SoundsHolder(){
        bpm.bind(ControlBar.bpmSlider.valueProperty());
        volume.bind(ControlBar.volSlider.valueProperty());
        
        initSounds();
    }

    public void initSounds(){

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

        sounds.add(null);
        
        if(kick!=null) {
            Sound hopaz = new Sound(kick, colNumber);
            sounds.add(hopaz);
        }
        if(closedHat!=null) {
            Sound hopaz1 = new Sound(closedHat, colNumber);
            sounds.add(hopaz1);
        }
        if(snare!=null) {
            Sound hopaz2 = new Sound(snare, colNumber);
            sounds.add(hopaz2);
        }
        if(clap!=null) {
            Sound hopaz3 = new Sound(clap, colNumber);
            sounds.add(hopaz3);
        }
    }


    public void play()  {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int i = 1;
            @Override
            public void run() {
                    if(i > colNumber){
                        i=1;
                    }
                    for (Sound hopa :
                            sounds) {
                        if(hopa != null)
                            hopa.play(i,(double) getVolume()/100);
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
            if(hopa != null)
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
        this.volume.setValue(volume);
    }
}
