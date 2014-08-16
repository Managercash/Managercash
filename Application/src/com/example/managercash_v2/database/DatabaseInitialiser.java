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
		
		//Create expense categories
		CategoriesExpense cE = new CategoriesExpense("Gas", "drawable/gasutility", 15); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Electric", "drawable/electricutility", 15);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Water", "drawable/waterutility", 15);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Education", "drawable/education", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Home", "drawable/home", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Leisure", "drawable/leisure", 5); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Food", "drawable/foodout", 5);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Drink", "drawable/drinksout", 3);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Food Shopping", "drawable/foodshopping", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("High Street Shopping", "drawable/highstreetshopping", 5);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Travel", "drawable/travel", 2);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Holiday", "drawable/holiday", 20);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Gifts", "drawable/gifts", 5); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Medical", "drawable/medicine", 5); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Petrol", "drawable/petrol", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Internet Shopping", "drawable/onlineshopping", 10);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Gambling", "drawable/gambling", 10);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Investment", "drawable/investment", 10);
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Family", "drawable/lovedones", 10); 
		db.createExpenseCategory(cE);
		
		cE = new CategoriesExpense("Gaming", "drawable/gaming", 5); 
		db.createExpenseCategory(cE);
		
		//Create Income Categories
		CategoriesIncome cI = new CategoriesIncome("Gift", "drawable/gifts"); 
		db.createIncomeCategory(cI);
		
		cI = new CategoriesIncome("Award", "drawable/award"); 
		db.createIncomeCategory(cI);
		
		cI = new CategoriesIncome("Interest", "drawable/interest"); 
		db.createIncomeCategory(cI);
		
		cI = new CategoriesIncome("Stocks", "drawable/stocks"); 
		db.createIncomeCategory(cI);
		
		cI = new CategoriesIncome("Sales", "drawable/selling"); 
		db.createIncomeCategory(cI);
		
		cI = new CategoriesIncome("Insurance", "drawable/insurance"); 
		db.createIncomeCategory(cI);
		
		cI = new CategoriesIncome("Salary", "drawable/salary"); 
		db.createIncomeCategory(cI);
		
		cI = new CategoriesIncome("Investment", "drawable/investment"); 
		db.createIncomeCategory(cI);
	}
	
}
