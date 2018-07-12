package app.billboardcat.com.billboardcat.UI.OtherView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.billboardcat.com.billboardcat.Constant.Constant;
import app.billboardcat.com.billboardcat.MainActivity;
import app.billboardcat.com.billboardcat.R;
import app.billboardcat.com.billboardcat.Util.SharedPreferenceUtils;

/**
 * 启动页（广告）
 * Created by luosonglin on 7/10/18.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private ImageView imageView;
    private TextView timeTv;

    /**
     * 倒计时60秒，一次1秒
     */
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否是第一次开启应用
        boolean isFirstOpen = SharedPreferenceUtils.getBoolean(this, Constant.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
//        if (!isFirstOpen) {
//            Intent intent = new Intent(this, WelcomeGuideActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }


        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash);

        imageView = (ImageView) findViewById(R.id.background);
        Glide.with(SplashActivity.this)
                .load(R.mipmap.pic_guidepage_1)
                .into(imageView);

        timeTv = (TextView) findViewById(R.id.time);
        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });

        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                timeTv.setText("剩余 " + millisUntilFinished / 1000 + " 秒");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }.start();

    }
}
