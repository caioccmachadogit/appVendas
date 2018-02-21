package vip.softwares.appvendas.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by re032629 on 19/07/2015.
 */
public class ControleConexao {

	public boolean isOnline(Context context) {

		if (context != null) {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm.getActiveNetworkInfo() != null
					&& cm.getActiveNetworkInfo().isAvailable()
					&& cm.getActiveNetworkInfo().isConnected()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String checkNetworkInterface(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinterface = connectivityManager.getActiveNetworkInfo();
		String active = new String();
		if ((networkinterface == null || !networkinterface.isConnected() || !networkinterface.isAvailable())) {
			active = "none";
		}
		else {
			active = networkinterface.getTypeName();
		}
		return active;
	}
}
