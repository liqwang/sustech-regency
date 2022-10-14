package com.sustech.regency.web.util;

import com.sustech.regency.web.handler.ApiException;

public class AssertUtil {
	public static void asserts(boolean condition, String message){
		if(!condition){
			throw ApiException.badRequest(message);
		}
	}
}
