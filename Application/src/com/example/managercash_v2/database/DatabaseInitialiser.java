package com.example.managercash_v2.database;


public class DatabaseInitialiser {
	DatabaseHandler db;
	
	public DatabaseInitialiser(DatabaseHandler db){
		this.db = db;
		// DATABASE TESTING
		

		// Create wallets
		Wallet w = new Wallet("Peter", 0, 0);
		db.createWallet(w);
		
		w = new Wallet("David", 0, 0);
		db.createWallet(w); 
		
		w = new Wallet("Jess", 0 , 0);
		db.createWallet(w);
		
		//Create categories
		CategoriesExpense cE = new CategoriesExpense("Gas", "drawable/gas", 15); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Electric", "drawable/electric", 15);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Water", "drawable/water", 15);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Education", "drawable/education", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Home", "drawable/home", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Leisure", "drawable/leisure", 5); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Food", "drawable/food", 5);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Drink", "drawable/drink", 3);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Food Shopping", "drawable/food_shopping", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("High Street Shopping", "drawable/high_street_shopping", 5);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Travel", "drawable/travel", 2);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Holiday", "drawable/holiday", 20);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Gifts", "drawable/gifts", 5); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Medical", "drawable/medical", 5); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Petrol", "drawable/petrol", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Internet Shopping", "drawable/internet_shopping", 10);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Gambling", "drawable/gambling", 10);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Investment", "drawable/investment", 10);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Family", "drawable/family", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Gaming", "drawable/gaming", 5); 
		db.createExpenseCategory(cE);
	}
	
}
