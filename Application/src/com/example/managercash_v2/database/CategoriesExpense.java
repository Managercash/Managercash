package com.example.managercash_v2.database;

public class CategoriesExpense {

	private int _id;
	private String _name;
	private String _img_src;
	private int _increment;
	private int _temp_amount;
	
	
	public CategoriesExpense (int id, String name, String imgSrc, int increment){
		this._id = id;
		this._name = name;
		this._img_src = imgSrc;
		this._increment = increment;
	}
	
	public CategoriesExpense (String name, String imgSrc, int increment){
		this._name = name;
		this._img_src = imgSrc;
		this._increment = increment;
	}
	
	

	
	public CategoriesExpense(){
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_img_src() {
		return _img_src;
	}

	public void set_img_src(String _img_src) {
		this._img_src = _img_src;
	}

	public int get_increment() {
		return _increment;
	}

	public void set_increment(int _increment) {
		this._increment = _increment;
	}
	
	public void add_to_temp_amount(int temp){
		this._temp_amount = this._temp_amount + temp;
	}
	
	public int get_temp_amount(){
		return _temp_amount;
	}
	
	public void set_temp_amount(int amount){
		this._temp_amount = amount;
	}
	
}
