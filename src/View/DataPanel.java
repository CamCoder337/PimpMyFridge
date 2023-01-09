package View;

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

public class DataPanel extends JPanel {
    private Font small;
    private CurveLineChart curveLineChart;
    private DAO dao;

    public DataPanel(){
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
        this.curveLineChart.addLegend("Energy", Color.decode("#e65c00"), Color.decode("#F9D423"));
        this.curveLineChart.setFillColor(true);
        this.dao = new DAO();
        this.setData();
    }

    private void setData(){

        try{
            this.dao.open();
            ArrayList<ModelData> lists = new ArrayList<>();
            String sql = "select DATE_FORMAT(time, '%H:%m:%s') as 'Days', tpin as T_in, tpout as T_out, humidity as Humidity, energy as Energy from pmf group by DATE_FORMAT(time, '%h:%m:%s') order by time";
            PreparedStatement p = this.dao.getConnection().prepareStatement(sql);
            ResultSet r = p.executeQuery();
            while (r.next()){
                String days = r.getString("Days");
                Double tp_in = r.getDouble("T_in");
                Double tp_out = r.getDouble("T_out");
                Double humidity = r.getDouble("Humidity");
                Double energy = r.getDouble("Energy");

                lists.add(new ModelData(days, tp_in,tp_out,humidity,energy ));
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
}
