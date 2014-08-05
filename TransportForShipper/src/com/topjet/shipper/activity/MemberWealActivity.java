package com.topjet.shipper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.topjet.shipper.BaseActivity;
import com.topjet.shipper.R;

public class MemberWealActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_member_weal);
		super.onCreate(savedInstanceState);
		common_title.setText("会员福利");
		common_recommand.setVisibility(View.VISIBLE);
	}

	@Override
	protected void ioHandler(Message msg) {

	}

	@Override
	protected void doError(Message msg) {

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_category_mall:
			startActivity(new Intent(MemberWealActivity.this,
					ScoreShopCategoryActivity.class));
			break;
		case R.id.choujiang:

			Intent intent = new Intent(MemberWealActivity.this,
					MemberWealSearchActivity.class);
			startActivity(intent);
			break;
		}
	}

}
