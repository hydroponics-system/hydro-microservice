package com.hydro.app.user.client.domain.enums;

import com.hydro.common.enums.TextEnum;

/**
 * Enums for all the possible user roles.
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum WebRole implements TextEnum {
	USER(1, "USER"), SYSTEM_USER(2, "SYSTEM_USER"), DEVELOPER(3, "DEVELOPER"), ADMIN(4, "ADMIN");

	private int rank;

	private String textId;

	WebRole(int rank, String textId) {
		this.rank = rank;
		this.textId = textId;
	}

	/**
	 * This is the rank the webrole holds.
	 * 
	 * @return {@link Integer} of the webroles rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * String version of the enum value for the webrole.
	 * 
	 * @return {@link String} of the text id of the webrole enum.
	 */
	public String getTextId() {
		return textId;
	}

	/**
	 * This will get the {@link WebRole} for the given rank. If the rank value does
	 * not exist then it will return {@link WebRole#EMPLOYEE} rank.
	 * 
	 * @param rank The rank to search for in the enums
	 * @return {@link WebRole} of the rank found, if any.
	 */
	public static WebRole getRole(int rank) {
		for (WebRole w : WebRole.values())
			if (w.rank == rank)
				return w;
		return USER;
	}
}
