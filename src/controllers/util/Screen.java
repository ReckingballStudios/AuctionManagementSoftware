package controllers.util;

import java.awt.*;

public class Screen {
    public static Color backgroundColor = null;
    private static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = (int)(resolution.getWidth() * 0.85);
    private static final int HEIGHT = (int)(resolution.getHeight() * 0.85);


    public static int width(){
        return WIDTH;
    }
    public static int width(float factor){
        return (int)(WIDTH * factor);
    }
    public static int height(){
        return HEIGHT;
    }
    public static int height(float factor){
        return (int)(HEIGHT * factor);
    }
}
