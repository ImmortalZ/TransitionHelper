package immortalz.me.transitionhelper.demo.fab;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FabActivity extends BaseActivity {
    @Bind(R.id.btn_circle)
    FloatingActionButton btnCircle;
    @Bind(R.id.btn_no)
    FloatingActionButton btnNo;
    @Bind(R.id.btn)
    Button btn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fab;
    }


    @OnClick({R.id.btn_no, R.id.btn_circle,R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_no:
                TransitionsHeleper.startAcitivty(FabActivity.this, FabNoCircleActivity.class, btnNo);
                break;
            case R.id.btn_circle:
                TransitionsHeleper.startAcitivty(FabActivity.this, FabCircleActivity.class, btnCircle);
                break;
            case R.id.btn:
                TransitionsHeleper.startAcitivty(FabActivity.this, ButtonActivity.class, btn);
                break;
        }
    }

}
