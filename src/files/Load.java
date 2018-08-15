package files;

import ecoute.gui.ControlBar;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.sound.sampled.UnsupportedAudioFileException;
import player.SoundsHolder;

public class Load {

    public static void loadSequence(File file) throws URISyntaxException, MalformedURLException, UnsupportedAudioFileException{
        if(file == null)
            return;
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            ControlBar.soundPlayer = new SoundsHolder();

            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            int colNum = ois.read();
            int rowNum = ois.read();

            ecoute.Ecoute.colNumberInit = colNum;
            ecoute.Ecoute.rowNumberInit = rowNum;

            for(int i = ecoute.Ecoute.defaultSamples; i < rowNum; i++)
                ControlBar.soundPlayer.samplePaths.add((URL)ois.readObject());


            ArrayList<ArrayList<Boolean>> timeMaps = (ArrayList<ArrayList<Boolean>>) ois.readObject();
            
            ois.close();
            fis.close();

            ControlBar.soundPlayer.initSounds();
            
            for(int i = 1; i <= rowNum; i++)
                ControlBar.soundPlayer.sampleList.get(i).timeMap = timeMaps.get(i-1);

            ecoute.Ecoute.grid.reset();
            ecoute.Ecoute.grid.updateButtons();

        } catch (IOException | ClassNotFoundException ex) {
        }
    }
}
    

