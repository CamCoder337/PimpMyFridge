package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PanelComponent {
    private int x;
    private int y;
    private String imageLocation;
    private Image image;



    public PanelComponent(int x, int y, String imageLocation){

        this.x = x;
        this.y = y;
        this.imageLocation = imageLocation;

        try{

            this.image = ImageIO.read(new File(imageLocation));
        }


        catch(IOException e){

            e.printStackTrace();
        }
    }

    public int getX(){

        return x;
    }

    public void setX(int x){

        this.x = x;
    }

    public int getY(){

        return y;
    }

    public void setY(int y){

        this.y = y;
    }

    public Image getImage(){

        return image;
    }

    public void update(int x, int y){

        this.setX(x);
        this.setY(y);
    }

    public void setImage(Image image){

        this.image = image;
    }
}
