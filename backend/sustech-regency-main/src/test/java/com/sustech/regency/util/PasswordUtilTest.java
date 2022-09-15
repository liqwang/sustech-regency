package com.sustech.regency.util;

import org.junit.jupiter.api.Test;

public class PasswordUtilTest {
	@Test
	void test(){
		System.out.println(PasswordUtil.specialChars.size());
		System.out.println('A'-'a');
		System.out.println(PasswordUtil.upperChars.size());
	}
}
