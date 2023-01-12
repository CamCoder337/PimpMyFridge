package View;

import Contract.IController;
import com.fazecast.jSerialComm.SerialPort;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainPanel extends JPanel {
    private Font small;
    private Combobox portBox;
    private Combobox baudBox;
    private String selectedPort = "";
    private SerialPort port;
    private Button connectButton;
    private Button refreshButton;
    private Boolean connected;
    private IController controller;
    private ArrayList<String> baudRates = new ArrayList<>(){{add("9600"); add("115200");}};

    public MainPanel() {
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
        this.setBounds(300, 0, 980, 720);
        this.setLayout(null);
        this.initComponents();
    }

    public MainPanel(IController controller) {
        super();
        this.controller = controller;

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
        this.setBounds(300, 0, 980, 720);
        this.setLayout(null);
        this.initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(small.deriveFont(Font.BOLD, 60f));
        g.setColor(Color.white);
        g.drawString("Pimp My Fridge", 220, 50);
        g.setFont(small.deriveFont(Font.BOLD, 20f));
        g.setFont(small.deriveFont(Font.BOLD, 30f));
        g.drawString("App Version 0.7", 350, 200);
    }

    public void setController(IController controller){
        this.controller = controller;
    }

    void initPortBoxComponents() {
        SerialPort[] get_port = this.controller.getSerialPorts();
        //SerialPort[] get_port = SerialPort.getCommPorts();

        for (SerialPort port : get_port) {
            this.portBox.addItem(port.getSystemPortName());
        }
    }

    void initBaudBoxComponents() {

        for (String baud : baudRates) {
            this.baudBox.addItem(baud);
        }
    }

    void initComponents(){
        //Initialization of the ports box
        this.portBox = new Combobox<>();
        this.portBox.setLabeText("COM Ports Names");
        this.portBox.setBounds(380, 300, 250, 50);
        this.add(portBox);
        initPortBoxComponents();

        //Initialization of the ports box
        this.baudBox = new Combobox<>();
        this.baudBox.setLabeText("Baud Rates");
        this.baudBox.setBounds(380, 400, 250, 50);
        this.add(baudBox);
        initBaudBoxComponents();

        //Initialization of the select button
        this.connectButton = new Button("CONNECT",Color.darkGray,Color.white,  755, 625, 100,50);
        this.add(connectButton);
        this.refreshButton = new Button("REFRESH",Color.decode("#515099"),Color.white,  755, 525, 100,50);
        this.add(refreshButton);

        this.connected = false;

    }

    void refreshBoxes(){
        this.portBox.removeAllItems();
        this.baudBox.removeAllItems();
        initPortBoxComponents();
        initBaudBoxComponents();
    }

    public Object getSelectedPort(){
        int id = this.portBox.getSelectedIndex();
        return this.portBox.getItemAt(id);
    }

    public Button getConnectButtonutton(){
        return this.connectButton;
    }
    public Button getRefreshButton(){
        return this.refreshButton;
    }

    public void connect(){

        try{
            SerialPort []allAvailablePorts = SerialPort.getCommPorts();
            port = allAvailablePorts[portBox.getSelectedIndex()];
            this.controller.launch(port);
//            port.setBaudRate(9600);
//            port.openPort();


            if(this.controller.getPort().openPort()){
                JOptionPane.showMessageDialog(this, this.controller.getPort().getDescriptivePortName() + " is Connected !");
                this.portBox.setEnabled(false);
                this.connectButton.setText("DISCONNECT");
                this.connected = true;
                this.refreshButton.setEnabled(false);
            }
            else {
                JOptionPane.showMessageDialog(this, this.controller.getPort().getDescriptivePortName() + " is not Available !");
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(this, ex,"ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void disconnect(){
        this.controller.stop();
        //port.closePort();
        this.portBox.setEnabled(true);
        this.connected = false;
        this.connectButton.setText("CONNECT");
        this.refreshButton.setEnabled(true  );
    }

    public void ConnectAction(){
        if(connectButton.getText() == "CONNECT"){
            this.connect();
        } else if (connectButton.getText() == "DISCONNECT") {
            this.disconnect();
        }
    }

}
