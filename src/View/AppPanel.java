package View;

import Contract.IController;
import Contract.IModel;
import View.slider.JSliderUI;
import View.slider.JsliderCustom;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

public class AppPanel extends JPanel implements Observer , Runnable{
    private Font small;

    //Card's Models
    private Model_Card temperature_in;
    private Model_Card temperature_out;
    private Model_Card humidity;
    private Model_Card rose;

    //Card Component
    private Card tp_in_card;
    private Card tp_out_card;
    private Card humidity_card;
    private Card rose_card;

    //Card's Icons
    private ImageIcon tp_img;
    private ImageIcon humidity_img;
    private ImageIcon energy_img;
    private JSliderUI temp_slider;
    private JsliderCustom slider;
    private TextField yr_temp;
    private double order = 18;
    private int minVal = 15;
    private int maxVal = 25;

    private SwitchButton switchButton;
    private final JLabel door_warn = new JLabel("OPEN DOOR  !    !   !");
    private JLabel warn_img;

    private final Button remButton = new Button("-",Color.darkGray,Color.white, 600, 165, 50,50);
    private final Button addButton = new Button("+",Color.decode("#515099"),Color.white, 600, 35, 50,50);
    final int FPS = 60;
    final int FPSS = 1000000000;
    Thread appThread;
    private IController controller;

    public AppPanel(IController controller){
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
        appThread = new Thread(this);
        this.controller = controller;

    }

    public SwitchButton getSwitchButton() {
        return switchButton;
    }

    public void initComponents(){
        loadIcons();
        //Creation of Card's Models
        this.temperature_in = new Model_Card(tp_img,"TP-IN", "N/A", "°C");
        this.temperature_out = new Model_Card(tp_img,"TP-OUT", "N/A", "°C");
        this.humidity = new Model_Card(humidity_img,"HUMIDITY ", "N/A", "%");
        this.rose = new Model_Card(tp_img, "ROSE", "N/A", "°C");

        //Creating Cards and Switch Button
        tp_in_card = new Card(Color.decode("#A349A4"), Color.decode("#515099"));
        tp_in_card.setData(temperature_in);
        tp_out_card = new Card(Color.decode("#A349A4"), Color.decode("#515099"));
        tp_out_card.setData(temperature_out);
        humidity_card = new Card(Color.decode("#A349A4"), Color.decode("#515099"));
        humidity_card.setData(humidity);
        rose_card = new Card(Color.decode("#A349A4"), Color.decode("#515099"));
        rose_card.setData(rose);

        //Inits switch button
        this.switchButton =  new SwitchButton();
        this.switchButton.setOn(false);

        //Inits Slider
        this.slider = new JsliderCustom();
        this.temp_slider = new JSliderUI(slider);

        this.yr_temp = new TextField();
        this.yr_temp.setBounds(755,165,100,50);
        this.yr_temp.setLabelText("Temperature");
        this.yr_temp.setDisabledTextColor(Color.BLACK);
        this.yr_temp.setEditable(false);
        this.yr_temp.setText(Double.toString(order));
        this.add(yr_temp);

        //Setting bounds to Cards and SwitchButton and adding them to the panel
        this.add(tp_in_card);
        this.tp_in_card.setBounds(360,25, 200,200);
        this.add(tp_out_card);
        this.tp_out_card.setBounds(360,325, 200,200);
        this.add(humidity_card);
        this.humidity_card.setBounds(60,325, 200,200);
        this.add(rose_card);
        this.rose_card.setBounds(660,325, 200,200);
        this.add(switchButton);
        this.switchButton.setBounds(755,25, 100,50);

        this.add(addButton);
        this.add(remButton);
        //Settings of the slider
        //this.add(slider);
        //this.slider.setLocation(755,25);


        this.add(door_warn);
        this.door_warn.setBounds(120,175,150,150);
        try{
            this.warn_img= new JLabel(new ImageIcon(ImageIO.read(new File("src/Assets/images/danger.png"))));
            this.add(warn_img);
            this.warn_img.setBounds(80,60,150,150);


        }
        catch(IOException e){
            e.printStackTrace();
        }
        this.setDoor_warn(false);
    }

    public void setDoor_warn(boolean isOpen){
        if(!isOpen){
            this.door_warn.setForeground(Color.red);
            this.warn_img.setVisible(!isOpen);
            this.warn_img.repaint();
//            this.add(warn_img);
        }else {
            this.door_warn.setForeground(Color.darkGray);
            this.warn_img.setVisible(isOpen);
            this.warn_img.repaint();
            //this.remove(warn_img);
        }
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
        //repaint();
        IModel model = (IModel) o;
        String valueToUpdate = (String) arg;
        switch (valueToUpdate){
            case "temp":
                temperature_out.setValues(Double.toString(this.controller.getModel().getTemp()));
                tp_out_card.setData(temperature_out);
                break;
            case "inside":
                temperature_in.setValues(Double.toString(this.controller.getModel().getInside()));
                tp_in_card.setData(temperature_in);
                break;
            case "humidity":
                humidity.setValues(Double.toString(this.controller.getModel().getHumidity()));
                humidity_card.setData(humidity);
                break;
            case "rose":
                rose.setValues(Double.toString(this.controller.getModel().getRose()));
                rose_card.setData(rose);
                break;
            case "door":
                this.setDoor_warn(this.controller.getModel().isDoorOpen());
                break;
            case "power":
                temperature_out.setValues(Double.toString(this.controller.getModel().getTemp()));
                tp_out_card.setData(temperature_out);
                break;
            case "order":
                this.yr_temp.setText(Double.toString(this.controller.getModel().getOrder()));
                break;

            default:
                break;
        }


    }
    public void startAppThread(){
        appThread = new Thread(this);
        appThread.start();
    }
    public void setController(IController controller){
        this.controller = controller;
    }
    public void stopAppThread(){
        appThread.interrupt();
    }

    public void setUserTemp(int val){
        if(val > 0){
            this.order = (order+1 <= maxVal) ? order+1 : order;
        }else{
            this.order = (order-1 >= minVal) ? order-1 : order;
        }
        //this.yr_temp.setText(Integer.toString(order));
        this.controller.setOrder(order);
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

                if(controller.getModel().isSerial()){
                    controller.ReceiveData();

                }
            }
            if(timer >= FPSS){
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
}
