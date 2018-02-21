package vip.softwares.appvendas.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vip.softwares.appvendas.models.Asyncs;
import vip.softwares.appvendas.models.Vendas;
import vip.softwares.appvendas.utils.db.GerenciadorDB;
import vip.softwares.appvendas.utils.db.convert.DatabaseConverter;

/**
 * Created by re032629 on 26/07/2015.
 */
public class AsyncREP {
    private final String NomeTabela = "asyncs";

    public static String createTable = "CREATE TABLE 'asyncs' ("+
			"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "Identifica varchar(10) DEFAULT NULL,"+
            "Sync varchar(2) DEFAULT NULL);";

    private GerenciadorDB gerDB = new GerenciadorDB();

    public long Insert(Context context, Asyncs asyncs) throws Exception{
		long resultadoInsercao = 0;
		try{
            ContentValues contentValues = DatabaseConverter.convertObjectToContentValue(asyncs);
            gerDB.checkOpeningDataBase(context, "write");
            resultadoInsercao = GerenciadorDB.db.insert(NomeTabela, null, contentValues);
            GerenciadorDB.db = gerDB.closeDataBase(context);
		}
		catch(Exception ex){
			//LogErrorBLL.LogError("", "ERROR DE PERSISTENCIA NO LIST "+NomeTabela ,context);
			resultadoInsercao = -1;
			throw ex;
		}
		return resultadoInsercao;
	}

    public List<Asyncs> ReadBySync(Context context, String sync) throws Exception{
		Cursor retorno;
		String dump = null;
		List<Asyncs> lst = new ArrayList<Asyncs>();
		try{
			gerDB.checkOpeningDataBase(context, "read");
			retorno = GerenciadorDB.db.rawQuery("SELECT * FROM "+NomeTabela+" " +
                    "WHERE Sync = '"+sync+"'", null);
			dump = DatabaseUtils.dumpCursorToString(retorno);
			GerenciadorDB.db = gerDB.closeDataBase(context);
			lst = DatabaseConverter.convertCursorToObject(retorno, Asyncs.class);
		}
		catch(Exception ex){
			throw ex;
		}
		Log.v("Valores Cursor", dump);
		return (lst.size() > 0 ? lst : null);
	}

	public List<Asyncs> ReadAll(Context context) throws Exception{
		Cursor retorno;
		String dump = null;
		List<Asyncs> lst = new ArrayList<Asyncs>();
		try{
			gerDB.checkOpeningDataBase(context, "read");
			retorno = GerenciadorDB.db.rawQuery("SELECT * FROM "+NomeTabela+"", null);
			dump = DatabaseUtils.dumpCursorToString(retorno);
			GerenciadorDB.db = gerDB.closeDataBase(context);
			lst = DatabaseConverter.convertCursorToObject(retorno, Asyncs.class);
		}
		catch(Exception ex){
			throw ex;
		}
		Log.v("Valores Cursor", dump);
		return (lst.size() > 0 ? lst : null);
	}

	public long UpdateByIdentifica(Context context, Asyncs async) throws Exception{
		long resultadoEdicao = 1;
		try{
			gerDB.checkOpeningDataBase(context, "write");
			gerDB.db.execSQL("UPDATE "+NomeTabela+" SET Sync = '"+async.getSync()+"' WHERE Identifica = '"+async.getIdentifica()+"'");
			GerenciadorDB.db = gerDB.closeDataBase(context);
		}
		catch(Exception ex){
			resultadoEdicao = -1;
			throw ex;
		}
		return resultadoEdicao;
	}
}
