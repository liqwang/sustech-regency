package com.sustech.regency.util;

import org.junit.jupiter.api.Test;

public class VerificationUtilTest {
	@Test
	void testJudge(){
		System.out.println(VerificationUtil.specialChars.size());
		System.out.println('A'-'a');
		System.out.println(VerificationUtil.upperChars.size());
	}

	@Test
	void testGenerateVerificationCode(){
		System.out.println(VerificationUtil.generateVerificationCode());
	}
}
