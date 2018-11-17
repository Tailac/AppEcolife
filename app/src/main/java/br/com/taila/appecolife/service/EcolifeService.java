package br.com.taila.appecolife.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import br.com.taila.appecolife.ComunicacaoAPI.ClienteApiecolife;
import br.com.taila.appecolife.model.Ecolife;

public class EcolifeService {

    ClienteApiecolife clienteApi = new ClienteApiecolife();


    public Ecolife retornarEcolife(String QRCode) throws JSONException, IOException {
        String responseBody = clienteApi.request("https://apirest-ecolife.herokuapp.com/api/ecolife_qrc/" + QRCode);
        JSONObject obj = new JSONObject(responseBody);
        String descricao = obj.get("descricao").toString();
        String endereco = obj.get("endereco").toString();
        String qrCode = obj.getString("qrCode").toString();
        String codigoSeguranca = obj.getString("codigoSeguranca");
        String status = obj.getString("status");
        String coleta = obj.getString("coleta");
        String imgQRCode = obj.getString("imgQRCode");
        return new Ecolife(descricao,endereco, qrCode, codigoSeguranca,status,coleta,imgQRCode);
    }


    public String gerarCodigoSeguranca(){

        String codigo;
        Set<Integer> numeros = new TreeSet<>();
        Random rand = new Random();
        while (numeros.size() < 20) {
            numeros.add(rand.nextInt(101));
        }
        return numeros.toString();

    }

}
