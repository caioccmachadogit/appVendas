package vip.softwares.appvendas.atualizacao.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import vip.softwares.appvendas.atualizacao.request.RequestAtualizacao;
import vip.softwares.appvendas.autorizacao.async.AuthInterface;

/**
 * Created by re032629 on 20/07/2015.
 */
public class AsyncAtualizacao extends AsyncTask<String, String, String> {
    private ProgressDialog pd;
	private Activity activity;
	private AtualizacaoInterface ti;

    public AsyncAtualizacao(Activity activity, AtualizacaoInterface ti){
        this.activity = activity;
		this.ti = ti;
    }

    @Override
    protected void onPreExecute() {
        //---Inicia a espera do progresso---
		pd = ProgressDialog.show(activity, "Atualizações", "Verificando...");
    }

    @Override
    protected String doInBackground(String... params) {
        //---Executa a Requisicao
        String jsonResponse = "";
        try{
            Thread.sleep(5000);
            RequestAtualizacao requestAtualizacao = new RequestAtualizacao();
            jsonResponse = requestAtualizacao.BuscarProdClien(params[0]);
        }
        catch (Exception ex){
            Log.e("ERROR ASYNATUALIZACAO", ex.getMessage());
			return null;
        }
        return jsonResponse;
    }

    @Override
	protected void onPostExecute(String result)
	{
		//---Executa a Pos-Requisicao
		ti.responseAsyncAtualizacao(result);
		this.pd.dismiss();
	}
}
