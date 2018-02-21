package vip.softwares.appvendas.autorizacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.autorizacao.async.AsyncAuth;
import vip.softwares.appvendas.autorizacao.async.AuthInterface;
import vip.softwares.appvendas.main.MainActivity;
import vip.softwares.appvendas.models.Auth;
import vip.softwares.appvendas.models.services.ServiceResult;
import vip.softwares.appvendas.utils.convert.ConvertJson;
import vip.softwares.appvendas.utils.form.FormUtil;
import vip.softwares.appvendas.utils.network.ControleConexao;
import vip.softwares.appvendas.utils.shared.SharedPreferencesUtil;

/**
 * Created by re032629 on 30/07/2015.
 */
public class FormAuthActivity extends AppCompatActivity {

    private EditText edtCod;
    private Button btnAuth;

    private ArrayList<EditText> lstValidade = new ArrayList<EditText>();

    private AuthInterface authInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_auth);
        FindElementosView();
    }

    private void FindElementosView() {
        edtCod = (EditText) findViewById(R.id.edtCod);
        lstValidade.add(edtCod);
        btnAuth = (Button) findViewById(R.id.btnAuth);
		btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BtnAutorizar();
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",FormAuthActivity.this);
                    e.printStackTrace();
                }
            }
        });
    }

    private void BtnAutorizar() throws Exception {
        try {
            FormUtil form = new FormUtil();
            if(form.ValidarEditTxt(lstValidade)){
                if(!ControleConexao.checkNetworkInterface(FormAuthActivity.this).equals("none")){
                    ResponseAsync();
                    new AsyncAuth(FormAuthActivity.this,authInterface).execute(edtCod.getText().toString());
                }
                else {
				Toast.makeText(FormAuthActivity.this, "Verifique sua Conexão com a Internet e tente novamente!", Toast.LENGTH_SHORT).show();
			    }
            }
        }
        catch (Exception ex){
            throw ex;
        }
    }

    private void ResponseAsync() {
        //=======INTERFACE DE RESPOSTA DOS ASYNC======================
        authInterface = new AuthInterface() {
            @Override
            public void responseAsyncAuth(String json) {
                Log.d("FormAuthResponse->", json);
                if(json != null && json != ""){
                    ServiceResult result = ConvertJson.ConvertJsonToServiceResult(json);
                    if(!result.isHasErrors()){
                        if(result.getMessages().get(0).getCodigo().equals("1")){
                            Auth auth = ConvertJson.ConvertJsonToObjectAuth(result.getMessages().get(0).getValueResult());
                            SharedPreferencesUtil.savePreferences("COD_AUTH", auth.getCodAuto(), FormAuthActivity.this);
                            SharedPreferencesUtil.savePreferences("COD_CLIENTE", auth.getCodCliente(), FormAuthActivity.this);
                            SharedPreferencesUtil.savePreferences("NOME_FANTASIA", auth.getNomeFantasia(), FormAuthActivity.this);
                            Toast.makeText(FormAuthActivity.this, "Dispositivo Autenticado com SUCESSO!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                             i.setClass(FormAuthActivity.this, MainActivity.class);
                             startActivity(i);
                        }
                        else
                            Toast.makeText(FormAuthActivity.this, "Dispositivo não Autenticado, Verifique o Código!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(FormAuthActivity.this, "Problemas com a Requisição, tente mais tarde!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
