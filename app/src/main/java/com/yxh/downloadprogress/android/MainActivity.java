package com.yxh.downloadprogress.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author YXH
 * time   2017/05/12  11:43
 * 仿新版应用宝下载进度条文字变色
 */
public class MainActivity extends AppCompatActivity {

    private Timer timer;
    private int progress;
    private GameDownloadView gameDownloadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameDownloadView = (GameDownloadView) findViewById(R.id.game_download_view);

        gameDownloadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer != null) {
                    timer.cancel();
                    timer = null;
                }
                progress = 0;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        progress++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(progress < 100) {
                                    gameDownloadView.setProgress(progress);
                                    gameDownloadView.setStatusView(progress+ "%");
                                }else {
                                    gameDownloadView.setProgress(100);
                                    gameDownloadView.setStatusView("安装");
                                    progress = 0;
                                    if(timer != null) {
                                        timer.cancel();
                                        timer = null;
                                    }
                                }
                            }
                        });
                    }
                }, 0, 100);
            }
        });
    }
}
