package immortalz.me.transitionhelper.demo.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class RvActivity extends BaseActivity {

    @Bind(R.id.rv)
    RecyclerView rv;
    private List<String> mList;
    private RvAdapter mAdapter;
    String imgUrl = "http://avatar.csdn.net/6/1/B/1_mr_immortalz.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(imgUrl);
        }
        mAdapter = new RvAdapter(this, R.layout.item_rv, mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclerview;
    }
}
