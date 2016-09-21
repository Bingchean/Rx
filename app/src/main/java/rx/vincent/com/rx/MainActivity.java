package rx.vincent.com.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable observable = Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello World");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> stringSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.print(s);
            }
        };

        observable.subscribe(stringSubscriber);

        //just 就是创建只发出一个事件的Observable对象

        Observable<String> observable1 = Observable.just("Hello World");

        Action1<String> onNextAction = new Action1<String>() {// 相当于上面的doNext doError doComplete
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

        observable1.subscribe(onNextAction);// onErrorAction onCompleteAction

        Observable.just("Hello World")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.print(s);
                    }
                });

        Observable.just("Hello World")// use lambda
                .subscribe(s -> System.out.print(s));

        //

        Observable.just("Hello World "+ "Dan")
                .subscribe(s -> System.out.println(s));

        Observable.just("Hello, world!")
                .subscribe(s -> System.out.println(s + " -Dan"));

        Observable.just("Hello World")// 差不多就是将just进来的参数做进一步处理
                .map(new Func1<String,String>(){

                    @Override
                    public String call(String s) {
                        return s + "-Dan";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.print(s);
                    }
                });

        Observable.just("Hello World")//use lamdba
                .map(s -> s + " -Dan")
                .subscribe(s -> System.out.println(s));

        //

        Observable.just("Hello World")
                .map(new Func1<String, Integer>() {

                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.print(Integer.toString(integer));
                    }
                });

        Observable.just("Hello World") // Use lamdba
                .map(s -> s.hashCode())
                .subscribe(integer -> System.out.print(Integer.toString(integer)));


        Observable.just("Hello World")//Subscriber做的事情越少越好，我们再增加一个map操作符
                .map(s -> s.hashCode())
                .map(i -> Integer.toString(i))
                .subscribe(s -> System.out.print(s));

        //

//        1.Observable和Subscriber可以做任何事情
//        Observable可以是一个数据库查询，Subscriber用来显示查询结果；
//        Observable可以是屏幕上的点击事件，Subscriber用来响应点击事件；
//        Observable可以是一个网络请求，Subscriber用来显示请求结果。
//
//        2.Observable和Subscriber是独立于中间的变换过程的。
//        在Observable和Subscriber中间可以增减任何数量的map。
//        整个系统是高度可组合的，操作数据是一个很简单的过程。

    }
}
