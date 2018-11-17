package br.com.taila.appecolife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    Button btnScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
    }

    public void AbreScanner(View view){
        Intent intentAbrirScanner = new Intent(this,ScannerQRCodeActivity.class);
        startActivity(intentAbrirScanner);
    }

    public void AbreCadastro(View view){
        Intent intentAbrirScanner = new Intent(this,CadastroActivity.class);
        startActivity(intentAbrirScanner);
    }


}
