package com.topjet.shipper.adapter;

import org.json.JSONObject;

import com.topjet.shipper.R;
import com.topjet.shipper.model.Identity;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserTypeTongJiAdapter extends CommonJsonAdapter {

	public UserTypeTongJiAdapter(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		 view = context.getLayoutInflater().inflate(
				R.layout.item_usertype_tongji_item, null);
		JSONObject g = (JSONObject) getItem(position);

		((TextView) view.findViewById(R.id.usrType)).setText(Identity
				.getIdentity(g.optString("DCT_UT")).getDescription());
		((TextView) view.findViewById(R.id.memberCount)).setText(""+g
				.optInt("MEMBER_NUMBER"));
		((TextView) view.findViewById(R.id.queryCount)).setText(""+g
				.optInt("QUERY_NUMBER"));

		return view;
	}

}
