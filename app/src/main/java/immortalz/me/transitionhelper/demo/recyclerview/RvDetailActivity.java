package immortalz.me.transitionhelper.demo.recyclerview;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.InflateShowMethod;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class RvDetailActivity extends BaseActivity {


    @Bind(R.id.iv_detail)
    ImageView ivDetail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclerview_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionsHeleper.getInstance()
                .setShowMethod(new InflateShowMethod(this, R.layout.activity_rv_inflate) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        Glide.with(RvDetailActivity.this)
                                .load(bean.getImgUrl())
                                .fitCenter()
                                .into(copyView);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                        Glide.with(RvDetailActivity.this)
                                .load(bean.getImgUrl())
                                .fitCenter()
                                .into((ImageView) targetView);
                    }
                })
                .show(this, ivDetail);
    }
}
