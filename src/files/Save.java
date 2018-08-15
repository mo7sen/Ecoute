package files;

import ecoute.gui.ControlBar;

import java.io.*;
import java.util.ArrayList;
import player.Sound;

public class Save {


    /*
    * The specifications of the save file of extension EDS (Ecoute drum Sequence)
    *
    * The file starts with 2 integers
    * >First integer : number of columns
    * >Second integer : number of sounds there are
    * >number of sounds Boolean arrays of size : number of columns
    *
    *
    *
    *
    * */

    public static void saveSequence(File file){
        FileOutputStream fos;
        ObjectOutputStream oos;
        //gets number of columns
        int rowNum = ecoute.Ecoute.rowNumber;
//                ControlBar
//                .soundPlayer
//                .sampleList
//                .size() - 1;

        //gets number of rows
        int columnNum = ecoute.Ecoute.colNumber;
//                ControlBar
//                .soundPlayer
//                .sampleList
//                .get(1)
//                .timeMap
//                .size() - 1;


        if (file != null) {
            try {
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.write(columnNum);
                oos.write(rowNum);
                
//                oos.writeObject(ControlBar.soundPlayer.samplePaths);
                for(int i = ecoute.Ecoute.defaultSamples ; i < ecoute.Ecoute.rowNumber ; i++)
                    oos.writeObject(ControlBar.soundPlayer.samplePaths.get(i));
                        
                ArrayList<ArrayList<Boolean>> timeMaps = new ArrayList<ArrayList<Boolean>>();
//                timeMaps = new ArrayList<Boolean>[rowNum];
                for(int i = 1; i <= ecoute.Ecoute.rowNumber; i++)
                        timeMaps.add(ControlBar.soundPlayer.sampleList.get(i).timeMap);
                
                oos.writeObject(timeMaps);
                
                oos.close();
                fos.close();
            } catch (IOException | NullPointerException ex) {
                ex.printStackTrace();
            }
        }


    }
}
