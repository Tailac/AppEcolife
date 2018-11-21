package br.com.taila.appecolife;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DescarteActivity extends ContentMqtt {

    ImageView img_plastico;
    ImageView img_metal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        img_plastico = (ImageView) findViewById(R.id.img_plastico);
        img_metal = (ImageView) findViewById(R.id.img_metal);

        MQTT();

        img_plastico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ligarLED1();
                desligarLED2();
                Toast toast = Toast.makeText(DescarteActivity.this, "DEPOSITE O RESÍDUO", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        });

        img_metal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ligarLED2();
                desligarLED1();
                Toast toast = Toast.makeText(DescarteActivity.this, "DEPOSITE O RESÍDUO", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

    }

}
