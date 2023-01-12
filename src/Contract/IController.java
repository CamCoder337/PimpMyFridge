package Contract;

import com.fazecast.jSerialComm.SerialPort;

public interface IController {
    void control();
    void start();
    public void setView(IView view);

    public void setModel(IModel model);
    public IView getView();
    public IModel getModel();

    public  void setTemp(double temp);

    public  void setHumidity(double humidity);

    public  void setRose(double rose);
    public void setInside(double inside);

    public  void setOrder(double order);

    public  void update(Object o);

    public  void sendData(String type, String value);

    public void ReceiveData();

    public  void closePop(String type);

    public void stop();
    public SerialPort getPort();


    public  void setConnected(boolean connected);

    public  void launch(SerialPort port);

    public  void setPower(boolean power);

    public void setFroze(boolean froze);

    public void setDoor(boolean door);

    public void reloadConnect();

    public SerialPort[] getSerialPorts();
}
