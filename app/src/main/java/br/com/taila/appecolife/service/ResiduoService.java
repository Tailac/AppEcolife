package br.com.taila.appecolife.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.taila.appecolife.ComunicacaoAPI.ClienteApiecolife;
import br.com.taila.appecolife.model.Residuo;

public class ResiduoService {


    ClienteApiecolife clienteApi = new ClienteApiecolife();

    public ResiduoService() throws IOException {
    }


    public  Residuo retornarResiduo(String codBarra) throws JSONException, IOException {
        String responseBody = clienteApi.request("https://apirest-ecolife.herokuapp.com/api/residuos_cb/" + codBarra);
        if(responseBody != ""){
            JSONObject obj = new JSONObject(responseBody);
            String descricao = obj.get("descricao").toString();
            String tipoResiduo = obj.get("tipoResiduo").toString();
            String codigoBarras = obj.get("codigoBarras").toString();
            String valorPontuacao = obj.get("valorPontuacao").toString();
            return new Residuo(descricao,codigoBarras,tipoResiduo,valorPontuacao);
        }else{
            return null;
        }

    }


}
