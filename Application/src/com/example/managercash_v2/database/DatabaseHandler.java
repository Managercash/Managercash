package com.example.managercash_v2.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// -------------------------------------------------------------------------------------------------------------------//

	// ///////////////////////////////////
	// /////// FINAL VARIABLES //////////

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "managercashDatabase";

	// Income Categories Table
	private static final String TABLE_CATEGORIES_INCOME = "categoriesIncome";

	// Expense Categories Table
	private static final String TABLE_CATEGORIES_EXPENSE = "categoriesExpense";
	private static final String KEY_INCREMENT = "increment";
	private static final String KEY_TEMP_AMOUNT = "tempAmount";

	// Expense table
	private static final String TABLE_EXPENSE = "expense";

	// Income table
	private static final String TABLE_INCOME = "income";

	// Wallet table
	private static final String TABLE_WALLET = "wallet";
	private static final String KEY_TOTAL_INCOME = "totalIncome";
	private static final String KEY_TOTAL_EXPENSE = "totalExpense";

	// Recurring Expense table
	private static final String TABLE_RECURRING_EXPENSE = "recurringExpense";
	private static final String KEY_EXPENSE_ID = "expenseID";

	// Recurring Income table
	private static final String TABLE_RECURRING_INCOME = "recurringIncome";
	private static final String KEY_INCOME_ID = "incomeId";

	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_IMAGE_SRC = "imageSrc";
	private static final String KEY_WALLET_ID = "walletId";
	private static final String KEY_CATEGORY_ID = "categoryId";
	private static final String KEY_DATE = "date";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_END_DATE = "endDate";
	private static final String KEY_NOTE = "note";

	// -------------------------------------------------------------------------------------------------------------------//

	// ///////////////////////////////////
	// //// TABLE CREATE STATEMENTS //////

	// Table create statements

	private static final String CREATE_TABLE_CATEGORIES_INCOME = "CREATE TABLE " + TABLE_CATEGORIES_INCOME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_NAME + " TEXT," + KEY_IMAGE_SRC + " TEXT "
			+ ")";

	private static final String CREATE_TABLE_CATEGORIES_EXPENSE = "CREATE TABLE " + TABLE_CATEGORIES_EXPENSE + "("
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_NAME + " TEXT," + KEY_IMAGE_SRC + " TEXT,"
			+ KEY_INCREMENT + " INTEGER, " +  KEY_TEMP_AMOUNT + " INTEGER " + ")";
	

	private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_WALLET_ID + " INTEGER, " + KEY_NAME + " TEXT, "
			+ KEY_CATEGORY_ID + " INTEGER, " + KEY_DATE + " DATETIME, " + KEY_AMOUNT + " INTEGER " + ")";

	private static final String CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_INCOME + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_WALLET_ID + " INTEGER, " + KEY_CATEGORY_ID
			+ " INTEGER, " + KEY_DATE + " DATETIME, " + KEY_AMOUNT + " INTEGER " + ")";

	private static final String CREATE_TABLE_WALLET = "CREATE TABLE " + TABLE_WALLET + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_NAME + " TEXT, " + KEY_TOTAL_INCOME + " INTEGER, "
			+ KEY_TOTAL_EXPENSE + " INTEGER " + ")";

	private static final String CREATE_TABLE_RECURRING_EXPENSE = "CREATE TABLE " + TABLE_RECURRING_EXPENSE + "("
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_EXPENSE_ID + " INTEGER, " + KEY_END_DATE
			+ " DATETIME, " + KEY_NOTE + " TEXT" + ")";

	private static final String CREATE_TABLE_RECURRING_INCOME = "CREATE TABLE " + TABLE_RECURRING_INCOME + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_INCOME_ID + " INTEGER, " + KEY_END_DATE
			+ " DATETIME, " + KEY_NOTE + " TEXT" + ")";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	// -------------------------------------------------------------------------------------------------------------------//

	// ///////////////////////////////////
	// /////// CREATE DATABASE //////////

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_CATEGORIES_INCOME);
		db.execSQL(CREATE_TABLE_CATEGORIES_EXPENSE);
		db.execSQL(CREATE_TABLE_INCOME);
		db.execSQL(CREATE_TABLE_EXPENSE);
		db.execSQL(CREATE_TABLE_RECURRING_INCOME);
		db.execSQL(CREATE_TABLE_RECURRING_EXPENSE);
		db.execSQL(CREATE_TABLE_WALLET);

		// -------------------------------------------------------------------------------------------------------------------//

		// ///////////////////////////////////
		// /////// UPGRADE DATABASE /////////
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// On upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES_INCOME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES_EXPENSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WALLET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECURRING_EXPENSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECURRING_INCOME);

		// create new tables
		onCreate(db);

	}

	// -------------------------------------------------------------------------------------------------------------------//

	/////////////////////////////////////
	////////// ALL DEM METHODS //////////

	// Create Income category
	public void createIncomeCategory(CategoriesIncome ci) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, ci.get_name());
		values.put(KEY_IMAGE_SRC, ci.get_img_src());

		db.insert(TABLE_CATEGORIES_INCOME, null, values);
		Log.w("Database", "CREATED " + ci.get_name() + " INCOME CATEGORY");

	}

	// Get Income Category by ID

	public CategoriesIncome getCategoriesIncome(int Id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES_INCOME + " WHERE " + KEY_ID + " = " + Id;

		Log.w("Database", "Select all from Categories income where key id = " + Id);

		CategoriesIncome CI = new CategoriesIncome();
		Cursor c = null;
		try {
			c = db.rawQuery(selectQuery, null);

			if (c != null) {
				c.moveToFirst();
			}

			CI.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
			CI.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
			CI.set_img_src(c.getString(c.getColumnIndex(KEY_IMAGE_SRC)));
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return CI;

	}

	// Get Income Category by Name

	public CategoriesIncome getCategoriesIncome(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES_INCOME + " WHERE " + KEY_NAME + " =?";

		Log.w("Database", "Select all from Categories income where key name = " + name);

		Cursor c = null;
		CategoriesIncome CI = new CategoriesIncome();

		try {
			c = db.rawQuery(selectQuery, new String[] { name });

			if (c != null) {
				c.moveToFirst();
			}

			CI.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
			CI.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
			CI.set_img_src(c.getString(c.getColumnIndex(KEY_IMAGE_SRC)));
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return CI;
	}

	// Get * Income Categories

	public List<CategoriesIncome> getAllIncomeCategories() {
		List<CategoriesIncome> LCI = new ArrayList<CategoriesIncome>();
		String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES_INCOME;

		Log.e("DATABASE HANDLER", selectQuery);

		CategoriesIncome cI = null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;

		try {
			c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					cI = new CategoriesIncome();
					cI.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					cI.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
					cI.set_img_src(c.getString(c.getColumnIndex(KEY_IMAGE_SRC)));

					LCI.add(cI);
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {

				c.close();
			}
		}

		return LCI;
	}

	// Create Expense category
	public void createExpenseCategory(CategoriesExpense ce) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, ce.get_name());
		values.put(KEY_IMAGE_SRC, ce.get_img_src());
		values.put(KEY_INCREMENT, ce.get_increment());
		values.put(KEY_TEMP_AMOUNT, 0);

		db.insert(TABLE_CATEGORIES_EXPENSE, null, values);
		Log.w("Database", "CREATED " + ce.get_name() + " EXPENSE CATEGORY");

	}
	
	
	// Update temp_value in Expense category by ID
	public void updateTempValue(CategoriesExpense ce){
		SQLiteDatabase db = this.getWritableDatabase();

		try{
			db.beginTransaction();
			
			ContentValues values=new ContentValues();
			values.put(KEY_TEMP_AMOUNT, ce.get_temp_amount());
			
			String[] args = new String[]{Integer.toString(ce.get_id())};
			db.update(TABLE_CATEGORIES_EXPENSE, values, KEY_ID + "=?", args);
			
			db.setTransactionSuccessful();
			
		}finally{
			db.endTransaction();
		}
			
	}

		
	

	// Get Expense Category by ID

	public CategoriesExpense getCategoriesExpense(int Id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES_EXPENSE + " WHERE " + KEY_ID + " = " + Id;

		Log.d("Database", "Select all from Categories expense where key id = " + Id);

		Cursor c = null;
		CategoriesExpense cE = null;

		try {

			c = db.rawQuery(selectQuery, null);

			if (c != null) {
				c.moveToFirst();
			}

			cE = new CategoriesExpense();
			cE.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
			cE.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
			cE.set_img_src(c.getString(c.getColumnIndex(KEY_IMAGE_SRC)));
			cE.set_increment(c.getInt(c.getColumnIndex(KEY_INCREMENT)));

		} finally {
			if (c != null) {
				c.close();
			}
		}

		return cE;

	}

	// Get Expense Category by Name

	public CategoriesExpense getCategoriesExpense(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES_EXPENSE + " WHERE " + KEY_NAME + " =?";

		Log.w("Database", "Select all from Categories expense where key name = " + name);

		Cursor c = null;
		CategoriesExpense cE = null;

		try {

			c = db.rawQuery(selectQuery, new String[] { name });

			if (c != null) {
				c.moveToFirst();
			}

			cE = new CategoriesExpense();
			cE.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
			cE.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
			cE.set_img_src(c.getString(c.getColumnIndex(KEY_IMAGE_SRC)));
			cE.set_increment(c.getInt(c.getColumnIndex(KEY_INCREMENT)));

		} finally {
			if (c != null) {
				c.close();
			}
		}

		return cE;
	}

	// Get * Expense Categories

	public List<CategoriesExpense> getAllExpenseCategories() {
		SQLiteDatabase db = this.getReadableDatabase();
		List<CategoriesExpense> LCE = new ArrayList<CategoriesExpense>();
		String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES_EXPENSE;

		Log.e("DATABASE HANDLER", selectQuery);

		CategoriesExpense cE = null;
		Cursor c = null;

		try {

			c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					cE = new CategoriesExpense();
					cE.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					cE.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
					cE.set_img_src(c.getString(c.getColumnIndex(KEY_IMAGE_SRC)));
					cE.set_increment(c.getInt(c.getColumnIndex(KEY_INCREMENT)));
					cE.set_temp_amount(c.getInt(c.getColumnIndex(KEY_TEMP_AMOUNT)));

					LCE.add(cE);
				} while (c.moveToNext());
			}

		} finally {
			if (c != null) {
				c.close();
				db.close();
			}
		}

		return LCE;
	}

	// Create Expense

	public void createExpense(Expense expense) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, expense.get_name());
		values.put(KEY_WALLET_ID, expense.get_wallet_id());
		values.put(KEY_CATEGORY_ID, expense.get_category_id());
		values.put(KEY_DATE, expense.get_date());
		values.put(KEY_AMOUNT, expense.get_amount());

		db.insert(TABLE_EXPENSE, null, values);
		Log.w("Database", this.getCategoriesExpense(expense.get_category_id()).get_name()
				+ " expense has been added to Expenses");

		// Increment total expense value
		Wallet w = this.getWallet(expense.get_wallet_id());

		long oldExpense = w.getTotalExpense();
		oldExpense += expense.get_amount();

		values = new ContentValues();
		values.put(KEY_TOTAL_EXPENSE, oldExpense);

		db.update(TABLE_WALLET, values, String.format("%s = ?", KEY_ID),
				new String[] { Integer.toString(expense.get_wallet_id()) });

	}

	// Get expense by ID

	public Expense getExpense(int id) {
		String selectQuery = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + KEY_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = null;
		Expense e = null;

		try {
			c = db.rawQuery(selectQuery, null);
			e = new Expense();

			if (c != null) {
				c.moveToFirst();
			}
			e.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
			e.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
			e.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
			e.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
			e.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
			e.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return e;
	}

	// Get Expense Between Dates
	public List<Expense> getExpense(String startDate, String endDate) {
		List<Expense> lE = new ArrayList<Expense>();
		String selectQuery = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + KEY_DATE + " BETWEEN " + startDate
				+ " AND " + endDate;
		Log.e("DATABASE HANDLER", selectQuery);

		Expense e = null;
		Cursor c = null;

		try {

			SQLiteDatabase db = this.getReadableDatabase();
			c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					e = new Expense();
					e.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					e.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
					e.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
					e.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
					e.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
					e.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));

					lE.add(e);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return lE;

	}

	// Get Expense By Category
	public List<Expense> getExpenseByCategory(int id) {
		List<Expense> lE = new ArrayList<Expense>();
		String selectQuery = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + KEY_CATEGORY_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = null;
		Expense e = null;

		c = db.rawQuery(selectQuery, null);

		try {
			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					e = new Expense();
					e.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					e.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
					e.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
					e.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
					e.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
					e.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));

					lE.add(e);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return lE;

	}

	// Get Expense By Wallet
	public List<Expense> getExpenseByWallet(int id) {
		List<Expense> lE = new ArrayList<Expense>();
		String selectQuery = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + KEY_WALLET_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();

		Expense e = null;
		Cursor c = null;

		try {
			c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					e = new Expense();
					e.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					e.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
					e.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
					e.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
					e.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
					e.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));

					lE.add(e);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return lE;

	}

	// Get all expenses
	public List<Expense> getAllExpenses() {
		List<Expense> lE = new ArrayList<Expense>();
		String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();

		Expense e = null;
		Cursor c = null;

		try {
			c = db.rawQuery(selectQuery, null);

			if (c.moveToFirst()) {
				do {
					e = new Expense();
					e.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					e.set_name(c.getString(c.getColumnIndex(KEY_NAME)));
					e.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
					e.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
					e.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
					e.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));

					lE.add(e);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return lE;

	}

	// Create Income

	public void createIncome(Income income) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_WALLET_ID, income.get_wallet_id());
		values.put(KEY_CATEGORY_ID, income.get_category_id());
		values.put(KEY_DATE, income.get_date());
		values.put(KEY_AMOUNT, income.get_amount());

		db.insert(TABLE_INCOME, null, values);
		Log.w("Database", income.get_id() + "id has been added to incomes");

		// Increment total expense value
		Wallet w = this.getWallet(income.get_wallet_id());

		long oldIncome = w.getTotalIncome();
		oldIncome += income.get_amount();

		values = new ContentValues();
		values.put(KEY_TOTAL_INCOME, oldIncome);

		db.update(TABLE_WALLET, values, String.format("%s = ?", KEY_ID),
				new String[] { Integer.toString(income.get_wallet_id()) });

	}

	// Get income by ID
	public Income getIncome(int id) {
		String selectQuery = "SELECT * FROM " + TABLE_INCOME + " WHERE " + KEY_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = null;
		Income i = null;

		try {
			c = db.rawQuery(selectQuery, null);
			i = new Income();

			if (c != null) {
				c.moveToFirst();
			}
			i.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
			i.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
			i.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
			i.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
			i.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return i;

	}

	// Get income Between Dates
	public List<Income> getIncome(String startDate, String endDate) {
		List<Income> lI = new ArrayList<Income>();
		String selectQuery = "SELECT * FROM " + TABLE_INCOME + " WHERE " + KEY_DATE + " BETWEEN " + startDate + " AND "
				+ endDate;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		Income e = null;

		try {
			c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					e = new Income();
					e.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					e.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
					e.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
					e.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
					e.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));

					lI.add(e);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return lI;

	}

	// Get income By Category
	public List<Income> getIncomeByCategory(int id) {
		List<Income> lI = new ArrayList<Income>();
		String selectQuery = "SELECT * FROM " + TABLE_INCOME + " WHERE " + KEY_CATEGORY_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		Income i = null;

		try {
			c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					i = new Income();
					i.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					i.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
					i.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
					i.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
					i.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));

					lI.add(i);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return lI;

	}

	// Get income By Wallet
	public List<Income> getIncomeByWallet(int id) {
		List<Income> lI = new ArrayList<Income>();
		String selectQuery = "SELECT * FROM " + TABLE_INCOME + " WHERE " + KEY_WALLET_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		Income i = null;

		try {
			c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					i = new Income();
					i.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					i.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
					i.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
					i.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
					i.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));

					lI.add(i);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return lI;

	}

	// Get all Incomes
	public List<Income> getAllIncomes() {
		List<Income> lI = new ArrayList<Income>();
		String selectQuery = "SELECT  * FROM " + TABLE_INCOME;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		Income i = null;

		try {
			c = db.rawQuery(selectQuery, null);

			if (c.moveToFirst()) {
				do {
					i = new Income();
					i.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
					i.set_amount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
					i.set_category_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
					i.set_date(c.getString(c.getColumnIndex(KEY_DATE)));
					i.set_wallet_id(c.getInt(c.getColumnIndex(KEY_WALLET_ID)));

					lI.add(i);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return lI;

	}

	// Create Wallet

	public void createWallet(Wallet wallet) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, wallet.getName());
		values.put(KEY_TOTAL_INCOME, wallet.getTotalIncome());
		values.put(KEY_TOTAL_EXPENSE, wallet.getTotalExpense());

		db.insert(TABLE_WALLET, null, values);
		Log.w("Database", wallet.getName() + " wallet has been added to wallets");

	}

	// Get wallet by ID
	public Wallet getWallet(int id) {
		String selectQuery = "SELECT * FROM " + TABLE_WALLET + " WHERE " + KEY_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		Wallet wallet = null;
		try {
			c = db.rawQuery(selectQuery, null);

			wallet = new Wallet();

			if (c != null) {
				c.moveToFirst();
			}
			wallet.setId(c.getInt(c.getColumnIndex(KEY_ID)));
			wallet.setName(c.getString(c.getColumnIndex(KEY_NAME)));
			wallet.setTotalExpense(c.getInt(c.getColumnIndex(KEY_TOTAL_EXPENSE)));
			wallet.setTotalIncome(c.getInt(c.getColumnIndex(KEY_TOTAL_INCOME)));

		} finally {
			if (c != null) {
				c.close();
			}
		}
		return wallet;
	}

	// Get all wallets
	public List<Wallet> getAllWallets() {
		List<Wallet> wL = new ArrayList<Wallet>();
		String selectQuery = "SELECT  * FROM " + TABLE_WALLET;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		Wallet w = null;

		try {
			c = db.rawQuery(selectQuery, null);

			if (c.moveToFirst()) {
				do {
					w = new Wallet();
					w.setId(c.getInt(c.getColumnIndex(KEY_ID)));
					w.setName(c.getString(c.getColumnIndex(KEY_NAME)));
					w.setTotalExpense(c.getLong(c.getColumnIndex(KEY_TOTAL_EXPENSE)));
					w.setTotalIncome(c.getLong(c.getColumnIndex(KEY_TOTAL_INCOME)));

					wL.add(w);
				} while (c.moveToNext());

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return wL;

	}

	// Set recurring income
	public void createRecurringIncome(RecurringIncome rI) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_INCOME_ID, rI.get_income_id());
		values.put(KEY_END_DATE, rI.get_end_date());
		values.put(KEY_NOTE, rI.get_note());

		db.insert(TABLE_RECURRING_INCOME, null, values);
		Log.w("Database", rI.get_note() + " recurring income has been added to recurring incomes");

	}

	// Get recurring income by ID
	public RecurringIncome getRecurringIncome(int id) {
		String selectQuery = "SELECT * FROM " + TABLE_RECURRING_INCOME + " WHERE " + KEY_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		RecurringIncome ri = null;
		
		try{
		
		c = db.rawQuery(selectQuery, null);
		ri = new RecurringIncome();

		if (c != null) {
			c.moveToFirst();
		}
		ri.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
		ri.set_income_id(c.getInt(c.getColumnIndex(KEY_INCOME_ID)));
		ri.set_end_date(c.getString(c.getColumnIndex(KEY_END_DATE)));
		ri.set_note(c.getString(c.getColumnIndex(KEY_NOTE)));
	}
	finally{
		if(c!=null){
			c.close();
		}
	}
		return ri;
	}

	// Get all recurring incomes
	public List<RecurringIncome> getAllRecurringIncomes() {
		List<RecurringIncome> rI = new ArrayList<RecurringIncome>();
		String selectQuery = "SELECT  * FROM " + TABLE_RECURRING_INCOME;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		RecurringIncome i = null;
		
		try{
		c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				i = new RecurringIncome();
				i.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
				i.set_income_id(c.getInt(c.getColumnIndex(KEY_INCOME_ID)));
				i.set_end_date(c.getString(c.getColumnIndex(KEY_END_DATE)));
				i.set_note(c.getString(c.getColumnIndex(KEY_NOTE)));

				rI.add(i);
			} while (c.moveToNext());

		}
		}
		finally{
			if(c!=null){
				c.close();
			}
		}

		return rI;

	}

	// Set recurring expense
	public void createRecurringExpense(RecurringExpense rE) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EXPENSE_ID, rE.get_expense_id());
		values.put(KEY_END_DATE, rE.get_end_date());
		values.put(KEY_NOTE, rE.get_note());

		db.insert(TABLE_RECURRING_EXPENSE, null, values);
		Log.w("Database", rE.get_note() + " recurring income has been added to recurring expenses");

	}

	// Get all recurring Expenses
	public List<RecurringExpense> getAllRecurringExpenses() {
		List<RecurringExpense> rE = new ArrayList<RecurringExpense>();
		String selectQuery = "SELECT  * FROM " + TABLE_RECURRING_EXPENSE;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		RecurringExpense e = null;
		
		try{
			
		c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				e = new RecurringExpense();
				e.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
				e.set_expense_id(c.getInt(c.getColumnIndex(KEY_EXPENSE_ID)));
				e.set_end_date(c.getString(c.getColumnIndex(KEY_END_DATE)));
				e.set_note(c.getString(c.getColumnIndex(KEY_NOTE)));

				rE.add(e);
			} while (c.moveToNext());

		}
		}finally{
			if(c!=null){
				c.close();
			}
		}

		return rE;

	}

	// Get recurring expense by ID
	public RecurringExpense getRecurringExpense(int id) {
		String selectQuery = "SELECT * FROM " + TABLE_RECURRING_EXPENSE + " WHERE " + KEY_ID + " = " + id;
		Log.e("DATABASE HANDLER", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = null;
		RecurringExpense re = null;
		
		try{
		c = db.rawQuery(selectQuery, null);

		re = new RecurringExpense();

		if (c != null) {
			c.moveToFirst();
		}
		re.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
		re.set_expense_id(c.getInt(c.getColumnIndex(KEY_EXPENSE_ID)));
		re.set_end_date(c.getString(c.getColumnIndex(KEY_END_DATE)));
		re.set_note(c.getString(c.getColumnIndex(KEY_NOTE)));

		}
		finally{
			if(c!=null){
				c.close();
			}
		}
		return re;
	}

}
