
package com.ottego.knowfinance.Model;

import com.ottego.knowfinance.IHasDate;

public class DateValueEntity implements IHasDate {

	private int date;
	private float value;

	public DateValueEntity(float value, int date) {
		super();
		this.value = value;
		this.date = date;
	}

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(int date) {
		this.date = date;
	}

	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}
}
