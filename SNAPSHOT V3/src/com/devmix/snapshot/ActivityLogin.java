package com.devmix.snapshot;

import java.io.IOException;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.devmix.snapshot.dao.CidadeManager;
import com.devmix.snapshot.dao.EstadoManager;
import com.devmix.snapshot.dao.ProfileManager;
import com.devmix.snapshot.model.Cidade;
import com.devmix.snapshot.model.CidadeList;
import com.devmix.snapshot.model.Estado;
import com.devmix.snapshot.model.EstadoList;
import com.devmix.snapshot.model.Profile;
import com.devmix.snapshot.utils.Globals;
import com.devmix.snapshot.utils.Utils;
import com.devmix.snapshot.ws.CidadeWS;
import com.devmix.snapshot.ws.EstadoWS;
import com.devmix.snapshot.ws.ProfileWS;
import com.orasystems.libs.utils.async.AsyncUtils;
import com.orasystems.libs.utils.async.GenericAsyncTask;
import com.orasystems.libs.utils.internet.Conexao;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnProfileRequestListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.ProfileFB;

import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

@SuppressWarnings("deprecation")
@EActivity(R.layout.activity_login)
@NoTitle
@Fullscreen
public class ActivityLogin extends SherlockFragmentActivity implements ISimpleDialogListener{

	private Profile profile;
	
	private final int FACEBOOK_LOGIN_REQUEST_CODE = 123;
	
	private SimpleFacebook mSimpleFacebook;
	
	@DrawableRes
	public Drawable icLauncher;
	
	/**
	 * REST SERVICE
	 */
	@RestService
	public ProfileWS profileWS;
	@RestService
	public EstadoWS estadoWS;
	@RestService
	public CidadeWS cidadeWS;
	
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
	
	/**
	 * ASYNCTASK
	 */
	@Bean(AsyncUtils.class)
	public GenericAsyncTask asyncTask;
	@Bean(AsyncUtils.class) 
	public GenericAsyncTask asyncTaskWS;
	
	/**
	 * DAO
	 */
	@Bean
	public ProfileManager profileManager;
	@Bean
	public EstadoManager estadoManager;
	@Bean
	public CidadeManager cidadeManager;
	
	@AfterInject
	public void afterInject(){
		setTheme(R.style.DialogStyleLight);
		profileWS.setRootUrl(Globals.rootUrlProfileWS);
		estadoWS.setRootUrl(Globals.rootUrlEstadoWS);
		cidadeWS.setRootUrl(Globals.rootUrlCidadeWS);
	}
	
	@AfterViews
	public void afterViews(){
		
		try{
			Utils.updateLanguage(getApplicationContext(), language);
			Utils.printHashKey(getApplicationContext(), packageName);
	
			SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
					.setAppId(applicationId).setNamespace(nameSapce)
					.setPermissions(permissions).build();
			SimpleFacebook.setConfiguration(configuration);
			mSimpleFacebook = SimpleFacebook.getInstance(this);
		}catch(Exception ex){
			finish();
		}
		
		profile = null;
		try { 
			profile = profileManager.loadFirst();
		} catch (Exception ignored) {}
		
		if(profile == null){ 
			if(Conexao.conectadoOuConectando(this)){
				startActivityForResult(new Intent(this, ActivityLoginFacebook_.class), FACEBOOK_LOGIN_REQUEST_CODE);
			}else{
				exibeMsgFinish(R.string.acvty_snapshot_sem_conexao);
			}
			return;
		}
		
		try {
			if(estadoManager.count() < 1 || cidadeManager.count() < 1){
				if(Conexao.conectadoOuConectando(this)){
					startSincronismoCidadeEstado();
				}else{
					exibeMsgFinish(R.string.acvty_snapshot_sem_conexao);
				}
			}else{
				exibeSucesso();
			}
		} catch (Exception ignored) {
			exibeMsgFinish(R.string.acvty_snapshot_sem_conexao);
			return;
		}
		
		startActivity(new Intent(this, ActivitySnapshot_.class));
		finish();
	}
	
	@OnActivityResult(FACEBOOK_LOGIN_REQUEST_CODE)
	public void onResult(int resultCode,Intent data){
		switch (resultCode) {
		case ActivityLoginFacebook.RESULT_LOGIN_BACK_PRESSED:
			finish();
			break;
		case ActivityLoginFacebook.RESULT_LOGIN_FAIL:
			exibeMsgFinish(R.string.acvty_snapshot_error);
			break;
		case ActivityLoginFacebook.RESULT_LOGIN_SUCESS:
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
						Profile profile = mapper.readValue(mapper.writeValueAsString(profileFB), Profile.class);
						salvaWS(profile);
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
			exibeMsgFinish(R.string.acvty_snapshot_not_working);
			break;
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
						reinicia();
						//exibeSucesso();
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
	
	private void reinicia() {
		ActivityLogin.this.finish();
		startActivity(new Intent(this, ActivityLogin_.class));
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
		}catch(Exception e){
			e.printStackTrace();
			exibeMsgFinish(R.string.frag_settings_cidades_estados_fail);
		}finally{
			asyncTask.hideProgressBar();
			reinicia();
		}
		
	} 
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(mSimpleFacebook == null){
			finish();
			return;
		}
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
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
	
	@Override
	public void onPositiveButtonClicked(int requestCode) {
		switch (requestCode) {
		case 1:
			reinicia();
			break;
		case 2:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onNegativeButtonClicked(int requestCode) {
		
	}
}
