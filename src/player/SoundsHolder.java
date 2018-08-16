package player;

import ecoute.gui.ControlBar;
import java.io.IOException;

import java.io.Serializable;
import javafx.beans.property.IntegerProperty;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundsHolder extends Thread implements Serializable{
    public ArrayList<Sound> sampleList = new ArrayList<>();
    public ArrayList<URL> samplePaths = new ArrayList<>();
    
    String[] coreSamples = new String[] {"Samples/Kick.wav",
                                    "Samples/ClosedHat.wav",
                                    "Samples/Snare.wav",
                                    "Samples/Clap.wav"};
    
    public IntegerProperty bpm = new SimpleIntegerProperty();
    
    public IntegerProperty volume = new SimpleIntegerProperty();
    
    private int pointer = 1;

    public SoundsHolder() throws URISyntaxException, MalformedURLException{
        
        bpm.bind(ControlBar.bpmSlider.valueProperty());
        volume.bind(ControlBar.volSlider.valueProperty());
        
        this.addDefault();
        
        try {
            initSounds();
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run()
    {
        try {
            SoundsHolder.this.play();
        } catch (InterruptedException | IOException | LineUnavailableException ex) {
            Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void addDefault() throws URISyntaxException, MalformedURLException
    {
        for(String sample : coreSamples)
            samplePaths.add(this.getClass().getClassLoader().getResource(sample).toURI().toURL());
    }

    
    public void initSounds() throws URISyntaxException, MalformedURLException, UnsupportedAudioFileException, IOException{
        sampleList.clear();
        sampleList.add(null);
        samplePaths.forEach((samplePath) -> {
            sampleList.add(new Sound(samplePath));
        });
    }


    public void play() throws InterruptedException, IOException, LineUnavailableException  {
        pointer = 1;
        while(true)
        {
            if(pointer > ecoute.Ecoute.colNumber){
                pointer = 1;
            }
            
            for (Sound sample : sampleList) {
                if(sample != null && sample.timeMap.get(pointer))
                    sample.play((double) getVolume()/100);
            }
            
            
                    ecoute.Ecoute.grid.point(pointer);
                
            
            
            pointer++;
            
            Thread.sleep((long) ((15d/bpm.get())*1000));
        }
    }
    
    public void pause()
    {
        this.suspend();
    }
    
    public void end()
    {
        pause();
        pointer = 1;
        ecoute.Ecoute.grid.point(pointer);
    }
    
    public void addRows(String... paths) throws MalformedURLException, URISyntaxException
    {
        if(paths.length != 0)
        {
            for(String path : paths)
                    samplePaths.add(new URL(path));
            try {
                initSounds();
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addColumn(){
        sampleList.stream().filter((sample) -> (sample != null)).forEachOrdered((sample) -> {
            sample.timeMap.add(false);
        });   
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
