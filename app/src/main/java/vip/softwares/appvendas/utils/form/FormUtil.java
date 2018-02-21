package vip.softwares.appvendas.utils.form;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by re032629 on 19/07/2015.
 */
public class FormUtil {

    public boolean ValidarEditTxt(ArrayList<EditText> lstValidade){
		boolean validadeOk = true;
		for(int i=0; i < lstValidade.size();i++){
			if(lstValidade.get(i).getText().toString().isEmpty()){
				lstValidade.get(i).setError("Informe o valor para o campo!");
				validadeOk = false;
			}
			else{
				lstValidade.get(i).setError(null);
			}
		}
		return validadeOk;
	}

	public static String GetDateNow(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();
		return dateFormat.format(now);
	}

	public static void CalculeHeightListView(ListView lv) {
		int totalHeight = 0;

		ListAdapter adapter = lv.getAdapter();
		int lenght = adapter.getCount();

		for (int i = 0; i < lenght; i++) {
			View listItem = adapter.getView(i, null, lv);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = lv.getLayoutParams();
		params.height = totalHeight + (lv.getDividerHeight() * (adapter.getCount() - 1));
		lv.setLayoutParams(params);
		lv.requestLayout();
	}
}
