package immortalz.me.transitionhelper.demo.intent;

import android.content.Intent;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/11/1.
 * email : mr_immortalz@qq.com
 */

public class IntentActivity extends BaseActivity {
    @Bind(R.id.btn)
    Button btn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_intent;
    }


    @OnClick(R.id.btn)
    public void onClick() {
        Intent intent = new Intent(this, IntentDetailActivity.class);
        intent.putExtra(IntentDetailActivity.TRANSITION_DATA, "This is immortalZ");
        TransitionsHeleper.startActivity(this, intent, btn);
    }
}
