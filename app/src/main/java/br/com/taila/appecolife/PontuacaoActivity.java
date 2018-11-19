package br.com.taila.appecolife;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class PontuacaoActivity extends NavegacaoTela {

    TextView txt_pontuacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        final String pontuacao = bundle.getString("pontuacao");

/*        txt_pontuacao = (TextView)findViewById(R.id.txt_pontuacao);
        txt_pontuacao.setText(pontuacao);*/

    }

}
