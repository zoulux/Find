package com.zgrjb.find.ui;

import java.io.File;

import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.zgrjb.find.R;
import com.zgrjb.find.bean.MyUser;
import com.zgrjb.find.config.ImgUir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;

public class RegistFinalActivity extends BaseActivity implements
		OnClickListener {
	private RadioGroup sexGroup;// ����һ��radioGoup���ж��Ա�
	private RadioButton sexBoy, sexGirl;// ����һ��radioGoup��ѡ���Ա�
	private NumberPicker agePicker;// ����һ��NumberPicker��ѡ������
	// ��registactivity���ȡ�û������ǳƺ�����
	private String getUserName, getUserNick, getUserPassWord;
	private boolean sex = true;// �Ա�ΪtrueʱĬ��Ϊ��
	private int age = 20;// Ĭ�ϳ�ʼ����Ϊ22��

	// ����һ��ȷ��ע��İ�ť
	private Button ensureRegistButton;

	// ����һ��ȡ��ע��İ�ť
	private Button cancleRegistButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_regist_final);
		init();
		initIntent();
	}

	/**
	 * ��ʼ����Registactivity���ȡ��ֵ
	 */
	private void initIntent() {
		Intent intent = getIntent();
		getUserName = intent.getStringExtra("userNameString");
		getUserNick = intent.getStringExtra("nickString");
		getUserPassWord = intent.getStringExtra("passwordString");

	}

	/**
	 * ��ʼ��id�ͼ���
	 */
	private void init() {
		sexBoy = (RadioButton) this.findViewById(R.id.id_regist_RaddioBoy);
		sexGirl = (RadioButton) this.findViewById(R.id.id_regist_RaddioGirl);
		agePicker = (NumberPicker) this.findViewById(R.id.id_regist_agePicker);
		sexGroup = (RadioGroup) findViewById(R.id.id_regist_RadioGroup);

		ensureRegistButton = (Button) this.findViewById(R.id.ensureRegist);
		cancleRegistButton = (Button) this.findViewById(R.id.cancleRegist);
		ensureRegistButton.setOnClickListener(this);
		cancleRegistButton.setOnClickListener(this);
		initRadioGroup();
		initNumberPicker();
	}

	/**
	 * ��ʼ������ѡ����
	 */
	private void initNumberPicker() {
		agePicker.setMinValue(1);
		agePicker.setMaxValue(99);
		agePicker.setValue(20);
		agePicker.getChildAt(0).setFocusable(false);

		agePicker.setFormatter(new Formatter() {

			@Override
			public String format(int value) {
				String tmpStr = String.valueOf(value);
				if (value < 10) {
					tmpStr = "0" + tmpStr;
				}
				return tmpStr;
			}
		});

		agePicker.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				age = newVal;

			}
		});

	}

	/**
	 * ��ʼ���Ա�ѡ���radioGroup
	 */
	private void initRadioGroup() {
		sexGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkId) {
				if (checkId == sexBoy.getId()) {
					sex = true;
				}
				if (checkId == sexGirl.getId()) {
					sex = false;
				}

			}
		});

	}

	/**
	 * ����õ���Ϣ�ϴ������������ע��
	 */

	ProgressDialog progress;

	private void registeToServer() {

		// final ProgressDialog progress = new
		// ProgressDialog(RegistActivity.this);
		progress = new ProgressDialog(RegistFinalActivity.this);

		final MyUser user = new MyUser();
		user.setUsername(getUserName);
		user.setNick(getUserNick);
		user.setPassword(getUserPassWord);
		user.setSex(sex);
		user.setAge(age);
		user.setInstallId(BmobInstallation.getInstallationId(this));
		user.setDeviceType("android");

		progress.setMessage("����ע��...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		user.signUp(RegistFinalActivity.this, new SaveListener() {
			@Override
			public void onSuccess() {
				// ShowToast("ע��ɹ�");
				BmobUserManager.getInstance(RegistFinalActivity.this)
						.bindInstallationForRegister(user.getUsername());
				// ���ϴ�ͷ���ļ�
				uploadAvatar();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				if (arg0 == 202) {
					ShowToast("�û����Ѵ���");
				}

			}
		});

	}

	/**
	 * �ϴ�ͷ��
	 */
	protected void uploadAvatar() {
		String filePath = ImgUir.ALBUM_PATH + "cut.jpg";
		final BmobFile pFile = new BmobFile(new File(filePath));
		pFile.upload(this, new UploadFileListener() {

			@Override
			public void onSuccess() {
				String pathAvatar = pFile.getFileUrl(RegistFinalActivity.this);
				System.out.println("ͼ���ļ��ϴ��ɹ�" + pathAvatar);
				updateMyAvatar(pathAvatar);

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				System.out.println(arg0 + ":" + arg1);

			}
		});

	}

	/**
	 * ����ͷ��
	 * 
	 * @param path
	 */
	protected void updateMyAvatar(String path) {

		MyUser user = new MyUser();
		user.setAvatar(path);
		user.update(RegistFinalActivity.this, BmobChatUser.getCurrentUser

		(RegistFinalActivity.this).getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				progress.dismiss();
				Intent intent = new Intent(RegistFinalActivity.this,
						MainUIActivity.class);
				intent.putExtra("success", 1);
				startActivity(intent);
				finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * ����������
	 */
	@Override
	public void onClick(View v) {
		if (v == ensureRegistButton) {
			registeToServer();
			Intent intent = new Intent("finish_RegistActivity");
			sendBroadcast(intent);
		} else if (v == cancleRegistButton) {
			Intent intent = new Intent(RegistFinalActivity.this,
					LogInActivity.class);
			intent.putExtra("failed", 0);
			startActivity(intent);
			finish();

		}
	}
}
