#include <Adafruit_Sensor.h>
#include <math.h>
#include "ArduinoJson.h"
#include "DHT.h"
#include <SharpIR.h>

#define ir A0
#define model 1080
SharpIR irSensor = SharpIR(ir,model);

#define DHTPIN 6
#define DHTPTP 2
#define DHTTYPE DHT22

//Alimentation electrique
double V_IN = 5.0;

//temperature de consigne
double order = 18;
float door = false;
float min_fridge_range = 7;
float max_fridge_range = 10;

//data flag
bool newData = false;

//power
boolean power = true;
boolean froze = true;

DHT dht(DHTPTP, DHTTYPE);
DHT dht_inside(DHTPIN, DHTTYPE);


void setup() {
  pinMode(12,OUTPUT);
  Serial.begin(9600);
  dht.begin();
  dht_inside.begin();

}

void loop() {
  //Receive data from Java
  ReceiveData();
  //Send data to Java app:
  SendData();

  //Regulation function !!

    
  

}

void SendData(){
      StaticJsonDocument<200> doc;

  float h = dht.readHumidity();

  float t = dht.readTemperature();

  float f = dht.readTemperature(true);

  float inside = dht_inside.readTemperature();

  if(isnan(h) || isnan(t) || isnan(f)){
    Serial.println("Failed to read from temp DHT sensor!");
    return;
  }
  /*if(isnan(inside)){
    Serial.println("Failed to read from inside DHT sensor!");
    return;
  }*/

  digitalWrite(12, froze);

  float hif = dht.computeHeatIndex(f, h);

  float hic = dht.computeHeatIndex(t, h, false);

  float temperature = t;
  float humidite = h/100;

  float alpha = ((17.27 * temperature)/(237.7 + temperature)) + log(humidite);
  float rose = (237.7 * alpha) / (17.27 - alpha);

  if(irSensor.distance() == min_fridge_range || irSensor.distance() == max_fridge_range){
    door = true;
  }
  else{
    door = false;
  }

  doc["temp"] = t;
  doc["inside"] = t;
  doc["hum"] = h;
  doc["rose"] = rose;
  doc["froze"] = froze;
  doc["order"] = order;
   doc["door"] = door;

  serializeJson(doc, Serial);
  Serial.println("");
  //delay(1000);

  }

  void ReceiveData(){
    String json = Serial.readString();
      //StaticJsonBuffer<200> jsonBufferReader;
      DynamicJsonDocument doc(1024);
      //JsonObject object = jsonBufferReader.parseObject(reader);
      DeserializationError error = deserializeJson(doc, json);
      if (error)
        return;
      order = doc["value"];
     
  }
