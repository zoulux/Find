package com.zgrjb.find.ui;

import com.zgrjb.find.R;
import com.zgrjb.find.ui.guide.ViewGuideActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
/**
 * ��activity���жϵ������ĸ�acitivity��ת�����Ը�acticity�ĳ���ʱ��ܶ�
 * @author Administrator
 *
 */
public class JudgeToGoActivity extends BaseActivity {
	//��ʼ����һ��Ϊfalse
	private boolean isFirstIn = false;
	//����һ����ͼ
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_judgetogo);
		SharedPreferences preferences = getSharedPreferences("first_pref",
				MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (isFirstIn) {
					// start guideactivity
					intent = new Intent(JudgeToGoActivity.this, ViewGuideActivity.class);
				} else {
					// start InitializeActivity
					intent = new Intent(JudgeToGoActivity.this, InitializeActivity.class);
				}
				JudgeToGoActivity.this.startActivity(intent);
				JudgeToGoActivity.this.finish();
			}
		}, 1);
	}

}
