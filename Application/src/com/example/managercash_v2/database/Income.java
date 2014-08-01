package com.example.managercash_v2.database;

public class Income {
    
    //private variables
    private int _id;
    private int _wallet_id;
    private int _category_id;
    private String _date;
    private long _amount;

     
    // Empty constructor
    public Income(){
         
    }
    // constructor
    public Income(int id, int _wallet_id, int _category_id, String _date, long _amount){
        this._id = id;
        this._wallet_id = _wallet_id;
        this._category_id = _category_id;
        this._date = _date;
        this._amount = _amount;
    }
     
    // constructor
    public Income(int _wallet_id, int _category_id, String _date, long _amount){
        this._wallet_id = _wallet_id;
        this._category_id = _category_id;
        this._date = _date;
        this._amount = _amount;
    }
    
    
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int get_wallet_id() {
		return _wallet_id;
	}
	public void set_wallet_id(int _wallet_id) {
		this._wallet_id = _wallet_id;
	}
	public int get_category_id() {
		return _category_id;
	}
	public void set_category_id(int _category_id) {
		this._category_id = _category_id;
	}
	public String get_date() {
		return _date;
	}
	public void set_date(String _date) {
		this._date = _date;
	}
	public long get_amount() {
		return _amount;
	}
	public void set_amount(long _amount) {
		this._amount = _amount;
	}
    

}