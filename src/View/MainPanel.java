package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MainPanel extends JPanel{
    private Font small;
    public MainPanel(){
        super();
        try {
            File smal = new File("src/Assets/fonts/Retro Gaming.ttf");
            this.small = Font.createFont(Font.TRUETYPE_FONT, smal);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setBackground(Color.decode("#515099"));
        //this.setBackground(Color.decode("#A349A4"));
        this.setBounds(300,0,980,720);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(small.deriveFont(Font.BOLD, 60f));
        g.setColor(Color.white);
        g.drawString("Pimp My Fridge", 220, 50);
        g.setFont(small.deriveFont(Font.BOLD, 20f));
//        g.drawString("In the need to realize a project that was close to our hearts:", 65, 200);
//        g.drawString( "\"a smart mini fridge\". We did a lot of experiments in the laboratory", 65, 250);
//        g.drawString("to find the components that would be useful to us, and the", 65, 300);
//        g.drawString("functionalities that it would need to be thuned such as:", 65, 350);
//        g.drawString("#Having the internal and external temperature in the refrigerator", 50, 420);
//        g.drawString("#Reajusting the température of the fridge", 50, 470);
//        g.drawString("#Turning ON/OFF the fridge", 50, 520);
//        g.drawString("#Display all the info related to the fridge", 50, 570);
        g.setFont(small.deriveFont(Font.BOLD, 30f));
        g.drawString("App Version 0.4", 380, 200);
    }
}
