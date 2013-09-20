package com.example.adapter;

import java.util.List;

import com.example.kursvaluti.R;
import com.example.model.Valuta;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ValutiAdapter extends ArrayAdapter<Valuta> {
	private Context context;

	private List<Valuta> listValuta;

	static class ViewHolder {
		public TextView tvShortName;
		public TextView tvFullName;
		public TextView tvValue;
		public ImageView image;
	}

	public ValutiAdapter(Context context, int resourceId,
			List<Valuta> listValuta) {
		super(context, resourceId, listValuta);
		this.context = context;
		this.listValuta = listValuta;
	}

	public int getCount() {
		return listValuta.size();
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		Valuta entry = listValuta.get(position);
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.rowlayout_valuti,
					parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.tvShortName = (TextView) rowView.findViewById(R.id.short_name);
			viewHolder.tvFullName = (TextView) rowView.findViewById(R.id.full_name);
			viewHolder.tvValue = (TextView) rowView.findViewById(R.id.value);
			viewHolder.image = (ImageView) rowView
					.findViewById(R.id.flag);
			rowView.setTag(viewHolder);

			
		}
		
			
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		holder.image.setImageResource(entry.getFlag());
		holder.tvShortName.setText(entry.getShortName());
		holder.tvFullName.setText(entry.getFullNameMac());
		holder.tvValue.setText(entry.getAverage());

		

		return rowView;
	}

}
