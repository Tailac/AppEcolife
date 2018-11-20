package br.com.taila.appecolife;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Float4;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ResiduoActivity extends NavegacaoTela  implements MqttCallback {

    ImageView img_plastico;
    ImageView img_metal;
    TextView txt_descResiduo;

    //Configuração MQTT
    static final String MQTTHOST = "tcp://m15.cloudmqtt.com:15260";
    static final String USERNAME = "hzwxnzwy";
    static final String PASSWORD = "8ztJzTw0rMy_";
    int controle = 0;
    String pontuacaoResiduoIdentificado;
    MqttAndroidClient client;
    String topicStc = "LED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residuo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String descResiduo = bundle.getString("descResiduo");
        final String tipoResiduo = bundle.getString("tipoResiduo");
        final String pontuacao = bundle.getString("pontuacao");
        pontuacaoResiduoIdentificado = pontuacao;

        img_plastico = (ImageView) findViewById(R.id.img_plastico);
        img_metal = (ImageView) findViewById(R.id.img_metal);
        txt_descResiduo = (TextView) findViewById(R.id.txt_descResiduo);
        Toast.makeText(this, "PONTUACAO: " + pontuacao, Toast.LENGTH_SHORT).show();
        MQTT();

        if(tipoResiduo.equals("plástico")){
            img_metal.setVisibility(View.INVISIBLE);
            img_plastico.setVisibility(View.VISIBLE);
        }else{
            img_metal.setVisibility(View.VISIBLE);
            img_plastico.setVisibility(View.INVISIBLE);
        }

        txt_descResiduo.setText(descResiduo);

        img_plastico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrePontuacao(pontuacaoResiduoIdentificado);
            }
        });

        img_metal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrePontuacao(pontuacaoResiduoIdentificado);
            }
        });

    }

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

    private void MQTT(){
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
                    Toast.makeText(ResiduoActivity.this, "Conectado", Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Conectado", Toast.LENGTH_LONG).show();
                    client.setCallback(ResiduoActivity.this);
                    final String topic = "Sensor";
                    final String topic1 = "LED";
                    final String topic2 = "controle";

                    try{
                        IMqttToken subToken = client.subscribe(topic,1);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Toast.makeText(ResiduoActivity.this, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Toast.makeText(ResiduoActivity.this, "Couldn't subscribe to: " + topic, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                        });
                        IMqttToken subToken1 = client.subscribe(topic1,1);
                        subToken1.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Toast.makeText(ResiduoActivity.this, "Successfully subscribed to: " + topic1, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Toast.makeText(ResiduoActivity.this, "Couldn't subscribe to: " + topic1, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                        });
                        IMqttToken subToken2 = client.subscribe(topic2,1);
                        subToken2.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Toast.makeText(ResiduoActivity.this, "Successfully subscribed to: " + topic2, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Toast.makeText(ResiduoActivity.this, "Couldn't subscribe to: " + topic2, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                        });

                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(ResiduoActivity.this, "Não conectado", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "Não conectado", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
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
