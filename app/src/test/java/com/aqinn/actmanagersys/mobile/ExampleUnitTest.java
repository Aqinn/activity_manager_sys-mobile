package com.aqinn.actmanagersys.mobile;

import android.util.Log;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.User;
import com.aqinn.actmanagersys.mobile.test.TestService;
import com.aqinn.actmanagersys.mobile.utils.RetrofitUtil;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.http.GET;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRetrofit() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        TestService service = retrofit.create(TestService.class);
        Observable<ApiResult<User>> observable = service.getUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull ApiResult<User> userApiResult) {
                        if (userApiResult.success) {
                            Log.d(TAG, "onNext: success");
                            Log.d(TAG, userApiResult.data.toString());
                        } else {
                            Log.d(TAG, "onNext: false");
                            Log.d(TAG, "onNext: errMsg => " + userApiResult.errMsg);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    @Test
    public void testEquals() {
        ActShow act1 = new ActShow();
        act1.setName("xixi");
        act1.setLocation(null);
        ActShow act2 = new ActShow();
        act2.setName("xixi");
        System.out.println(act1.equals(act2));
    }

}