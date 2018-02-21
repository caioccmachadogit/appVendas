package vip.softwares.appvendas.models;

import java.io.Serializable;

import vip.softwares.appvendas.utils.db.convert.Column;
import vip.softwares.appvendas.utils.db.convert.DatabaseBeans;

/**
 * Created by re032629 on 21/07/2015.
 */
@DatabaseBeans
public class Clientes  implements Serializable{
    private static final long serialVersionUID = 1L;

    @Column
    private String _id;
    @Column
    private int Codigo;
    @Column
    private String Nome;
    @Column
    private double AcordoComercial;

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public double getAcordoComercial() {
        return AcordoComercial;
    }

    public void setAcordoComercial(double acordoComercial) {
        AcordoComercial = acordoComercial;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
