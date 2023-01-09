package Model;

public class ModelData {

    String days;
    double tp_in;
    double tp_out;
    double humidity;
    double energy;

    public String getDays() {
        return days;
    }

    public double getTp_in() {
        return tp_in;
    }

    public double getTp_out() {
        return tp_out;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getEnergy() {
        return energy;
    }

    public ModelData(String days, double tp_in, double tp_out, double humidity, double energy){
        this.days = days;
        this.tp_in = tp_in;
        this.tp_out = tp_out;
        this.humidity = humidity;
        this.energy = energy;
    }
}
