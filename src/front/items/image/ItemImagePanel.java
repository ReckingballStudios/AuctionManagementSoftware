package front.items.image;

import controllers.util.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ItemImagePanel extends JPanel {

    private BufferedImage image = null;


    public ItemImagePanel(){
        super();
        super.setLayout(new GridBagLayout());
        initializePanel();
        initializeComponents();
    }
    private void initializePanel(){
        Dimension dimension = getPreferredSize();
        dimension.height = Screen.height(0.3f);
        dimension.width = Screen.width(0.3f);
        setPreferredSize(dimension);
        Border innerBorder = BorderFactory.createTitledBorder("Item Image");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    private void initializeComponents(){
        try{
            this.image = ImageIO.read(new File("images/sample/sample_image.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }

    }


    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        try {
            graphics.drawImage(this.image,
                    20, Screen.height(0.25f), super.getWidth() - 40, Screen.height(0.5f),
                    0, 0, this.image.getWidth(), this.image.getHeight(), Color.white, this);
        } catch(NullPointerException exception){
            exception.printStackTrace();
        }
    }
}
