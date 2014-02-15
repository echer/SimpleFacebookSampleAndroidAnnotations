package com.devmix.snapshot.fragments;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.rest.RestService;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.devmix.snapshot.R;
import com.devmix.snapshot.adapter.FollowBAdapter;
import com.devmix.snapshot.dao.ConfiguracaoEnterpriseManager;
import com.devmix.snapshot.model.CidadeListString;
import com.devmix.snapshot.model.ConfiguracaoCidade;
import com.devmix.snapshot.model.ConfiguracaoCidadeList;
import com.devmix.snapshot.model.ConfiguracaoEnterprise;
import com.devmix.snapshot.model.Enterprise;
import com.devmix.snapshot.model.EnterpriseFoto;
import com.devmix.snapshot.model.EnterpriseList;
import com.devmix.snapshot.model.Profile;
import com.devmix.snapshot.utils.Globals;
import com.devmix.snapshot.ws.EnterpriseWS;
import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.orasystems.libs.utils.async.AsyncUtils;
import com.orasystems.libs.utils.async.GenericAsyncTask;
import com.orasystems.libs.utils.internet.Conexao;
import com.todddavies.components.progressbar.ProgressWheel;

@EFragment
public class FragmentFollow extends SherlockFragment {
	
	/**
	 * ASYNCTASK
	 */
	@Bean(AsyncUtils.class)
	public GenericAsyncTask asyncTask;
	
	//@ViewById
	//public ListView listFollows;
	
	@RestService
	public EnterpriseWS enterpriseWS;
	
	@DrawableRes
	public Drawable icLauncher;
	
	@FragmentArg
	public Profile profile;
	
	@FragmentArg
	public ConfiguracaoCidadeList cidades;
	
	@ViewById
	public StaggeredGridView gridView;
	
	@ViewById
	public LinearLayout layoutError;
	
	@Bean
	public ConfiguracaoEnterpriseManager configuracaoEnterpriseManager;
	
	private FollowBAdapter followBAdapter;
	
	@AfterInject
	public void afterInject(){
		enterpriseWS.setRootUrl(Globals.rootUrlEnterpriseWS);
	}
	
	@AfterViews
	public void afterViews(){
		
		if(cidades == null)return;
		
		if(profile == null)return;
		
		/*View header = getActivity().getLayoutInflater().inflate(R.layout.list_item_header_footer, null);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.list_item_header_footer, null);
        TextView txtHeaderTitle = (TextView) header.findViewById(R.id.txt_title);
        TextView txtFooterTitle =  (TextView) footer.findViewById(R.id.txt_title);
        txtHeaderTitle.setText("THE HEADER!");
        txtFooterTitle.setText("THE FOOTER!");
        gridView.addHeaderView(header);
        gridView.addFooterView(footer);*/
        
		if(Conexao.conectadoOuConectando(getSherlockActivity())){
			gridView.setVisibility(StaggeredGridView.VISIBLE);
			layoutError.setVisibility(LinearLayout.GONE);
			carregaEnterprises();
		}else{
			exibeNotNetwork();
		}
		
	}

	private void exibeNotNetwork() {
		gridView.setVisibility(StaggeredGridView.GONE);
		layoutError.setVisibility(LinearLayout.VISIBLE);
		layoutError.removeAllViews();
		View error = getSherlockActivity().getLayoutInflater().inflate(R.layout.not_network, null);
		error.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				afterViews();
			}
		});
		layoutError.addView(error);
	}

	@Background
	public void carregaEnterprises() {
		asyncTask.showProgressBar(getSherlockActivity(), R.string.acvty_snapshot_progress_title, R.string.frag_follow_carregando_lojas, 100, icLauncher, false, true, false);
		try{
			List<String> listaCidades = new ArrayList<String>();
			for(ConfiguracaoCidade cc:cidades.getCidades()){
				listaCidades.add(cc.getCidade().getNomeCidade());
			}
			EnterpriseList eList = enterpriseWS.list(new CidadeListString(listaCidades), profile.getId());
			exibeEnterprises(eList);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			asyncTask.hideProgressBar();
		}
	}

	@UiThread
	public void exibeEnterprises(EnterpriseList eList) {
		try {
			@SuppressWarnings("unchecked")
			List<ConfiguracaoEnterprise> enterprises = configuracaoEnterpriseManager.list();
			if(enterprises != null){
				for(Enterprise e:eList.getEnterprises()){
					for(ConfiguracaoEnterprise ce:enterprises){
						if(e.getId() == ce.getEnterprise().getId())e.setFollow(true);
					}
				}
			}
			
			followBAdapter = new FollowBAdapter(getSherlockActivity(), eList.getEnterprises(),this,R.id.txt_line1);
			//listFollows.setAdapter(followBAdapter);
			gridView.setAdapter(followBAdapter);
			followBAdapter.notifyDataSetChanged();
			//gridView.setOnScrollListener(this);
			//gridView.setOnItemClickListener(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
        inflater.inflate(R.menu.menu_follow, menu);
	    super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_follow, container, false);
	}
	
	@Background
	public void downloadImagem(ProgressWheel progress, DynamicHeightImageView storeImage, Enterprise item) {
		startSpin(progress);
		try{
			EnterpriseFoto eFoto = enterpriseWS.getEnterpriseImagem(item, profile.getId());
			if(eFoto != null && eFoto.getImagem() != null){
				exibeImagem(eFoto,storeImage);
				item.setImagem(eFoto.getImagem());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			stopSpinning(progress);
		}
	}
	
	@UiThread
	public void exibeImagem(EnterpriseFoto eFoto, DynamicHeightImageView storeImage) {
		//storeImage.setBackgroundDrawable(new BitmapDrawable(getResources(),(BitmapFactory.decodeByteArray(eFoto.getImagem(), 0, eFoto.getImagem().length))));
		storeImage.setImageBitmap((BitmapFactory.decodeByteArray(eFoto.getImagem(), 0, eFoto.getImagem().length)));
	}

	@UiThread
    public void stopSpinning(ProgressWheel progress) {
    	progress.stopSpinning();
    	progress.setVisibility(ProgressWheel.GONE);
	}
	@UiThread
	public void startSpin(ProgressWheel progress) {
		progress.setVisibility(ProgressWheel.VISIBLE);
		progress.setText("Baixando imagem aguarde...");
        progress.spin();		
	}
}
