package vip.softwares.appvendas.atualizacao.request;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import vip.softwares.appvendas.utils.network.VarConfig;

/**
 * Created by re032629 on 20/07/2015.
 */
public class RequestAtualizacao {
    private final String URL = VarConfig.URLWS;

    public String BuscarProdClien(String codClien) throws Exception {
		InputStream inputStream = null;
        String result = "";
		try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(VarConfig.GetBuscarProdClien);

            // 3. string In
            String jsonIn = codClien;

             // 4. set json to StringEntity
            StringEntity se = new StringEntity(jsonIn);

            // 5. set httpPost Entity
            httpPost.setEntity(se);
            // 6. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 7. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
//HTTP/1.1 400 Bad Request

            // 8. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 9. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
		}
		catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
			throw e;
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
