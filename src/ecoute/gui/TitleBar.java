package ecoute.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TitleBar 
{
    public static BorderPane build()
    {
        String tileStyle = "-fx-font-size:14px;"
                            + "-fx-font-weight:bold;"
                            + "-fx-text-fill:cornsilk;"
                            + "-fx-background-color:transparent;";
        
        Label title = new Label("Écoute");
        title.setBackground(Background.EMPTY);
        title.setStyle("-fx-text-fill:cornsilk;"
                       + "-fx-font-weight:bold;"
                        + "-fx-font-size:14px");
        
        
        Button closeButton = new Button("\u2715"), 
                maximizeButton = new Button("\uD83D\uDDD6"), 
                minimizeButton = new Button("\uD83D\uDDD5");
        
        closeButton.setStyle(tileStyle);
        minimizeButton.setStyle(tileStyle);
        maximizeButton.setStyle(tileStyle);
        
        
        
        HBox tiles = new HBox(minimizeButton, maximizeButton, closeButton);
        BorderPane titleBar = new BorderPane(title, null, tiles, null, null);
        titleBar.setStyle("-fx-background-color:#403020");
        return titleBar;
    }
}
