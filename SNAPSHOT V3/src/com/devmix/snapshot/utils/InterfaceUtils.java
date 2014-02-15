package com.devmix.snapshot.utils;

import android.app.Activity;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

@EBean
public class InterfaceUtils {

	@RootContext
	Activity activity;
	/**
	 * EXIBE TOAST NA TELA
	 * @param string STRING A SER EXIBIDA
	 */
	@UiThread
	public void exibeToast(String string) {
		Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
	}
	
	@UiThread
	public void exibeToast(int stringResId) {
		Toast.makeText(activity, activity.getResources().getString(stringResId), Toast.LENGTH_SHORT).show();
	}
	
	
	@UiThread
	public void exibeMsg(String msg){
		DialogUtils.exibeMsg(msg, activity);
	}
	
	@UiThread
	public void exibeMsg(int stringResId){
		DialogUtils.exibeMsg(activity.getResources().getString(stringResId), activity);
	}
	
	@UiThread
	public void exibeMsgFinish(String msg,boolean finish){
		DialogUtils.exibeMsgFinish(msg, activity,finish);
	}
	
	@UiThread
	public void exibeMsgFinish(int stringResId,boolean finish){
		DialogUtils.exibeMsgFinish(activity.getResources().getString(stringResId), activity,finish);
	}
}
