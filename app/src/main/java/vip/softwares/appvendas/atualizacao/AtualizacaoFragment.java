package vip.softwares.appvendas.atualizacao;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.atualizacao.async.AsyncAtualizacao;
import vip.softwares.appvendas.atualizacao.async.AtualizacaoInterface;
import vip.softwares.appvendas.models.Clientes;
import vip.softwares.appvendas.models.Produtos;
import vip.softwares.appvendas.models.services.Mensagem;
import vip.softwares.appvendas.models.services.ServiceResult;
import vip.softwares.appvendas.repository.ClienteREP;
import vip.softwares.appvendas.repository.ProdutoREP;
import vip.softwares.appvendas.utils.convert.ConvertJson;
import vip.softwares.appvendas.utils.shared.SharedPreferencesUtil;

/**
 * Created by re032629 on 20/07/2015.
 */
public class AtualizacaoFragment extends Fragment{

    public AtualizacaoFragment(){

    }

    private AtualizacaoInterface atualizacaoInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        BuscarAtualizacao();
        return view;
    }

    private void BuscarAtualizacao() {
        try{
            ResponseAsync();
            new AsyncAtualizacao(getActivity(),atualizacaoInterface).execute(SharedPreferencesUtil.loadSavedPreferencesString("COD_CLIENTE", getActivity()));
        }
        catch (Exception ex){

        }
    }

    private void ResponseAsync() {
        //=======INTERFACE DE RESPOSTA DOS ASYNC======================
        atualizacaoInterface = new AtualizacaoInterface() {
            @Override
            public void responseAsyncAtualizacao(String json) {
                if(json != null && json != ""){
                    ServiceResult result = ConvertJson.ConvertJsonToServiceResult(json);
                    if(!result.isHasErrors()){
                        Mensagem msgProd = result.getMessages().get(0);
                        Mensagem msgClien = result.getMessages().get(1);

                        if(msgProd.getCodigo().equals("1")){
                            List<Produtos> produtos = ConvertJson.ConvertJsonToLstProdutos(msgProd.getValueResult());
                            ProdutoREP produtoREP = new ProdutoREP();
                            try {
                                produtoREP.DeleteAll(getActivity());
                                long insercao = produtoREP.Insert(getActivity(),produtos);
                                if(insercao > 0){
                                    Toast.makeText(getActivity(), "Produtos atualizados com SUCESSO!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Problemas na insercao Produtos!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        else if(msgProd.getCodigo().equals("0")) {
                            Toast.makeText(getActivity(), "Atualização Produtos Não realizada, Problemas!", Toast.LENGTH_SHORT).show();
                        }

                        if(msgClien.getCodigo().equals("2")){
                            List<Clientes> clientes = ConvertJson.ConvertJsonToLstClientes(msgClien.getValueResult());
                            ClienteREP clienteREP = new ClienteREP();
                            try {
                                clienteREP.DeleteAll(getActivity());
                                long insercao = clienteREP.Insert(getActivity(),clientes);
                                if(insercao > 0){
                                    Toast.makeText(getActivity(), "Clientes atualizados com SUCESSO!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Problemas na insercao Clientes!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        else if(msgClien.getCodigo().equals("0")) {
                            Toast.makeText(getActivity(), "Atualização Clientes Não realizada, Problemas!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getActivity(), "Problemas com a Requisição!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

}
