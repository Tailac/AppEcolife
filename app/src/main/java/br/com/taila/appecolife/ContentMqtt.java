package br.com.taila.appecolife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ContentMqtt extends NavegacaoTela implements MqttCallback {

    private Class<?> mClss = ScannerQRCodeActivity.class;
    private Intent intent;

    //Configuração MQTT
    static final String MQTTHOST = "tcp://m15.cloudmqtt.com:15260";
    static final String USERNAME = "hzwxnzwy";
    static final String PASSWORD = "8ztJzTw0rMy_";
    int controle = 0;
    String pontuacaoResiduoIdentificado;
    MqttAndroidClient client;
    String topicStc = "LED";


    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if(message.toString().equals("PLASTICO_ATIVADO") & controle == 0){
            Toast.makeText(this, "RESIDUO IDENTIFICADO", Toast.LENGTH_LONG).show();
            controle = 1;
            desligarLED1();
            desligarLED2();
            abrePontuacao(pontuacaoResiduoIdentificado);
        }

        if(message.toString().equals("timeOut") & controle == 0){
            controle = 1;
            desligarLED1();
            desligarLED2();
            abreScannerBarCode();
        }

//        if(message.toString().equals("DESATIVADO") & controle == 1){
//            Toast.makeText(this, "FECHA COMPARTIMENTO", Toast.LENGTH_LONG).show();
//            controle = 0;
//        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }



    public void MQTT(){
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
//        client = new MqttAndroidClient(context,MQTTHOST,clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(ContentMqtt.this, "Conectado", Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Conectado", Toast.LENGTH_LONG).show();
                    client.setCallback(ContentMqtt.this);
                    final String topic = "Sensor";
                    final String topic1 = "LED";
                    final String topic2 = "controle";

                    try{
                        IMqttToken subToken = client.subscribe(topic,1);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Toast.makeText(ContentMqtt.this, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Toast.makeText(ContentMqtt.this, "Couldn't subscribe to: " + topic, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                        });
                        IMqttToken subToken1 = client.subscribe(topic1,1);
                        subToken1.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken)  {
                                Toast.makeText(ContentMqtt.this, "Successfully subscribed to: " + topic1, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Toast.makeText(ContentMqtt.this, "Couldn't subscribe to: " + topic1, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                        });
                        IMqttToken subToken2 = client.subscribe(topic2,1);
                        subToken2.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Toast.makeText(ContentMqtt.this, "Successfully subscribed to: " + topic2, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Toast.makeText(ContentMqtt.this, "Couldn't subscribe to: " + topic2, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                        });

                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(ContentMqtt.this, "Não conectado", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "Não conectado", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



    public void ligarLED1(){
        String topic = topicStc;
        String message = "L1";
        try{
            client.publish(topic,message.getBytes(),0, false);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }



    public void desligarLED1(){
        String topic = topicStc;
        String message = "D1";
        try{
            client.publish(topic,message.getBytes(),0, false);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void ligarLED2(){
        String topic = topicStc;
        String message = "L2";
        try{
            client.publish(topic,message.getBytes(),0, false);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void desligarLED2(){
        String topic = topicStc;
        String message = "D2";
        try{
            client.publish(topic,message.getBytes(),0, false);

        }catch (MqttException e){
            e.printStackTrace();
        }
    }





}


