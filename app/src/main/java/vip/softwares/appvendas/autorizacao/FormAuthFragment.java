package vip.softwares.appvendas.autorizacao;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * A simple {@link Fragment} subclass.
 */
public class FormAuthFragment extends Fragment{


    public FormAuthFragment() {
        // Required empty public constructor
    }

    private EditText edtCod;
    private Button btnAuth;

    private ArrayList<EditText> lstValidade = new ArrayList<EditText>();

    private AuthInterface authInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_form_auth, container, false);
        FindElementosView(view);
        return view;
    }

    private void FindElementosView(View view) {
        edtCod = (EditText) view.findViewById(R.id.edtCod);
        lstValidade.add(edtCod);
        btnAuth = (Button) view.findViewById(R.id.btnAuth);
		btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BtnAutorizar();
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                    e.printStackTrace();
                }
            }
        });
    }

    private void BtnAutorizar() throws Exception {
        try {
            FormUtil form = new FormUtil();
            if(form.ValidarEditTxt(lstValidade)){
                if(!ControleConexao.checkNetworkInterface(getActivity()).equals("none")){
                    ResponseAsync();
                    new AsyncAuth(getActivity(),authInterface).execute(edtCod.getText().toString());
                }
                else {
				Toast.makeText(getActivity(), "Verifique sua Conexão com a Internet e tente novamente!", Toast.LENGTH_SHORT).show();
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
                if(json != null && json != ""){
                    ServiceResult result = ConvertJson.ConvertJsonToServiceResult(json);
                    if(!result.isHasErrors()){
                        if(result.getMessages().get(0).getCodigo().equals("1")){
                            Auth auth = ConvertJson.ConvertJsonToObjectAuth(result.getMessages().get(0).getValueResult());
                            SharedPreferencesUtil.savePreferences("COD_AUTH", auth.getCodAuto(), getActivity());
                            SharedPreferencesUtil.savePreferences("COD_CLIENTE", auth.getCodCliente(), getActivity());
                            SharedPreferencesUtil.savePreferences("NOME_FANTASIA", auth.getNomeFantasia(), getActivity());
                            Toast.makeText(getActivity(), "Dispositivo Autenticado com SUCESSO!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                             i.setClass(getActivity(), MainActivity.class);
                             startActivity(i);
                        }
                        else
                            Toast.makeText(getActivity(), "Dispositivo não Autenticado, Verifique o Código!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(), "Problemas com a Requisição!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }




}
