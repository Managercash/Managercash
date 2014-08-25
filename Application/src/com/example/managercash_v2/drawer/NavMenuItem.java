package com.example.managercash_v2.drawer;

import android.content.Context;

public class NavMenuItem implements NavDrawerItem {

	public static final int ITEM_TYPE = 1;

	private int id;
	private String label;
	private int counter; //Wallet balance
	private int icon;
	private boolean updateActionBarTitle;
	private boolean usesCounter;

	protected NavMenuItem() {
	}

	public static NavMenuItem create(int id, String label, String icon,
			boolean updateActionBarTitle, Context context) {
		NavMenuItem item = new NavMenuItem();
		item.setId(id);
		item.setLabel(label);
		item.usesCounter(false);
		item.setIcon(context.getResources().getIdentifier(icon, "drawable",
				context.getPackageName()));
		item.setUpdateActionBarTitle(updateActionBarTitle);
		return item;
	}

	public static NavMenuItem create(int id, String label, int counter,
			String icon, boolean updateActionBarTitle, Context context) {
		NavMenuItem item = new NavMenuItem();
		item.setId(id);
		item.setLabel(label);
		item.usesCounter(true);
		item.setCounter(counter);
		item.setIcon(context.getResources().getIdentifier(icon, "drawable",
				context.getPackageName()));
		item.setUpdateActionBarTitle(updateActionBarTitle);
		return item;
	}

	@Override
	public int getType() {
		return ITEM_TYPE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getCounter() {
		if(usesCounter)
			return counter;
		else 
			return -1;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public boolean usesCounter() {
		return usesCounter;
	}

	public void usesCounter(boolean usesCounter) {
		this.usesCounter = usesCounter;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean updateActionBarTitle() {
		return this.updateActionBarTitle;
	}

	public void setUpdateActionBarTitle(boolean updateActionBarTitle) {
		this.updateActionBarTitle = updateActionBarTitle;
	}
}
