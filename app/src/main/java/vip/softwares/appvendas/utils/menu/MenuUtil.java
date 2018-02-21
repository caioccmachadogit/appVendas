package vip.softwares.appvendas.utils.menu;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import java.util.List;

import vip.softwares.appvendas.R;

/**
 * Created by re032629 on 18/07/2015.
 */
public class MenuUtil {

    public DrawerLayout mDrawerLayout;
	public ListView mDrawerList;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	public void Inicializar(DrawerLayout layout, ListView list, Context context, List<DrawerItem> dataList){
		// Initializing
		mDrawerLayout = layout;
		mDrawerList = list;
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		adapter = new CustomDrawerAdapter(context, R.layout.custom_drawer_item, dataList);
		mDrawerList.setAdapter(adapter);
	}
}
