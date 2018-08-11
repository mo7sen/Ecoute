package files;

import ecoute.gui.ControlBar;

import java.io.*;
import java.util.ArrayList;

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

    public static void Save(File file){
        FileOutputStream fos;
        ObjectOutputStream oos;

        //gets number of columns
        int rowNum = ControlBar
                .soundPlayer
                .sounds
                .size()-1;

        //gets number of rows
        int columnNum = ControlBar
                .soundPlayer
                .sounds
                .get(1)
                .lista
                .size()-1;


        if (file != null) {
            try {
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.writeInt(columnNum);
                oos.writeInt(rowNum);
                System.out.println(columnNum);
                System.out.println(rowNum);

                int temp = 0;
                for(int i = 1;i <rowNum;i++){
                    for(int j = 1;j <columnNum;j++) {
                        oos.writeBoolean(ControlBar.soundPlayer.sounds.get(i).lista.get(j));
                        System.out.println(ControlBar.soundPlayer.sounds.get(i).lista.get(j));
                    }
                }
                fos.close();
                oos.close();

                throw new IOException();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }


    }
}
