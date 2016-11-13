package immortalz.me.library.bean;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by Mr_immortalZ on 2016/10/18.
 * email : mr_immortalz@qq.com
 */

public class InfoBean {
    public int statusBarHeight;
    public int titleHeight;
    //image's url or resource id
    private String imgUrl;
    private int imgId;
    public Bitmap bitmap;

    public int translationY;
    public int translationX;
    //origin view
    public Rect originRect = new Rect();
    public int originWidth;
    public int originHeight;
    //target view
    public Rect targetRect = new Rect();
    public int targetWidth;
    public int targetHeight;
    //Content Window's size
    public int windowWidth;
    public int windowHeight;

    public float scalling;


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
