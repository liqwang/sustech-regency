package com.sustech.regency.web.util;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("unused")
public class JwtUtil {
	public static final long DEFAULT_TTL=3600*24*7; //JWT的默认TTL为一周
	public static final String KEY="QuanQuan"; //解加密使用的密钥
	public static final String ISSUER="QuanQuan"; //签发者

	public static final Logger LOGGER = LogManager.getLogger(JwtUtil.class);

	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	/**
	 * @param subject Token中要存放的数据(Json格式)
	 */
	public static String createJwt(String subject){
		return createJwt(subject,null);
	}

	/**
	 * @param subject Token中要存放的数据(Json格式)
	 */
	public static String createJwt(String subject,Long ttlMills){
		return createJwt(subject,ttlMills,getUUID());
	}

	/**
	 * @param subject Token中要存放的数据(Json格式)
	 */
	public static String createJwt(String subject,Long ttlMills,String uuid){
		return getJwtBuilder(subject,ttlMills,uuid).compact();
	}

	/**
	 * @param subject Token中要存放的数据(Json格式)
	 */
	private static JwtBuilder getJwtBuilder(String subject, Long ttlMills, String uuid){
		if(ttlMills==null){
			ttlMills= DEFAULT_TTL*1000;
		}
		return Jwts.builder()
				   .setId(uuid)
				   .setSubject(subject)    //设置主题，这里是Json数据
				   .setIssuer(ISSUER)  //签发者
				   .setIssuedAt(new Date())
				   .signWith(SignatureAlgorithm.HS256, createKey()) //HS256对称加密算法
				   .setExpiration(new Date(System.currentTimeMillis()+ttlMills));
	}

	public static SecretKey createKey(){
		byte[] decodedKey = Base64.getDecoder().decode(KEY);
		return new SecretKeySpec(decodedKey,"AES");
	}

	public static Claims parseJwt(String jwt){
		try{
			return Jwts.parser()
					   .setSigningKey(createKey())
					   .parseClaimsJws(jwt)
					   .getBody();
		}catch (Exception e){
			if(e instanceof ExpiredJwtException){
				LOGGER.info("Expired JWT: {}",jwt);
				throw new BadCredentialsException(String.format("JWT %s is expired",jwt),e);
			}else{
				LOGGER.info("Invalid JWT: {}",jwt);
				throw new BadCredentialsException(String.format("JWT %s is invalid",jwt),e);
			}
		}
	}
}
