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
import android.view.Window;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONException;

import java.io.IOException;

import br.com.taila.appecolife.model.Ecolife;
import br.com.taila.appecolife.service.EcolifeService;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerQRCodeActivity extends ContentZXingScanner {

//    private static final int ZXING_CAMERA_PERMISSION = 1;
//    private static final int INTERNET = 2;
//    private ZXingScannerView mScannerView;
//    private Class<?> mClss = ScannerQRCodeActivity.class;
//    private EcolifeService ecolifeService = new EcolifeService();?

    public ScannerQRCodeActivity() throws IOException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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


}
