package com.example.administrator.rxjavademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.rxjavademo.bean.Course;
import com.example.administrator.rxjavademo.bean.Student;
import com.example.administrator.rxjavademo.notifi.NotificationServiceActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

//        rxFirst2();

//        rxFirst3();

//        rxFirst4();

//        rxFirst5();

//        rxFirst6();


//        rxFirst7();

        rxFirst8();

    }

    /**
     * lift简单使用
     */
    private void rxFirst8() {
        Observable.just(1,2)
                .lift(new Observable.Operator<String, Integer>() {
                    @Override
                    public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                        return new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                subscriber.onNext(integer + "");
                            }
                        };
                    }
                })
                .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("=====", "lift:" + s);
            }
        });
    }

    /**
     * flatMap
     */
    private void rxFirst7() {
        List<Course> courses = new ArrayList<>();
        Course course = new Course("p1", "历史");
        courses.add(course);
        course = new Course("p2", "英语");
        courses.add(course);
        course = new Course("p3", "国文");
        courses.add(course);

        List<Student> students = new ArrayList<>();
        Student stu = new Student(1, "aa");
        stu.setCourses(courses);
        students.add(stu);
        stu = new Student(2, "bb");
        stu.setCourses(courses);
        students.add(stu);

//        /**1对1**/
//        Observable.from(students)
//                .map(new Func1<Student, String>() {
//                    @Override
//                    public String call(Student student) {
//                        return student.getName();
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.e("==", "stu:" + s);
//                    }
//            });
//
//        /**1对多**/
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .map(new Func1<Course, String>() {
                    @Override
                    public String call(Course course) {
                        return course.getName();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("====", "course:" + s);
                    }
                });

        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                }).subscribe(new Action1<Course>() {
            @Override
            public void call(Course course) {

            }
        });

//        Observable.from(students)
//                .map(new Func1<Student, List<Course>>() {
//                    @Override
//                    public List<Course> call(Student student) {
//                        return student.getCourses();
//                    }
//                })
//                .map(new Func1<List<Course>, Observable<Course>>() {
//            @Override
//            public Observable<Course> call(List<Course> courses) {
//                return Observable.from(courses);
//            }
//        }).subscribe(new Action1<Observable<Course>>() {
//            @Override
//            public void call(Observable<Course> courseObservable) {
//                courseObservable.subscribe(new Action1<Course>() {
//                    @Override
//                    public void call(Course course) {
//                           Log.e("====", "coues:" + course.getName());
//                    }
//                });
//            }
//        });

        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        Log.e("==", "stu:" + student.getName());
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course course) {
                        Log.e("====", "coues:" + course.getName());
                    }
                 });
    }

    /**
     * map简单使用
     */
    private void rxFirst6() {
        Observable.just("http://image/logo.png")
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        Bitmap bitmap = Bitmap.createBitmap(null);
                        return bitmap;
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        //TODO : show bitmap
                    }
                });
    }

    /**
     * 线程切换:Schedulers
     */
    private void rxFirst5() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.d("===========", "number:" + number);
                    }
                });
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
     * 基础用法2: Action
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
     * 基础用法1:Observer、Subscriber
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


    public void onNotificationListenerService(View view) {
        startActivity(new Intent(this, NotificationServiceActivity.class));
    }
}
