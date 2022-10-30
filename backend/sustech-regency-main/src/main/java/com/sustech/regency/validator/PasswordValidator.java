package com.sustech.regency.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.StringUtils.hasText;
import static java.util.stream.Collectors.toSet;

public class PasswordValidator implements ConstraintValidator<Password,String> {

	private ConstraintValidatorContext context;
	public static final Set<Character> DIGITS =Set.of('0','1','2','3','4','5','6','7','8','9');
	public static final Set<Character> SPECIAL_CHARS =Set.of('`','~','!','@','#','$','%','^','&','*','(',')','_','-','+','=','{','[',']','}','|','\\',';',':','\'','\"',',','<','>','.','?','/');
	public static final Set<Character> UPPER_CHARS=new HashSet<>();
	public static final Set<Character> LOWER_CHARS=new HashSet<>();
	static{
		for(char c='a';c<='z';c++){
			UPPER_CHARS.add((char)(c-32));
			LOWER_CHARS.add(c);
		}
	}

	/**
	 * 判断setA和setB的交集是否为空
	 */
	public static <E> boolean hasIntersect(Set<E> setA, Set<E> setB){
		Set<E> set = new HashSet<>(setA);
		set.retainAll(setB);
		return !set.isEmpty();
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		this.context=context;
		if(!asserts(hasText(password), "密码为空") ||
		   !asserts(password.length()>=8 && password.length()<=30,"密码必须为8-30位")){
			return false;
		}
		Set<Character> chars=password.codePoints()
							.mapToObj(ch->(char)ch)
							.collect(toSet());
		return asserts(hasIntersect(chars,DIGITS),"密码必须包含数字")
			&& asserts(hasIntersect(chars,SPECIAL_CHARS),"密码必须包含特殊字符")
			&& asserts(hasIntersect(chars,UPPER_CHARS),"密码必须包含大写字母")
			&& asserts(hasIntersect(chars,LOWER_CHARS),"密码必须包含小写字母");
	}

	private boolean asserts(boolean condition, String message){
		if(!condition){
			context.disableDefaultConstraintViolation();
			//这里如果message中包含用户输入的password就会有SpEL注入漏洞,只要把message写死就好了
			//https://cloud.tencent.com/developer/article/1642366
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}
		return condition;
	}
}
