package com.zgrjb.find.ui.guide;

import com.zgrjb.find.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class GuideItemImage06 extends ImageView {
/**
 * ��������ҳ6��ͼƬ�Ķ���
 * @param context
 * @param attrs
 */
	public GuideItemImage06(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.image_scale_alpha_in_item_6));
	}

}
