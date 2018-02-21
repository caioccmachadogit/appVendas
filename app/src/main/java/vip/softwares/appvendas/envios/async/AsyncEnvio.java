package vip.softwares.appvendas.envios.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import vip.softwares.appvendas.envios.request.RequestEnvio;
import vip.softwares.appvendas.models.Vendas;

/**
 * Created by re032629 on 26/07/2015.
 */
public class AsyncEnvio extends AsyncTask<String, String, String> {
    private ProgressDialog pd;
	private Activity activity;
    private EnvioInterface ti;

    public AsyncEnvio(Activity activity, EnvioInterface ti){
        this.activity = activity;
		this.ti = ti;
    }

    @Override
    protected void onPreExecute() {
        //---Inicia a espera do progresso---
		pd = ProgressDialog.show(activity, "Envio", "Enviando registros...");
    }

    @Override
    protected String doInBackground(String... params) {
        //---Executa a Requisição
        String jsonResponse = "";
        try{
            RequestEnvio requestEnvio = new RequestEnvio();
            jsonResponse = requestEnvio.GravarVenda(params[0]);
            Thread.sleep(3000);
        }
        catch (Exception ex){
            Log.e("ERROR ASYNENVIO", ex.getMessage());
			return null;
        }
        return jsonResponse;
    }

    @Override
	protected void onPostExecute(String result)
	{
		//---Executa a Pos-Requisição
		ti.responseAsyncEnvio(result);
		this.pd.dismiss();
	}
}
