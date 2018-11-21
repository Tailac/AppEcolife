package br.com.taila.appecolife.model;

import java.math.BigDecimal;

public class Ecolife {

    private Long id;
    private String descricao;
    private String endereco;
    private String qrcode;
    private String coidgoSeguranca;
    private String status;
    private String coleta;
    private String imgQRCode;

    public Ecolife(){

    }

    public Ecolife(Long id, String descricao, String endereco, String qrcode, String coidgoSeguranca, String status, String coleta, String imgQRCode) {
        this.id = id;
        this.descricao = descricao;
        this.endereco = endereco;
        this.qrcode = qrcode;
        this.coidgoSeguranca = coidgoSeguranca;
        this.status = status;
        this.coleta = coleta;
        this.imgQRCode = imgQRCode;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCoidgoSeguranca() {
        return coidgoSeguranca;
    }

    public void setCoidgoSeguranca(String coidgoSeguranca) {
        this.coidgoSeguranca = coidgoSeguranca;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColeta() {
        return coleta;
    }

    public void setColeta(String coleta) {
        this.coleta = coleta;
    }

    public String getImgQRCode() {
        return imgQRCode;
    }

    public void setImgQRCode(String imgQRCode) {
        this.imgQRCode = imgQRCode;
    }
}
