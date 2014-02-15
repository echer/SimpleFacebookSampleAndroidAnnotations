package com.orasystems.libs.utils.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

@EBean
public class AsyncUtils implements GenericAsyncTask{

	@Override
	@UiThread
	public void publishProgress(int progress,String message) {
		progressDialog.setProgress(progress);
		if(message != null)progressDialog.setMessage(message);
	}
	@Override
	@UiThread
	public void hideProgressBar(){
		progressDialog.dismiss();
	}
	private ProgressDialog progressDialog;
	@Override
	@UiThread
	public void showProgressBar(Activity activity,String title,String message,int max,Drawable icon,boolean setCancelable,boolean setIndeterminate,boolean progressHorizontal){
		progressDialog = LightProgressDialog.create(activity,progressHorizontal);
        progressDialog.setCancelable(setCancelable);
        progressDialog.setIndeterminate(setIndeterminate);
        progressDialog.setMax(max);
        progressDialog.setTitle(title);
        progressDialog.setIcon(icon);
        progressDialog.setMessage(message);
        progressDialog.show();
	}
	
	@Override
	public ProgressDialog getProgressDialog(){
		return progressDialog;
	}
	
	@UiThread
	@Override
	public void atualizaProgressDialog(GenericAsyncTask asyncTask,String message,int progress){
		if(message != null)asyncTask.getProgressDialog().setMessage(message);
		if(progress > 0)asyncTask.getProgressDialog().setProgress(progress);
	}
	@Override
	@UiThread
	public void showProgressBar(Activity activity, int titleResId,
			int stringResId, int max, Drawable icon, boolean setCancelable,
			boolean setIndeterminate, boolean progressHorizontal) {
		progressDialog = LightProgressDialog.create(activity,progressHorizontal);
        progressDialog.setCancelable(setCancelable);
        progressDialog.setIndeterminate(setIndeterminate);
        progressDialog.setMax(max);
        progressDialog.setTitle(activity.getResources().getString(titleResId));
        progressDialog.setIcon(icon);
        progressDialog.setMessage(activity.getResources().getString(stringResId));
        progressDialog.show();
	}


}
