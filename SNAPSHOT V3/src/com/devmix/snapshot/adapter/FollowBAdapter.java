package com.devmix.snapshot.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.devmix.snapshot.R;
import com.devmix.snapshot.fragments.FragmentFollow;
import com.devmix.snapshot.model.Enterprise;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.todddavies.components.progressbar.ProgressWheel;

public class FollowBAdapter extends ArrayAdapter<Object> {
	
	private List<Enterprise> follows = new ArrayList<Enterprise>();
	private FragmentFollow instance;
	private Activity activity;
	private Random mRandom;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	public FollowBAdapter(Activity activity,List<Enterprise> follows,FragmentFollow instance, final int textViewResourceId){
		super(activity, textViewResourceId);
		this.follows = follows;
		this.activity = activity;
		this.instance = instance;
		mRandom = new Random();
	}
	
	static class ViewHolder {
        DynamicHeightTextView txtLineOne;
        TextView btnGo;
        ProgressWheel progressWheel;
        DynamicHeightImageView storeImage;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /*ViewGroup view = null;

        if (convertView == null) {
            view = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.rounded_item, parent, false);
        } else {
            view = (ViewGroup) convertView;
            return view;
        }
        
        Enterprise item = (Enterprise) getItem(position);

        RoundedImageView imagem = (RoundedImageView) view.findViewById(R.id.imageView1);
        
        //((RoundedImageView) view.findViewById(R.id.imageView1)).setScaleType(item.mScaleType);
//        ((RoundedImageView) view.findViewById(R.id.imageView1)).setOval(true);
        ((TextView) view.findViewById(R.id.textView1)).setText(item.getNome());
        ((TextView) view.findViewById(R.id.textView2)).setText(item.getCidade().getNomeCidade()+" - "+item.getCidade().getEstado().getUfEstado());
        ((TextView) view.findViewById(R.id.textView3)).setText(item.isFollow() ? " Seguindo":"Seguir");
        
        ProgressWheel progress = (ProgressWheel) view.findViewById(R.id.pw_spinner);
        progress.setVisibility(ProgressWheel.GONE);
        
        
        //DOWNLOAD IMAGEM WEBSERVICE
        if(item.getImagem() == null){
        	instance.downloadImagem(progress,imagem,item);
        }
        
        return view;*/
    	
    	ViewHolder vh;
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_item_sample, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (DynamicHeightTextView) convertView.findViewById(R.id.txt_line1);
            vh.btnGo = (TextView) convertView.findViewById(R.id.btn_go);
            vh.progressWheel = (ProgressWheel) convertView.findViewById(R.id.pw_spinner);
            vh.storeImage = (DynamicHeightImageView)convertView.findViewById(R.id.storeImage);
            
            vh.progressWheel.setVisibility(ProgressWheel.GONE);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        
        Enterprise enterprise = ((Enterprise)getItem(position));
        double positionHeight = getPositionRatio(position);

        //DOWNLOAD IMAGEM WEBSERVICE
        if(enterprise.getImagem() == null){
        	instance.downloadImagem(vh.progressWheel,vh.storeImage,enterprise);
        }

        vh.storeImage.setHeightRatio(positionHeight);
        
        vh.txtLineOne.setText(enterprise.getNome());

        vh.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(activity, "Button Clicked Position " +
                        position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

	//@Override
	public int getCount() {
		return follows.size();
	}

	//@Override
	public Object getItem(int position) {
		return follows.get(position);
	}

	//@Override
	public long getItemId(int position) {
		return ((Enterprise)(getItem(position))).getId();
	}
	
	private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d("", "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }
	
	private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
    
}
