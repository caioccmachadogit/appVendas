package vip.softwares.appvendas.utils.shared;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by re032629 on 20/08/2015.
 */
public class CompartilharUtil {

    public static void CompartilharPrintScreen(Activity mActivity, String print) {
		try {
			Intent sharedIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);

			final String PATH = PrintScreenUtil.PATHPRINT;

			sharedIntent.setType("text/plain");

			ArrayList<Uri> uris = new ArrayList<Uri>();
			uris.add(Uri.parse("file://" + PATH + print));

			sharedIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

			sharedIntent.setType("image/png");

			mActivity.startActivityForResult(
					Intent.createChooser(sharedIntent, "Compartilhar:"),
					PrintScreenUtil.SEND_EMAIL);
		} catch (Exception e) {
			Log.e("Compartilhar", e.getMessage(), e);
		}
	}
}
