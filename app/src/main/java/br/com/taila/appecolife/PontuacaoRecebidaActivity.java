package br.com.taila.appecolife;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PontuacaoRecebidaActivity extends NavegacaoTela {

    TextView txt_pontuacao;
    ImageView img_novoDescarte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao_recebida);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String pontuacao = bundle.getString("pontuacao");


        txt_pontuacao = (TextView) findViewById(R.id.txt_pontuacao);
        txt_pontuacao.setText(pontuacao + " Econs");

        img_novoDescarte = (ImageView)findViewById(R.id.img_novoDescarte);
        img_novoDescarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreScannerBarCode();
            }
        });

    }

}
