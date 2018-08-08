package ecoute.gui;



import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static ecoute.gui.ControlBar.sounds;

public class MusicButton extends Rectangle
{
    int sideLength;
    int colIndex, rowIndex;
    double borderRatio = 0.1;
    Color defaultColor, activeColor, defaultBorderColor, activeBorderColor;
    boolean active = false;
    
    MusicButton(int sideLength, Color defaultColor, Color activeColor, int colIndex, int rowIndex)
    {
        super();
        
        this.colIndex = colIndex;
        this.rowIndex = rowIndex;
        
        //Setting up shape properties
        this.sideLength = sideLength;
        this.defaultColor = defaultColor;
        this.activeColor = activeColor;
        this.defaultBorderColor = defaultColor.darker();
        this.activeBorderColor = activeColor.darker();
        
        this.setWidth(sideLength * (1-borderRatio));
        this.setHeight(sideLength * (1-borderRatio));
        
        this.setArcHeight(sideLength/2);
        this.setArcWidth(sideLength/2);
        this.setFill(defaultColor);
        this.setStroke(defaultBorderColor);
        this.setStrokeWidth(sideLength * borderRatio);
        //EndOf: Setting up shape properties
        
        //Handling The Clicking Event
        //InEdited: Every single Button knows the index of the boolean to change
        //          in your array. Have Fun!!
        this.setOnMouseClicked((event) -> {
            if(!active)
            {
                this.setFill(activeColor);
                this.setStroke(activeBorderColor);

                //this line sets the boolean array in position of column index inside the array of sounds
                sounds.sounds.get(rowIndex).lista.set(colIndex,true);
            }
            else
            {
                this.setFill(defaultColor);
                this.setStroke(defaultBorderColor);
                //this line does the opposite
                sounds.sounds.get(rowIndex).lista.set(colIndex,false);
            }
            active = !active;
        });
        //EndOf: Handling The Clicking Event
    }


    public int getColIndex() {
        return colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }
}
