package com.revso.movies.enums;

import java.util.List;

public enum CategoryEnum {
	COMEDY, HORROR, ACTION, ADVENTURE, ROMANCE, FICTION;
	
	public static List<CategoryEnum> list;
	
	static {
		list.add(COMEDY);
		list.add(HORROR);
		list.add(ACTION);
		list.add(ADVENTURE);
		list.add(ROMANCE);
		list.add(FICTION);
	}
}
