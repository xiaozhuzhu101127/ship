package com.topjet.shipper.goods;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.core.LoadData;
import com.topjet.shipper.util.Common;
import com.topjet.shipper.util.DefaultConst;
import com.topjet.shipper.util.Dict;
import com.topjet.shipper.util.ShareHelper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class GoodsManager {
	
	private Context context;
	public static GoodsManager instance;
	private JSONObject dealInfo;
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DefaultConst.CMD_GOODS_DEAL:
				dealGoods((JSONObject)msg.obj);
				break;
			}
		}

	};
	private void dealGoods(JSONObject data) {
		try {
			dealInfo = data.getJSONObject("dealInfo");
				if(null != dealInfo){//成交提示
					String ct_target = Common.splitBX(dealInfo.optString("CT_TARGET"));
					StringBuilder info = new StringBuilder("“去");
					info.append(Dict.getLocation(ct_target)).append("、");
					info.append(Dict.getTruckLength(dealInfo.optString("TKLEN"))).append("、");
					info.append(Common.weightOrVolume(dealInfo.optString("GSSCALE"), dealInfo.optString("GSUNIT"))).append("”");
					BaseActivity.showTradeDialog(dealInfo.optLong("GSID"),info.toString());
				}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	//单例
	public synchronized static GoodsManager getIntance(Context context){
		if(null == instance)
			instance = new GoodsManager(context);
		return instance;
	}
	//构造方法私有化
	private GoodsManager(Context context){
		this.context = context;
	}
	//轮询
	public void checkGoods(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USRID", ShareHelper.getInstance(context).getUserId());
		LoadData.getInstance().getDealInfo(handler, DefaultConst.CMD_GOODS_DEAL, params);
	}

}
