package vip.softwares.appvendas.models;

import java.io.Serializable;

import vip.softwares.appvendas.utils.db.convert.Column;
import vip.softwares.appvendas.utils.db.convert.DatabaseBeans;

/**
 * Created by re032629 on 21/07/2015.
 */
@DatabaseBeans
public class Produtos implements Serializable{
    private static final long serialVersionUID = 1L;

    @Column
    private String _id;
    @Column
    private int Codigo;
    @Column
    private String Produto;
    @Column
    private String Unidade;
    @Column
    private double Preco;

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getProduto() {
        return Produto;
    }

    public void setProduto(String produto) {
        Produto = produto;
    }

    public String getUnidade() {
        return Unidade;
    }

    public void setUnidade(String unidade) {
        Unidade = unidade;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double preco) {
        Preco = preco;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
