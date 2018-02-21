package vip.softwares.appvendas.models;

import java.io.Serializable;

import vip.softwares.appvendas.utils.db.convert.Column;
import vip.softwares.appvendas.utils.db.convert.DatabaseBeans;

/**
 * Created by re032629 on 25/07/2015.
 */
@DatabaseBeans
public class Vendas implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    private String _id;
    @Column
    private String CodAuth;
    @Column
    private String CodigoClien;
    @Column
    private String Data;
    @Column
    private String Doc;
    @Column
    private String CodCliente;
    @Column
    private String NomeCliente;
    @Column
    private String NomeProduto;
    @Column
    private String CodProduto;
    @Column
    private String Unidade;
    @Column
    private String Qtde;
    @Column
    private String Desconto;
    @Column
    private String Preco;
    @Column
    private String Total;


    public String getCodAuth() {
        return CodAuth;
    }

    public void setCodAuth(String codAuth) {
        CodAuth = codAuth;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getDoc() {
        return Doc;
    }

    public void setDoc(String doc) {
        Doc = doc;
    }

    public String getCodCliente() {
        return CodCliente;
    }

    public void setCodCliente(String codCliente) {
        CodCliente = codCliente;
    }

    public String getNomeCliente() {
        return NomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        NomeCliente = nomeCliente;
    }

    public String getNomeProduto() {
        return NomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        NomeProduto = nomeProduto;
    }

    public String getQtde() {
        return Qtde;
    }

    public void setQtde(String qtde) {
        Qtde = qtde;
    }

    public String getDesconto() {
        return Desconto;
    }

    public void setDesconto(String desconto) {
        Desconto = desconto;
    }

    public String getPreco() {
        return Preco;
    }

    public void setPreco(String preco) {
        Preco = preco;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCodigoClien() {
        return CodigoClien;
    }

    public void setCodigoClien(String codigoClien) {
        CodigoClien = codigoClien;
    }

    public String getCodProduto() {
        return CodProduto;
    }

    public void setCodProduto(String codProduto) {
        CodProduto = codProduto;
    }

    public String getUnidade() {
        return Unidade;
    }

    public void setUnidade(String unidade) {
        Unidade = unidade;
    }
}
