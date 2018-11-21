package br.com.taila.appecolife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Float4;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
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

public class ResiduoActivity extends ContentMqtt {

    ImageView img_plastico;
    ImageView img_metal;
    TextView txt_descResiduo;


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
                abrePontuacaoRecebida(pontuacaoResiduoIdentificado);
            }
        });

        img_metal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrePontuacaoRecebida(pontuacaoResiduoIdentificado);
            }
        });

    }

        private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(broadcastReceiver);
    }


}
