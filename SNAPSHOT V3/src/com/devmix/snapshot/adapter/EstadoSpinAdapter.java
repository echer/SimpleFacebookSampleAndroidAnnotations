package com.devmix.snapshot.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.devmix.snapshot.R;
import com.devmix.snapshot.model.Estado;

public class EstadoSpinAdapter extends ArrayAdapter<Estado>{

    private Context context;
    private List<Estado> estados;

    public EstadoSpinAdapter(Context context, int textViewResourceId,
            List<Estado> estados) {
        super(context, textViewResourceId, estados);
        this.context = context;
        this.estados = estados;
    }

    public int getCount(){
       return estados.size();
    }

    public Estado getItem(int position){
       return estados.get(position);
    }

    public long getItemId(int position){
       return ((Estado)estados.get(position)).getIdEstado();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextAppearance(context, R.style.CardText);
        label.setPadding(5, 5, 5, 5);
        Estado estado = getItem(position);
        label.setText(estado.getUfEstado()+" - "+estado.getNomeEstado());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextAppearance(context, R.style.CardText);
        label.setPadding(5, 5, 5, 5);
        Estado estado = getItem(position);
        label.setText(estado.getUfEstado()+" - "+estado.getNomeEstado());

        return label;
    }
}