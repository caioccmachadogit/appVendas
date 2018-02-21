package vip.softwares.appvendas.envios.request;

import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import vip.softwares.appvendas.utils.network.VarConfig;

/**
 * Created by re032629 on 26/07/2015.
 */
public class RequestEnvio {

        public String GravarVenda(String jsonVendasIn) throws Exception {
            String result = "";
            HttpURLConnection urlConnection=null;

            try{
                URL url = new URL(VarConfig.PostGravarVenda);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json");

                urlConnection.connect();

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonVendasIn);
                out.close();

                int HttpResult = urlConnection.getResponseCode();
                if(HttpResult == HttpURLConnection.HTTP_OK){
                    result = convertInputStreamToString(urlConnection.getInputStream());
                }
                else{
                     result = "Did not work!";
                }
            }
            catch (MalformedURLException e) {
                Log.e("InputStream", e.getLocalizedMessage());
                throw e;
            }
            finally{
                if(urlConnection!=null)
                urlConnection.disconnect();
            }
            return result;
        }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
