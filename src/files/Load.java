package files;

import ecoute.gui.ControlBar;
import player.Sound;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Load {

    public static void loadSequence(File file){
        System.out.println("Opening file "  + file.getAbsolutePath());
        FileInputStream fis;
        ObjectInputStream ois;
        if (file != null) {
            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                int colNum = ois.read();
                int rowNum = ois.read();

                ControlBar.soundPlayer.sampleList.clear();
                ControlBar.soundPlayer.initSounds();
                
                
                if(colNum>ecoute.Ecoute.colNumber){
                    for(Sound sample:ControlBar.soundPlayer.sampleList){
                        if(sample != null)
                            sample.timeMap = new ArrayList<>(Collections.nCopies(colNum, false));
                        
                    }
                }
                
                for(Sound sound : ControlBar.soundPlayer.sampleList)
                    if(sound != null)
                    {
                        sound.timeMap = (ArrayList<Boolean>) ois.readObject();
                    }
                fis.close();
                ois.close();
                
                ecoute.Ecoute.grid.clear();
                ecoute.Ecoute.grid.updateButtons();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
