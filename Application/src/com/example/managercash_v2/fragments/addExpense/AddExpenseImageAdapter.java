package com.example.managercash_v2.fragments.addExpense;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.managercash_v2.R;
import com.example.managercash_v2.database.CategoriesExpense;
import com.example.managercash_v2.database.DatabaseHandler;

//Used to display the grid of images in AddExpense.java
public class AddExpenseImageAdapter extends BaseAdapter {
	
    private Context context;
	private List<CategoriesExpense> lCategoriesExpense; 
	private DatabaseHandler dh;

    public AddExpenseImageAdapter(Context c) {
        context = c;
        dh = new DatabaseHandler(context);
        //Gets categoriesList from the text adapter to save on database queries
        lCategoriesExpense = dh.getAllExpenseCategories();
        
    }

    public int getCount() {
        return 20;
    }

    public Object getItem(int position) {
        return lCategoriesExpense.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
    

    // create a new ImageView for each item referenced by the Adapter
    @SuppressLint("NewApi") // If sdk less than 16 can't use grey_bg.xml
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	
        	//Calculates screen size and scales images accordingly
        	Resources r = Resources.getSystem();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics());
			
			//Creates image view
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams((int)px, (int)px));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            
            
        } else {
            imageView = (ImageView) convertView;
        }
 
        
        String uri = "@" + lCategoriesExpense.get(position).get_img_src();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        
        imageView.setImageDrawable(res);
        return imageView;
        
    }
    
}
    
