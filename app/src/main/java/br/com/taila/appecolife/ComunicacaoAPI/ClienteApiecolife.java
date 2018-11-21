package br.com.taila.appecolife.ComunicacaoAPI;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import br.com.taila.appecolife.model.Residuo;

public class ClienteApiecolife {



    public ClienteApiecolife() throws IOException {
    }

    private  String readStream(InputStream in){
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    public  String request(String stringUrl) throws IOException {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public  String requestPut(String StringUrl, JSONObject data, String metodo) throws IOException {

        try {
            URL urlEndPoint = new URL(StringUrl);
            HttpURLConnection connection = (HttpURLConnection) urlEndPoint.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");

            connection.setRequestMethod(metodo);
            connection.setDoInput(true);
            connection.setDoOutput(true);


            final OutputStream outputStream = connection.getOutputStream();
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            writer.write(data.toString());
            writer.flush();
            writer.close();
            outputStream.close();

            connection.connect();

            final InputStream stream = connection.getInputStream();
            return new Scanner(stream, "UTF-8").next();

        } catch (Exception e) {
            Log.e("Your tag", "Error", e);
        }

        return null;
    }

}
