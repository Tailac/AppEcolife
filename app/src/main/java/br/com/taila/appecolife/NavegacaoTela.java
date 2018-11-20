package br.com.taila.appecolife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.taila.appecolife.model.Ecolife;
import br.com.taila.appecolife.model.Residuo;

public class NavegacaoTela extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        if(id == R.id.action_sair){
            sairAplicacao();
        }
        return super.onOptionsItemSelected(item);
    }

    public void abreScannerQRCode(){
        Intent intentAbrirScanner = new Intent(this,ScannerQRCodeActivity.class);
        startActivity(intentAbrirScanner);
        finish();

    }

    public void abreGerenciarConta(){
        Intent intentAbrirScanner = new Intent(this,GerenciarContaActivity.class);
        startActivity(intentAbrirScanner);
        finish();

    }

    public void abrePontuacao(String pontuacao){
        Intent intentPontuacao = new Intent(this,PontuacaoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("pontuacao", pontuacao);
        intentPontuacao.putExtras(bundle);
        startActivity(intentPontuacao);
        finish();
    }

    public void sairAplicacao( ){
        Toast.makeText(this, "SAIR DA APP", Toast.LENGTH_LONG).show();
        finish();
    }

    public void abreCodigoSeguranca(Ecolife ecolife){
        Intent abreCodigoSeg = new Intent(this,CodigoSegurancaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("codSegGerado",ecolife.getCoidgoSeguranca());
        abreCodigoSeg.putExtras(bundle);
        startActivity(abreCodigoSeg);
        finish();
    }

    public void abreScannerBarCode( ){
        Intent intentAbrirScannerBarCode = new Intent(this,ScannerBarCodeActivity.class);
        startActivity(intentAbrirScannerBarCode);
        finish();
    }


    public void abreResiduoDescarte(Residuo residuo){
        Intent intentAbrirResiduo = new Intent(this,ResiduoActivity.class);
        startActivity(intentAbrirResiduo);
        Bundle bundle = new Bundle();
        bundle.putString("descResiduo",residuo.getDescricao());
        bundle.putString("tipoResiduo",residuo.getTipoResiduo());
        bundle.putString("pontuacao",residuo.getValorPontuacao());
        intentAbrirResiduo.putExtras(bundle);
        startActivity(intentAbrirResiduo);
        finish();
    }




}
