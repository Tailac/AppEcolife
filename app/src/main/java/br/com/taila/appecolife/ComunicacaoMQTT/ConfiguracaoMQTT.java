package br.com.taila.appecolife.ComunicacaoMQTT;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import br.com.taila.appecolife.NavegacaoTela;
import br.com.taila.appecolife.ScannerBarCodeActivity;

public class ConfiguracaoMQTT extends NavegacaoTela implements MqttCallback {

    static final String MQTTHOST = "tcp://m15.cloudmqtt.com:15260";
    static final String USERNAME = "hzwxnzwy";
    static final String PASSWORD = "8ztJzTw0rMy_";

    String topicStc = "LED";
    MqttAndroidClient client;
    Context context;

    public ConfiguracaoMQTT(Context context ) {
        this.context = context;
    }

    public void MQTT() {

        String clientId = MqttClient.generateClientId();
//        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
        client = new MqttAndroidClient(context,MQTTHOST,clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(ConfiguracaoMQTT.this, "Conectado", Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Conectado", Toast.LENGTH_LONG).show();
                    client.setCallback(ConfiguracaoMQTT.this);
                    final String topic = "LED";

                    try{
                        IMqttToken subToken = client.subscribe(topic,1);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
//                                Toast.makeText(ConfiguracaoMQTT.this, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                                Toast.makeText(ConfiguracaoMQTT.this, "Couldn't subscribe to: " + topic, Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                        });
                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Toast.makeText(ConfiguracaoMQTT.this, "Não conectado", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Não conectado", Toast.LENGTH_SHORT).show();

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


    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


}
