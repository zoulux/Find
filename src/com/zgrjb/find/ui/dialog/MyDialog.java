package com.zgrjb.find.ui.dialog;

import com.zgrjb.find.bean.MyUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class MyDialog {
	// ����һ��������
	private Context context;
	// ����dialog
	private AlertDialog.Builder builder;

	public MyDialog(Context context) {
		this.context = context;
		builder = new AlertDialog.Builder(context);
	}

	/**
	 * ���������dialog
	 * 
	 * @param whatTheme
	 *            ��һ������
	 */
	public void showChoiceThemeDialog(final int whatTheme) {
		builder.setTitle("��ʾ");
		builder.setMessage("ȷ���������⣿");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent("choiceIcon");
				intent.putExtra("theme", whatTheme);
				context.sendBroadcast(intent);
				Toast.makeText(context, "��������ɹ�", 1).show();
			}
		});
		builder.setNegativeButton("ȡ��", null);
		builder.show();

	}

	/**
	 * ɾ�����ѵ�dialog
	 * 
	 * @param user
	 *            ĳ������
	 */
	public void showDeleteUserDialog(final MyUser user) {
		builder.setTitle("��ʾ");
		builder.setMessage("ȷ��ɾ������" + user.getNick() + "?");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent("senddelete");
				intent.putExtra("user", user);
				context.sendBroadcast(intent);
				// context.startActivity(new
				// Intent(context,ContactsFragment.class));
			}
		});
		builder.setNegativeButton("ȡ��", null);
		builder.show();

	}
}
