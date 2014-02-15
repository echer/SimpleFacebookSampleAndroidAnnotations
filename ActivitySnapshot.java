package com.devmix.snapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

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
import com.devmix.snapshot.adapter.MenuAdapter;
import com.devmix.snapshot.dao.CidadeManager;
import com.devmix.snapshot.dao.ConfiguracaoCidadeManager;
import com.devmix.snapshot.dao.ConfiguracoesManager;
import com.devmix.snapshot.dao.EstadoManager;
import com.devmix.snapshot.dao.ProfileManager;
import com.devmix.snapshot.fragments.FragmentFollow;
import com.devmix.snapshot.fragments.FragmentFollow_;
import com.devmix.snapshot.fragments.FragmentHome;
import com.devmix.snapshot.fragments.FragmentHome_;
import com.devmix.snapshot.fragments.FragmentProfile;
import com.devmix.snapshot.fragments.FragmentProfile_;
import com.devmix.snapshot.fragments.FragmentSettings;
import com.devmix.snapshot.fragments.FragmentSettings_;
import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.CidadeList;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.ConfiguracaoCidadeList;
import com.devmix.snapshot.model.Configuracoes;
import com.devmix.snapshot.model.Estado;
import com.devmix.snapshot.model.EstadoList;
import com.devmix.snapshot.model.Profile;
import com.devmix.snapshot.utils.Globals;
import com.devmix.snapshot.utils.InterfaceUtils;
import com.devmix.snapshot.utils.Utils;
import com.devmix.snapshot.ws.CidadeWS;
import com.devmix.snapshot.ws.ConfiguracoesWS;
import com.devmix.snapshot.ws.EstadoWS;
import com.devmix.snapshot.ws.ProfileWS;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;
import com.orasystems.libs.utils.async.AsyncUtils;
import com.orasystems.libs.utils.async.GenericAsyncTask;
import com.orasystems.libs.utils.internet.Conexao;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnProfileRequestListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.ProfileFB;
import com.viewpagerindicator.TabPageIndicator;

