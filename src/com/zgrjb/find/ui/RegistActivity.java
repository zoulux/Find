package com.zgrjb.find.ui;

import java.io.File;
import java.io.IOException;
import com.zgrjb.find.R;
import com.zgrjb.find.file_handle.HandlePicFile;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;
import com.zgrjb.find.bean.MyUser;
import com.zgrjb.find.config.ImgUir;
import com.zgrjb.find.utils.CommonUtils;

public class RegistActivity extends BaseActivity implements OnClickListener {
	private EditText userName, nick, password, passwordAgain;// �����û����ǳƣ����룬�ظ������editText
	private BroadcastReceiver broadcastReceiver;

	private String userNameString, nickString, passwordString,
			passwordAgainString;
	// ����һ����ͷ����ص�layout
	private LinearLayout linearLayout;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// ����
	private static final int PHOTO_REQUEST_CUT = 2;// ���
	// ����һ��HandlePicFile��
	private HandlePicFile handleFile;
	// ����һ��ע���ͷ��
	private ImageView imageView;
	// �����һ����Ƭ��λͼ
	private Bitmap photo;

	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		File file = new File("/sdcard/Find/Picture_Regist/");
		if (!file.exists()) {
			file.mkdirs();
		}
		init();
		initBroadcast();
		initRightTitleBarSet();

	}

	/**
	 * ��ʼ���������ұߵİ�ť�ͼ���
	 */
	private void initRightTitleBarSet() {
		setDrawablePath(getResources().getDrawable(R.drawable.jiantou));
		rightButtonIsVisible(true);
		showTitleText("ע��");
		rightImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isOk()) {
					Intent intent = new Intent(RegistActivity.this,
							RegistFinalActivity.class);
					intent.putExtra("userNameString", userNameString);
					intent.putExtra("nickString", nickString);
					intent.putExtra("passwordString", passwordString);
					startActivity(intent);

				}

			}
		});
	}

	/**
	 * ��ʼ��id�ͼ���
	 */
	public void init() {
		// ensureRegistButton = (Button) this.findViewById(R.id.ensureRegist);
		linearLayout = (LinearLayout) this
				.findViewById(R.id.LinerLayoutToImageView);
		// cancleRegistButton = (Button) this.findViewById(R.id.cancleRegist);
		imageView = (ImageView) this.findViewById(R.id.pictureRegist);
		// ensureRegistButton.setOnClickListener(this);
		linearLayout.setOnClickListener(this);

		// cancleRegistButton.setOnClickListener(this);
		handleFile = new HandlePicFile(this, ImgUir.ALBUM_PATH);
		imageView
				.setImageBitmap(getBitmap(this, ImgUir.ALBUM_PATH + "cut.jpg"));

		userName = (EditText) findViewById(R.id.id_regist_userName);
		nick = (EditText) findViewById(R.id.id_regist_nick);
		password = (EditText) findViewById(R.id.id_regist_ps);
		passwordAgain = (EditText) findViewById(R.id.id_regist_psAgain);

	}

	/**
	 * ��ʼ���㲥������
	 */
	private void initBroadcast() {
		broadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				RegistActivity.this.finish();
			}
		};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// ѡ������ʱ����
			startPhotoZoom(Uri.fromFile(ImgUir.tempFile));
			break;
		case PHOTO_REQUEST_CUT:
			if (data != null) {
				Bitmap bmpBitmap = getBitmap(this, ImgUir.ALBUM_PATH
						+ "cut.jpg");
				imageView.setImageBitmap(bmpBitmap);
				sentPicToNext(data);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * ���ü���
	 */
	@Override
	public void onClick(View v) {
		if (v == linearLayout) {
			Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// ָ������������պ���Ƭ�Ĵ���·��
			cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(ImgUir.tempFile));
			startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
		}

	}

	/**
	 * �ж�ע���Ƿ�ɹ�
	 * 
	 * @return
	 */
	private boolean isOk() {
		userNameString = userName.getText().toString();
		nickString = nick.getText().toString();
		passwordString = password.getText().toString();
		passwordAgainString = passwordAgain.getText().toString();

		if (TextUtils.isEmpty(userNameString)) {
			ShowToast("�������û���");
			return false;
		}

		if (TextUtils.isEmpty(nickString)) {
			ShowToast("�������ǳ�");
			return false;
		}

		if (TextUtils.isEmpty(passwordString)) {
			ShowToast("����������");
			password.setText("");
			return false;
		}
		if (TextUtils.isEmpty(passwordAgainString)) {
			ShowToast("���ظ�����");
			passwordAgain.setText("");
			return false;
		}
		if (!passwordString.equals(passwordAgainString)) {
			ShowToast("�������벻һ��");
			return false;
		}

		// String mat1 = "^[a-zA-Z][a-zA-Z0-9_]{3,15}$";
		String mat = "[a-zA-Z0-9_]{4,15}$";
		if (!userNameString.matches(mat)) {
			ShowToast("�˻�����ʹ��4λ���ϵ���ĸ�����ֵ����");
			return false;
		}

		if (!passwordString.matches(mat)) {
			ShowToast("���뽨��ʹ��4λ���ϵ���ĸ�����ֵ����");
			return false;
		}

		if (!CommonUtils.isNetworkAvailable(RegistActivity.this)) {
			ShowToast("�豸û������");
			return false;
		}

		return true;
	}

	/**
	 * ��ָ��·����ͼƬ����Bitmap������
	 * 
	 * @param context
	 *            ������
	 * @param resName
	 *            �ǻ�ȡͼƬ��·��
	 * @return
	 */
	public static Bitmap getBitmap(Context context, String resName) {
		try {
			return BitmapFactory.decodeFile(resName);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ��ʼ����ͼƬ
	 * 
	 * @param uri
	 */
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// cropΪtrue�������ڿ�����intent��������ʾ��view���Լ���
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY �Ǽ���ͼƬ�Ŀ��
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	/**
	 * �����к��ͼƬ��ʾ��RegistĿ¼��
	 * 
	 * @param picdata
	 */
	private void sentPicToNext(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			photo = bundle.getParcelable("data");
			if (photo == null) {
				imageView.setImageResource(R.drawable.ic_launcher);
			} else {
				try {
					handleFile.savePictureFile(photo, "cut.jpg");
				} catch (IOException e) {
					e.printStackTrace();
				}
				imageView.setImageBitmap(photo);
			}

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ע��㲥
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("finish_RegistActivity");
		registerReceiver(broadcastReceiver, intentFilter);
	}

}
