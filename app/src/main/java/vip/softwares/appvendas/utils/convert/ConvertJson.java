package vip.softwares.appvendas.utils.convert;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import vip.softwares.appvendas.models.Auth;
import vip.softwares.appvendas.models.Clientes;
import vip.softwares.appvendas.models.Produtos;
import vip.softwares.appvendas.models.services.ServiceResult;

/**
 * Created by re032629 on 20/07/2015.
 */
public class ConvertJson {

    public static ServiceResult ConvertJsonToServiceResult(String stringJson) {
        ServiceResult serviceResult = null;
        try{
            GsonBuilder builder = new GsonBuilder();
			Gson gson    = builder.create();
            serviceResult = gson.fromJson(stringJson,ServiceResult.class);
        }
        catch (Exception ex){
            Log.e("ERROR JsonToObject", ex.getMessage());
        }
        return serviceResult;
    }

    public static Auth ConvertJsonToObjectAuth(String stringJson) {
        Auth auth = null;
        try{
            GsonBuilder builder = new GsonBuilder();
			Gson gson    = builder.create();
            auth = gson.fromJson(stringJson,Auth.class);
        }
        catch (Exception ex){
            Log.e("ERROR JsonToObjectAuth", ex.getMessage());
        }
        return auth;
    }

    public static List<Produtos> ConvertJsonToLstProdutos(String stringJson) {
        List<Produtos> produtos = null;
        try{
			Gson gson    = new Gson();
            Type listType = new TypeToken<List<Produtos>>(){}.getType();
            produtos = gson.fromJson(stringJson, listType);
        }
        catch (Exception ex){
            Log.e("ERROR JsonToLstProd", ex.getMessage());
        }
        return produtos;
    }

    public static List<Clientes> ConvertJsonToLstClientes(String stringJson) {
        List<Clientes> clientes = null;
        try{
			Gson gson    = new Gson();
            Type listType = new TypeToken<List<Clientes>>(){}.getType();
            clientes = gson.fromJson(stringJson, listType);
        }
        catch (Exception ex){
            Log.e("ERROR JsonToLstClie", ex.getMessage());
        }
        return clientes;
    }
}
