package com.zgrjb.find.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewHolde {
	// �ı�����
	ImageView iv_avatar; // ͷ��
	ImageView iv_fail_resend; // ����ʧ��
	TextView tv_send_status; // ����״̬
	TextView tv_time; // ����ʱ��
	TextView tv_message; // �ı�����

	// ����
	ProgressBar pb_upLoad; // �ϴ��Ľ�����
	ImageView iv_voiceIcon; // ��������ʱ��С����
	LinearLayout ll_voiceLength; // �����ĳ�����״
	TextView tv_voiceLength; // �����ĳ�����ߵ�����
	
	//����Ự
	TextView tv_recent_name;   //����Ự�б��е��泼
	TextView tv_recent_msg;    //��Ϣ
	TextView tv_recent_time;	//
	TextView tv_recent_unread;	//δ����־
	
	//ͼƬ
	ImageView iv_picture;
	
	//ͨѶ¼
		TextView tv_contact_nick,tv_contact_userName; //�û��ǳ�
}

