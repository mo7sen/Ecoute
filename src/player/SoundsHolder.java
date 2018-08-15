package player;

import ecoute.gui.ControlBar;
import java.io.IOException;

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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
        
        this.addDefault();
        
        try {
            initSounds();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run()
    {
        try {
            SoundsHolder.this.play();
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void addDefault() throws URISyntaxException, MalformedURLException
    {
        for(String sample : coreSamples)
            samplePaths.add(this.getClass().getClassLoader().getResource(sample).toURI().toURL());
    }

    
    public void initSounds() throws URISyntaxException, MalformedURLException, UnsupportedAudioFileException, IOException{    
        
        
//        for(Sound sample : sampleList)
//            if(sample != null)
//                sample.destroy();
        
        sampleList.clear();
        
        sampleList.add(null);
        
        
        for(URL samplePath : samplePaths)
            sampleList.add(new Sound(samplePath));
    }


    public void play() throws InterruptedException, IOException, LineUnavailableException  {
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
        }
    }
    
    public void end()
    {
        this.suspend();
    }
    
    public void addRows(String... paths) throws MalformedURLException, URISyntaxException
    {
        if(paths.length != 0)
        {
            for(String path : paths)
                    samplePaths.add(new URL(path));
            try {
                initSounds();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SoundsHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

}
