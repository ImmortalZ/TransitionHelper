package immortalz.me.transitionhelper.demo.intent;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.expose.FoldExposeView;
import immortalz.me.library.method.ColorShowMethod;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2017/11/27.
 * email : mr_immortalz@qq.com
 */

public class ForResultDetailActivity extends BaseActivity {
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.btn_back)
    Button btnBack;

    protected static final String TRANSITION_DATA = "data";
    protected static int REQUEST_CODE = 10050;
    protected static int RESULT_CODE = 10050;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            tv.setText(intent.getStringExtra(TRANSITION_DATA));
        }
        TransitionsHeleper.build(this)
                .setShowMethod(new ColorShowMethod(R.color.bg_teal_light, R.color.bg_teal) {
                    @Override
                    public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(placeholder, "scaleX", 1, 0),
                                ObjectAnimator.ofFloat(placeholder, "scaleY", 1, 0)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(showDuration / 4 * 5).start();
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, View targetView) {

                    }
                })
                .setExposeView(new FoldExposeView(this, FoldExposeView.FOLD_TYPE_VERTICAL))
                .setExposeColor(getResources().getColor(R.color.bg_teal))
                .show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_for_result_detail;
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra(ForResultDetailActivity.TRANSITION_DATA, "receive from ForResultDetailActivity");
        setResult(ForResultDetailActivity.RESULT_CODE, intent);
        finish();
    }
}
