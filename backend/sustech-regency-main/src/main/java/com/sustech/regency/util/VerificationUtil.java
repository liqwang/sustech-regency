package com.sustech.regency.util;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static com.sustech.regency.web.util.AssertUtil.asserts;

public class VerificationUtil {
	public static Set<Character> digits=Set.of('0','1','2','3','4','5','6','7','8','9');
	public static Set<Character> specialChars=Set.of('`','~','!','@','#','$','%','^','&','*','(',')','_','-','+','=','{','[',']','}','|','\\',';',':','\'','\"',',','<','>','.','?','/');
	public static Set<Character> upperChars=new HashSet<>();
	public static Set<Character> lowerChars=new HashSet<>();
	static{
		for(char c='a';c<='z';c++){
			upperChars.add((char)(c-32));
			lowerChars.add(c);
		}
	}

	public static void judge(String password){
		HashSet<Character> chars = new HashSet<>(password.length());
		for (char c : password.toCharArray()) {
			chars.add(c);
		}
		asserts(hasIntersect(chars,digits),"密码必须包含数字");
		asserts(hasIntersect(chars,specialChars),"密码必须包含特殊字符");
		asserts(hasIntersect(chars,upperChars),"密码必须包含大写字母");
		asserts(hasIntersect(chars,lowerChars),"密码必须包含小写字母");
	}

	/**
	 * 判断setA和setB的交集是否为空
	 */
	public static <E> boolean hasIntersect(Set<E> setA, Set<E> setB){
		Set<E> set = new HashSet<>(setA);
		set.retainAll(setB);
		return !set.isEmpty();
	}

	/**
	 * 生成六位数字验证码
	 */
	public static String generateVerificationCode(){
		StringBuilder code = new StringBuilder((int) (Math.random() * (1e6)) + "");
		while(code.length()!=6){
			code.insert(0, "0");
		}
		return code.toString();
	}

	public static void validateCode(String code,String trueCode){
		asserts(trueCode!=null,"验证码已过期，请重新发送");
		asserts(trueCode.equals(code),"验证码错误");
	}

	public static Integer getUserId(){
		return (int)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
