package com.zgrjb.find.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;

public class FileServiceFlag {
	private Context context;

	public FileServiceFlag(Context context) {
		this.context = context;
	}

	/**
	 * ��ȡdata���file�ļ�
	 * @param fileName
	 * @return
	 */
	public String readContentFromFile(String fileName) {
		FileInputStream fileInputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			fileInputStream = context.openFileInput(fileName);
			int len = 0;
			byte[] data = new byte[1024];
			while ((len = fileInputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			return new String(outputStream.toByteArray());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ���ļ��洢��data���file�ļ���
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param mode
	 *            д���ļ���ģʽ
	 * @param data
	 *            �����ļ�������
	 * @return
	 */
	public boolean saveContentToFile(String fileName, int mode, byte[] data) {
		boolean flag = false;
		FileOutputStream outputStream = null;
		try {
			outputStream = context.openFileOutput(fileName, mode);
			outputStream.write(data, 0, data.length);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e2) {
				}
			}
		}

		return flag;
	}
}
