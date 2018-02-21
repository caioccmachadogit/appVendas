package vip.softwares.appvendas.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vip.softwares.appvendas.models.Produtos;
import vip.softwares.appvendas.utils.db.GerenciadorDB;
import vip.softwares.appvendas.utils.db.convert.DatabaseConverter;

/**
 * Created by re032629 on 18/07/2015.
 */
public class ProdutoREP {
    private final String NomeTabela = "produtos";
	public static String createTable = "CREATE TABLE 'produtos' ("+
			"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
			  "Codigo INTEGER,"+
			  "Produto varchar(150) DEFAULT NULL,"+
			  "Unidade varchar(10) DEFAULT NULL,"+
			  "Preco double DEFAULT NULL);";

	private GerenciadorDB gerDB = new GerenciadorDB();

	public long Insert(Context context, List<Produtos> list) throws Exception{
		long resultadoInsercao = 0;
		try{
			if(list != null){
				for(int i=0; i < list.size();i++){
					Produtos produto = list.get(i);
					ContentValues contentValues = DatabaseConverter.convertObjectToContentValue(produto);
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

	public List<Produtos> ReadAll(Context context) throws Exception{
		Cursor retorno;
		String dump = null;
		List<Produtos> lst = new ArrayList<Produtos>();
		try{
			gerDB.checkOpeningDataBase(context, "read");
			retorno = GerenciadorDB.db.rawQuery("SELECT * FROM "+NomeTabela+"", null);
			dump = DatabaseUtils.dumpCursorToString(retorno);
			GerenciadorDB.db = gerDB.closeDataBase(context);
			lst = DatabaseConverter.convertCursorToObject(retorno, Produtos.class);
		}
		catch(Exception ex){
			throw ex;
		}
		Log.v("Valores Cursor", dump);
		return (lst.size() > 0 ? lst : null);
	}
}
