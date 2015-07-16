package com.zgrjb.find.ui.guide;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zgrjb.find.R;
import com.zgrjb.find.adapter.GuideViewPagerAdapter;
import com.zgrjb.find.ui.BaseActivity;
import com.zgrjb.find.ui.InitializeActivity;
import com.zgrjb.find.ui.LogInActivity;

public class ViewGuideActivity extends Activity implements OnClickListener, OnPageChangeListener {
	// ����һ��viewpager��xml�ļ����viewpager���Ӧ
	private ViewPager viewPager;
	// ����һ��GuideViewPagerAdapter������
	private GuideViewPagerAdapter vpAdapter;
	// ����list������6��view
	private ArrayList<View> views;
	// ���� ��СԲ��
	private ImageView[] points;
	// СԲ��ĵ�ǰ��λ��
	private int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_view_guide);
		// showTitleText("Find");
		initView();
		initData();
		// ���ǵ�һ��ʹ�ø�app������acticity
		SharedPreferences preferences = getSharedPreferences("first_pref",
				MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isFirstIn", false);
		editor.commit();

	}

	/**
	 * ��ʼ��View
	 */
	private void initView() {
		views = new ArrayList<View>();
		viewPager = (ViewPager) this.findViewById(R.id.viewpager);
		LayoutInflater inflater = getLayoutInflater();

		views.add(inflater.inflate(R.layout.guide_item_1, null));
		views.add(inflater.inflate(R.layout.guide_item_2, null));
		views.add(inflater.inflate(R.layout.guide_item_3, null));
		views.add(inflater.inflate(R.layout.guide_item_4, null));
		views.add(inflater.inflate(R.layout.guide_item_5, null));
	//	views.add(inflater.inflate(R.layout.guide_item_6, null));
		vpAdapter = new GuideViewPagerAdapter(views);
	}

	/**
	 * ��ʼ������Ҫ������
	 */
	private void initData() {
		viewPager.setAdapter(vpAdapter);
		viewPager.setOnPageChangeListener(this);
		initPoint();

	}

	/**
	 * ��ʼ��СԲ��
	 */
	private void initPoint() {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);

		points = new ImageView[4];

		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < 4; i++) {
			// �õ�һ��LinearLayout�����ÿһ����Ԫ��
			points[i] = (ImageView) linearLayout.getChildAt(i);
			// Ĭ�϶���Ϊ��ɫ
			points[i].setEnabled(true);
			// ��ÿ��С�����ü���
			points[i].setOnClickListener(this);
			// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
			points[i].setTag(i);
		}

		// ���õ���Ĭ�ϵ�λ��
		currentIndex = 0;
		// ����Ϊ��ɫ����ѡ��״̬
		points[currentIndex].setEnabled(false);
	}

	@Override
	public void onPageScrollStateChanged(int position) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onPageSelected(int position) {
		// ���õײ�С��ѡ��״̬
		setCurDot(position);
		
		
		if (position==4) {
			mHandler.sendEmptyMessageAtTime(0, 1000);
			
		}
		
		
		
	/*	
		if (position == 5) {
			setDrawablePath(getResources().getDrawable(R.drawable.right_bt1));
			rightButtonIsVisible(true);
			rightImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(ViewGuideActivity.this,
							InitializeActivity.class));
					ViewGuideActivity.this.finish();
				}
			});
		}
		*/

	}


	/**
	 * �����СԲ���ʱ��ҳ�淢���仯��СԲ�㷢���仯
	 */
	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);

	}

	/**
	 * ���õ�ǰҳ���λ��
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= 4) {
			return;
		}
		viewPager.setCurrentItem(position);
	}

	/**
	 * ���õ�ǰ��С���λ��
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > 4 - 1 || currentIndex == positon) {
			return;
		}
		points[positon].setEnabled(false);
		points[currentIndex].setEnabled(true);

		currentIndex = positon;
	}
	
	Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			
			startActivity(new Intent(ViewGuideActivity.this,
					InitializeActivity.class));
			ViewGuideActivity.this.finish();
			
		};
		
	};

}
