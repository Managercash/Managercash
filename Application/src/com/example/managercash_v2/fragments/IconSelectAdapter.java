package com.example.managercash_v2.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.managercash_v2.R;

public class IconSelectAdapter extends BaseAdapter {

	private Integer[] imageIDs = { R.drawable.category37, R.drawable.category1, R.drawable.category4,
			R.drawable.category50, R.drawable.category5, R.drawable.category32, R.drawable.category8,
			R.drawable.category9, R.drawable.category10, R.drawable.category11, R.drawable.category36,
			R.drawable.category15, R.drawable.category49, R.drawable.category2, R.drawable.category16,
			R.drawable.category18, R.drawable.category56, R.drawable.category6, R.drawable.category7,
			R.drawable.category20, R.drawable.category13, R.drawable.category35, R.drawable.category22,
			R.drawable.category24, R.drawable.category25, R.drawable.category17, R.drawable.category27,
			R.drawable.category28, R.drawable.category29, R.drawable.category30, R.drawable.category14,
			R.drawable.category31, R.drawable.category26, R.drawable.category33, R.drawable.category21,
			R.drawable.category34, R.drawable.category52, R.drawable.category3, R.drawable.category38,
			R.drawable.category59, R.drawable.category53, R.drawable.category58, R.drawable.category39,
			R.drawable.category40, R.drawable.category19, R.drawable.category41, R.drawable.category42,
			R.drawable.category44, R.drawable.category12, R.drawable.category45, R.drawable.category46,
			R.drawable.category48, R.drawable.category51, R.drawable.category43, R.drawable.category54,
			R.drawable.category23, R.drawable.category55, R.drawable.category47, R.drawable.category57,
			R.drawable.category60 };

	private Context context;

	public IconSelectAdapter(Context context) {
		this.context = context;

	}

	@Override
	public int getCount() {
		return imageIDs.length;
	}

	@Override
	public Integer getItem(int position) {
		return imageIDs[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(context);
			
			// Calculates screen size and scales images accordingly
			Resources r = Resources.getSystem();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics());
			
			imageView.setLayoutParams(new GridView.LayoutParams((int)px, (int)px));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(5, 5, 5, 5);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(imageIDs[position]);
		return imageView;
	}
}
