package Controller;

import Contract.IController;
import Contract.IModel;
import Contract.IView;
import com.fazecast.jSerialComm.SerialPort;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller implements IController {

    private IView view;
    private IModel model;
    private boolean bootValue = false;
    private BufferedReader input;
    private SerialManager serialManager;

    public Controller(final IView view, final IModel model) {
        this.setView(view);
        this.view.setController(this);
        this.setModel(model);
        this.serialManager = new SerialManager(this);
        this.model.addObserver(this.view.getAppPanel());
        this.model.addObserver(this.view.getDataPanel());
        //this.model.addObserver(this.view.getAppPanel());
    }

    public Controller() {
        this.serialManager = new SerialManager(this);
    }

    @Override
    public void control() {

    }

    @Override
    public void start() {

    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public IView getView() {
        return this.view;
    }

    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public void setTemp(double temp) {
        this.model.setTemp(temp);
    }

    @Override
    public void setHumidity(double humidity) {
        this.model.setHumidity(humidity);
    }

    @Override
    public void setRose(double rose) {
        this.model.setRose(rose);
    }

    @Override
    public void setInside(double inside) {
        this.model.setInside(inside);
    }

    @Override
    public void setOrder(double order) {
        this.model.setOrder(order);
    }

    @Override
    public void update(Object o) {

        try {
            if (!(input.readLine() != null)) ;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            JSONObject sensor = new JSONObject((String) o);
            System.out.print("temp is: " + sensor.getDouble("temp"));
            System.out.print("  " + "humidity: " + sensor.getDouble("hum"));
            System.out.print("  " + "rose: " + sensor.getDouble("rose"));
            System.out.print("  " + "froze: " + sensor.getBoolean("froze"));
            System.out.print("  " + "order is : " + sensor.getDouble("order"));
            System.out.println("  " + "door: " + (sensor.getInt("door") == 1));
            System.out.println();
            System. out.println(" model order "+ this.model.getOrder());
            setTemp(sensor.getDouble("temp"));
            setHumidity(sensor.getDouble("hum"));
            setRose(sensor.getDouble("rose"));
            setDoor(sensor.getInt("door") == 1);
            this.sendData("order",Double.toString(this.model.getOrder()));

        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void sendData(String type, String value) {
        try {
            serialManager.send(type, value);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ReceiveData() {
        if (this.getPort().openPort()) {
            getPort().setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
            input = new BufferedReader(new InputStreamReader(getPort().getInputStream()));
            while (true) {
                try {
                    if (!(input.readLine() != null)) break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    //System.out.println(input.readLine());
                    String data = (input.readLine() != null) ? input.readLine() : "";
                    if (data.charAt(0) == '{') {
                        this.update(data);

                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }

    @Override
    public void closePop(String type) {

    }

    @Override
    public void stop() {
        this.serialManager.close();

    }

    @Override
    public SerialPort getPort() {
        return this.serialManager.serialPort;
    }

    @Override
    public void setConnected(boolean connected) {
        this.model.setSerial(connected);
    }

    @Override
    public void launch(SerialPort port) {
        this.serialManager.initialize(port);
    }

    @Override
    public void setPower(boolean power) {
        this.model.setPowerOn(power);
    }

    @Override
    public void setFroze(boolean froze) {
        this.model.setFroze(froze);
    }

    @Override
    public void setDoor(boolean door) {
        this.model.setDoorOpen(door);
    }

    @Override
    public void reloadConnect() {

    }

    @Override
    public SerialPort[] getSerialPorts() {
        SerialPort[] get_port = SerialPort.getCommPorts();
        return get_port;
    }

}
