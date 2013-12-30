package com.devmix.snapshot;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.devmix.snapshot.dao.ProfileManager;
import com.devmix.snapshot.fragments.FragmentProfile;
import com.devmix.snapshot.fragments.FragmentProfile_;
import com.devmix.snapshot.fragments.FragmentSettings;
import com.devmix.snapshot.fragments.FragmentStore;
import com.devmix.snapshot.fragments.MenuAdapter;
import com.devmix.snapshot.utils.Utils;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Fullscreen;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.OnActivityResult;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.DrawableRes;
import com.orasystems.libs.utils.async.AsyncUtils;
import com.orasystems.libs.utils.async.GenericAsyncTask;
import com.orasystems.libs.utils.internet.Conexao;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnProfileRequestListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.viewpagerindicator.TabPageIndicator;


@NoTitle
@Fullscreen
@EActivity(R.layout.activity_snapshot)
public class ActivitySnapshot extends SherlockFragmentActivity {
 
	@DrawableRes(R.drawable.ic_action_person)
	public Drawable icActionPerson;
	@DrawableRes(R.drawable.ic_store)
	public Drawable icActionStore;
	@DrawableRes(R.drawable.ic_home)
	public Drawable icActionHome;
	@DrawableRes(R.drawable.ic_settings)
	public Drawable icSettings;
	
	public String language = "en";
	public String packageName = "com.devmix.facebook.login";
	public String applicationId = "207173972801162";
	public String nameSapce = "devmixsnapshot";
	public Permissions[] permissions = new Permissions[] {
	// Permissions.USER_PHOTOS,
	Permissions.EMAIL // ,
	};
	
	@ViewById
	public TabPageIndicator tabs;
	@ViewById
	public ViewPager pager;
	private MenuAdapter adapter;
	private int currentColor = 0xFF666666;
	private Drawable oldBackground = null;
	private final int FACEBOOK_LOGIN_REQUEST_CODE = 123;
	
	/**
	 * ASYNCTASK
	 */
	@Bean(AsyncUtils.class)
	public GenericAsyncTask asyncTask;
	
	private final Handler handler = new Handler();
	private Profile profile;
	
	/**
	 * DAO
	 */
	@Bean
	public ProfileManager profileManager;
	
	/**
	 * FRAGMENTS
	 */
	public FragmentProfile fragmentProfile;
	public FragmentSettings fragmentSettings;
	public FragmentStore fragmentStore;
	
	private SimpleFacebook mSimpleFacebook;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	@OnActivityResult(FACEBOOK_LOGIN_REQUEST_CODE)
	public void onResult(int resultCode,Intent data){
		switch (resultCode) {
		case FacebookLogin.RESULT_LOGIN_BACK_PRESSED:
			finish();
			break;
		case FacebookLogin.RESULT_LOGIN_FAIL:
			
			break;
		case FacebookLogin.RESULT_LOGIN_SUCESS:
			
			mSimpleFacebook.getProfile(new OnProfileRequestListener() {
				@Override
				public void onFail(String reason) {
					asyncTask.hideProgressBar();
				}
				
				@Override
				public void onException(Throwable throwable) {
					asyncTask.hideProgressBar();
				}
				
				@Override
				public void onThinking() {
				}
				
				@Override
				public void onComplete(Profile profile) {
					asyncTask.hideProgressBar();
					
					if(profile != null){
						//envia profile para webservice
						
						//salva profile
						try {
							if(profileManager.save(profile)!= -1){
								//sucesso
								
							}else{
								//erro ao salvar
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					startActivity(new Intent(ActivitySnapshot.this, ActivitySnapshot_.class));
					finish();
				}
			});

			break;

		default:
			break;
		}
	}
	
	@AfterViews
	public void afterViews(){
		
		Utils.updateLanguage(getApplicationContext(), language);
		Utils.printHashKey(getApplicationContext(), packageName);

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(applicationId).setNamespace(nameSapce)
				.setPermissions(permissions).build();
		SimpleFacebook.setConfiguration(configuration);

		mSimpleFacebook = SimpleFacebook.getInstance(this);
		
		try {
			profile = profileManager.loadFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(profile == null){
			if(Conexao.conectadoOuConectando(this)){
				startActivityForResult(new Intent(this, FacebookLogin_.class), FACEBOOK_LOGIN_REQUEST_CODE);
			}else{
				//SEM CONEXAO COM A INTERNET
				finish();
			}
		}
		
		//This is a workaround for http://b.android.com/15340 from http://stackoverflow.com/a/5852198/132047
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
            bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            getSupportActionBar().setBackgroundDrawable(bg);

            BitmapDrawable bgSplit = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped_split_img);
            bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
        }
        
        /*adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);*/
		
		adapter = new MenuAdapter(getSupportFragmentManager());
		fragmentProfile = FragmentProfile_.builder().perfil(profile).build();
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(fragmentProfile);
		fragments.add(new Fragment());
		fragments.add(new Fragment());
		fragments.add(new Fragment());
		
		adapter.setmFragments(fragments);
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);

		changeColor(currentColor);
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Save")
        .setIcon(R.drawable.ic_action_person)
        .setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return false;
			}
		})
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("Search")
            .setIcon(R.drawable.ic_search)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
        menu.add("Search")
        .setIcon(R.drawable.ic_search)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add("Refresh")
            .setIcon(R.drawable.ic_refresh)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }
	
	@SuppressLint("NewApi")
	private void changeColor(int newColor) {
		if(newColor < 0)return;

		//tabs.setIndicatorColor(newColor);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}

		currentColor = newColor;
	}
	
	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@SuppressLint("NewApi")
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};
}
