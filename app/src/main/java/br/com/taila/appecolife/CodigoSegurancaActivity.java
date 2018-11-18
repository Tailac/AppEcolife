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

import java.io.IOException;
import java.util.Random;

import br.com.taila.appecolife.model.Ecolife;
import br.com.taila.appecolife.service.EcolifeService;

public class CodigoSegurancaActivity extends NavegacaoTela {

    Button bt_Cd1,bt_Cd2,bt_Cd3;
    private EcolifeService ecolifeService = new EcolifeService();

    public CodigoSegurancaActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_seguranca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String codSegGerado = bundle.getString("codSegGerado");

        String codigo1 = ecolifeService.gerarCodigoSeguranca();
        String codigo2 = ecolifeService.gerarCodigoSeguranca();

        bt_Cd1 = (Button) findViewById(R.id.btn_cd1);
        bt_Cd2 = (Button) findViewById(R.id.btn_cd2);
        bt_Cd3 = (Button) findViewById(R.id.btn_cd3);

        setCodigoSegurancaButton(codSegGerado,codigo1,codigo2);

        bt_Cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt_Cd1.getText()==codSegGerado){
                    abreScannerBarCode();
                    finish();
                }else{
                    Toast.makeText(CodigoSegurancaActivity.this, "Código incorreto", Toast.LENGTH_SHORT).show();
                    abreScannerQRCode();
                    finish();
                }

            }
        });

        bt_Cd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt_Cd1.getText()==codSegGerado){
                    abreScannerBarCode();
                    finish();
                }else{
                    Toast.makeText(CodigoSegurancaActivity.this, "Código incorreto", Toast.LENGTH_SHORT).show();
                    abreScannerQRCode();
                    finish();
                }

            }
        });

        bt_Cd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt_Cd1.getText()==codSegGerado){
                    abreScannerBarCode();
                    finish();
                }else{
                    Toast.makeText(CodigoSegurancaActivity.this, "Código incorreto", Toast.LENGTH_SHORT).show();
                    abreScannerQRCode();
                    finish();
                }

            }
        });

    }

    public void setCodigoSegurancaButton(String cdS1, String cd2, String cd3){
        Random rand = new Random();
        int n = rand.nextInt(2);
        switch (n){
            case 0:
                bt_Cd1.setText(cdS1);
                bt_Cd2.setText(cd2);
                bt_Cd3.setText(cd3);
                break;
            case 1:
                bt_Cd1.setText(cd3);
                bt_Cd2.setText(cdS1);
                bt_Cd3.setText(cd2);
                break;
            case 2:
                bt_Cd1.setText(cd2);
                bt_Cd2.setText(cd3);
                bt_Cd3.setText(cdS1);
                break;
        }


    }


}
