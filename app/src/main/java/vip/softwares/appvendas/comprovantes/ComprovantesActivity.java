package vip.softwares.appvendas.comprovantes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.main.MainActivity;
import vip.softwares.appvendas.models.Produtos;
import vip.softwares.appvendas.models.Vendas;
import vip.softwares.appvendas.repository.ProdutoREP;
import vip.softwares.appvendas.repository.VendaREP;
import vip.softwares.appvendas.utils.convert.ConvertType;
import vip.softwares.appvendas.utils.form.FormUtil;
import vip.softwares.appvendas.utils.shared.CompartilharUtil;
import vip.softwares.appvendas.utils.shared.PrintScreenUtil;
import vip.softwares.appvendas.utils.shared.SharedPreferencesUtil;

/**
 * Created by re032629 on 20/08/2015.
 */
public class ComprovantesActivity extends PrintScreenUtil {

    private String DOC = null;

    private ListView lvProds;
    private TextView tvCliente;
    private TextView tvData;
    private TextView tvNum;
    private TextView tvTotal;
    private TextView tvEmpresa;

    private ArrayList<String> prods = new ArrayList<String>();
    private ComprovantesActivity mActivity;
    private VendaREP vendaREP = new VendaREP();
    private List<Vendas> lstVenda;
    private String Total = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        DOC = intent.getStringExtra("DOC");
        setContentView(R.layout.activity_comprovantes);
        mActivity = this;
        FindElementosView();
        CarregarListView();
        MontarComprovante();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comprovante, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_compartilhar:
                Compartilhar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
            }
    }

    private void Compartilhar() {
        String namePrint = mActivity.SavePrintScreen(findViewById(R.id.mainContainer));
        CompartilharUtil.CompartilharPrintScreen(mActivity, namePrint);
    }

    private void CarregarListView() {
        try{
            PrepararArrayProds();
            lvProds.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prods));
            FormUtil.CalculeHeightListView(lvProds);
        }
        catch (Exception ex){
            Toast.makeText(this, "Não foi possível gerar o Comprovante!", Toast.LENGTH_SHORT).show();
        }
    }

    private void PrepararArrayProds() throws Exception{
        //prods.add("Morango Albion - CX 22 x 6,45 = 141,90");
        try {
            double tot = 0;
            lstVenda = vendaREP.ReadByDoc(this, DOC);
            if(lstVenda != null){
                String desc = "";
                for (Vendas venda: lstVenda) {
                    if(!venda.getDesconto().equals("0")){
                        desc = " "+venda.getDesconto()+"%";
                    }
                    else
                        desc = "";

                    String produto = venda.getNomeProduto().toLowerCase() +" - "+ venda.getUnidade().toLowerCase() +" "+ venda.getQtde().toLowerCase()+" x "+ venda.getPreco().toLowerCase() + desc + " = "+ venda.getTotal().toLowerCase();
                    prods.add(produto);

                    tot += ConvertType.ConvertStringRealToDouble(venda.getTotal().replaceAll("\\.", ""));
                }
                Total = ConvertType.ConvertDoubleToStringReal(tot);
            }
        }
        catch (Exception ex){
            throw ex;
        }
    }

    private void MontarComprovante() {
        if(lstVenda != null){
            tvEmpresa.setText(SharedPreferencesUtil.loadSavedPreferencesString("NOME_FANTASIA", this));
            tvData.setText(ConvertType.ConvertDateddMMyyyy_HHmm(lstVenda.get(0).getData()));
            tvNum.setText(DOC);
            tvCliente.setText(lstVenda.get(0).getNomeCliente());
            tvTotal.setText("Total: "+Total);
        }
    }

    private void FindElementosView() {
        lvProds = (ListView) findViewById(R.id.lvProds);
        tvCliente = (TextView) findViewById(R.id.tvCliente);
        tvEmpresa = (TextView) findViewById(R.id.tvEmpresa);
        tvData = (TextView) findViewById(R.id.tvData);
        tvNum = (TextView) findViewById(R.id.tvNum);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
    }

    @Override
	public void onBackPressed() {
        MainActivity.SELECTED_ITEM = 2;
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }
}
