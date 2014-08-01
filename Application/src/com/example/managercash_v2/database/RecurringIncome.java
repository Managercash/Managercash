package com.example.managercash_v2.database;

public class RecurringIncome {

	private int _id;
	private int _income_id;
	private String _end_date;
	private String _note;
	
	public RecurringIncome(int id, int incomeId, String endDate, String note){
		
		this._id = id;
		this._income_id = incomeId;
		this._end_date = endDate;
		this._note = note;
	}
	
	public RecurringIncome(int incomeId, String endDate, String note){
		
		this._income_id = incomeId;
		this._end_date = endDate;
		this._note = note;
	}
	
	public RecurringIncome(){
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_income_id() {
		return _income_id;
	}

	public void set_income_id(int _income_id) {
		this._income_id = _income_id;
	}

	public String get_end_date() {
		return _end_date;
	}

	public void set_end_date(String _end_date) {
		this._end_date = _end_date;
	}

	public String get_note() {
		return _note;
	}

	public void set_note(String _note) {
		this._note = _note;
	}
}
