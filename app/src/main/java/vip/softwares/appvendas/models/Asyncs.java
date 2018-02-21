package vip.softwares.appvendas.models;

import java.io.Serializable;

import vip.softwares.appvendas.utils.db.convert.Column;
import vip.softwares.appvendas.utils.db.convert.DatabaseBeans;

/**
 * Created by re032629 on 26/07/2015.
 */
@DatabaseBeans
public class Asyncs implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    private String _id;
    @Column
    private String Identifica;
    @Column
    private String Sync;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdentifica() {
        return Identifica;
    }

    public void setIdentifica(String identifica) {
        Identifica = identifica;
    }

    public String getSync() {
        return Sync;
    }

    public void setSync(String sync) {
        Sync = sync;
    }
}
