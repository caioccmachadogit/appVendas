package vip.softwares.appvendas.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import vip.softwares.appvendas.R;
import vip.softwares.appvendas.atualizacao.AtualizacaoFragment;
import vip.softwares.appvendas.comprovantes.ListComprovantesFragment;
import vip.softwares.appvendas.envios.EnvioFragment;
import vip.softwares.appvendas.menu.MenuCreator;
import vip.softwares.appvendas.menu.MenuItemEnum;
import vip.softwares.appvendas.utils.network.ControleConexao;
import vip.softwares.appvendas.utils.shared.SharedPreferencesUtil;
import vip.softwares.appvendas.vendas.VendasFragment;


public class MainActivity extends AppCompatActivity {

    //=======MENU==========================
	public static int SELECTED_ITEM = 1;
	private DrawerLayout drawerLayout;
    //=======MENU==========================
	private final static String TAG_FRAGMENT_VENDA = "VENDA";
	private NavigationView navigationView;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		ConfigurarMenu();
		loadIniciarVenda();
    }

	//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    // ==========PADRÃO PARA CRIAR MENU===============================

	private void ConfigurarMenu() {
		setUpToolbar();
		setupNavDrawer();
	}

	// Configura a Toolbar
	protected void setUpToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
	}

	// Configura o Nav Drawer
	protected void setupNavDrawer() {
		// Drawer Layout
		final ActionBar actionBar = getSupportActionBar();
		// Ícone do menu do nav drawer
		actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
		actionBar.setDisplayHomeAsUpEnabled(true);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navigationView = (NavigationView) findViewById(R.id.nav_view);
		if (navigationView != null && drawerLayout != null) {
			// Atualiza a imagem e textos do header
//			 setHeader(navigationView, R.id.containerNavDrawerListViewHeader);
			// Trata o evento de clique no menu.
			navigationView.setNavigationItemSelectedListener(
					new NavigationView.OnNavigationItemSelectedListener() {
						@Override
						public boolean onNavigationItemSelected(MenuItem menuItem) {
							// Seleciona a linha
							menuItem.setChecked(true);
							// Fecha o menu
							drawerLayout.closeDrawers();
							// Trata o evento do menu
							onNavDrawerItemSelected(menuItem);
							return true;
						}
					});
		}

		Menu menu = navigationView.getMenu();
		MenuCreator.create(menu);
		navigationView.invalidate();
	}

	private void setHeader(NavigationView navigationView, int containerNavDrawerListViewHeader) {
		View view = navigationView.findViewById(containerNavDrawerListViewHeader);

		if(!SharedPreferencesUtil.loadSavedPreferencesString("NOME_FANTASIA", this).isEmpty()){
			TextView tvUser = (TextView) view.findViewById(R.id.tvUser);
			tvUser.setText(SharedPreferencesUtil.loadSavedPreferencesString("NOME_FANTASIA", this));
		}
	}

	// Trata o evento do menu lateral
	private void onNavDrawerItemSelected(MenuItem menuItem) {
		Log.d("onNavDrawerItemSelected", String.valueOf(menuItem.getItemId()));
		MenuItemEnum itemEnum = MenuItemEnum.values()[menuItem.getItemId()];

		Fragment fragment = null;
		FragmentManager frgManager;

		switch (itemEnum) {
			case SINCRONIZAR:
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
			case INICIAR_VENDA:
					loadIniciarVenda();
				break;
			case COMPROVANTES:
				if(!SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", this).isEmpty()){
					fragment = new ListComprovantesFragment();
					frgManager = getFragmentManager();
					frgManager.beginTransaction().replace(R.id.content_frame, fragment,TAG_FRAGMENT_VENDA).commit();
				}
				else
					Toast.makeText(this, "Necessário Validar seu dispositivo!", Toast.LENGTH_SHORT).show();
				break;
			case ENVIAR_VENDAS:
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
		}
	}

	private void loadIniciarVenda() {
		if(!SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", this).isEmpty()){
			Fragment fragment = new VendasFragment();
			FragmentManager frgManager = getFragmentManager();
			frgManager.beginTransaction().replace(R.id.content_frame, fragment,TAG_FRAGMENT_VENDA).commit();
		}
		else
			Toast.makeText(this, "Necessário Validar seu dispositivo!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		setHeader(navigationView,R.id.containerNavDrawerListViewHeader);
		switch (item.getItemId()) {
			case android.R.id.home:
				// Trata o clique no botão que abre o menu.
				if (drawerLayout != null) {
					openDrawer();
					return true;
				}
		}
		return super.onOptionsItemSelected(item);
	}

	// Abre o menu lateral
	protected void openDrawer() {
		if (drawerLayout != null) {
			drawerLayout.openDrawer(GravityCompat.START);
		}
	}

	// Fecha o menu lateral
	protected void closeDrawer() {
		if (drawerLayout != null) {
			drawerLayout.closeDrawer(GravityCompat.START);
		}
	}

	// ==========PADRÃO PARA CRIAR MENU===============================


	@Override
	public void onBackPressed() {
//		FragmentManager frgManager = getFragmentManager();
//		final VendasFragment fragmentVenda = (VendasFragment) frgManager.findFragmentByTag(TAG_FRAGMENT_VENDA);
//
//		if (fragmentVenda.AllowBackPressed()) {
//			super.onBackPressed();
//		}
	}
}
