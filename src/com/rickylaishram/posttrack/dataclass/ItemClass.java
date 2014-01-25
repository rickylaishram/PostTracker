package com.rickylaishram.posttrack.dataclass;

public class ItemClass {
	public String id;
	public String name;
	public String booked_at;
	public String booked_on;
	public String delivered_at;
	public String delivered_on;
	public Integer added_on;
	public Integer confimed;
	public Integer new_exist;
	
	public ItemClass(){}
	
	public void set(String id, String name, String booked_at, String booked_on,
					String delivered_at, String delivered_on, Integer added_on,
					Integer confirmed, Integer new_exist) {
		this.id 			= id;
		this.name			= name;
		this.booked_at		= booked_at;
		this.booked_on		= booked_on;
		this.delivered_on	= delivered_on;
		this.delivered_at	= delivered_at;
		this.added_on		= added_on;
		this.confimed		= confirmed;
		this.new_exist		= new_exist;
	}

}
