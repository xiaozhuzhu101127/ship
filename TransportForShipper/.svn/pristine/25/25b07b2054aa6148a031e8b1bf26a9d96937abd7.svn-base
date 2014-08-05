package com.topjet.shipper.util;

import com.topjet.shipper.R;

import android.content.Context;




/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-21 上午11:06:42  
 * Description:
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class DialogUtil {
	private static MyDialog TK_LEN_DIALOG;
	private static MyDialog TK_TYPE_DIALOG;
	private static MyDialog PROVINCE_DIALOG;
	private static MyDialog CITY_DIALOG;
	private static MyDialog GOODS_TYPE_DIALOG;
	private static MyDialog GOODS_DW_DIALOG;
	private static MyDialog PAYWAY_DIALOG;
	private static MyDialog GSLOADWAY_DIALOG;

	public static boolean isProvinceDialog(MyDialog dialog){
		if(null == PROVINCE_DIALOG || dialog != PROVINCE_DIALOG){
			return false;
		}
		return true;
	}

	public static boolean isCityDialog(MyDialog dialog){
		if(null == CITY_DIALOG || dialog != CITY_DIALOG){
			return false;
		}
		return true;
	}

	public static MyDialog showProvinceDialog(Context context){
		if(PROVINCE_DIALOG == null || !context.equals(PROVINCE_DIALOG.getContext())){
			PROVINCE_DIALOG = new MyDialog(context, R.layout.dialog_province, Dict.getProvinceDict());
		}
		PROVINCE_DIALOG.showDialog(0, 200);
		return PROVINCE_DIALOG;
	}

	public static MyDialog showCityDialog(Context context, String provinceCode){
		if(CITY_DIALOG == null || !context.equals(CITY_DIALOG.getContext())){
			CITY_DIALOG = new MyDialog(context, R.layout.dialog_city, Dict.getCityDict(provinceCode));
		}else{
			CITY_DIALOG.setData(Dict.getCityDict(provinceCode));
			CITY_DIALOG.setChanged(true);
		}
		CITY_DIALOG.showDialog(0, 200);
		return CITY_DIALOG;
	}

	public static MyDialog showTruckLengthDialog(Context context){
		if(TK_LEN_DIALOG == null || !context.equals(TK_LEN_DIALOG.getContext())){
			TK_LEN_DIALOG = new MyDialog(context, R.layout.dialog_tk_len, Dict.getTruckLengthDict());
		}
		TK_LEN_DIALOG.showDialog(0, 200);
		return TK_LEN_DIALOG;
	}

	public static MyDialog showTruckTypeDialog(Context context){
		if(TK_TYPE_DIALOG == null || !context.equals(TK_TYPE_DIALOG.getContext())){
			TK_TYPE_DIALOG = new MyDialog(context, R.layout.dialog_tk_type, Dict.getTruckTypeDict());
		}
		TK_TYPE_DIALOG.showDialog(0, 200);
		return TK_TYPE_DIALOG;
	}
	public static MyDialog showGoodsTypeDialog(Context context){
		if(GOODS_TYPE_DIALOG == null || !context.equals(GOODS_TYPE_DIALOG.getContext())){
			GOODS_TYPE_DIALOG = new MyDialog(context, R.layout.dialog_gs_type, Dict.getGsTypeDict());
		}
		GOODS_TYPE_DIALOG.showDialog(0, 200);
		return GOODS_TYPE_DIALOG;
	}
	public static MyDialog showGoodsDwDialog(Context context){
		if(GOODS_DW_DIALOG == null || !context.equals(GOODS_DW_DIALOG.getContext())){
			GOODS_DW_DIALOG = new MyDialog(context, R.layout.dialog_gs_dw, Dict.getGoodsDwDict());
		}
		GOODS_DW_DIALOG.showDialog(0, 200);
		return GOODS_DW_DIALOG;
	}
	public static MyDialog showPayWayDialog(Context context){
		if(PAYWAY_DIALOG == null || !context.equals(PAYWAY_DIALOG.getContext())){
			PAYWAY_DIALOG = new MyDialog(context, R.layout.dialog_payway, Dict.getPayWayDict());
		}
		PAYWAY_DIALOG.showDialog(0, 200);
		return PAYWAY_DIALOG;
	}
	public static MyDialog showGsLoadWayDialog(Context context){
		if(GSLOADWAY_DIALOG == null || !context.equals(GSLOADWAY_DIALOG.getContext())){
			GSLOADWAY_DIALOG = new MyDialog(context, R.layout.dialog_loadway, Dict.getGsLoadDict());
		}
		GSLOADWAY_DIALOG.showDialog(0, 200);
		return GSLOADWAY_DIALOG;
	}
}
