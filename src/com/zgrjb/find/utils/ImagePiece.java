package com.zgrjb.find.utils;

import android.graphics.Bitmap;

/**
 * ���úͻ�ȡͼƬ��Ƭ��λ���Լ����úͻ�ȡλͼ
 * 
 * @author ly
 * 
 */
public class ImagePiece {
	private int index;
	private Bitmap bitmap;

	public ImagePiece() {

	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public String toString() {
		return "ImagePiece [index=" + index + ", bitmap=" + bitmap + "]";
	}
}
