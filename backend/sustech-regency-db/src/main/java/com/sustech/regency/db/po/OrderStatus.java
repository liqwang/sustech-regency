package com.sustech.regency.db.po;

import java.lang.Enum;

import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

/**
 * 当数据库的存储类型为varchar时，使用{@link EnumTypeHandler}并最终调用{@link Enum#valueOf}进行映射<p>
 * 当数据库的存储类型为int, bigint, smallint等类型时，使用{@link EnumOrdinalTypeHandler}进行映射
 */
public enum OrderStatus {

	PAYED,
	NOT_COMMENTED,
	COMMENTED,
	REFUNDED
}
