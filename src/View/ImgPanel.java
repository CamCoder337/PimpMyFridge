package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImgPanel extends JPanel {
    private Image image;

    public ImgPanel(Image image) {
        this.image = image;
    }
    public ImgPanel(String path) throws IOException {
        super();
        setImage(path);
    }

    public void setImage(String path) throws IOException {
        try {
            this.image = ImageIO.read(new File(path));
            repaint();
        }
        catch (IOException e) {
            throw new IOException(path+" introuvable", e);
        }
    }

    public Image getImage() {
        return image;
    }

    @Override
    public void paintComponent(Graphics g){
        if(image!=null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
