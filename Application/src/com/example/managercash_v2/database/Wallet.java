package com.example.managercash_v2.database;

public class Wallet {
    
    //private variables
    private int _id;
    private String _name;
    private long _total_income;
    private long _total_expense;

     
    // Empty constructor
    public Wallet(){
         
    }
    // constructor
    public Wallet(int id, String name, long _total_income, long _total_expense){
        this._id = id;
        this._name = name;
        this._total_income = _total_income;
        this._total_expense = _total_expense;
    }
     
    // constructor
    public Wallet(String name, long _total_income, long _total_expense){
        this._name = name;
        this._total_income = _total_income;
        this._total_expense = _total_expense;
    }
    
    public int getId(){
        return this._id;
    }
     
    public void setId(int id){
        this._id = id;
    }
     
    public String getName(){
        return this._name;
    }
     
    public void setName(String name){
        this._name = name;
    }
     
    public long getTotalExpense(){
        return this._total_expense;
    }
     
    public void setTotalExpense(long total_expense){
        this._total_expense = total_expense;
    }
    
    public long getTotalIncome(){
    	return this._total_income;
    }
    
    public void setTotalIncome(long totalIncome){
    	this._total_income = totalIncome;
    }
}