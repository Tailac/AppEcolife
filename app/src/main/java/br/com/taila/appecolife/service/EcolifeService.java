package br.com.taila.appecolife.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import br.com.taila.appecolife.ComunicacaoAPI.ClienteApiecolife;
import br.com.taila.appecolife.model.Ecolife;

public class EcolifeService {

    ClienteApiecolife clienteApi = new ClienteApiecolife();

    public EcolifeService() throws IOException {
    }


    public Ecolife retornarEcolife(String QRCode) throws JSONException, IOException {
        try{
            String responseBody = clienteApi.request("https://apirest-ecolife.herokuapp.com/api/ecolife_qrc/" + QRCode);
            if(responseBody != ""){
                JSONObject obj = new JSONObject(responseBody);
                Long id = obj.getLong("id");
                String descricao = obj.get("descricao").toString();
                String endereco = obj.get("endereco").toString();
                String qrCode = obj.getString("qrCode").toString();
                String codigoSeguranca = obj.getString("codigoSeguranca");
                String status = obj.getString("status");
                String coleta = obj.getString("coleta");
                String imgQRCode = obj.getString("imgQRCode");
                return new Ecolife(id,descricao,endereco, qrCode, codigoSeguranca,status,coleta,imgQRCode);
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }


    public String gerarCodigoSeguranca(){

        String codigo = "";
        Set<Integer> numeros = new TreeSet<>();
        int n,i=0;
        Random rand = new Random();
        while (i < 8) {
            n = rand.nextInt(10);
            i++;
            codigo = String.format("%s%d", codigo, n);
        }
        return codigo.trim();
    }

    public Ecolife GravarCodigoSeguranca(Ecolife ecolife, String codSegGerado) throws IOException, JSONException {

        ecolife.setCoidgoSeguranca(codSegGerado);

        JSONObject objJson = new JSONObject();
        objJson.put("id",ecolife.getId());
        objJson.put("descricao",ecolife.getDescricao());
        objJson.put("endereco",ecolife.getEndereco());
        objJson.put("qrCode",ecolife.getQrcode());
        objJson.put("codigoSeguranca",ecolife.getCoidgoSeguranca());
        objJson.put("status",ecolife.getStatus());
        objJson.put("coleta",ecolife.getColeta());
        objJson.put("imgQRCode",ecolife.getImgQRCode());

        String responseBody = clienteApi.requestPut("https://apirest-ecolife.herokuapp.com/api/ecolife/",objJson,"PUT");


        JSONObject objJsonRet = new JSONObject(responseBody);
        Long id = objJsonRet.getLong("id");
        String descricao = objJsonRet.get("descricao").toString();
        String endereco = objJsonRet.get("endereco").toString();
        String qrCode = objJsonRet.getString("qrCode").toString();
        String codigoSeguranca = objJsonRet.getString("codigoSeguranca");
        String status = objJsonRet.getString("status");
        String coleta = objJsonRet.getString("coleta");
        String imgQRCode = objJsonRet.getString("imgQRCode");
        return new Ecolife(id,descricao,endereco, qrCode, codigoSeguranca,status,coleta,imgQRCode);

    }

}
