package com.example.managercash_v2.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.managercash_v2.R;
import com.example.managercash_v2.database.CategoriesExpense;
import com.example.managercash_v2.database.DatabaseHandler;
import com.example.managercash_v2.database.Expense;
import com.example.managercash_v2.database.Wallet;

public class Overview extends Fragment {
	private DatabaseHandler dh;
	private Context context;
	private Wallet currentWallet;
	private View thisView;
	
	// Graph Variables 
	private GraphicalView lineChartView;
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer multiRenderer;
	private GraphicalView pieChartView;
	private CategorySeries mSeries = new CategorySeries("");
	private DefaultRenderer mRenderer = new DefaultRenderer();

	private static int[] colors = new int[] { Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN };
	private static double[] values = new double[21];
	private static String[] names = new String[21];


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if (getActivity() != null) {
			context = getActivity();
		}

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View overview = inflater.inflate(R.layout.overview, container, false);
		this.thisView = overview;
		dh = new DatabaseHandler(context);

		updateWallet();
		TextView textView1 = (TextView) overview.findViewById(R.id.overviewText1);
		
		
		textView1.setText("Overview \nTotal Income = £" + currentWallet.getTotalIncome() + "\nTotal Expense =  £" + currentWallet.getTotalExpense());
		
		createLineChart();
		createPieChart();
		return overview;
	}
	
	private void createLineChart(){
		
		int [] x = {1,2,3,4,5,6,7};
		int [] daysOfWeek = new int [7];
		int [] totalExpenseByDay = new int [7];
		int [] totalIncomeByDay = {10,20,50,10,10,80,6};
		Calendar calendar = Calendar.getInstance();
		
		List <Expense> expenseList = dh.getExpenseBetweenDates("'2014-08-10 00:00:00'", "'2014-08-31 18:00:00'");
		
		// Populate days of week for the last 7 days counting back from current day
		daysOfWeek[6] = calendar.get(Calendar.DAY_OF_MONTH);
		
		for (int p = 5; p>=0; p--){
			daysOfWeek[p] = (daysOfWeek[p+1]-1);			
		}
		
		// Populates total expense (and income) for past 7 days per day
		for (int i = 0; i<daysOfWeek.length;i++){
			for (Expense e : expenseList){
				
				String date = e.get_date();
				int day = Character.getNumericValue(date.charAt(8));
				
				if (day == 0){
					day = Character.getNumericValue(date.charAt(9));
				}
				else{
					day = day*10 + Character.getNumericValue(date.charAt(9));
				}
				
				if(day == daysOfWeek[i]){
					totalExpenseByDay[i] += e.get_amount();
				}
				

			}
		}

		 // Creating an  XYSeries for Income
        XYSeries incomeSeries = new XYSeries("Income");
        // Creating an  XYSeries for Expense
        XYSeries expenseSeries = new XYSeries("Expense");
        // Adding data to Income and Expense Series
        for(int i=0;i<x.length;i++){
            incomeSeries.add(x[i], totalIncomeByDay[i]);
            expenseSeries.add(x[i],totalExpenseByDay[i]);
        }
 
        // Creating a dataset to hold each series
        dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(incomeSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(expenseSeries);
 
        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.GREEN);
        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(4);
        incomeRenderer.setDisplayChartValues(true);
 
        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.RED);
        expenseRenderer.setPointStyle(PointStyle.CIRCLE);
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);
 
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.argb(0, 255, 255, 255));
        multiRenderer.setMarginsColor(Color.argb(0, 255, 255, 255));
        multiRenderer.setMargins(new int []{0,0,0,0});
        multiRenderer.setPanEnabled(false);
        multiRenderer.setZoomEnabled(false);
        multiRenderer.setAxisTitleTextSize(30);
        multiRenderer.setLabelsTextSize(30);
        multiRenderer.setLegendTextSize(30);
        multiRenderer.setChartTitle("Expense vs Income Chart");
        multiRenderer.setXTitle("Date of the month");
        multiRenderer.setYTitle("Amount in Pounds");
        multiRenderer.setInScroll(true);
        multiRenderer.setZoomButtonsVisible(false);
        for(int i=0;i<x.length;i++){
            multiRenderer.addXTextLabel(i+1, Integer.toString(daysOfWeek[i]));
        }
 
        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);


		
        if (lineChartView != null) {
			lineChartView.repaint();
		}
	}
	
	private void createPieChart() {

		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(30);
		mRenderer.setLegendTextSize(30);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setStartAngle(90);
		mRenderer.setShowLegend(false);
		mRenderer.setPanEnabled(false);
		mRenderer.setInScroll(true);
		
		
		List<CategoriesExpense> lCE = new ArrayList<CategoriesExpense>();
		int totalExpense = 0;
		
		//POPULATING THE VALUES AND NAMES FIELDS
		// Loop through category id's
		for (int i = 1; i <= 20;) {
			
			// Adds all expenses with category id = i
			List<Expense> lE = dh.getExpenseByCategory(i);
			
			// If any values found:-
			if (lE.size() > 0) {
				// Add category to categories list.
				names[i] = dh.getCategoriesExpense(i).get_name();

				// Loop through expense list, totaling expenditure.
				for (int e = 0; e < lE.size();) {
					
					totalExpense += lE.get(e).get_amount();
					e++;
				}
				values[i] = totalExpense;
				totalExpense = 0;
			}
			lE = null;
			i++;
		}

		for (int i = 1; i < values.length; i++) {
			if(names[i] != null){
			mSeries.add(names[i] + " " + values[i], values[i]);
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			renderer.setColor(colors[(mSeries.getItemCount() - 1) % colors.length]);
			mRenderer.addSeriesRenderer(renderer);
			}
		}

		if (pieChartView != null) {
			pieChartView.repaint();
		}

	}
	
	@Override 
	public void onPause(){

		
		super.onPause();
	}
	
	@Override 
	public void onResume(){
		super.onResume();
		updateWallet(); 
		
		Log.w("overview", "overview - onResume has been called");
		if(lineChartView == null){
			Log.w("overview", "Line chart view = null");

		}
		if(pieChartView == null){
			Log.w("Overview", "pieChartView = null");

		}
		


		if(lineChartView == null){
			LinearLayout layout = (LinearLayout) thisView.findViewById(R.id.lineChart);
			lineChartView = ChartFactory.getLineChartView(context, dataset, multiRenderer);
			layout.addView(lineChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		}
		else{
			lineChartView.refreshDrawableState();
			lineChartView.repaint();
		}
		
		if(pieChartView == null){

			LinearLayout layout = (LinearLayout) thisView.findViewById(R.id.pieChart);
			pieChartView = ChartFactory.getPieChartView(context, mSeries, mRenderer);
			layout.addView(pieChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		}
		else{
			pieChartView.refreshDrawableState();
			pieChartView.repaint();
		}
		
		
		
	}
	
	
	
	private void updateWallet(){
		currentWallet = dh.getWallet(1); //Need to change this integer!
	}
	
}
