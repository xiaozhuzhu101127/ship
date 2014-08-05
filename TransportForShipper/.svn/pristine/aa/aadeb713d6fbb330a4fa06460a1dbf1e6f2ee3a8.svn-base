package com.topjet.shipper.util;

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-7-15 上午9:29:19  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public enum GoodsSourceState {
	/**
	 * 已报价
	 */
	GSSTS_CALLPRICE(0),
	/**
	 * 已被抢
	 */
	GSSTS_SUBMIT(1),
	/**
	 * 抢货中
	 */
	GSSTS_WT(2),
	/**
	 * 已取消报价
	 */
	GSSTS_CANCE(3);

	private int state;
	private GoodsSourceState(int state){
		this.state = state;
	}

	public static GoodsSourceState get(int state){
		for(GoodsSourceState s : GoodsSourceState.values()){
			if(s.state == state){
				return s;
			}
		}
		return null;
	}
}
