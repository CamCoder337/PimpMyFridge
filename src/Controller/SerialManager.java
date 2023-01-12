package Controller;

import Contract.IController;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class SerialManager implements SerialPortDataListener {

    public IController controller;
    SerialPort serialPort;
    JSONObject sensor;


    private BufferedReader input;
    /** The output stream to the port */
    private OutputStream output;
    private SerialPortDataListener dl;
    /** Milliseconds to block while waiting for port open */

    public SerialManager(IController controller) {
        this.controller = controller;
    }

    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;

    public void initialize(SerialPort port){
        this.serialPort = port;
        this.serialPort.setBaudRate(9600);
        //this.serialPort.addDataListener(new);
        this.serialPort.openPort();
        controller.setConnected(true);

        if(this.serialPort.openPort()){
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER,0,0);
            Thread tcp = new Thread() {
                @Override
                public void run() {
                    input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

                    while(true){
                        try {
                            if (!(input.readLine() != null)) break;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            //System.out.println(input.readLine());
                            String data = input.readLine();
                            if(data.charAt(0) == '{'){
                                JSONObject sensor = new JSONObject(input.readLine());
                                System.out.print("temp is: " + sensor.getDouble("temp"));
                                System.out.print("  " + "humidity: " + sensor.getDouble("hum"));
                                System.out.print("  " + "rosee: " + sensor.getDouble("rosee"));
                                System.out.println("  " + "frooze: " + sensor.getBoolean("frooze"));

                            }

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            };
            //tcp.start();

        }

    }
    @Override
    public int getListeningEvents() {
        return 0;
    }

    public synchronized void send(String type, String value) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("value", value);
        try {
            OutputStream outputStream = serialPort.getOutputStream();
            DataOutputStream data_output = new DataOutputStream(outputStream);
            //output.write(json.toString().getBytes());
            data_output.write(json.toString().getBytes("UTF-8"));
        } catch (Exception e) {

        }
    }


    public synchronized void close() {
        controller.setConnected(false);
        if (serialPort != null) {
            serialPort.removeDataListener();
            serialPort.closePort();
        }
    }

    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
            try {
                String inputLine=input.readLine();
                controller.update(inputLine);
            } catch (IOException e) {
                System.err.print("Error: ");
                // Close the serialPort
                System.err.println(e.toString());
                close();
            }
        }
    }


}
