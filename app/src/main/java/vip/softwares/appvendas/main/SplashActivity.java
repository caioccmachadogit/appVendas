package vip.softwares.appvendas.main;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import vip.softwares.appvendas.R;
import vip.softwares.appvendas.autorizacao.FormAuthActivity;
import vip.softwares.appvendas.autorizacao.FormAuthFragment;
import vip.softwares.appvendas.repository.AsyncREP;
import vip.softwares.appvendas.repository.ClienteREP;
import vip.softwares.appvendas.repository.ProdutoREP;
import vip.softwares.appvendas.repository.VendaREP;
import vip.softwares.appvendas.utils.db.GerenciadorDB;
import vip.softwares.appvendas.utils.shared.SharedPreferencesUtil;


public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSIONS_EXTERNAL_STORAGE = 2;
    private Thread mSplashThread;
    private boolean mblnClicou = false;

    /** Evento chamado quando a activity � executada pela primeira vez */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.d("checkPermission", "PERMITIDO");
            }
            else {
                Log.d("checkPermission", "NAO PERMITIDO");
                requestPermission();
            }
        }
        else {
            Log.d("checkPermission", "PERMISSAO NAO REQUERIDA. VERSAO ANDROID < 23");
        }
    }

    private void startSplash(){
        //thread para mostrar uma tela de Splash
        mSplashThread = new Thread() {
            @Override
            public void run() {
             try {
                    synchronized(this){
                        //Espera por 5 segundos ou sai quando
                     //o usu�rio tocar na tela
                        wait(4000);
                        mblnClicou = true;
                    }
                }
                catch(InterruptedException ex){
                }

                if (mblnClicou){
                	try {
                		/*----------------------------------------------------------------------------------------------------------------------------------------*/
                		// **** CARREGA UM ARRAY DE QUERYS PARA EXECU��O NO MOMENTO DE CRIA��O DO BANCO DE DADOS
                        GerenciadorDB.QUERY_CREATE_BANCO_DE_DADOS.add(ProdutoREP.createTable);
                        GerenciadorDB.QUERY_CREATE_BANCO_DE_DADOS.add(ClienteREP.createTable);
                        GerenciadorDB.QUERY_CREATE_BANCO_DE_DADOS.add(VendaREP.createTable);
                        GerenciadorDB.QUERY_CREATE_BANCO_DE_DADOS.add(AsyncREP.createTable);
                        // **** CRIA DIR EXTERNO E BANCO DE DADOS
                		GerenciadorDB ger = new GerenciadorDB();
                		ger.criarDB(SplashActivity.this);
                		/*----------------------------------------------------------------------------------------------------------------------------------------*/
                     //fechar a tela de Splash
                        finish();

                        Intent i = new Intent();
                        if(SharedPreferencesUtil.loadSavedPreferencesString("COD_AUTH", SplashActivity.this).isEmpty()){
                            i.setClass(SplashActivity.this, FormAuthActivity.class);
                        }
                        else {
                            //Carrega a Activity Principal
                             i.setClass(SplashActivity.this, MainActivity.class);
                        }
                        startActivity(i);
					}
                	catch (Exception e) {

					}

                }
            }
        };

        mSplashThread.start();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
//        }
//        else {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_EXTERNAL_STORAGE);
//            Log.d("requestPermission", "REQUERIR PERMISSAO");
//        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_EXTERNAL_STORAGE);
        Log.d("requestPermission", "REQUERIR PERMISSAO");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionResult", "PERMISSAO CONCEDIDA");
                    startSplash();
                }
                else {
                    Log.d("PermissionResult", "PERMISSAO NAO CONCEDIDA");
                }
                break;
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();

        //garante que quando o usu�rio clicar no bot�o
        //"Voltar" o sistema deve finalizar a thread
        if(mSplashThread != null)
            mSplashThread.interrupt();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mSplashThread != null){
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //o m�todo abaixo est� relacionado a thread de splash
                synchronized(mSplashThread){
                    mblnClicou = true;

                    //o m�todo abaixo finaliza o comando wait
                    //mesmo que ele n�o tenha terminado sua espera
                    mSplashThread.notifyAll();
                }
            }
        }
        return true;
    }
}
