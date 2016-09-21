package rx.vincent.com.rx;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */
public class RxJava2{
    public void Main(String args) {

        Observable observable = query("Hello World!");

        // 便利输出结果中的每一个url
        if (observable != null) {
            observable.subscribe(new Action1<String[]>() {
                @Override
                public void call(String[] urls) {
                    for(String url : urls){
                        System.out.print(url);
                    }
                }
            });
        }

        query("") // Use lamdba
                .subscribe(urls->{
                    for(int i = 0;i < urls.size();i++){
                        System.out.print(urls.get(i));
                    }
                });
//        API 提供的from就是输出数组的每一个元素，就不用for来便利了
//        query("").from("url1","url2","url3")
//                .subscribe(url->System.out.print(url));

        // 要利用from就得要在subscribe中出现observable
        query("Hello world!")
                .subscribe(urls->{
                   Observable.from(urls)
                           .subscribe(url->System.out.print(url));
                });

        // 所以就出现了flatmap这个操作符
        query("Hello world!")
                .flatMap(new Func1<List<String>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .subscribe(url->System.out.print(url));

        query("Hello world!") // Use lamdba
                .flatMap( urls -> Observable.from(urls))
                .subscribe(url->System.out.print(url));

        query("")
                .flatMap(urls->Observable.from(urls))
                .flatMap(url -> getTitle(url))
                .subscribe(url->System.out.print(url));

        query("")
                .flatMap(urls->Observable.from(urls))
                .flatMap(url->getTitle(url))
                .filter(url->url!=null)
                .subscribe(title->System.out.print(title));

        query("")
                .flatMap(Observable::from)
                .flatMap(this::getTitle)
                .filter(url->url!=null)
                .subscribe(System.out::print);

    }

    private Observable<List<String>> query(String text){
        return null;
    }

    private Observable<String> getTitle(String url){
        return null;
    }

}
