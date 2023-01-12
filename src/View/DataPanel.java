package View;

import Contract.IController;
import Contract.IModel;
import Model.DAO;
import Model.ModelData;
import View.chart.CurveLineChart;
import View.chart.ModelChart;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DataPanel extends JPanel implements Observer {
    private Font small;
    private CurveLineChart curveLineChart;
    private DAO dao;
    private IController controller;
    private int counter = 0;

    public DataPanel(IController controller){
        super();
        try {
            File smal = new File("src/Assets/fonts/Retro Gaming.ttf");
            this.small = Font.createFont(Font.TRUETYPE_FONT, smal);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setBackground(new Color(34, 59, 69));
        this.setBounds(0,0,1280,720);
        this.setLayout(null);
        this.setController(controller);
        this.initComponents();

    }

    public void initComponents(){
        this.curveLineChart = new CurveLineChart();
        this.add(curveLineChart);
        this.curveLineChart.setBounds(0,15,1260,600);
        this.curveLineChart.setTitle("My Fridge");
        this.curveLineChart.addLegend("T_in", Color.decode("#7b4397"), Color.decode("#dc2430"));
        this.curveLineChart.addLegend("T_out", Color.decode("#377D22"), Color.decode("#75F94D"));
        this.curveLineChart.addLegend("Humidity", Color.decode("#3580BB"), Color.decode("#3282F6"));
        this.curveLineChart.addLegend("Rose", Color.decode("#e65c00"), Color.decode("#F9D423"));
        this.curveLineChart.setFillColor(true);
        curveLineChart.start();
        this.dao = new DAO();
        this.setData();
        //this.curveLineChart.repa
    }


    private void setData(){

        try{
            this.dao.open();
            ArrayList<ModelData> lists = new ArrayList<>();
            String sql = "select DATE_FORMAT(time, '%H:%m:%s') as 'Days', inside as Inside, temp as Temp, humidity as Humidity, rose as Rose from pmf group by DATE_FORMAT(time, '%h:%m:%s') order by time";
            PreparedStatement p = this.dao.getConnection().prepareStatement(sql);
            ResultSet r = p.executeQuery();
            while (r.next()){
                String days = r.getString("Days");
                Double inside = r.getDouble("Inside");
                Double temp = r.getDouble("Temp");
                Double humidity = r.getDouble("Humidity");
                Double rose = r.getDouble("Rose");

                lists.add(new ModelData(days, inside,temp,humidity,rose ));
            }
            r.close();
            p.close();
            //Add data to chart by the end to the start

//            for(int i = lists.size()-1; i>=0; i--){
//                ModelData d = lists.get(i);
//                curveLineChart.addData(new ModelChart(d.getDays(), new double[]{d.getTp_in(),d.getTp_out(),d.getHumidity(),d.getHumidity()}));
//            }

            //Add data to chart by the start to the end
            for(int i = 0; i<=lists.size()-1; i++){
                ModelData d = lists.get(i);
                curveLineChart.addData(new ModelChart(d.getDays(), new double[]{d.getTp_in(),d.getTp_out(),d.getHumidity(),d.getHumidity()}));
            }
            this.dao.close();
            curveLineChart.start();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void refresh(){
        this.curveLineChart.clear();
        this.setData();
    }

    @Override
    public void update(Observable o, Object arg) {
//        IModel model = (IModel) o;
//        String valueToUpdate = (String) arg;
////        if(counter <= 50){
//          curveLineChart.addData(new ModelChart("", new double[]{5,this.controller.getModel().getTemp(),this.controller.getModel().getHumidity(),this.controller.getModel().getRose()}));
////            System.out.println("counter: "+ counter );
////            curveLineChart.start();
//            curveLineChart.repaint();
//            counter++;
//        }else {
//
//            counter = 0;
//        }
       //this.curveLineChart.repaint();

    }

    public void setController(IController controller){
        this.controller = controller;
    }
}
