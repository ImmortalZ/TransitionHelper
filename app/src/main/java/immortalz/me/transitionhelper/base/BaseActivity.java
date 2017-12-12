package immortalz.me.transitionhelper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;
import immortalz.me.library.TransitionsHeleper;

/**
 * Created by Mr_immortalZ on 2016/10/28.
 * email : mr_immortalz@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public abstract int getLayoutId();


    @Override
    protected void onPause() {
        super.onPause();
        TransitionsHeleper.onPause(this);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
        Log.d("tag", "onDestroy");
    }

}
