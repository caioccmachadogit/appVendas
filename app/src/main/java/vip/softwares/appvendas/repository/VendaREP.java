package vip.softwares.appvendas.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vip.softwares.appvendas.models.Vendas;
import vip.softwares.appvendas.utils.db.GerenciadorDB;
import vip.softwares.appvendas.utils.db.convert.DatabaseConverter;

/**
 * Created by re032629 on 26/07/2015.
 */
public class VendaREP {
    private final String NomeTabela = "vendas";

    public static String createTable = "CREATE TABLE 'vendas' ("+
			"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "CodAuth INTEGER,"+
			"CodigoClien INTEGER,"+
            "Data varchar(30) DEFAULT NULL,"+
            "Doc varchar(10) DEFAULT NULL,"+
            "CodCliente varchar(10) DEFAULT NULL,"+
            "NomeCliente varchar(150) DEFAULT NULL,"+
			"NomeProduto varchar(150) DEFAULT NULL,"+
			"CodProduto varchar(10) DEFAULT NULL,"+
			"Unidade varchar(10) DEFAULT NULL,"+
            "Qtde varchar(10) DEFAULT NULL,"+
            "Desconto varchar(10) DEFAULT NULL,"+
            "Preco varchar(10) DEFAULT NULL,"+
            "Total varchar(10) DEFAULT NULL);";

	private GerenciadorDB gerDB = new GerenciadorDB();

    public long Insert(Context context, List<Vendas> list) throws Exception{
		long resultadoInsercao = 0;
		try{
			if(list != null){
				for(int i=0; i < list.size();i++){
					Vendas vendas = list.get(i);
					ContentValues contentValues = DatabaseConverter.convertObjectToContentValue(vendas);
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

    public List<Vendas> ReadByDoc(Context context, String doc) throws Exception{
		Cursor retorno;
		String dump = null;
		List<Vendas> lst = new ArrayList<Vendas>();
		try{
			gerDB.checkOpeningDataBase(context, "read");
			retorno = GerenciadorDB.db.rawQuery("SELECT * FROM "+NomeTabela+" " +
                    "WHERE Doc = '"+doc+"'", null);
			dump = DatabaseUtils.dumpCursorToString(retorno);
			GerenciadorDB.db = gerDB.closeDataBase(context);
			lst = DatabaseConverter.convertCursorToObject(retorno, Vendas.class);
		}
		catch(Exception ex){
			throw ex;
		}
		Log.v("Valores Cursor", dump);
		return (lst.size() > 0 ? lst : null);
	}


}
