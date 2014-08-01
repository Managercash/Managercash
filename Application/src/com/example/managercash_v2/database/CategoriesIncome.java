package com.example.managercash_v2.database;

public class CategoriesIncome {
	
	private int _id;
	private String _name;
	private String _img_src;
	
	public CategoriesIncome(int id, String name, String imgSrc){
		
		this._id = id;
		this._name = name;
		this._img_src = imgSrc;
		
	}
	
	public CategoriesIncome(String name, String imgSrc){

		this._name = name;
		this._img_src = imgSrc;
		
	}
	
	public CategoriesIncome(){
		
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

}
