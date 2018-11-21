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

public class ScannerBarCodeActivity extends ContentMqtt implements ZXingScannerView.ResultHandler {


    private ZXingScannerView mScannerView;
    private Class<?> mClss = ScannerBarCodeActivity.class;
    private ResiduoService residuoService = new ResiduoService();


    public ScannerBarCodeActivity() throws IOException {
    }

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
                abreResiduoDescarte(RetResiduo);
            }else{
                ligarLED2();
                desligarLED1();
                abreResiduoDescarte(RetResiduo);
            }
        }
    }
}
