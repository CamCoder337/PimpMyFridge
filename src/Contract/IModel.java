package Contract;

import java.util.Observer;

public interface IModel {

    public boolean isPowerOn();

    public void setPowerOn(boolean power);

    public double getTemp();

    public void setTemp(double temp);

    public  double getInside();

    public void setInside(double inside);

    public double getHumidity();

    public void setHumidity(double humidity);

    public double getOrder();

    public void setOrder(double order);

    public double getRose();

    public void setRose(double rose);

    public boolean isFroze();

    public void setFroze(boolean froze);

    public  boolean isDoorOpen();
    public void setDoorOpen(boolean doorOpen);
    public  boolean isSerial();
    public void setSerial(boolean serial);
    public void addObserver(Observer o);

    public void recordData();

}
