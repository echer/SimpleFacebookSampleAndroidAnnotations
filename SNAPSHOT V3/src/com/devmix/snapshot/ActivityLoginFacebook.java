package com.devmix.snapshot;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.devmix.snapshot.utils.Utils;
import com.orasystems.libs.utils.async.AsyncUtils;
import com.orasystems.libs.utils.async.GenericAsyncTask;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

@SuppressWarnings("deprecation")
@Fullscreen 
@NoTitle 
@EActivity(R.layout.facebook_login)
public class ActivityLoginFacebook extends Activity {
	protected static final String TAG = ActivityLoginFacebook.class.getName();

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
	
	@DrawableRes(R.drawable.ic_launcher)
	public Drawable icon;
	
	/**
	 * ASYNCTASK
	 */
	@Bean(AsyncUtils.class)
	public GenericAsyncTask asyncTask;

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
			asyncTask.getProgressDialog().setMessage("Aguarde processando informações");
		}

		@Override
		public void onException(Throwable throwable) {
			asyncTask.hideProgressBar();
			Intent i = new Intent();
			i.putExtra("exception", throwable);
			setResult(RESULT_LOGIN_FAIL, i);
			finish();
		}

		@Override
		public void onFail(String reason) {
			asyncTask.hideProgressBar();
			Intent i = new Intent();
			i.putExtra("result", reason);
			setResult(RESULT_LOGIN_FAIL, i);
			finish();
		}

		@Override
		public void onNotAcceptingPermissions() {
			asyncTask.hideProgressBar();
			Intent i = new Intent();
			i.putExtra("result", "Permissões recusadas");
			setResult(RESULT_LOGIN_FAIL, i);
			finish();
		}

		@Override
		public void onLogin() {
			asyncTask.hideProgressBar();
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
		asyncTask.showProgressBar(this, "Aguarde", "Aguarde carregando...", 100, icon, false, true, false);
		mSimpleFacebook.login(mOnLoginListener);
	}
}
