package com.example.managercash_v2.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.managercash_v2.R;
import com.example.managercash_v2.database.CategoriesExpense;

//Pop up window list adapter for the add expense screen
public class PopupWindowListAdapter extends ArrayAdapter {

	private AddExpenseTextAdapter tA;
	Context context;
	private List<CategoriesExpense> toDisplay;

	public PopupWindowListAdapter(Context context, int textViewResourceId, AddExpenseTextAdapter tA) {

		super(context, textViewResourceId);
		this.tA = tA;
		this.context = context;
		populateExpenseList();

	}
	
	public void populateExpenseList(){
	
		toDisplay = new ArrayList<CategoriesExpense>();
		List<CategoriesExpense> expenseList = tA.getCategoriesExpenseList();
		for (CategoriesExpense e : expenseList){
			if(e.get_temp_amount() > 0){
				toDisplay.add(e);
			}
		}
	}
	
		

	@Override
	public int getCount(){
		return toDisplay.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.add_expense_list_view_item, parent, false);
		
			TextView textView = (TextView) rowView.findViewById(R.id.popUpListText);
			textView.setText(toDisplay.get(position).get_name() + " - £" + toDisplay.get(position).get_temp_amount());

			ImageView imageView = (ImageView) rowView.findViewById(R.id.customimageView1);
			String mDrawableName = "@" + toDisplay.get(position).get_img_src();
			int resID = context.getResources().getIdentifier(mDrawableName , null, context.getPackageName());
			Drawable res = context.getResources().getDrawable(resID);
			
			imageView.setImageDrawable(res);


		return rowView;
	}

}

    