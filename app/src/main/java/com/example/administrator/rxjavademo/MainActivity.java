package com.example.administrator.rxjavademo;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;

public class MainActivity extends AppCompatActivity {

    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });

    Observable observable1 = Observable.just("Hello", "You", "Best");

    String[] words = {"Hello", "Hi", "Aloha"};
    Observable observable2 = Observable.from(words);

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);

//        rxFirst();
//
//        rxFirst2();

        rxFirst3();

        rxFirst4();

    }

    /**
     * 使用rx方式设置图片
     */
    private void rxFirst4() {
        final int imageResId = R.mipmap.rx_test0;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = MainActivity.this.getResources().getDrawable(imageResId);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                image.setImageDrawable(drawable);
            }
        });
    }

    /**
     * 基本使用3
     */
    private void rxFirst3() {
        String[] names = {"A", "B", "C", "D"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("======", s);
                    }
                });
    }

    /**
     * 基础用法2
     */
    private void rxFirst2() {
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("============", "onNextAction:" + s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("============", "onErrorAction:" + throwable.getMessage());
            }
        };

        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                Log.e("============", "onCompleteAction");
            }
        };

        //订阅
        observable.subscribe(onNextAction);
        observable.subscribe(onNextAction, onErrorAction);
        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);
    }

    /**
     * 基础用法1
     */
    private void rxFirst() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.e("======", "onNext:" + s);
            }

            @Override
            public void onCompleted() {
                Log.e("======", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("======", "onError : " + e.getMessage());
            }
        };


        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onNext(String s) {
                Log.e("======", "onNext:" + s);
            }

            @Override
            public void onCompleted() {
                Log.e("======", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("======", "onError : " + e.getMessage());
            }
        };

        //订阅
        observable.subscribe(observer);
        observable.subscribe(subscriber);
        observable1.subscribe(observer);
    }


}
