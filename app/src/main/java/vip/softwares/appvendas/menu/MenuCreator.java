package vip.softwares.appvendas.menu;

import android.view.Menu;
import android.view.MenuItem;

import vip.softwares.appvendas.R;

/**
 * Created by ccouto on 13/12/2017.
 */

public class MenuCreator {

    public static void create(Menu menu){
        MenuItem itemsinc = menu.add(0, MenuItemEnum.SINCRONIZAR.ordinal(), 0, R.string.itemSinc);
        itemsinc.setIcon(R.drawable.ic_sincronizacao);
        itemsinc.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem itemIni = menu.add(0, MenuItemEnum.INICIAR_VENDA.ordinal(), 0, R.string.itemInicio);
        itemIni.setIcon(R.drawable.ic_compose_inverse);
        itemIni.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem itemComp = menu.add(0, MenuItemEnum.COMPROVANTES.ordinal(), 0, R.string.itemCompr);
        itemComp.setIcon(R.drawable.ic_registration_form_inverse);
        itemComp.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        MenuItem itemEnviar = menu.add(0, MenuItemEnum.ENVIAR_VENDAS.ordinal(), 0, R.string.itemEnviar);
        itemEnviar.setIcon(R.drawable.ic_upload_inverse);
        itemEnviar.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    }
}
