package br.com.taila.appecolife.model;

public class Residuo {

    private Long id;
    private String descricao;
    private String codigoBarras;
    private String tipoResiduo;
    private String valorPontuacao;
    private String fabricante;
    private String status;

    public Residuo(String descricao, String codigoBarras, String tipoResiduo, String valorPontuacao) {
        this.descricao = descricao;
        this.codigoBarras = codigoBarras;
        this.tipoResiduo = tipoResiduo;
        this.valorPontuacao = valorPontuacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getTipoResiduo() {
        return tipoResiduo;
    }

    public void setTipoResiduo(String tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public String getValorPontuacao() {
        return valorPontuacao;
    }

    public void setValorPontuacao(String valorPontuacao) {
        this.valorPontuacao = valorPontuacao;
    }
}
