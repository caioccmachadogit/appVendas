package vip.softwares.appvendas.vendas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.models.Vendas;

/**
 * Created by re032629 on 25/07/2015.
 */
public class AdapterProdsVenda extends ArrayAdapter<Vendas> {

    private List<Vendas> items;
	private int layoutResourceId;
	private Context context;

    public AdapterProdsVenda(Context context, int layoutResourceId, List<Vendas> items) {
        super(context, R.layout.lv_item_prod_venda, items);
		this.context = context;
		this.items = items;
		this.layoutResourceId = layoutResourceId;
    }

    public static class ViewHolder{
    	TextView nome;
    	TextView qtde;
        TextView valor;
        TextView desc;
        TextView tot;
    	Vendas venda;
    }

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		try {
			if(row == null){
				LayoutInflater inflater = ((Activity) context).getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);

				ViewHolder holder = new ViewHolder();
				holder = new ViewHolder();
				holder.nome = (TextView)row.findViewById(R.id.tv_prod);
				holder.qtde = (TextView)row.findViewById(R.id.tv_qtde);
                holder.valor = (TextView)row.findViewById(R.id.tv_valor);
                holder.desc = (TextView)row.findViewById(R.id.tv_desc);
                holder.tot = (TextView)row.findViewById(R.id.tv_tot);


				row.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) row.getTag();
			holder.venda = items.get(position);

			holder.nome.setText(holder.venda.getNomeProduto());
            holder.qtde.setText(holder.venda.getQtde());
            holder.valor.setText(holder.venda.getPreco());
            holder.desc.setText(holder.venda.getDesconto());
            holder.tot.setText(holder.venda.getTotal());
		}
		catch (Exception e) {
			//LogErrorBLL.LogError(e.getMessage(), " - ERROR ADAPTER Bateria",context);
			e.printStackTrace();
		}
		return row;
	}
}
