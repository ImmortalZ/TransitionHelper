package immortalz.me.library.expose.base;

/**
 * Created by Mr_immortalZ on 2017/12/6.
 * email : mr_immortalz@qq.com
 */

public enum ExposeType {
    DEFAULT(0),
    INFLATE(1),;

    private int value;

    ExposeType(int value) {
        this.value = value;
    }

    int toInt() {
        return value;
    }
}
