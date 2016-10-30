package immortalz.me.transitionhelper.demo.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import immortalz.me.library.TransitionsHeleper;
import immortalz.me.transitionhelper.R;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class RvAdapter extends CommonAdapter<String> {
    public RvAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, final String str, int position) {
        Glide.with(mContext)
                .load(str)
                .into((ImageView) holder.itemView.findViewById(R.id.iv1));
        holder.setOnClickListener(R.id.iv1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionsHeleper.startAcitivty((Activity) mContext, RvDetailActivity.class,
                        holder.itemView.findViewById(R.id.iv1),
                        str);
            }
        });
    }
}
