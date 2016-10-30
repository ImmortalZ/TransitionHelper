package immortalz.me.transitionhelper.demo.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.OnClick;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/27.
 * email : mr_immortalz@qq.com
 */

public class ImageActivity extends BaseActivity {


    @Bind(R.id.iv1)
    ImageView iv1;

    String imgUrl = "http://imga.mumayi.com/android/wallpaper/2012/01/02/sl_600_2012010206150877826134.jpg";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this)
                .load(imgUrl)
                .centerCrop()
                .into(iv1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @OnClick(R.id.iv1)
    public void onClick() {
        TransitionsHeleper.startAcitivty(this, ImageDetailActivity.class, iv1, imgUrl);
    }
}
