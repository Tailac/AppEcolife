package br.com.taila.appecolife;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONException;

import java.io.IOException;

import br.com.taila.appecolife.model.Ecolife;
import br.com.taila.appecolife.service.EcolifeService;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerQRCodeActivity extends NavegacaoTela implements ZXingScannerView.ResultHandler {

    private static final int ZXING_CAMERA_PERMISSION = 1;
    private static final int INTERNET = 2;
    private ZXingScannerView mScannerView;
    private Class<?> mClss = ScannerQRCodeActivity.class;
    private EcolifeService ecolifeService = new EcolifeService();

    public ScannerQRCodeActivity() throws IOException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_qrcode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        launchActivity(this.getClass());

        //permite conexÃ£o com a Internet na Thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            ConexaoExterna();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);

    }


    //PERMISSÃO PARA ACESSAR A CAMERA
    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            /*Intent intent = new Intent(this, clss);
            startActivity(intent);*/
        }
    }

    //PERMISSÃO PARA ACESSAR A CAMERA
    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
/*                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);*/
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
            case INTERNET:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Falha conexão com a internet!!!", Toast.LENGTH_LONG).show();
                }
                return;
        }
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
                mScannerView.resumeCameraPreview(ScannerQRCodeActivity.this);
            }
        }, 2000);

        Toast.makeText(this, "Consultando Base de dados....Aguarde!!", Toast.LENGTH_SHORT).show();

        try {
            CarregarEcolife(result.getText().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ConexaoExterna() throws IOException {

         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET);
         }
    }


    public void CarregarEcolife(String QRCode) throws JSONException, IOException {

        Ecolife RetEcolife = ecolifeService.retornarEcolife(QRCode);
        if(RetEcolife == null){
            Toast.makeText(this, "QRCode não encontrado", Toast.LENGTH_SHORT).show();
        }else{
            String codigoSeg = ecolifeService.gerarCodigoSeguranca();
            Ecolife EcolifeCodSeg = ecolifeService.GravarCodigoSeguranca(RetEcolife,codigoSeg.toString().trim());
            Toast.makeText(this, "CodSeguranca = " + codigoSeg + "  " + RetEcolife.getCoidgoSeguranca(), Toast.LENGTH_SHORT).show();
            abreCodigoSeguranca(EcolifeCodSeg);
        }

    }



}
