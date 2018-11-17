package br.com.taila.appecolife;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.taila.appecolife.model.Ecolife;

public class CodigoSegurancaActivity extends NavegacaoTela {

    Button bt_Cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_seguranca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_Cd = (Button) findViewById(R.id.btn_cd);
        bt_Cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreScannerBarCode();
            }
        });


    }


}
