package immortalz.me.transitionhelper;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import immortalz.me.transitionhelper.base.BaseActivity;
import immortalz.me.transitionhelper.demo.fab.FabActivity;
import immortalz.me.transitionhelper.demo.fragment.FActivity;
import immortalz.me.transitionhelper.demo.image.ImageActivity;
import immortalz.me.transitionhelper.demo.recyclerview.RvActivity;

public class MainActivity extends BaseActivity {

    @Bind(R.id.btn_image)
    Button btnImage;
    @Bind(R.id.btn_recycleview)
    Button btnRecycleview;
    @Bind(R.id.btn_fab)
    Button btnFab;
    @Bind(R.id.btn_fragment)
    Button btnFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.btn_image, R.id.btn_recycleview, R.id.btn_fab,R.id.btn_fragment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_image:
                gotoNextActivity(ImageActivity.class);
                break;
            case R.id.btn_recycleview:
                gotoNextActivity(RvActivity.class);
                break;
            case R.id.btn_fab:
                gotoNextActivity(FabActivity.class);
                break;
            case R.id.btn_fragment:
                gotoNextActivity(FActivity.class);
                break;
        }
    }

    private void gotoNextActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


}
