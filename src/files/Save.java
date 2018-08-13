package files;

import ecoute.gui.ControlBar;

import java.io.*;
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
        int rowNum = ControlBar
                .soundPlayer
                .sampleList
                .size() - 1;

        //gets number of rows
        int columnNum = ControlBar
                .soundPlayer
                .sampleList
                .get(1)
                .timeMap
                .size() - 1;


        if (file != null) {
            try {
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.write(columnNum);
                oos.write(rowNum);
                for(Sound sound : ControlBar.soundPlayer.sampleList)
                    if(sound != null)
                        oos.writeObject(sound.timeMap);
                
                oos.close();
                fos.close();
            } catch (IOException | NullPointerException ex) {
                ex.printStackTrace();
            }
        }


    }
}
