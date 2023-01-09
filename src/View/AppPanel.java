package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class AppPanel extends JPanel implements Observer , Runnable{
    private Font small;

    //Card's Models
    private Model_Card temperature_in;
    private Model_Card temperature_out;
    private Model_Card humidity;
    private Model_Card energy;

    //Card Component
    private Card tp_in_card;
    private Card tp_out_card;
    private Card humidity_card;
    private Card energy_card;

    //Card's Icons
    private ImageIcon tp_img;
    private ImageIcon humidity_img;
    private ImageIcon energy_img;

    private String temp_in = "";
    private String temp_out = "";
    //private String humidity = "100";
    //private String energy = "2.089";
    private SwitchButton switchButton;
    private final Button remButton = new Button("-",Color.darkGray,Color.white, 600, 165, 50,50);
    private final Button addButton = new Button("+",Color.decode("#515099"),Color.white, 600, 35, 50,50);
    final int FPS = 60;
    final int FPSS = 1000000000;
    Thread appThread;

    public AppPanel(){
        super();
        try {
            File smal = new File("src/Assets/fonts/Retro Gaming.ttf");
            this.small = Font.createFont(Font.TRUETYPE_FONT, smal);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setBackground(Color.decode("#C8BFE7"));
        this.setBounds(300,0,980,720);
        this.setLayout(null);
        initComponents();

    }

    public SwitchButton getSwitchButton() {
        return switchButton;
    }

    public void initComponents(){
        loadIcons();
        //Creation of Card's Models
        this.temperature_in = new Model_Card(tp_img,"Temperature IN", "18", "°C");
        this.temperature_out = new Model_Card(tp_img,"Temperature OUT", "43", "°C");
        this.humidity = new Model_Card(humidity_img,"Humidity ", "79", "%");
        this.energy = new Model_Card(energy_img, "Energy", "200.3", "Watts");

        //Creating Cards and Switch Button
        tp_in_card = new Card(Color.decode("#A349A4"), Color.decode("#515099"));
        tp_in_card.setData(temperature_in);
        tp_out_card = new Card(Color.decode("#A349A4"), Color.decode("#515099"));
        tp_out_card.setData(temperature_out);
        humidity_card = new Card(Color.decode("#A349A4"), Color.decode("#515099"));
        humidity_card.setData(humidity);
        energy_card = new Card(Color.decode("#A349A4"), Color.decode("#515099"));
        energy_card.setData(energy);

        this.switchButton =  new SwitchButton();
        this.switchButton.setOn(false);

        //Setting bounds to Cards and SwitchButton and adding them to the panel
        this.add(tp_in_card);
        this.tp_in_card.setBounds(360,25, 200,200);
        this.add(tp_out_card);
        this.tp_out_card.setBounds(360,325, 200,200);
        this.add(humidity_card);
        this.humidity_card.setBounds(60,325, 200,200);
        this.add(energy_card);
        this.energy_card.setBounds(660,325, 200,200);
        this.add(switchButton);
        this.switchButton.setBounds(755,25, 100,50);

        this.add(addButton);
        this.add(remButton);
    }

    public Button getRemButton() {
        return remButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void loadIcons(){
        try{
            tp_img = new ImageIcon(ImageIO.read(new File("src/Assets/images/tp.png")));
            humidity_img = new ImageIcon(ImageIO.read(new File("src/Assets/images/wtr.png")));
            energy_img = new ImageIcon(ImageIO.read(new File("src/Assets/images/tdr.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

//        g.setFont(small.deriveFont(Font.BOLD, 25f));
//        g.setColor(Color.black);
        //g2.drawRoundRect(60,25,200,200,50,30);
        PanelComponent fridge = new PanelComponent(0, 0, "src/Assets/images/mini-fridge.png");
        if(switchButton.isOn()== true){
            g.setColor(Color.white);
        }
        else {
            g.setColor(Color.lightGray);
        }
        g2.fillRoundRect(60,25, 200,200,50,30);
        g.drawImage(fridge.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT),25 , 0,this);


    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
    public void startGameThread(){
        appThread = new Thread(this);
        appThread.start();
    }
    public void stopGameThread(){
        appThread.stop();
    }

    @Override
    public void run() {
        double drawInterval = FPSS/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(appThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >=1){
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= FPSS){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
}
