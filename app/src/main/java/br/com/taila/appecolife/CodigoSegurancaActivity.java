package br.com.taila.appecolife;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.taila.appecolife.model.Ecolife;

public class CodigoSegurancaActivity extends NavegacaoTela {

    TextView txtTest;

    public CodigoSegurancaActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_seguranca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    /*    Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String QRCode = bundle.getString("QRCode");

        txtTest = (TextView) txtTest.findViewById(R.id.txt_teste);
        txtTest.setText(QRCode);*/

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_Lerqrcode){
            Toast.makeText(this, "Ler QRCode", Toast.LENGTH_LONG).show();
            abreScannerQRCode();
            return true;
        }
        if(id == R.id.action_conta){
            Toast.makeText(this, "Gerenciar Conta", Toast.LENGTH_LONG).show();
            abreGerenciarConta();
        }
        if(id == R.id.action_pontuacao){
            Toast.makeText(this, "Pontuação", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void abreScannerQRCode(){
        Intent intentAbrirScanner = new Intent(this,ScannerQRCodeActivity.class);
        startActivity(intentAbrirScanner);
    }

    public void abreGerenciarConta(){
        Intent intentAbrirScanner = new Intent(this,GerenciarContaActivity.class);
        startActivity(intentAbrirScanner);
    }

    public void abrePontuacao(View view){
        Intent intentAbrirScanner = new Intent(this,ScannerQRCodeActivity.class);
        startActivity(intentAbrirScanner);
    }

    public void sairAplicacao(View view){
        Toast.makeText(this, "SAIR DA APP", Toast.LENGTH_LONG).show();
        finish();
    }


}
