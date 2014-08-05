package com.topjet.shipper.util;

import java.util.regex.Pattern;
 
/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-2 下午4:51:55  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class PlateNoValidator {
	private static final String ORDER_REG = "^([a-zA-Z0-9]+)$";

	public static boolean validateOrder(String order) {
		if(Common.isEmpty(order) || order.length() != 5){
			return false;
		}
		return Pattern.compile(ORDER_REG).matcher(order).matches();
	}
}
