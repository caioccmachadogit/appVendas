package vip.softwares.appvendas.models;

/**
 * Created by re032629 on 19/07/2015.
 */
public class Auth {
    private String CodAuto;
    private String CodCliente;
    private String NomeFantasia;

    public String getCodAuto() {
        return CodAuto;
    }

    public void setCodAuto(String codAuto) {
        CodAuto = codAuto;
    }

    public String getCodCliente() {
        return CodCliente;
    }

    public void setCodCliente(String codCliente) {
        CodCliente = codCliente;
    }

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }
}