import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

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
	
	/*
	 * STRING RESOURCES
	 */
	@StringRes(R.string.acvty_snapshot_progress_processando_info)
	public String processandoInfo;
	@StringRes(R.string.acvty_snapshot_progress_estamos_terminando)
	public String estamosTerminando;
	
	/**
	 * FACEBOOK CONFIGURATIONS
	 */
	public String language = "en";
	public String packageName = "com.devmix.facebook.login"; 
	public String applicationId = "207173972801162";
	public String nameSapce = "devmixsnapshot";
	public Permissions[] permissions = new Permissions[] {
	// Permissions.USER_PHOTOS, 
	Permissions.EMAIL // ,
	};
	private final int FACEBOOK_LOGIN_REQUEST_CODE = 123;
	
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
	@Bean(AsyncUtils.class) 
	public GenericAsyncTask asyncTaskWS;
	
	
	private final Handler handler = new Handler();
	
	/**
	 * DAO
	 */
	@Bean
	public ProfileManager profileManager;
	@Bean
	public ConfiguracoesManager configuracoesManager;
	@Bean
	public EstadoManager estadoManager;
	@Bean
	public CidadeManager cidadeManager;
	@Bean
	public ConfiguracaoCidadeManager configuracaoCidadeManager;
	
	/**
	 * REST SERVICE
	 */
	@RestService
	public ProfileWS profileWS;
	@RestService
	public EstadoWS estadoWS;
	@RestService
	public CidadeWS cidadeWS;
	@RestService
	public ConfiguracoesWS configuracoesWS;
	
	/**
	 * INTERFACE UTILS
	 */
	@Bean
	public InterfaceUtils interfaceUtils;
	
	/**
	 * FRAGMENTS
	 */
	public FragmentProfile fragmentProfile;
	public FragmentHome fragmentHome;
	public FragmentSettings fragmentSettings;
	public FragmentFollow fragmentFollow;
	
	private SimpleFacebook mSimpleFacebook;
	
	private Profile profile;
	
	private boolean atualizouCidades = false;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@UiThread
	public void exibeMsgFinish(int stringResId){
		try{
			SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
			.setTitle(R.string.acvty_snapshot_dialog_title)
			.setMessage(getResources().getString(stringResId))
			.setCancelable(false)
			.setPositiveButtonText("Ok")
			.setRequestCode(2)
			.show();
		}catch(Exception ignored){
			finish();
		}
	}
	
	@Background
	public void salvaWS(Profile profile) {
		asyncTaskWS.showProgressBar(this, R.string.acvty_snapshot_progress_title, R.string.acvty_snapshot_progress_realizando_cadastro, 100, icLauncher, false, true, false);
		try{
			if(profile != null){
				//envia profile para webservice
				profile = profileWS.save(profile);
				//salva profile
				try {
					if(profileManager.save(profile)!= -1){ 
						//sucesso
						exibeSucesso();
					}else{
						//erro ao salvar
						exibeMsgFinish(R.string.acvty_snapshot_error);
					}
				} catch (Exception e) {
					e.printStackTrace();
					exibeMsgFinish(R.string.acvty_snapshot_error);
				}
			}else{
				exibeMsgFinish(R.string.acvty_snapshot_error);
			}
		}catch(Exception e){
			e.printStackTrace();
			exibeMsgFinish(R.string.acvty_snapshot_not_working);
		}finally{
			asyncTaskWS.hideProgressBar();
		} 
	}
	
	@UiThread
	public void exibeSucesso() {
		SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
		.setTitle(R.string.acvty_snapshot_dialog_title)
		.setMessage(R.string.acvty_snapshot_cadastro_sucess)
		.setCancelable(false)
		.setPositiveButtonText(R.string.acvty_snapshot_btn_txt_iniciar)
		.setRequestCode(1)
		.show();		
	}

	@UiThread
	public void reinicia() {
		startActivity(new Intent(ActivitySnapshot.this, ActivitySnapshot_.class));
		finish();
	}

	@OnActivityResult(FACEBOOK_LOGIN_REQUEST_CODE)
	public void onResult(int resultCode,Intent data){
		switch (resultCode) {
		case FacebookLogin.RESULT_LOGIN_BACK_PRESSED:
			finish();
			break;
		case FacebookLogin.RESULT_LOGIN_FAIL:
			exibeMsgFinish(R.string.acvty_snapshot_error);
			break;
		case FacebookLogin.RESULT_LOGIN_SUCESS:
			asyncTask.showProgressBar(this, R.string.acvty_snapshot_progress_title, R.string.acvty_snapshot_progress_obtendo_info, 100, icLauncher, false, true, false);
			mSimpleFacebook.getProfile(new OnProfileRequestListener() {
				@Override
				public void onFail(String reason) {
					asyncTask.hideProgressBar();
					exibeMsgFinish(R.string.acvty_snapshot_error);
				}
				
				@Override
				public void onException(Throwable throwable) {
					asyncTask.hideProgressBar();
					exibeMsgFinish(R.string.acvty_snapshot_error);
				}
				
				@Override
				public void onThinking() {
					if(asyncTask.getProgressDialog() != null)asyncTask.getProgressDialog().setMessage(estamosTerminando);
				}
				
				@Override
				public void onComplete(ProfileFB profileFB) { 
					try {
						asyncTask.getProgressDialog().setMessage(processandoInfo);
						ObjectMapper mapper = new ObjectMapper();
						mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
						fragmentProfile.setPerfil(mapper.readValue(mapper.writeValueAsString(profileFB), Profile.class));
						salvaWS(fragmentProfile.getPerfil());
					} catch (JsonGenerationException e) {
						e.printStackTrace();
						exibeMsgFinish(R.string.acvty_snapshot_error);
					} catch (JsonMappingException e) {
						e.printStackTrace();
						exibeMsgFinish(R.string.acvty_snapshot_error);
					} catch (IOException e) {
						e.printStackTrace();
						exibeMsgFinish(R.string.acvty_snapshot_error);
					}finally{
						asyncTask.hideProgressBar();
					}
					
				}
			});

			break;

		default:
			break;
		}
	}
	 
	@AfterInject
	public void afterInject(){
		setTheme(R.style.DialogStyleLight);
		profileWS.setRootUrl(Globals.rootUrlProfileWS);
		estadoWS.setRootUrl(Globals.rootUrlEstadoWS);
		cidadeWS.setRootUrl(Globals.rootUrlCidadeWS);
		configuracoesWS.setRootUrl(Globals.rootUrlConfiguracoesWS);
	}

	@UiThread
	public void init(boolean afterViews){
		Utils.updateLanguage(getApplicationContext(), language);
		Utils.printHashKey(getApplicationContext(), packageName);

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(applicationId).setNamespace(nameSapce)
				.setPermissions(permissions).build();
		SimpleFacebook.setConfiguration(configuration);
 
		mSimpleFacebook = SimpleFacebook.getInstance(this);
		
		if(profile == null){ 
			if(Conexao.conectadoOuConectando(this)){
				startActivityForResult(new Intent(this, FacebookLogin_.class), FACEBOOK_LOGIN_REQUEST_CODE);
			}else{
				exibeMsgFinish(R.string.acvty_snapshot_sem_conexao);
			}
		}
		if(afterViews)afterViews();
		
	}
	
	@AfterViews
	public void afterViews() {
		//This is a workaround for http://b.android.com/15340 from http://stackoverflow.com/a/5852198/132047
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
            bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            getSupportActionBar().setBackgroundDrawable(bg);

            BitmapDrawable bgSplit = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped_split_img);
            bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
        }
		
        profile = null;
		try { 
			profile = profileManager.loadFirst();
		} catch (Exception ignored) {}
        
        Configuracoes configuracoes = null;
        try{
        	configuracoes = configuracoesManager.loadFirst();
        }catch(Exception ignored){}
        
		adapter = new MenuAdapter(getSupportFragmentManager()); 
		
		//MONTA FRAGMENT PROFILE
		fragmentProfile = FragmentProfile_.builder().perfil(profile).build();
		fragmentProfile.setHasOptionsMenu(true);
		 
		//MONTA FRAGMENT SETTINGS
		fragmentSettings = FragmentSettings_.builder().configuracoes(configuracoes).profile(profile).build();
		fragmentSettings.setHasOptionsMenu(true);
		
		//MONTA FRAGMENT HOME
		fragmentHome = FragmentHome_.builder().build();
		fragmentHome.setHasOptionsMenu(true);
		
		//MONTA FRAGMENT FOLLOW 
		List<ConfiguracaoCidade> listCC = new ArrayList<ConfiguracaoCidade>();
		if(configuracoes != null){
			for(ConfiguracaoCidade cc:configuracoes.getCidades()){
				listCC.add(cc);
			}
		}
		ConfiguracaoCidadeList ccList = new ConfiguracaoCidadeList(listCC);
		fragmentFollow = FragmentFollow_.builder().cidades(ccList).profile(profile).build();
		fragmentFollow.setHasOptionsMenu(true);
		
		List<Fragment> fragments = new ArrayList<Fragment>();  
		fragments.add(fragmentHome);
		fragments.add(fragmentFollow);
		fragments.add(fragmentSettings);  
		fragments.add(fragmentProfile);
		   
		adapter.setmFragments(fragments); 
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);

		changeColor(currentColor);
		
		try {
			if(estadoManager.count() < 1 || cidadeManager.count() < 1){
				if(Conexao.conectadoOuConectando(this)){
					startSincronismoCidadeEstado();
				}else{
					exibeMsgFinish(R.string.acvty_snapshot_sem_conexao);
				}
			}else{
				if(!atualizouCidades)init(false);
			}
		} catch (Exception ignored) {}
	}

	@Background
	public void startSincronismoCidadeEstado(){
		asyncTask.showProgressBar(this, R.string.acvty_snapshot_progress_title, R.string.frag_settings_atualizando_cidades_estados, 100, icLauncher, false, true, false);
		try{
			EstadoList estadoList = estadoWS.list();
			for(Estado e:estadoList.getEstados()){
				estadoManager.saveIfNotExist(e);
			}
			CidadeList cidadeList = cidadeWS.list();
			for(Cidade c:cidadeList.getCidades()){
				cidadeManager.saveIfNotExist(c);
			}
			atualizouCidades = true;
			init(true);
		}catch(Exception e){
			e.printStackTrace();
			exibeMsgFinish(R.string.frag_settings_cidades_estados_fail);
		}finally{
			asyncTask.hideProgressBar();
		}
		
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
		case 1:
			reinicia();
			break;
		case 2:
			finish();
			break;
		case 3:
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					deleteCidade(fragmentSettings.getCidadeExcluir());
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
				fragmentSettings.reloadCidades();
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
