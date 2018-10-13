package ecoute.gui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class RadSlider extends Pane
{
    private double RX;
    private double RY;
    private double StrokeWidth;
    private static double centerY;
    private static double centerX;

    private StrokeLineCap strokeLineCap = StrokeLineCap.ROUND;
    
    private double MIN_ANGLE = 225;
    private double MAX_ANGLE = -45;
    private double ARC_LENGTH = -270;

    private Arc arc, backgroundArc;
    private Label label;
    private Color foregroundColor = Color.BLACK;
    private Color backgroundColor = Color.DARKGRAY;
    private double foregroundWidth = 10, 
            backgroundWidth = 10;
    private double startingAngle, endingAngle;
    private boolean clockwise, animated = true;
    private double xMouse,yMouse;
    private int minValue, maxValue;
    private String unit;
    private StringProperty valueStringProperty; //To bind with the label
    private IntegerProperty value = new SimpleIntegerProperty();;
    private Timeline valueChangeAnim;
    private double labelWidth;
    private double labelHeight;
    private double padding = 0;
    
    public RadSlider(int minValue, int maxValue, String unit, Color arcColor, double RX, double RY, double StrokeWidth)
    {
        centerX = RX + StrokeWidth/2 + padding;
        centerY = RY + StrokeWidth/2 + padding;
        
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.RX = RX;
        this.RY = RY;
        this.StrokeWidth = StrokeWidth;
        
        //Setting up the background arc
        backgroundArc = new Arc(centerX, centerY, RX, RY, MIN_ANGLE, ARC_LENGTH);
        backgroundArc.setFill(Color.TRANSPARENT);
        backgroundArc.setStroke(Color.DARKGREY);
        backgroundArc.setStrokeWidth(StrokeWidth);
        backgroundArc.setType(ArcType.OPEN);
        backgroundArc.setStrokeLineCap(StrokeLineCap.ROUND);
        //EndOf: Setting up the background arc
        
        //Setting up the slider arc
        arc = new Arc(centerX, centerY, RX, RY, MIN_ANGLE, ARC_LENGTH/2);        
        arc.setFill(Color.TRANSPARENT);
        arc.setStroke(arcColor);
        arc.setStrokeWidth(StrokeWidth);
        arc.setType(ArcType.OPEN);
        arc.setStrokeLineCap(StrokeLineCap.ROUND);
        //EndOf: Setting up the slider arc

        valueStringProperty = new SimpleStringProperty(getCurrentValue() + this.unit); // Initial content of the label
        value.setValue(getCurrentValue());
        
        arc.setCache(true);
        backgroundArc.setCache(true);
        
        EventHandler adjustValue = (EventHandler<MouseEvent>) (MouseEvent event) -> 
        {
            if(animated)
                valueChangeAnim.playFromStart(); //Plays from start to prevent inconsistencies in animation when
            //triggered before reaching the end of its previous call
            
            xMouse = event.getX() - centerX;
            yMouse = event.getY() - centerY;
            
            
            double angleInRadians = Math.atan2(-yMouse, xMouse);
            double endAngle = Math.toDegrees(angleInRadians);
            
            if(endAngle < -100) // To deal with the fact that Math.atan2()
                endAngle+=360;   // returns values from -PI to PI
            
            if(endAngle < MIN_ANGLE && endAngle > MAX_ANGLE) //Limits the length of the arc so that it can't go a full 360
                arc.setLength(endAngle - MIN_ANGLE);
            
            valueStringProperty.setValue(getCurrentValue() + unit); //Update the label
            value.setValue(getCurrentValue()); //Update the Control
        }; // EventHandler to mimic the behaviour of a slider
        
        
        backgroundArc.setOnMouseDragged(adjustValue);
        backgroundArc.setOnMouseClicked(adjustValue);
        arc.setOnMouseDragged(adjustValue);
        arc.setOnMouseClicked(adjustValue);

        
        label = new Label();
        
        //Setting dimensions for the label
        labelWidth = 2*RX - StrokeWidth*1.1;
        labelHeight = 2*RY - StrokeWidth*1.1;
        label.setMaxSize(labelWidth,labelHeight);
        label.setMinSize(labelWidth,labelHeight);
        //EndOf: Setting dimensions for the label
        
        label.setAlignment(Pos.BASELINE_CENTER);
        label.setTextFill(arcColor);
        
        label.setStyle("-fx-font-weight:bold;");
        label.setFont(Font.font(labelWidth/4));
        
//        label.setBlendMode(BlendMode.EXCLUSION);

        label.setLayoutX(RX + StrokeWidth/2 - labelWidth/2 + padding);
        label.setLayoutY(RY + StrokeWidth/2 - labelHeight/2 + padding);

        label.textProperty().bind(valueStringProperty); //Binding for real time automatic update of text
        
        //Building the Timeline for animation of value changind
        valueChangeAnim = new Timeline();
        
        KeyValue kv = new KeyValue(label.scaleXProperty(), 1.25);
        KeyValue kv_1 = new KeyValue(label.scaleYProperty(), 1.25);
        KeyValue kv2 = new KeyValue(label.scaleXProperty(), 1, Interpolator.EASE_IN);
        KeyValue kv2_1 = new KeyValue(label.scaleYProperty(), 1, Interpolator.EASE_IN);
        
        KeyFrame kf = new KeyFrame(Duration.millis(10), kv, kv_1);
        KeyFrame kf2 = new KeyFrame(Duration.millis(200), kv2, kv2_1);
        
        valueChangeAnim.getKeyFrames().addAll(kf,kf2);

        this.getChildren().addAll(backgroundArc, arc, label);
        
        
        this.setMaxSize(2*RX + StrokeWidth, 2*RY + StrokeWidth + padding*2);    // Making the size constant to prevent stuttering when
        this.setMinSize(2*RX + StrokeWidth, 2*RY + StrokeWidth + padding*2);    // animations are triggered during value changing
        
    }
    
    public int getCurrentValue()
    {   //Rounds the value there is a low change of getting an actual 100% of the length
        return (int) Math.round(arc.getLength() / ARC_LENGTH * (maxValue - minValue) + minValue);
    }
    
    public IntegerProperty valueProperty()
    {
        return value;
    }
    
    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        backgroundArc.setStroke(backgroundColor);
    }
    
    public Color getBackgroundColor()
    {
        return backgroundColor;
    }
    
    public void setStrokeLineCap(StrokeLineCap strokeLineCap)
    {
        this.strokeLineCap = strokeLineCap;
        arc.setStrokeLineCap(strokeLineCap);
        backgroundArc.setStrokeLineCap(strokeLineCap);
    }
    
    public StrokeLineCap getStrokeLineCap()
    {
        return strokeLineCap;
    }
    
    public void setForegroundColor(Color foregroundColor)
    {
        this.foregroundColor = foregroundColor;
        arc.setStroke(foregroundColor);
    }
    
    public Color getForegroundColor()
    {
        return foregroundColor;
    }
    
    public void setForegroundWidth(double foregroundWidth)
    {
        this.foregroundWidth = foregroundWidth;
        arc.setStrokeWidth(foregroundWidth);
    }
    
    public double getForegroundWidth()
    {
        return this.foregroundWidth;
    }
    
    public void setBackgroundWidth(double backgroundWidth)
    {
        this.backgroundWidth = backgroundWidth;
        backgroundArc.setStrokeWidth(backgroundWidth);
    }
    
    public double getBackgroundWidth()
    {
        return backgroundWidth;
    }
    
    public void setStartingAngle(double startingAngle)
    {
        this.startingAngle = startingAngle;
    }
    
    public void setEndingAngle(double endingAngle)
    {
        this.endingAngle = endingAngle;
    }
    
    public double getStartingAngle()
    {
        return startingAngle;
    }
    
    public double getEndingAngle()
    {
        return endingAngle;
    }
    
    public void setClockwise(boolean clockwise)
    {
        this.clockwise = clockwise;
    }
    
    public boolean getClockwise()
    {
        return clockwise;
    }
    
    public void setAnimated(boolean animated)
    {
        this.animated = animated;
    }
    
    public boolean getAnimated()
    {
        return animated;
    }
    
    
}
