package com.yang.toolbox.anim;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

/**
 * 属性动画
 * Created by YangWei
 * on 2016/5/19.
 */
public class PropertyAnimUtil {


    /**
     * 数字变化
     */
    public static void xNumberInt(final TextView textView, int start, int end){

        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (Integer) animation.getAnimatedValue();
                textView.setText(value+"");
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }
    public static void xNumberfolat(final TextView textView, float start, float end){

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                textView.setText(value+"");
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }
}
