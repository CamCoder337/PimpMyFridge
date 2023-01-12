package Model;

import Contract.IModel;

import java.util.Observable;
import java.util.Observer;

public class Model extends Observable implements IModel {

    private double temp;
    private double inside;
    private double humidity;
    private double rose;
    private double order;
    private boolean serial = false;
    private boolean powerOn;
    private boolean froze;
    private boolean doorOpen;
    public Model(){
        this.temp = 0;
    }

    @Override
    public boolean isPowerOn() {
        return this.powerOn;
    }

//    @Override
//    public void addObserver(Observer o){
//        this.addObserver(o);
//    }

    @Override
    public void setPowerOn(boolean power) {
        this.powerOn = power;
    }

    @Override
    public double getTemp() {
        return this.temp;
    }

    @Override
    public void setTemp(double temp) {
        this.temp = temp;
        setChanged();
        notifyObservers("temp");
    }

    @Override
    public double getInside() {
        return this.inside;
    }

    @Override
    public void setInside(double inside) {
        this.inside = inside;
        setChanged();
        notifyObservers("inside");
    }

    @Override
    public double getHumidity() {
        return this.humidity;
    }

    @Override
    public void setHumidity(double humidity) {
        this.humidity = humidity;
        setChanged();
        notifyObservers("humidity");
    }

    @Override
    public double getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(double order) {
        this.order = order;
        setChanged();
        notifyObservers("order");
    }

    @Override
    public double getRose() {
        return this.rose;
    }

    @Override
    public void setRose(double rose) {
        this.rose = rose;
        setChanged();
        notifyObservers("rose");
    }

    @Override
    public boolean isFroze() {
        return this.froze;
    }

    @Override
    public void setFroze(boolean froze) {
        this.froze = froze;
        setChanged();
        notifyObservers("froze");
    }

    @Override
    public boolean isDoorOpen() {
        return this.doorOpen;
    }

    @Override
    public void setDoorOpen(boolean doorOpen) {
        this.doorOpen = doorOpen;
        setChanged();
        notifyObservers("door");
    }

    @Override
    public boolean isSerial() {
        return this.serial;
    }

    @Override
    public void setSerial(boolean serial) {
        this.serial = serial;
        setChanged();
        notifyObservers("serial");
    }
}
