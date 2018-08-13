package player;

import ecoute.gui.ControlBar;

import java.io.Serializable;
import javafx.beans.property.IntegerProperty;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;

public class SoundsHolder extends Thread implements Serializable{
    public ArrayList<Sound> sampleList = new ArrayList<Sound>();
    public ArrayList<URL> samplePaths = new ArrayList<URL>();
    String[] coreSamples = new String[] {"Samples/Kick.wav",
                                    "Samples/ClosedHat.wav",
                                    "Samples/Snare.wav",
                                    "Samples/Clap.wav"};
    public IntegerProperty bpm = new SimpleIntegerProperty();
    
    public IntegerProperty volume = new SimpleIntegerProperty();
    

    public SoundsHolder() throws URISyntaxException, MalformedURLException{
        
        bpm.bind(ControlBar.bpmSlider.valueProperty());
        volume.bind(ControlBar.volSlider.valueProperty());
        
        for(String sample : coreSamples)
            samplePaths.add(this.getClass().getClassLoader().getResource(sample).toURI().toURL());
        
        initSounds();
    }
    
    
    @Override
    public void run()
    {
        try {
            SoundsHolder.this.play();
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public void initSounds(String... samples){
        
        
        try {
            if(samples.length > 0)
                for(String sample : samples)
                    samplePaths.add(new URL(sample));
            
            sampleList.clear();
            sampleList.add(null);
            
            for(URL samplePath : samplePaths)
                sampleList.add(
                        new Sound(samplePath)
                );
            
        } catch (MalformedURLException e) {
            System.out.println("Exception while adding samples");
        }

        
    }


    public void play() throws InterruptedException  {
        int i = 1;
        while(true)
        {
            if(i > ecoute.Ecoute.colNumber){
                i=1;
            }
            
            for (Sound sample : sampleList) {
                if(sample != null && sample.timeMap.get(i))
                    sample.play((double) getVolume()/100);
            }
            
            i++;
            
            Thread.sleep((long) ((15d/bpm.get())*1000));
            
            for (Sound sample : sampleList)
                if(sample != null)
                    sample.stop();
        }
    }
    
    public void end()
    {
        this.suspend();
    }
    
    public void addRow(String path) throws MalformedURLException
    {
        initSounds(path);
    }

    

    public void addColumn(){
        for(Sound sample: sampleList){
            if(sample != null)
                sample.timeMap.add(false);
        }
        
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

//    public void initSounds() {
//        initSounds();
//    }
}
