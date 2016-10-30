package immortalz.me.transitionhelper.demo.fragment;

import android.support.design.widget.FloatingActionButton;

import butterknife.Bind;
import butterknife.OnClick;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FActivity extends BaseActivity {
    @Bind(R.id.btn_circle)
    FloatingActionButton btnCommit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @OnClick(R.id.btn_circle)
    public void onClick() {
        TransitionsHeleper.startAcitivty(this, FDetailActivity.class, btnCommit);
    }
}
