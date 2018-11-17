package br.com.taila.appecolife;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import java.io.IOException;

import br.com.taila.appecolife.ComunicacaoMQTT.ConfiguracaoMQTT;
import br.com.taila.appecolife.model.Ecolife;
import br.com.taila.appecolife.model.Residuo;
import br.com.taila.appecolife.service.EcolifeService;
import br.com.taila.appecolife.service.ResiduoService;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerBarCodeActivity extends NavegacaoTela implements ZXingScannerView.ResultHandler, MqttCallback {


    private ZXingScannerView mScannerView;
    private Class<?> mClss = ScannerBarCodeActivity.class;
    private ResiduoService residuoService = new ResiduoService();
//    private ConfiguracaoMQTT configuracaoMQTT = new ConfiguracaoMQTT(this);

    static final String MQTTHOST = "tcp://m15.cloudmqtt.com:15260";
    static final String USERNAME = "hzwxnzwy";
    static final String PASSWORD = "8ztJzTw0rMy_";

    String topicStc = "LED";
    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_bar_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        MQTT();
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
                    Toast.makeText(ScannerBarCodeActivity.this, "Conectado", Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Conectado", Toast.LENGTH_LONG).show();
                    client.setCallback(ScannerBarCodeActivity.this);
                    final String topic = "LED";

                    try{
                        IMqttToken subToken = client.subscribe(topic,1);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Toast.makeText(ScannerBarCodeActivity.this, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Toast.makeText(ScannerBarCodeActivity.this, "Couldn't subscribe to: " + topic, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Successfully subscribed to: " + topic, Toast.LENGTH_SHORT).show();
                            }

                        });
                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(ScannerBarCodeActivity.this, "Não conectado", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "Não conectado", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    //LEITURA DO QRCODE
    @Override
    public void handleResult(Result result) {
        Toast.makeText(this, "Contents = " + result.getText() +
                ", Format = " + result.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScannerBarCodeActivity.this);
            }
        }, 5000);

        Toast.makeText(this, "Consultando Base de dados....Aguarde!!", Toast.LENGTH_SHORT).show();

        try {
            CarregarResiduo(result.getText().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void CarregarResiduo(String CodBarra) throws JSONException, IOException {

        Residuo RetResiduo = residuoService.retornarResiduo(CodBarra);
        if (RetResiduo == null) {
            Toast.makeText(this, "Código de barra não encontrado", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "Pegou dados da API", Toast.LENGTH_SHORT).show();
            //String codigoSeg = ecolifeService.gerarCodigoSeguranca();
            Toast.makeText(this, "QRcode = " + RetResiduo.getTipoResiduo() + "  " + RetResiduo.getDescricao(), Toast.LENGTH_SHORT).show();
            //AbrirTelaCodigoSeguranca(codigoSeg);
            if(RetResiduo.getTipoResiduo().equals("plástico")){
                ligarLED1();
                desligarLED2();
            }else{
                ligarLED2();
                desligarLED1();
            }
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
