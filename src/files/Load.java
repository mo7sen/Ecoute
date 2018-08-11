package files;

import ecoute.gui.ControlBar;
import player.Sound;

import java.io.*;

public class Load {


    public static void Load(File file){
        System.out.println("Opening file "  + file.getAbsolutePath());
        FileInputStream fis;
        ObjectInputStream ois;
        if (file != null) {
            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);

                int columnNum = ois.readInt();
                int rowNum = ois.readInt();

                ControlBar.soundPlayer.sounds.clear();
                ControlBar.soundPlayer.initSounds();

                if(rowNum>4){
                    for(int i = 4;i<=rowNum;i++) {
                        ControlBar.soundPlayer.sounds.add(null);
                    }
                }
                if(columnNum>16){
                    for(Sound hopa:ControlBar.soundPlayer.sounds){
                        for(int i=16;i<=columnNum;i++){
                            hopa.lista.add(false);
                        }
                    }
                }


                int temp = 0;
                for(int i = 1;i < rowNum;i++){
                    for(int j = 1;j < columnNum;j++)
                        ControlBar.soundPlayer.sounds.get(i).lista.set(j,ois.readBoolean());
                }
                fis.close();
                ois.close();

                throw new IOException();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
