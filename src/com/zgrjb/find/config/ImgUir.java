package com.zgrjb.find.config;

import java.io.File;

import android.os.Environment;

public class ImgUir {
	//����֮�󣬲��кú���Ƭ��ŵ�·��,������ͷ��
	public static File tempFile=new File(Environment
			.getExternalStorageDirectory() + "/Find/Picture_Regist/cut.jpg/");
	//��ȡ��Ƭ��·��
	public final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/Find/Picture_Regist/";
	
	//����֮�󣬲��кú���Ƭ��ŵ�·��,������ͷ��
		public static File sendFile=new File(Environment
				.getExternalStorageDirectory() + "/Find/Picture_Regist/sendCut.jpg");
}
