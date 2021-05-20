package com.revso.movies.utils;

import org.springframework.mock.web.MockHttpServletRequest;

public class TestUtils {
	
	public static MockHttpServletRequest buildMockHttpServletRequest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setLocalPort(8080);
		request.setRemoteAddr("localhost");
		return request;
	}
}
