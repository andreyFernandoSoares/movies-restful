package com.revso.movies.enums;

import java.util.List;

public enum StreamingEnum {
	NETFLIX, AMAZONPRIME, TELECINE, GLOBOPLAY, DISNEY, HBOMAX;
	
	public static List<StreamingEnum> list;
	
	static {
		list.add(NETFLIX);
		list.add(AMAZONPRIME);
		list.add(TELECINE);
		list.add(GLOBOPLAY);
		list.add(DISNEY);
		list.add(HBOMAX);
	}
}
