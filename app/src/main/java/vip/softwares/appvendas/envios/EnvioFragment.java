package vip.softwares.appvendas.envios;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import vip.softwares.appvendas.envios.async.AsyncEnvio;
import vip.softwares.appvendas.envios.async.EnvioInterface;
import vip.softwares.appvendas.models.Asyncs;
import vip.softwares.appvendas.models.Vendas;
import vip.softwares.appvendas.models.services.Mensagem;
import vip.softwares.appvendas.models.services.ServiceResult;
import vip.softwares.appvendas.repository.AsyncREP;
import vip.softwares.appvendas.repository.VendaREP;
import vip.softwares.appvendas.utils.convert.ConvertJson;
import vip.softwares.appvendas.utils.shared.AlertaDialog;

/**
 * Created by re032629 on 26/07/2015.
 */
public class EnvioFragment extends Fragment {

    public EnvioFragment(){

    }

    private EnvioInterface envioInterface;
    private Gson gson;
    private JsonArray jsonArray;
    private AsyncREP asyncREP = new AsyncREP();
    private int countVendaEnv = 0;
    private int countVenda = 0;
    private List<Asyncs> lstAsync = new ArrayList<Asyncs>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        EnviarVendas();
        return view;
    }

    private void EnviarVendas() {
        try {

            VendaREP vendaREP = new VendaREP();
            lstAsync = asyncREP.ReadBySync(getActivity(),"N");
            if(lstAsync != null){
                for (int i =0; i < lstAsync.size();i++) {
                    List<Vendas> lstVenda = vendaREP.ReadByDoc(getActivity(), lstAsync.get(i).getIdentifica());
                    if (lstVenda != null) {
                        gson = new GsonBuilder().create();
                        jsonArray = gson.toJsonTree(lstVenda).getAsJsonArray();
                        ResponseAsync();
                        new AsyncEnvio(getActivity(), envioInterface).execute(jsonArray.toString());
                    }
                }
            }
            else
                Toast.makeText(getActivity(), "Não existem vendas a serem enviadas!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(getActivity(), "Venda não enviada! #E1: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ResponseAsync() {
        //=======INTERFACE DE RESPOSTA DOS ASYNC======================
        envioInterface = new EnvioInterface() {
            @Override
            public void responseAsyncEnvio(String json) {
                try{
                    countVenda++;
                    if(json != null && json != ""){
                        ServiceResult result = ConvertJson.ConvertJsonToServiceResult(json);
                        if(!result.isHasErrors()){
                            Mensagem msgResult = result.getMessages().get(0);
                            if(msgResult.getCodigo().equals("1")){
                                countVendaEnv++;
                                Asyncs asyncsEdit = new Asyncs();
                                asyncsEdit.setIdentifica(msgResult.getValueResult());
                                asyncsEdit.setSync("S");
                                if(asyncREP.UpdateByIdentifica(getActivity(), asyncsEdit) == 1){
                                    Toast.makeText(getActivity(), "Venda Enviada! #"+asyncsEdit.getIdentifica()+"", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                            Toast.makeText(getActivity(), "Problemas no Envio!", Toast.LENGTH_SHORT).show();
                        }
                    if(countVenda == lstAsync.size()){
                        new AlertaDialog(getActivity()).ShowDialogAviso("Confirmação Envio", countVendaEnv+" Registros Enviados!");
                        ZerarCount();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(), "Venda não enviada! #E2 - "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void ZerarCount(){
        countVenda = 0;
        countVendaEnv = 0;
    }
}
