package vip.softwares.appvendas.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vip.softwares.appvendas.models.Clientes;
import vip.softwares.appvendas.utils.db.GerenciadorDB;
import vip.softwares.appvendas.utils.db.convert.DatabaseConverter;

/**
 * Created by re032629 on 18/07/2015.
 */
public class ClienteREP {

    private final String NomeTabela = "clientes";
	public static String createTable = "CREATE TABLE 'clientes' ("+
			"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
			  "Codigo INTEGER,"+
			  "Nome varchar(150) DEFAULT NULL,"+
			  "AcordoComercial double DEFAULT NULL);";

	private GerenciadorDB gerDB = new GerenciadorDB();

	public long Insert(Context context, List<Clientes> list) throws Exception{
		long resultadoInsercao = 0;
		try{
			if(list != null){
				for(int i=0; i < list.size();i++){
					Clientes clientes = list.get(i);
					ContentValues contentValues = DatabaseConverter.convertObjectToContentValue(clientes);
					gerDB.checkOpeningDataBase(context, "write");
					resultadoInsercao = GerenciadorDB.db.insert(NomeTabela, null, contentValues);
				}
				GerenciadorDB.db = gerDB.closeDataBase(context);
			}
		}
		catch(Exception ex){
			//LogErrorBLL.LogError("", "ERROR DE PERSISTENCIA NO LIST "+NomeTabela ,context);
			resultadoInsercao = -1;
			throw ex;
		}
		return resultadoInsercao;
	}

	public int DeleteAll(Context context) throws Exception{
		int retorno = 0;
		try{
			gerDB.checkOpeningDataBase(context, "write");
			retorno = GerenciadorDB.db.delete(NomeTabela,null,null);
			GerenciadorDB.db = gerDB.closeDataBase(context);
		}
		catch(Exception ex){
			//LogErrorBLL.LogError("", "ERROR DE PERSISTENCIA NO LIST "+NomeTabela ,context);
			retorno = -1;
			throw ex;
		}
		return retorno;
	}

	public List<Clientes> ReadAll(Context context) throws Exception{
		Cursor retorno;
		String dump = null;
		List<Clientes> lst = new ArrayList<Clientes>();
		try{
			gerDB.checkOpeningDataBase(context, "read");
			retorno = GerenciadorDB.db.rawQuery("SELECT * FROM "+NomeTabela+"", null);
			dump = DatabaseUtils.dumpCursorToString(retorno);
			GerenciadorDB.db = gerDB.closeDataBase(context);
			lst = DatabaseConverter.convertCursorToObject(retorno, Clientes.class);
		}
		catch(Exception ex){
			throw ex;
		}
		Log.v("Valores Cursor", dump);
		return (lst.size() > 0 ? lst : null);
	}
}
