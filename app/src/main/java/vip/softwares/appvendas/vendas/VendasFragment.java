package vip.softwares.appvendas.vendas;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.comprovantes.ComprovantesActivity;
import vip.softwares.appvendas.main.MainActivity;
import vip.softwares.appvendas.models.Asyncs;
import vip.softwares.appvendas.models.Clientes;
import vip.softwares.appvendas.models.Produtos;
import vip.softwares.appvendas.models.Vendas;
import vip.softwares.appvendas.repository.AsyncREP;
import vip.softwares.appvendas.repository.ClienteREP;
import vip.softwares.appvendas.repository.ProdutoREP;
import vip.softwares.appvendas.repository.VendaREP;
import vip.softwares.appvendas.utils.convert.ConvertType;
import vip.softwares.appvendas.utils.form.FormUtil;
import vip.softwares.appvendas.utils.shared.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendasFragment extends Fragment {


    public VendasFragment() {
        // Required empty public constructor
    }

    private EditText edtCodClie;
    private EditText edtCodProd;
    private EditText edtNomeClie;
    private EditText edtNomeProd;
    private EditText edtUn;
    private EditText edtValor;
    private EditText edtDesc;
    private EditText edtTot;
    private EditText edtBuscarClie;
    private EditText edtBuscarProd;
    private EditText edtQtde;
    private TextView tvCodProd;
    private TextView tvNomeProd;
    private TextView tvUn;
    private TextView tvQtde;
    private TextView tvValor;
    private TextView tvDesc;
    private TextView tvTot;
    private CheckBox ckNovo;
    private Button btnNomeCliente;
    private Button btnCodCliente;
    private Button btnNomeProduto;
    private Button btnCodProduto;
    private Button btnSalvar;
    private Button btnCancelar;
    private ListView lvClie;
    private ListView lvProd;
    private ListView lvProdsVenda;
    private TextView tvTotal;
    private TextView tvCount;

    private ArrayList<String> pesquisaCliente = new ArrayList<String>();
    private ArrayList<String> pesquisaProduto = new ArrayList<String>();
    private String[] arrayClientes;
    private String[] arrayProdutos;
    private ArrayList<EditText> lstValidade = new ArrayList<EditText>();
    private ClienteREP clienteREP = new ClienteREP();
    private ProdutoREP produtoREP = new ProdutoREP();
    private List<Clientes> lstClientes;
    private List<Produtos> lstProdutos;
    private List<Vendas> lstVendas = new ArrayList<Vendas>();
    private String DOC = null;
    private String tipoPesqClien = null;
    private String tipoPesqProd = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_vendas, container, false);
        FindElementosView(view);
        InputsCliente(false);
        HabilitarInputsProduto(false);
        return view;
    }

    private void FindElementosView(View view) {
        edtCodClie = (EditText) view.findViewById(R.id.edtCodClie);
        edtCodProd = (EditText) view.findViewById(R.id.edtCodProd);
        edtNomeClie = (EditText) view.findViewById(R.id.edtNomeClie);
        edtNomeProd = (EditText) view.findViewById(R.id.edtNomeProd);
        edtUn = (EditText) view.findViewById(R.id.edtUn);
        tvCodProd = (TextView) view.findViewById(R.id.tvCodProd);
        tvNomeProd = (TextView) view.findViewById(R.id.tvNomeProd);
        tvUn = (TextView) view.findViewById(R.id.tvUn);
        tvQtde = (TextView) view.findViewById(R.id.tvQtde);
        tvValor = (TextView) view.findViewById(R.id.tvValor);
        tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        tvTot = (TextView) view.findViewById(R.id.tvTot);
        tvCount = (TextView) view.findViewById(R.id.tvCount);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        edtQtde = (EditText) view.findViewById(R.id.edtQtde);
        edtQtde.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtQtde.setError(null);
                int qtde = !edtQtde.getText().toString().isEmpty() ? Integer.valueOf(edtQtde.getText().toString()) : 0;
                if (qtde > 0){
                    if (!edtValor.getText().toString().isEmpty()) {
                        double desc = 0;
                        if (!edtDesc.getText().toString().isEmpty()) {
                            desc = Double.parseDouble(edtDesc.getText().toString());
                        }
                        CalcularTotalProduto(qtde, ConvertType.ConvertStringRealToDouble(edtValor.getText().toString()), desc);
                    }
                }
                else {
                    edtQtde.setError("deve ser maior que 0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtValor = (EditText) view.findViewById(R.id.edtValor);
        edtValor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!edtDesc.getText().toString().isEmpty()){
                        if(!edtValor.getText().toString().isEmpty()){
                            double desc = Double.parseDouble(edtDesc.getText().toString());
                            int qtde = !edtQtde.getText().toString().isEmpty() ? Integer.valueOf(edtQtde.getText().toString()) : 0;
                            if (qtde > 0){
                                CalcularTotalProduto(qtde,
                                    ConvertType.ConvertStringRealToDouble(edtValor.getText().toString()),desc);
                                }
                            }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        edtDesc = (EditText) view.findViewById(R.id.edtDesc);
        edtDesc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    double desc = !edtDesc.getText().toString().isEmpty() ? Double.parseDouble(edtDesc.getText().toString()) : 0;
                    if(!edtValor.getText().toString().isEmpty()){
                        int qtde = !edtQtde.getText().toString().isEmpty() ? Integer.valueOf(edtQtde.getText().toString()) : 0;
                        if (qtde > 0){
                            CalcularTotalProduto(qtde,
                                ConvertType.ConvertStringRealToDouble(edtValor.getText().toString()),desc);
                            }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        edtTot = (EditText) view.findViewById(R.id.edtTot);

        ckNovo = (CheckBox) view.findViewById(R.id.ckNovo);

        btnNomeCliente = (Button) view.findViewById(R.id.btnNomeCliente);
		btnNomeCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tipoPesqClien = "nome";
                    BtnSelecionarCliente();
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                    e.printStackTrace();
                }
            }
        });

        btnCodCliente = (Button) view.findViewById(R.id.btnCodCliente);
		btnCodCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tipoPesqClien = "cod";
                    BtnSelecionarCliente();
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                    e.printStackTrace();
                }
            }
        });
        
        btnNomeProduto = (Button) view.findViewById(R.id.btnNomeProduto);
		btnNomeProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tipoPesqProd = "nome";
                    BtnSelecionarProduto();
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                    e.printStackTrace();
                }
            }
        });

        btnCodProduto = (Button) view.findViewById(R.id.btnCodProduto);
		btnCodProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tipoPesqProd = "cod";
                    BtnSelecionarProduto();
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                    e.printStackTrace();
                }
            }
        });

        btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
		btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BtnSalvarProduto();
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                    e.printStackTrace();
                }
            }
        });

        btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
		btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HabilitarInputsProduto(false);
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                    e.printStackTrace();
                }
            }
        });

        lvProdsVenda = (ListView) view.findViewById(R.id.lvProdsVenda);
        lvProdsVenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
                    try {
                        RemoverProduto(position);
                    } catch (Exception e) {
                        //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                        e.printStackTrace();
                    }
                }
            });
        ckNovo = (CheckBox) view.findViewById(R.id.ckNovo);
        ckNovo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              InputsCliente(((CheckBox) v).isChecked());
          }
        });
        view.findViewById(R.id.btn_finalizar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BtnFinalizarVenda();
                } catch (Exception e) {
                    //LogErrorBLL.LogError(e.getMessage(), "ERROR BtnFoto",getActivity());
                    e.printStackTrace();
                }
            }
        });

    }

    private void RemoverProduto(final int index) throws Exception{

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Remover Produto");
        builder.setMessage("Deseja Remover o Produto?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                try {
                    Vendas vendaRemove = lstVendas.get(index);
                    lstVendas.remove(vendaRemove);
                    AtualizarListView();
                    PreencherCabecalhoListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                Toast.makeText(getActivity(), "Produto Removido!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void BtnFinalizarVenda() throws Exception {
        if(!lstVendas.isEmpty()){


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Finalizar Venda!");
                builder.setMessage("Deseja Finalizar Venda?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                VendaREP vendaREP = new VendaREP();
                                if (vendaREP.Insert(getActivity(), lstVendas) > 0) {
                                    AsyncREP asyncREP = new AsyncREP();
                                    Asyncs asyncs = new Asyncs();
                                    asyncs.setIdentifica(DOC);
                                    asyncs.setSync("N");
                                    asyncREP.Insert(getActivity(), asyncs);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Venda Finalizada!");
                                    builder.setMessage("Deseja Consultar Comprovante?");

                                    builder.setPositiveButton("Consultar", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            //========COMPROVANTES==============
                                            Toast.makeText(getActivity(), "Venda Registrada!", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent();
                                            i.putExtra("DOC",DOC);
                                            i.setClass(getActivity(), ComprovantesActivity.class);
                                            startActivity(i);
                                            DOC = null;
                                        }
                                    });

                                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // I do not need any action here you might
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "Venda Registrada!", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent();
                                            i.setClass(getActivity(), MainActivity.class);
                                            startActivity(i);
                                        }
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            }
                            catch (Exception ex){
                            }
                        }
                    }
                );

                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener()

                            {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // I do not need any action here you might
                                    dialog.dismiss();
                                }
                            }

                    );

                    AlertDialog alert = builder.create();
                    alert.show();
        }
        else
            Toast.makeText(getActivity(), "Adicione Produtos para Venda!", Toast.LENGTH_SHORT).show();

    }

    private void BtnSalvarProduto() throws Exception {
        if(!edtCodClie.getText().toString().isEmpty() && !edtNomeClie.getText().toString().isEmpty()
                && !edtCodProd.getText().toString().isEmpty() && !edtQtde.getText().toString().isEmpty()){
            String valor = !edtValor.getText().toString().isEmpty() ? edtValor.getText().toString() : "0";
            if(ConvertType.ConvertStringRealToDouble(valor) > 0){
                Vendas venda =  PrepararProduto();
                lstVendas.add(venda);
                AtualizarListView();
                PreencherCabecalhoListView();
                HabilitarInputsProduto(false);
                Toast.makeText(getActivity(), "Produto Adicionado!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getActivity(), "Valor deve ser maior que 0!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
    }

    private void AtualizarListView() throws Exception{
        AdapterProdsVenda adapter = new AdapterProdsVenda(getActivity(), R.layout.lv_item_prod_venda, lstVendas);
        lvProdsVenda.setAdapter(adapter);
        FormUtil.CalculeHeightListView(lvProdsVenda);
    }

    private void InputsCliente(boolean enabled){
        edtCodClie.setText("");
        edtNomeClie.setText("");
        if(enabled){
            edtNomeClie.setText("");
            edtNomeClie.setEnabled(true);
            edtCodClie.setText("0");
        }
        else {
            ckNovo.setChecked(false);
            edtNomeClie.setEnabled(false);
        }
    }

    private void PreencherCabecalhoListView(){
        double totalVendadb = 0;
        String totalVenda = "0";
        String qtdeProd = "0";
        if(!lstVendas.isEmpty()){
            for(int i=0;i< lstVendas.size();i++){
                String valor = lstVendas.get(i).getTotal();
                valor = valor.replaceAll("\\.", "");
                totalVendadb += ConvertType.ConvertStringRealToDouble(valor);
            }
            totalVenda = ConvertType.ConvertDoubleToStringReal(totalVendadb);
            qtdeProd = String.valueOf(lstVendas.size());
        }
        tvCount.setText(qtdeProd);
        tvTotal.setText(totalVenda);
    }

    private Vendas PrepararProduto() throws Exception{
        Vendas _venda = new Vendas();
        if(DOC == null){
            long num = (long) (100 + Math.random() * 899);
            DOC = String.valueOf(num);
        }
        _venda.setCodAuth(SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", getActivity()));
        _venda.setCodigoClien(SharedPreferencesUtil.loadSavedPreferencesString("COD_CLIENTE", getActivity()));
        _venda.setData(FormUtil.GetDateNow());
        _venda.setDoc(DOC);
        _venda.setCodCliente(edtCodClie.getText().toString());
        _venda.setNomeCliente(edtNomeClie.getText().toString());
        _venda.setNomeProduto(edtNomeProd.getText().toString());
        _venda.setCodProduto(edtCodProd.getText().toString());
        _venda.setUnidade(edtUn.getText().toString());
        _venda.setQtde(String.valueOf(edtQtde.getText().toString()));
        _venda.setDesconto(!edtDesc.getText().toString().isEmpty() ? edtDesc.getText().toString().replace(".", ",") : "0");
        _venda.setPreco(ConvertType.ConvertDoubleToStringReal(ConvertType.ConvertStringRealToDouble(edtValor.getText().toString())));
        _venda.setTotal(edtTot.getText().toString());
        return _venda;
    }

    //===========PRODUTOS==================================
    private void BtnSelecionarProduto() throws Exception {
        lstProdutos = produtoREP.ReadAll(getActivity());
        if(lstProdutos != null){
            PrepararArrayProduto();
            String tit = "";
            if(tipoPesqProd.equals("nome")){
                tit = "Produtos por Nome";
            }
            else
                tit = "Produtos por Cód.";

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_produtos);
            dialog.setTitle(tit);
            dialog.setCancelable(true);

            lvProd = (ListView) dialog.findViewById(R.id.lvProd);
            lvProd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    TextView selected = (TextView) arg1.findViewById(android.R.id.text1);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    HabilitarInputsProduto(true);
                    PreencherCamposProduto(selected.getText().toString());
                }
            });
            edtBuscarProd = (EditText) dialog.findViewById(R.id.edtBuscaProd);
            edtBuscarProd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    lvProd.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pesquisaProduto));
                    PesquisarProdutos();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            lvProd.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pesquisaProduto));
            PesquisarProdutos();

            dialog.show();
        }
        else
            Toast.makeText(getActivity(), "Não possui Produtos, favor Atualizar!", Toast.LENGTH_SHORT).show();
    }

    private void PreencherCamposProduto(String selected) {
        try{
            String[] parts = selected.split("-");
            String[] parts2 = parts[0].split("#");
            String valor = "0";
            if(tipoPesqProd.equals("nome")){
                edtNomeProd.setText(parts2[0].trim());
                edtCodProd.setText(parts2[1].trim());
            }
            else {
                edtNomeProd.setText(parts2[1].trim());
                edtCodProd.setText(parts2[0].trim());
            }
            edtUn.setText(parts[1].trim());
            valor = parts[2].replace("R$", "").trim();
            edtValor.setText(valor);
            edtQtde.setText("1");
            edtDesc.setText("0");

            CalcularTotalProduto(1, ConvertType.ConvertStringRealToDouble(valor),0);

        }
        catch (Exception ex){
            Toast.makeText(getActivity(), "Erro: Registro nao inserido-> "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void PrepararArrayProduto() {
        arrayProdutos = new String[lstProdutos.size()];
        for (int i = 0; i < lstProdutos.size();i++){
            if(tipoPesqProd.equals("nome")){
                arrayProdutos[i] = lstProdutos.get(i).getProduto()+" #"+lstProdutos.get(i).getCodigo()+" - "+
                    lstProdutos.get(i).getUnidade()+" - "+ConvertType.ConvertDoubleToStringReal(lstProdutos.get(i).getPreco());
            }
            else
                arrayProdutos[i] = lstProdutos.get(i).getCodigo() +"# "+ lstProdutos.get(i).getProduto()+" - "+
                    lstProdutos.get(i).getUnidade()+" - "+ConvertType.ConvertDoubleToStringReal(lstProdutos.get(i).getPreco());
        }
    }

    private void PesquisarProdutos() {
        int textlength = edtBuscarProd.getText().length();
		pesquisaProduto.clear();

		for (int i = 0; i < arrayProdutos.length; i++) {
			if (textlength <= arrayProdutos[i].length()) {
				if (edtBuscarProd.getText().toString().equalsIgnoreCase((String)arrayProdutos[i].subSequence(0, textlength))) {
					pesquisaProduto.add(arrayProdutos[i]);
				}
			}
		}
    }

    private void CalcularTotalProduto(int qtde, double valor, double desc){
        double total = 0;
        double desconto;
            if(valor > 0){
                total = (valor*qtde);
                if(desc > 0){
                desconto = (total*desc)/100;
                total = total - desconto;
            }
        }
        edtTot.setText(ConvertType.ConvertDoubleToStringReal(total));
    }

    private void LimparInputProduto(){
        edtCodProd.setText(null);
        edtNomeProd.setText(null);
        edtUn.setText(null);
        edtQtde.setText("1");
        edtValor.setText(null);
        edtDesc.setText(null);
        edtTot.setText(null);
    }

    private void HabilitarInputsProduto(boolean enabled){
        int habilitar;
        if(enabled){
            habilitar = View.VISIBLE;
        }
        else {
            LimparInputProduto();
            habilitar = View.GONE;
        }

        tvCodProd.setVisibility(habilitar);
        edtCodProd.setVisibility(habilitar);
        tvNomeProd.setVisibility(habilitar);
        edtNomeProd.setVisibility(habilitar);
        tvUn.setVisibility(habilitar);
        edtUn.setVisibility(habilitar);
        tvQtde.setVisibility(habilitar);
        edtQtde.setVisibility(habilitar);
        tvValor.setVisibility(habilitar);
        edtValor.setVisibility(habilitar);
        tvDesc.setVisibility(habilitar);
        edtDesc.setVisibility(habilitar);
        tvTot.setVisibility(habilitar);
        edtTot.setVisibility(habilitar);
        btnCancelar.setVisibility(habilitar);
        btnSalvar.setVisibility(habilitar);
    }
    //===========PRODUTOS==================================

    //===========CLIENTES==================================
    private void BtnSelecionarCliente() throws Exception {
        InputsCliente(false);
        lstClientes = clienteREP.ReadAll(getActivity());
        if(lstClientes != null){
            PrepararArrayCliente();
            String tit = "";
            if(tipoPesqClien.equals("nome")){
                tit = "Clientes por Nome";
            }
            else
                tit = "Clientes por Cód.";

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_clientes);
            dialog.setTitle(tit);
            dialog.setCancelable(true);

            lvClie = (ListView) dialog.findViewById(R.id.lvClie);
            lvClie.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
                    TextView selectedClien = (TextView) arg1.findViewById(android.R.id.text1);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    PreencherCamposCliente(selectedClien.getText().toString());
                }
            });
            edtBuscarClie = (EditText) dialog.findViewById(R.id.edtBuscaClie);
            edtBuscarClie.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    lvClie.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pesquisaCliente));
                    PesquisarClientes();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            lvClie.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pesquisaCliente));
            PesquisarClientes();

            dialog.show();
        }
        else
            Toast.makeText(getActivity(), "Não possui Clientes, favor Atualizar!", Toast.LENGTH_SHORT).show();
    }

    private void PreencherCamposCliente(String selectedClien) {
        String[] parts = selectedClien.split("#");
        if(tipoPesqClien.equals("nome")){
            edtCodClie.setText(parts[1].trim());
            edtNomeClie.setText(parts[0].trim());
        }
        else {
            edtCodClie.setText(parts[0].trim());
            edtNomeClie.setText(parts[1].trim());
        }
    }

    private void PrepararArrayCliente() {
        arrayClientes = new String[lstClientes.size()];
        for (int i = 0; i < lstClientes.size();i++){
            if(tipoPesqClien.equals("nome")){
                arrayClientes[i] = lstClientes.get(i).getNome() + " #" +lstClientes.get(i).getCodigo();
            }
            else
                arrayClientes[i] = lstClientes.get(i).getCodigo()  + "# " + lstClientes.get(i).getNome();
        }
    }

    private void PesquisarClientes() {
		int textlength = edtBuscarClie.getText().length();
		pesquisaCliente.clear();

		for (int i = 0; i < arrayClientes.length; i++) {
			if (textlength <= arrayClientes[i].length()) {
				if (edtBuscarClie.getText().toString().equalsIgnoreCase((String)arrayClientes[i].subSequence(0, textlength))) {
					pesquisaCliente.add(arrayClientes[i]);
				}
			}
		}
	}
    //===========CLIENTES==================================

    public boolean AllowBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sair da Venda!");
        builder.setMessage("Deseja Sair da Venda?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                i.setClass(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

        return false;
    }
}
