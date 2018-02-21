package vip.softwares.appvendas.comprovantes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.models.Comprovantes;

/**
 * Created by re032629 on 25/08/2015.
 */
public class AdapterComprovantes extends ArrayAdapter<Comprovantes> {

    private List<Comprovantes> items;
    private int layoutResourceId;
	private Context context;

    public AdapterComprovantes(Context context, int layoutResourceId, List<Comprovantes> items) {
        super(context, R.layout.lv_item_comprovantes, items);
		this.context = context;
		this.items = items;
		this.layoutResourceId = layoutResourceId;
    }

    public static class ViewHolder{
    	TextView cliente;
    	TextView data;
        TextView num;
        TextView async;
    	Comprovantes comprovantes;
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
				holder.cliente = (TextView)row.findViewById(R.id.tv_cliente);
				holder.data = (TextView)row.findViewById(R.id.tv_data);
                holder.num = (TextView)row.findViewById(R.id.tv_doc);
                holder.async = (TextView)row.findViewById(R.id.tv_async);
				row.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) row.getTag();
			holder.comprovantes = items.get(position);

			String sync = "Não";
            if(holder.comprovantes.getSync().equals("S")){
                sync = "Sim";
            }

			holder.cliente.setText(holder.comprovantes.getNomeCliente());
            holder.data.setText(holder.comprovantes.getData());
            holder.num.setText(holder.comprovantes.getDoc());
            holder.async.setText(sync);
		}
		catch (Exception e) {
			//LogErrorBLL.LogError(e.getMessage(), " - ERROR ADAPTER Bateria",context);
			e.printStackTrace();
		}
		return row;
	}
}
