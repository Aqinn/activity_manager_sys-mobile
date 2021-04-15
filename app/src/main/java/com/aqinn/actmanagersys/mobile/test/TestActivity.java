package com.aqinn.actmanagersys.mobile.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.LoginResult;
import com.aqinn.actmanagersys.mobile.model.User;
import com.aqinn.actmanagersys.mobile.utils.RetrofitUtil;
import com.aqinn.actmanagersys.mobile.utils.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * 测试的Activity
 *
 * @author Aqinn
 * @date 2021/4/15 3:49 PM
 */
public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    @BindView(R.id.bt_test)
    Button btTest;
    @BindView(R.id.tv_res)
    TextView tvRes;

    /**
     * LoginResult{
     * id=25,
     * token='eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyNSIsImlhdCI6MTYxODQ3NDU3NywiZXhwIjoxNjE5MDc5Mzc3fQ.nz2a_u6FcGyip2wEhm1F1FTLfDCgeNrexY70lhPymjk'
     * }
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    private void loginAndSetToken() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        TestService service = retrofit.create(TestService.class);
        Observable<ApiResult<LoginResult>> observable = service.login("test1", "123456");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult<LoginResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull ApiResult<LoginResult> loginResultApiResult) {
                        if (loginResultApiResult.success) {
                            Log.d(TAG, "onNext: success");
                            Log.d(TAG, loginResultApiResult.data.toString());
                            SPUtil.setLoginToken(TestActivity.this, loginResultApiResult.data.getToken());
                        } else {
                            Log.d(TAG, "onNext: false");
                            Log.d(TAG, "onNext: errMsg => " + loginResultApiResult.errMsg);
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

    private void test() {
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
                            tvRes.setText(userApiResult.data.toString());
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


    @OnClick(R.id.bt_test)
    public void onClick() {
        // loginAndSetToken();
        test();
    }

}
