package com.devmix.snapshot;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.devmix.snapshot.utils.Utils;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.Fullscreen;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

@Fullscreen 
@NoTitle
@EActivity(R.layout.facebook_login)
public class FacebookLogin extends Activity {
	protected static final String TAG = FacebookLogin.class.getName();

	public static final int RESULT_LOGIN_SUCESS = 1;
	public static final int RESULT_LOGIN_FAIL = -1;
	public static final int RESULT_LOGIN_BACK_PRESSED = -2;
	@Extra
	public int iconId;
	@Extra
	public String strProfileName;
	@Extra
	public String appName;
	@Extra
	public String applicationId = "207173972801162";
	@Extra
	public String nameSapce = "devmixsnapshot";
	@StringRes
	public String permissaoNegada;
	@Extra
	public String language = "en";
	@Extra
	public String packageName = "com.devmix.facebook.login";
	@ViewById
	public ImageView splashIcon;
	@ViewById
	public TextView profileName;
	@ViewById
	public TextView splashAppName;

	private SimpleFacebook mSimpleFacebook;
	@Extra
	public Permissions[] permissions = new Permissions[] {
	// Permissions.USER_PHOTOS,
	Permissions.EMAIL // ,
	};

	// Permissions.PUBLISH_ACTION };

	@Override
	public void onBackPressed() {
		setResult(RESULT_LOGIN_BACK_PRESSED);
		finish();
	}

	// Login listener
	private OnLoginListener mOnLoginListener = new OnLoginListener() {
		@Override
		public void onThinking() {
			Log.i("Chamou", "Chamou");
		}

		@Override
		public void onException(Throwable throwable) {
			Intent i = new Intent();
			i.putExtra("exception", throwable);
			setResult(RESULT_LOGIN_FAIL, i);
			finish();
		}

		@Override
		public void onFail(String reason) {
			Intent i = new Intent();
			i.putExtra("result", reason);
			setResult(RESULT_LOGIN_FAIL, i);
			finish();
		}

		@Override
		public void onNotAcceptingPermissions() {
			Intent i = new Intent();
			i.putExtra("result", "");
			setResult(RESULT_LOGIN_FAIL, i);
			finish();
		}

		@Override
		public void onLogin() {
			setResult(RESULT_LOGIN_SUCESS);
			finish();
		}
	};

	@AfterViews
	public void afterViews() {

		setResult(RESULT_CANCELED);

		Utils.updateLanguage(getApplicationContext(), language);
		Utils.printHashKey(getApplicationContext(), packageName);

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(applicationId).setNamespace(nameSapce)
				.setPermissions(permissions).build();
		SimpleFacebook.setConfiguration(configuration);

		mSimpleFacebook = SimpleFacebook.getInstance(this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Login example.
	 */
	@Click
	public void loginButton() {
		mSimpleFacebook.login(mOnLoginListener);
	}
}
