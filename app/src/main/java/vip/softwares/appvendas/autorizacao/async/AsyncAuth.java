package vip.softwares.appvendas.autorizacao.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import vip.softwares.appvendas.autorizacao.request.RequestAuth;

/**
 * Created by re032629 on 19/07/2015.
 */
public class AsyncAuth extends AsyncTask<String, String, String> {
    private ProgressDialog pd;
	private Activity activity;
	private AuthInterface ti;

    public AsyncAuth(Activity activity, AuthInterface ti){
        this.activity = activity;
		this.ti = ti;
    }

    @Override
    protected void onPreExecute() {
        //---Inicia a espera do progresso---
		pd = ProgressDialog.show(activity, "Validar Dispositivo", "Autenticando...");
    }

    @Override
    protected String doInBackground(String... params) {
        //---Executa a Requisição
        String jsonResponse = "";
        try{
            Thread.sleep(5000);
            RequestAuth requestAuth = new RequestAuth();
            jsonResponse = requestAuth.ObterAuth(params[0]);
        }
        catch (Exception ex){
            Log.e("ERROR ASYNCAUTH", ex.getMessage());
			return null;
        }
        return jsonResponse;
    }

    @Override
	protected void onPostExecute(String result)
	{
		//---Executa a Pos-Requisição
		ti.responseAsyncAuth(result);
		this.pd.dismiss();
	}
}
