package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CrdPanel extends JPanel {
    private Font small;
    public CrdPanel(){
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
        this.setBounds(300,0,980,720);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(small.deriveFont(Font.BOLD, 50f));
        g.setColor(Color.white);
        g.drawString("Credits", 350, 50);
        g.setFont(small.deriveFont(Font.BOLD, 20f));
        g.drawString("This product was made by the G1 constituted of theses persons:", 100, 200);
        g.drawString("# Fred Tchiadeu (Lead Dev):   fred.tchiadeu@2026.ucac-icam.com", 65, 300);
        g.drawString("# Axelle Kwamou (Physician):   axelle.kwamou@2026.ucac-icam.com",65,350);
        g.drawString("# Lula Mbeck (Backend Dev):    lula.mbeck@2026.ucac-icam.com", 65, 400);
        g.drawString("# Claude Djiojip (Physician):    claude.djiojip@2026.ucac-icam.com", 65, 450);
        g.setFont(small.deriveFont(Font.BOLD, 30f));
        g.drawString("IUI - X2026", 375, 650);

    }
}
