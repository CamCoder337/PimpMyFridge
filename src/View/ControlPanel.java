package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ControlPanel extends JPanel {
    private Font small;
    public ControlPanel(){
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
        this.setBounds(0,0,300,720);
        this.setVisible(true);
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(small.deriveFont(Font.BOLD, 30f));//font
        g.setColor(Color.white);
        g.drawString("PMF", 115, 40);
    }
}
