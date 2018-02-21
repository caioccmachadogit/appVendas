package vip.softwares.appvendas.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.atualizacao.AtualizacaoFragment;
import vip.softwares.appvendas.autorizacao.FormAuthFragment;
import vip.softwares.appvendas.comprovantes.ListComprovantesFragment;
import vip.softwares.appvendas.envios.EnvioFragment;
import vip.softwares.appvendas.utils.menu.DrawerItem;
import vip.softwares.appvendas.utils.menu.MenuUtil;
import vip.softwares.appvendas.utils.network.ControleConexao;
import vip.softwares.appvendas.utils.shared.SharedPreferencesUtil;
import vip.softwares.appvendas.vendas.VendasFragment;


public class MainActivity extends AppCompatActivity {

    //=======MENU==========================
    private List<DrawerItem> dataList;
    private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
    private MenuUtil menu = new MenuUtil();
	public static int SELECTED_ITEM = 1;
    //=======MENU==========================
	private final static String TAG_FRAGMENT_VENDA = "VENDA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		ConfigurarMenu();
		TextView tv_titulo = (TextView) findViewById(R.id.tv_titulo);
		if(!SharedPreferencesUtil.loadSavedPreferencesString("NOME_FANTASIA", this).isEmpty()){
			tv_titulo.setText(SharedPreferencesUtil.loadSavedPreferencesString("NOME_FANTASIA", this));
			SelectItem(SELECTED_ITEM);
		}
		else
			tv_titulo.setText("Valide seu Dispositivo!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // ==========PADRÃO PARA CRIAR MENU===============================

	private void ConfigurarMenu() {
		PreencherListMenu();
		mTitle = mDrawerTitle = getTitle();
		menu.Inicializar((DrawerLayout) findViewById(R.id.drawer_layout),
				(ListView) findViewById(R.id.left_drawer), this, dataList);
		menu.mDrawerList
				.setOnItemClickListener(new DrawerItemClickListener());

//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this,
				menu.mDrawerLayout, R.drawable.ic_drawer,
				R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
//				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
//				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		menu.mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void PreencherListMenu() {
		dataList = new ArrayList<DrawerItem>();
		// Add Drawer Item to dataList
		dataList.add(new DrawerItem("Sincronizar", R.drawable.ic_sincronizacao));
		dataList.add(new DrawerItem("Iniciar Venda", R.drawable.ic_compose_inverse));
		dataList.add(new DrawerItem("Comprovantes", R.drawable.ic_registration_form_inverse));
		dataList.add(new DrawerItem("Enviar Vendas", R.drawable.ic_upload_inverse));
	}

	@SuppressLint("NewApi")
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		if(title.equals("Iniciar Venda"))
			mTitle = "Venda Externa";

//		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);

		}
	}

    public void SelectItem(int possition) {
        Fragment fragment = null;
		FragmentManager frgManager;
		switch (possition) {
            /*case 0: // FORM AUTH
				if(SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", this).isEmpty()){
					fragment = new FormAuthFragment();
					frgManager = getFragmentManager();
					frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
				}
				else
					Toast.makeText(this, "Dispositivo Já Validado!", Toast.LENGTH_SHORT).show();
			break;*/
			case 0: //ATUALIZAÇÕES
				if(!SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", this).isEmpty()){
					if(!ControleConexao.checkNetworkInterface(this).equals("none")){
						fragment = new AtualizacaoFragment();
						frgManager = getFragmentManager();
						frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
					}
					else
					Toast.makeText(this, "Verifique sua Conexão com a Internet e tente novamente!", Toast.LENGTH_SHORT).show();
				}
				else
				Toast.makeText(this, "Necessário Validar seu dispositivo!", Toast.LENGTH_SHORT).show();
			break;
			case 1: //INICIAR VENDA
				if(!SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", this).isEmpty()){
					fragment = new VendasFragment();
					frgManager = getFragmentManager();
					frgManager.beginTransaction().replace(R.id.content_frame, fragment,TAG_FRAGMENT_VENDA).commit();
				}
				else
				Toast.makeText(this, "Necessário Validar seu dispositivo!", Toast.LENGTH_SHORT).show();
			break;
			case 2: //COMPROVANTES
				if(!SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", this).isEmpty()){
					fragment = new ListComprovantesFragment();
					frgManager = getFragmentManager();
					frgManager.beginTransaction().replace(R.id.content_frame, fragment,TAG_FRAGMENT_VENDA).commit();
				}
				else
				Toast.makeText(this, "Necessário Validar seu dispositivo!", Toast.LENGTH_SHORT).show();
			break;
			case 3: //ENVIAR VENDAS
				if(!SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", this).isEmpty()){
					if(!ControleConexao.checkNetworkInterface(this).equals("none")){
						fragment = new EnvioFragment();
						frgManager = getFragmentManager();
						frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
					}
					else
					Toast.makeText(this, "Verifique sua Conexão com a Internet e tente novamente!", Toast.LENGTH_SHORT).show();
				}
				else
				Toast.makeText(this, "Necessário Validar seu dispositivo!", Toast.LENGTH_SHORT).show();
			break;
        	default:
			break;
        }
        menu.mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		menu.mDrawerLayout.closeDrawer(menu.mDrawerList);
    }
	// ==========PADRÃO PARA CRIAR MENU===============================


	@Override
	public void onBackPressed() {
		FragmentManager frgManager = getFragmentManager();
		final VendasFragment fragmentVenda = (VendasFragment) frgManager.findFragmentByTag(TAG_FRAGMENT_VENDA);

		if (fragmentVenda.AllowBackPressed()) {
			super.onBackPressed();
		}
	}
}
