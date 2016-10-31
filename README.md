# BottomSheetPickers

This is a **simple** util to create Activity transition animation

API compatible with <b>Android 2.2+</b>

##Screenshots

<img src="screenshots/image.gif" width="180" height="320">
<img src="screenshots/recyclerview.gif" width="180" height="320">
<img src="screenshots/button.gif" width="180" height="320">
<img src="screenshots/fab.gif" width="180" height="320">
##Simple example

**This is just a part of the usage,more usage,please to see demo.**

### With layout which you can add Customized ###

```java
TransitionsHeleper.startAcitivty((Activity) mContext, RvDetailActivity.class,
                        holder.itemView.findViewById(R.id.iv1),
                        imageUrl);
```

>target Activity



```java
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
```

### With Image ###

```java
TransitionsHeleper.startAcitivty(this, ImageDetailActivity.class, iv1, imgUrl);
```
> target Activity

```java
TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.bg_teal_light,
                        R.color.bg_purple) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        Glide.with(ImageDetailActivity.this)
                                .load(bean.getImgUrl())
                                .centerCrop()
                                .into(copyView);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                        Glide.with(ImageDetailActivity.this)
                                .load(bean.getImgUrl())
                                .centerCrop()
                                .into(targetView);
                    }

                }).show(this, ivDetail);
```

### Width Other View ,such as FloatingActionButton ###

```java
TransitionsHeleper.startAcitivty(FabActivity.this, FabCircleActivity.class, btnCircle);
```
> target Activity

```java
TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.bg_purple,R.color.bg_teal) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(copyView,"rotation",0,180),
                                ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(duration / 4 * 5).start();
                    }
                    
                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                    }
                })
                .show(this,null);
```

##Quick start


**Gradle**

```java
dependencies {
   compile 'me.immortalz:transitionhelper:1.0.4'
}
```

**Maven**

```java
<dependency>
  <groupId>me.immortalz</groupId>
  <artifactId>transitionhelper</artifactId>
  <version>1.0.3</version>
  <type>pom</type>
</dependency>
```

##TODO


- [ ] To better support imageview

##End


Welcome to perfect this library .

##Contact

WeChat

![这里写图片描述](http://img.blog.csdn.net/20161007100121713)

WeiBo：

http://weibo.com/u/1956502961

## License
```
Copyright 2016 ImmortalZ

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```