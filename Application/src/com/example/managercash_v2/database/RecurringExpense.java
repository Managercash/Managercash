package com.example.managercash_v2.database;

public class RecurringExpense {

	private int _id;
	private int _expense_id;
	private String _end_date;
	private String _note;
	
	public RecurringExpense(int id, int expenseId, String endDate, String note){
		
		this._id = id;
		this._expense_id = expenseId;
		this._end_date = endDate;
		this._note = note;
	}
	
	public RecurringExpense(int expenseId, String endDate, String note){
		
		this._expense_id = expenseId;
		this._end_date = endDate;
		this._note = note;
	}
	
	public RecurringExpense(){
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_expense_id() {
		return _expense_id;
	}

	public void set_expense_id(int _expense_id) {
		this._expense_id = _expense_id;
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
