package com.zgrjb.find.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import com.zgrjb.find.R;
import com.zgrjb.find.utils.ShakeListener;
import com.zgrjb.find.utils.ShakeListener.OnShakeListener;

public class ShackActivity extends Activity {
	// ����һ��ҡһҡ����
	ShakeListener mShakeListener = null;
	// ����һ������
	private Vibrator mVibrator;
	// ����һ�����ϵ�layout
	private RelativeLayout mImgUp;
	// ����һ�����µ�layout
	private RelativeLayout mImgDn;
	// ����һ���������ֵ���
	private MediaPlayer player;
	// ��mShakeListenerֹͣ��ʱ�򣬻�ȡ��ǰ��ʱ��
	long currentTimestop;
	// ��mShakeListener������ʱ�򣬻�ȡ��ǰ��ʱ��
	long currentTimestart;
	// �ж��Ƿ��ҡ����ʼ��Ϊ��ҡ״̬
	private boolean isOnshake = true;
	// ����һ��ʱ��
	private long time = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_ui_discover_shack);

		mVibrator = (Vibrator) getApplication().getSystemService(
				VIBRATOR_SERVICE);

		mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
		mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
		mShakeListener = new ShakeListener(ShackActivity.this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {
				startAnim(); // ��ʼ ҡһҡ���ƶ���
				startVibrato(); // ��ʼ ��
				controlTheShake();
			}
		});
	}

	/**
	 * ͨ�����̶߳�ҡһҡ�Ŀ���
	 */
	private void controlTheShake() {
		mShakeListener.stop();
		isOnshake = true;
		currentTimestop = System.currentTimeMillis();
		time = currentTimestop;

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				currentTimestart = System.currentTimeMillis();
				mShakeListener.start();
				isOnshake = false;
			}
		}, 2000);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isOnshake) {
					mShakeListener.stop();
					ProgressDialog dialog2 = ProgressDialog.show(
							ShackActivity.this, "��ʾ", "���Һ�����");
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							startActivity(new Intent(ShackActivity.this,
									MapActivity.class));
							finish();
						}
					}, 2000);
				}
			}
		}, 3000);
	}

	/**
	 * ����ҡһҡ��������
	 */
	private void startAnim() {
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-0.5f);
		mytranslateanimup0.setDuration(1000);
		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				+0.5f);
		mytranslateanimup1.setDuration(1000);
		mytranslateanimup1.setStartOffset(1000);
		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);
		mImgUp.startAnimation(animup);

		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				+0.5f);
		mytranslateanimdn0.setDuration(1000);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-0.5f);
		mytranslateanimdn1.setDuration(1000);
		mytranslateanimdn1.setStartOffset(1000);
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		mImgDn.startAnimation(animdn);
	}

	/**
	 * ������
	 */
	private void startVibrato() {

		player = MediaPlayer.create(this, R.raw.notify);

		player.setLooping(false);
		player.start();

		// ������
		mVibrator.vibrate(new long[] { 600, 300, 600, 300 }, -1); // ��һ�����������ǽ������飬
																	// �ڶ����������ظ�������-1Ϊ���ظ�����-1���մ�pattern��ָ���±꿪ʼ�ظ�
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mShakeListener.start();

	}

	@Override
	protected void onPause() {

		super.onPause();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}
}