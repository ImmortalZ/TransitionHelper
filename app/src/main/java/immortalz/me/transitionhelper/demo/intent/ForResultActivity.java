package immortalz.me.transitionhelper.demo.intent;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2017/11/27.
 * email : mr_immortalz@qq.com
 */

public class ForResultActivity extends BaseActivity {

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.btn_go)
    Button btnGo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_for_result;
    }


    @OnClick(R.id.btn_go)
    public void onClick() {
        Intent intent = new Intent(this, ForResultDetailActivity.class);
        intent.putExtra(ForResultDetailActivity.TRANSITION_DATA, "This is immortalZ");
        TransitionsHeleper.startActivityForResult(this, intent, ForResultDetailActivity.REQUEST_CODE, null, btnGo, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ForResultDetailActivity.REQUEST_CODE && resultCode == ForResultDetailActivity.RESULT_CODE) {
            String str = data.getStringExtra(ForResultDetailActivity.TRANSITION_DATA);
            tv.setText(str);
        }
    }
}
