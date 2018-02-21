package vip.softwares.appvendas.comprovantes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.models.Asyncs;
import vip.softwares.appvendas.models.Comprovantes;
import vip.softwares.appvendas.models.Vendas;
import vip.softwares.appvendas.repository.AsyncREP;
import vip.softwares.appvendas.repository.VendaREP;

/**
 * Created by re032629 on 24/08/2015.
 */
public class ListComprovantesFragment extends Fragment {

    private ListView lvComprovantes;
    private AsyncREP asyncREP = new AsyncREP();
    private VendaREP vendaREP = new VendaREP();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lv_comprovantes, container, false);
        FindElementosView(view);
        AtualizarListView();
        return view;
    }

    private void AtualizarListView() {
        try{
            List<Comprovantes> comprovantes = GetListComprovantes();
            if(comprovantes != null){
                AdapterComprovantes adapter = new AdapterComprovantes(getActivity(), R.layout.lv_item_comprovantes, comprovantes);
                lvComprovantes.setAdapter(adapter);
            }
            else
                Toast.makeText(getActivity(), "Não existe nenhuma Venda Finalizada!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(getActivity(), "Não foi possível gerar o Comprovante!", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Comprovantes> GetListComprovantes() throws Exception {
        List<Comprovantes> lstComprovantes = new ArrayList<Comprovantes>();
        try{
            List<Asyncs> lstAsyncs = asyncREP.ReadAll(getActivity());
            if(lstAsyncs != null){
                    for (Asyncs asyncs:lstAsyncs){
                    List<Vendas> lstVendas = vendaREP.ReadByDoc(getActivity(),asyncs.getIdentifica());
                    if(lstVendas != null){
                        Comprovantes comprovantes = new Comprovantes();
                        comprovantes.setSync(asyncs.getSync());
                        comprovantes.setNomeCliente(lstVendas.get(0).getNomeCliente());
                        comprovantes.setDoc(lstVendas.get(0).getDoc());
                        comprovantes.setData(lstVendas.get(0).getData());
                        lstComprovantes.add(comprovantes);
                    }
                }
                Collections.reverse(lstComprovantes);
            }
        }
        catch (Exception ex){
            throw ex;
        }
        return (lstComprovantes.size() > 0 ? lstComprovantes : null);
    }

    private void FindElementosView(View view) {
        lvComprovantes = (ListView) view.findViewById(R.id.lvComprovantes);
        lvComprovantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
                    //========COMPROVANTES==============
                    String DOC = ((TextView) arg1.findViewById(R.id.tv_doc)).getText().toString();
                    Intent i = new Intent();
                    i.putExtra("DOC", DOC);
                    i.setClass(getActivity(), ComprovantesActivity.class);
                    startActivity(i);
                }
            });
    }
}
