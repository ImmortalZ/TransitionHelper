# TransitionHelper

This is a **simple** util to create Activity transition animation

API compatible with <b>Android 2.2+</b>


[中文说明](README_zh_CN.md)

## Screenshots

<img src="screenshots/image.gif" width="180" height="320">
<img src="screenshots/recyclerview.gif" width="180" height="320">
<img src="screenshots/button.gif" width="180" height="320">
<img src="screenshots/fab.gif" width="180" height="320">

## How to use

**1.startActivity**
> TransitionsHeleper.startActivity(this, ImageDetailActivity.class, view, load);

**2. in target Activity**
```
    TransitionsHeleper.build(this)
                .setShowMethod(new ColorShowMethod(R.color.bg_teal_light, R.color.bg_purple) {
                    @Override
                    public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                        Glide.with(ImageDetailActivity.this)
                                .load(bean.getLoad())
                                .centerCrop()
                                .into(placeholder);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, View targetView) {
                        Glide.with(ImageDetailActivity.this)
                                .load(bean.getLoad())
                                .centerCrop()
                                .into((ImageView) targetView);
                        tv.setText("immortalz");
                    }
                })
                .setExposeColor(getResources().getColor(R.color.bg_purple))
                .intoTargetView(ivDetail)
                .show();
```

**3. unbind this**
```
    @Override
    protected void onDestroy() {
        TransitionsHeleper.unbind(this);
        super.onDestroy();
    }
```

## API

| name                      | description      
| ------------------------- | ------- 
| setExposeView              | CirleExposeView，FoldExposeView（you can custom others by extends ExposeView）  
| setExposeColor     |    if not set , will be transparent color
| setExposeAcceleration     |    must > 0 ，default 7
| setShowMethod    | NoneShowMethod(Default),ColorShowMethod,InflateShowMethod（you can custom others by extends ShowMethod） 
| intoTargetView  |  
| setTransitionDuration |  
| setTransitionListener           | 



## Quick start


**Gradle**

```java
dependencies {
   compile 'me.immortalz:transitionhelper:2.3.4'
}
```

**Maven**

```java
<dependency>
  <groupId>me.immortalz</groupId>
  <artifactId>transitionhelper</artifactId>
  <version>2.3.0</version>
  <type>pom</type>
</dependency>
```

## TODO


- [ ] To better support imageview

- [ ] Add Back animation

- [x] Add Animation Listener

## Update record

**v2.3**

- remove TransitionsHeleper.onPause (add TransitionsHeleper.unbind)
- Add setExposeAcceleration

**v2.2**

- Repair because of the constant clicks leading to OOM 
- Add more listener

**v2.1**

- Repair caused memory leaks because of misuse
- Add more Animation

## End


Welcome to perfect this library .

## Contact

WeChat

![这里写图片描述](http://img.blog.csdn.net/20161007100121713)

WeiBo：

[http://weibo.com/u/1956502961](http://weibo.com/u/1956502961)

## License
```
Copyright (c) 2017 ImmortalZ

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