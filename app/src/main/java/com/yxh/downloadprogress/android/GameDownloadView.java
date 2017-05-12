package com.yxh.downloadprogress.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by YXH on 2017/5/12.
 */

public class GameDownloadView extends LinearLayout {

    private int statusWidth;
    private int statusHeight;
    private int statusUpTextColor;
    private int statusDownTextColor;
    private int statusUpBackgroundColor;
    private int statusDownBackgroundColor;
    private int statusCornerRadius = 10;
    private int statusStrokeBoundWidth = 5;
    private int statusTextSize;
    private TextView statusUp, statusDown;
    private LinearLayout.LayoutParams linearParams;
    private LinearLayout download_layout;

    public GameDownloadView(Context context) {
        this(context, null);
    }

    public GameDownloadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameDownloadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomGameDownloadView, defStyleAttr, 0);

        statusUpTextColor = array.getColor(R.styleable.CustomGameDownloadView_statusUpTextColor, statusUpTextColor);
        statusDownTextColor = array.getColor(R.styleable.CustomGameDownloadView_statusDownTextColor, statusDownTextColor);
        statusUpBackgroundColor = array.getColor(R.styleable.CustomGameDownloadView_statusUpBackgroundColor, statusUpBackgroundColor);
        statusDownBackgroundColor = array.getColor(R.styleable.CustomGameDownloadView_statusDownBackgroundColor, statusDownBackgroundColor);

        statusWidth = (int) array.getDimension(R.styleable.CustomGameDownloadView_statusWidth, statusWidth);
        statusHeight = (int) array.getDimension(R.styleable.CustomGameDownloadView_statusHeight, statusHeight);
        statusTextSize = array.getDimensionPixelSize(R.styleable.CustomGameDownloadView_statusTextSize, statusTextSize);
        statusCornerRadius = array.getDimensionPixelSize(R.styleable.CustomGameDownloadView_statusCornerRadius, statusCornerRadius);
        statusStrokeBoundWidth = array.getDimensionPixelSize(R.styleable.CustomGameDownloadView_statusStrokeBoundWidth, statusStrokeBoundWidth);
        array.recycle();

        statusTextSize = px2dip(getContext(), statusTextSize);

        statusDownTextColor = statusUpBackgroundColor;
//        statusDownBackgroundColor = statusUpTextColor;
        statusStrokeBoundWidth = px2dip(getContext(), statusStrokeBoundWidth);
        statusStrokeBoundWidth = 2;

        setOrientation(HORIZONTAL);
        GradientDrawable gradientDrawable1 = new GradientDrawable();
        gradientDrawable1.setColor(statusDownBackgroundColor);
        gradientDrawable1.setCornerRadius(statusCornerRadius);
        gradientDrawable1.setStroke(statusStrokeBoundWidth, statusUpBackgroundColor);


        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(statusUpBackgroundColor);
        gradientDrawable2.setCornerRadius(statusCornerRadius);
        gradientDrawable2.setStroke(statusStrokeBoundWidth, statusUpBackgroundColor);

        statusDown = new TextView(getContext());
        statusDown.setTextSize(statusTextSize);
        statusDown.setTextColor(statusDownTextColor);
        statusDown.setGravity(Gravity.CENTER);
        statusDown.setText("下载");
        statusDown.setSingleLine(true);
        statusDown.setBackgroundDrawable(gradientDrawable1);
        LinearLayout.LayoutParams statusLayoutParams = new LinearLayout.LayoutParams(statusWidth, statusHeight);
        statusDown.setLayoutParams(statusLayoutParams);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(-statusWidth, 0, 0, 0);

        download_layout = new LinearLayout(getContext());
        download_layout.setOrientation(HORIZONTAL);

        statusUp = new TextView(getContext());
        statusUp.setTextSize(statusTextSize);
        statusUp.setTextColor(statusUpTextColor);
        statusUp.setGravity(Gravity.CENTER);
        statusUp.setText("下载");
        statusUp.setSingleLine(true);
        statusUp.setBackgroundDrawable(gradientDrawable2);
        statusUp.setLayoutParams(statusLayoutParams);

        download_layout.setLayoutParams(lp);
        download_layout.addView(statusUp);

        addView(statusDown);
        addView(download_layout);

        linearParams = (LinearLayout.LayoutParams) download_layout.getLayoutParams();
        linearParams.width = dip2px(getContext(), 0);
        download_layout.setLayoutParams(linearParams);
    }

    public void setStatusView(String statusString) {
        statusUp.setText(statusString);
        statusDown.setText(statusString);
    }

    public void setProgress(int progress) {
        linearParams.width = dip2px(getContext(), px2dip(getContext(), statusWidth) * progress / 100);
        download_layout.setLayoutParams(linearParams);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}