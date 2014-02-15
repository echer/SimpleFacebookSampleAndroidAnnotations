package com.devmix.snapshot;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.rest.RestService;

import android.annotation.SuppressLint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.devmix.snapshot.adapter.MenuAdapter;
import com.devmix.snapshot.dao.ConfiguracaoCidadeManager;
import com.devmix.snapshot.dao.ConfiguracoesManager;
import com.devmix.snapshot.dao.ProfileManager;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.Configuracoes;
import com.devmix.snapshot.model.Profile;
import com.devmix.snapshot.utils.Globals;
import com.devmix.snapshot.utils.InterfaceUtils;
import com.devmix.snapshot.ws.ConfiguracoesWS;
import com.orasystems.libs.utils.async.AsyncUtils;
import com.orasystems.libs.utils.async.GenericAsyncTask;
import com.viewpagerindicator.TabPageIndicator;

import eu.inmite.android.lib.dialogs.ISimpleDialogListener;

@EActivity(R.layout.activity_snapshot)   
public class ActivitySnapshot extends SherlockFragmentActivity implements ISimpleDialogListener{
   
	/**  
	 * DRAWABLE RESOURCES
	 */
	@DrawableRes(R.drawable.ic_action_person)
	public Drawable icActionPerson;    
	@DrawableRes(R.drawable.ic_store)
	public Drawable icActionStore;  
	@DrawableRes(R.drawable.ic_home)
	public Drawable icActionHome;
	@DrawableRes(R.drawable.ic_settings)
	public Drawable icSettings;
	@DrawableRes
	public Drawable icLauncher;
	
	
	
	@ViewById
	public TabPageIndicator tabs;
	@ViewById
	public ViewPager pager;
	private MenuAdapter adapter;
	private int currentColor = 0xFF666666;
	private Drawable oldBackground = null;
	
	/**
	 * ASYNCTASK
	 */
	@Bean(AsyncUtils.class)
	public GenericAsyncTask asyncTask;
	
	
	private final Handler handler = new Handler();
	
	/**
	 * DAO
	 */
	@Bean
	public ProfileManager profileManager;
	@Bean
	public ConfiguracoesManager configuracoesManager;
	@Bean
	public ConfiguracaoCidadeManager configuracaoCidadeManager;
	
	/**
	 * REST SERVICE
	 */
	@RestService
	public ConfiguracoesWS configuracoesWS;
	
	/**
	 * INTERFACE UTILS
	 */
	@Bean
	public InterfaceUtils interfaceUtils;
	
	private Profile profile;
	private Configuracoes configuracoes;
	
	@AfterInject
	public void afterInject(){
		setTheme(R.style.DialogStyleLight);
		configuracoesWS.setRootUrl(Globals.rootUrlConfiguracoesWS);
	}

	@AfterViews
	public void afterViews() {
		
		profile = null;
		try { 
			profile = profileManager.loadFirst();
		} catch (Exception ignored) {
			finish();
		}
		
		try{
        	configuracoes = configuracoesManager.loadFirst();
        }catch(Exception ignored){}
		
		
		//This is a workaround for http://b.android.com/15340 from http://stackoverflow.com/a/5852198/132047
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
            bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            getSupportActionBar().setBackgroundDrawable(bg);

            BitmapDrawable bgSplit = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped_split_img);
            bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
        }
		
		adapter = new MenuAdapter(getSupportFragmentManager()); 
		adapter.setConfiguracoes(configuracoes);
		adapter.setProfile(profile);
		
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);

		changeColor(currentColor);
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

	@Override
	public void onPositiveButtonClicked(int requestCode) {
		switch (requestCode) {
		case 3:
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					//deleteCidade(fragmentSettings.getCidadeExcluir());
				}
			}, 1000);
			break;
		default:
			break;
		}
		
	}
	
	@Background
	public void deleteCidade(ConfiguracaoCidade cidadeExcluir) {
		asyncTask.showProgressBar(this, R.string.acvty_snapshot_progress_title, R.string.acvty_snapshot_excluindo_cidade, 100, icLauncher, false, true, false);
		try{
			if(configuracoesWS.deleteCidade(cidadeExcluir.getCidade(), profile.getId()) 
					&& configuracaoCidadeManager.delete(cidadeExcluir) != -1){
				interfaceUtils.exibeToast(R.string.acvty_snapshot_cidade_excluida);
				//fragmentSettings.reloadCidades();
			}else{
				interfaceUtils.exibeToast(R.string.acvty_snapshot_cidade_excluir_erro);
			}
		}catch(Exception e){
			e.printStackTrace();
			interfaceUtils.exibeToast(R.string.acvty_snapshot_cidade_excluir_erro);
		}finally{
			asyncTask.hideProgressBar();
		}
	}

	@Override
	public void onNegativeButtonClicked(int requestCode) {
		
	}

}
