package com.aqinn.actmanagersys.mobile.actcard;

import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;

import com.aqinn.actmanagersys.mobile.base.BaseApplication;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.index.actcenter.actdetail.IActDetail;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.aqinn.actmanagersys.mobile.utils.DialogUtil;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;
import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.*;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 活动卡片列表 - Model
 *
 * @author Aqinn
 * @date 2021/4/18 4:33 PM
 */
public class ActCardModel implements IActCard.Model {

    private static final String TAG = "ActCardModel";

    public List<ActShow> actList;
    public Map<ActShow, List<AttendShow>> actAttendMap;
    public List<QMUISection<SectionHeader_Act, SectionItem_Attend>> data;
    private ActCardType mType;

    public ActCardModel(ActCardType type) {
        // 加载数据
        actList = new ArrayList<>();
        actAttendMap = new LinkedHashMap<>();
        data = new ArrayList<>();
        mType = type;
//        if (mType == ActCardType.FLAG_CREATE) {
//            prepareCreateActTestData();
//        } else if (mType == ActCardType.FLAG_JOIN) {
//            prepareJoinActTestData();
//        }
    }

    @Override
    public void initData(InitDataCallback callback) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                if (mType == ActCardType.FLAG_CREATE) {
                    // 获取创建的活动
                    getSetupService().getCreateActs()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ApiResult<List<ActShow>>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull ApiResult<List<ActShow>> result) {
                                    if (result.success) {
                                        callback.onSuccess("成功加载创建的签到数据");
                                        emitter.onNext("成功加载创建的签到数据");
                                        List<ActShow> receiveActList = result.data;
                                        Log.d(TAG, "onNext: size " + receiveActList.size());
                                        Map<ActShow, List<AttendShow>> toBeAddMap = new HashMap<>();
                                        for (ActShow act : receiveActList) {
                                            Log.d(TAG, "onNext: 接收到一个活动, " + act);
                                            // Log.d(TAG, "onNext: 接收到一个活动, " + act.getActId());
                                            toBeAddMap.put(act, new ArrayList<>());
                                        }
                                        // 获取创建的签到
                                        getSetupService().getCreateAttends()
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ApiResult<List<AttendShow>>>() {
                                                    @Override
                                                    public void onSubscribe(@NonNull Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(@NonNull ApiResult<List<AttendShow>> result) {
                                                        if (result.success) {
                                                            List<AttendShow> receiveAttendList = result.data;
                                                            Iterator<Map.Entry<ActShow, List<AttendShow>>> it = toBeAddMap.entrySet().iterator();
                                                            if (receiveAttendList.size() == 0) {
                                                                while (it.hasNext()) {
                                                                    Map.Entry<ActShow, List<AttendShow>> entry = it.next();
                                                                    createSection(entry.getKey(), entry.getValue(), true);
                                                                    Log.d(TAG, "onNext: entry: " + entry);
                                                                }
                                                                callback.onSuccess("成功加载加入的活动数据");
                                                                emitter.onNext("成功加载加入的活动数据");
                                                                return;
                                                            }
                                                            Map<Long, List<AttendShow>> actIdAttendListMap = new HashMap<>();
                                                            while (it.hasNext()) {
                                                                Map.Entry<ActShow, List<AttendShow>> entry = it.next();
                                                                actIdAttendListMap.put(entry.getKey().getActId(), entry.getValue());
                                                            }
                                                            for (AttendShow attend : receiveAttendList) {
                                                                actIdAttendListMap.get(attend.getActId()).add(attend);
                                                            }
                                                            it = toBeAddMap.entrySet().iterator();
                                                            while (it.hasNext()) {
                                                                Map.Entry<ActShow, List<AttendShow>> entry = it.next();
                                                                QMUISection<SectionHeader_Act, SectionItem_Attend> section = createSection(entry.getKey(), entry.getValue(), true);

                                                                Log.d(TAG, "onNext: entry: " + entry);
                                                            }
                                                            callback.onSuccess("成功加载创建的签到数据");
                                                            emitter.onNext("成功加载创建的签到数据");
                                                        } else {
                                                            callback.onError(IActCard.ErrorCode.FAIL_LOAD_CREATE_ATTEND);
                                                            emitter.onNext("加载创建的签到数据出错");
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(@NonNull Throwable e) {
                                                        e.printStackTrace();
//                                                        callback.onError(IActCard.ErrorCode.UNKNOWN_NETWORK_ERROR);
                                                        callback.onError(IActCard.ErrorCode.FAIL_LOAD_CREATE_ATTEND);
                                                        emitter.onNext("加载创建的签到数据出错 网络错误");
                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    } else {
                                        callback.onError(IActCard.ErrorCode.FAIL_LOAD_CREATE_ACT);
                                        emitter.onNext("加载创建的活动数据出错");
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    e.printStackTrace();
//                                    callback.onError(IActCard.ErrorCode.UNKNOWN_NETWORK_ERROR);
                                    callback.onError(IActCard.ErrorCode.FAIL_LOAD_CREATE_ACT);
                                    emitter.onNext("加载创建的活动数据出错 网络错误");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                } else if (mType == ActCardType.FLAG_JOIN) {
                    // 获取加入的活动
                    getSetupService().getJoinActs()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ApiResult<List<ActShow>>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull ApiResult<List<ActShow>> result) {
                                    if (result.success) {
                                        callback.onSuccess("成功加载加入的活动数据");
                                        emitter.onNext("成功加载加入的活动数据");
                                        List<ActShow> receiveActList = result.data;
                                        Map<ActShow, List<AttendShow>> toBeAddMap = new HashMap<>();
                                        for (ActShow act : receiveActList) {
                                            toBeAddMap.put(act, new ArrayList<>());
                                            Log.d(TAG, "onNext: act: " + act);
                                        }
                                        // 获取加入的签到
                                        getSetupService().getJoinAttends()
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ApiResult<List<AttendShow>>>() {
                                                    @Override
                                                    public void onSubscribe(@NonNull Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(@NonNull ApiResult<List<AttendShow>> result) {
                                                        if (result.success) {
                                                            List<AttendShow> receiveAttendList = result.data;
                                                            Iterator<Map.Entry<ActShow, List<AttendShow>>> it = toBeAddMap.entrySet().iterator();
                                                            if (receiveAttendList.size() == 0) {
                                                                while (it.hasNext()) {
                                                                    Map.Entry<ActShow, List<AttendShow>> entry = it.next();
                                                                    createSection(entry.getKey(), entry.getValue(), true);
                                                                    Log.d(TAG, "onNext: entry: " + entry);
                                                                }
                                                                callback.onSuccess("成功加载加入的活动数据");
                                                                emitter.onNext("成功加载加入的活动数据");
                                                                return;
                                                            }
                                                            Map<Long, List<AttendShow>> actIdAttendListMap = new HashMap<>();
                                                            while (it.hasNext()) {
                                                                Map.Entry<ActShow, List<AttendShow>> entry = it.next();
                                                                actIdAttendListMap.put(entry.getKey().getActId(), entry.getValue());
                                                            }
                                                            for (AttendShow attend : receiveAttendList) {
                                                                actIdAttendListMap.get(attend.getActId()).add(attend);
                                                            }
                                                            it = toBeAddMap.entrySet().iterator();
                                                            while (it.hasNext()) {
                                                                Map.Entry<ActShow, List<AttendShow>> entry = it.next();
                                                                createSection(entry.getKey(), entry.getValue(), true);
                                                                Log.d(TAG, "onNext: entry: " + entry);
                                                            }
                                                            callback.onSuccess("成功加载加入的签到数据");
                                                            emitter.onNext("成功加载加入的签到数据");
                                                        } else {
                                                            callback.onError(IActCard.ErrorCode.FAIL_LOAD_JOIN_ATTEND);
                                                            emitter.onNext("加载加入的签到数据出错");
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(@NonNull Throwable e) {
                                                        e.printStackTrace();
//                                                        callback.onError(IActCard.ErrorCode.UNKNOWN_NETWORK_ERROR);
                                                        callback.onError(IActCard.ErrorCode.FAIL_LOAD_JOIN_ATTEND);
                                                        emitter.onNext("加载加入的签到数据出错 网络错误");
                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    } else {
                                        callback.onError(IActCard.ErrorCode.FAIL_LOAD_JOIN_ACT);
                                        emitter.onNext("加载加入的活动数据出错");
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    e.printStackTrace();
//                                    callback.onError(IActCard.ErrorCode.UNKNOWN_NETWORK_ERROR);
                                    callback.onError(IActCard.ErrorCode.FAIL_LOAD_JOIN_ACT);
                                    emitter.onNext("加载加入的活动数据出错 网络错误");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    private Disposable mDisposable;
                    private Dialog mDialog;
                    private StringBuffer sb;
                    private int finishLoadItem = 0;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
//                        mDialog = DialogUtil.createLoadingDialog(BaseApplication.getContext(), "正在加载最新数据...");
                        sb = new StringBuffer();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        finishLoadItem++;
                        if (!s.contains("成功"))
                            sb.append(s + ",");
                        if (finishLoadItem == 4) {
//                            DialogUtil.closeDialog(mDialog);
                            callback.onSuccess(sb.toString());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        mDisposable.dispose();
//                        DialogUtil.closeDialog(mDialog);
                        callback.onError(IActCard.ErrorCode.INCOMPLETE_DATA);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void refreshData(InitDataCallback callback) {
        actList.clear();
        actAttendMap.clear();
        data.clear();
        initData(callback);
    }

    @Override
    public List<QMUISection<SectionHeader_Act, SectionItem_Attend>> getData() {
        return data;
    }

    @Override
    public List<ActShow> getActList() {
        return actList;
    }

    @Override
    public Map<ActShow, List<AttendShow>> getActAttendMap() {
        return actAttendMap;
    }

    @Override
    public void deleteAct(int sectionHeaderIndex, DeleteActCallback callback) {
        getActService().deleteAct(actList.get(sectionHeaderIndex).getActId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult result) {
                        if (result.success) {
                            try {
                                actAttendMap.remove(actList.get(sectionHeaderIndex));
                                actList.remove(sectionHeaderIndex);
                                data.remove(sectionHeaderIndex);
                                callback.onSuccess();
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updateAct(int sectionHeaderIndex, ActShow act, UpdateActCallback callback) {
        // ActDetailPresenter那边已经做了网络请求了，所以这里不需要做了
        try {
            List<ActShow> backupActList = new ArrayList<>(actList);
            backupActList.get(sectionHeaderIndex).copyOther(act);
            Map<ActShow, List<AttendShow>> backupActAttendMap = new LinkedHashMap<>();
            for (Map.Entry<ActShow, List<AttendShow>> entry : actAttendMap.entrySet()) {
                if (entry.getKey().getActId().equals(act.getActId())) {
                    backupActAttendMap.put(act, entry.getValue());
                } else {
                    backupActAttendMap.put(entry.getKey(), entry.getValue());
                }
            }
            actList.clear();
            actAttendMap.clear();
            data.clear();
            for (Map.Entry<ActShow, List<AttendShow>> entry : backupActAttendMap.entrySet()) {
                createSection(entry.getKey(), entry.getValue(), true);
            }
            callback.onSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError();
        }
//        if (!FormatUtil.verifyAct(act)) {
//            callback.onError();
//            return;
//        }
//        getActService().updateAct(act.getActId(), act.getName(), act.getDesc(), act.getLocation(), act.getStartTime(), act.getEndTime())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ApiResult>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull ApiResult result) {
//                        if (result.success) {
//                            try {
//                                ActShow rawAct = data.get(sectionHeaderIndex).getHeader().getAct();
//                                rawAct.copyOther(act);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                callback.onError();
//                            }
//                            callback.onSuccess();
//                        } else {
//                            callback.onError();
//                            // callback.onError(IActDetail.ErrorCode.ACT_REPEAT);
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        e.printStackTrace();
//                        callback.onError();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    @Override
    public void insertAct(int position, ActShow act, List<AttendShow> attendList, InsertActCallback callback) {
        // 首页那边已经做了网络请求了，这边不需要再做
        try {
            List<AttendShow> toBeAddAttendList = attendList;
            if (attendList == null)
                toBeAddAttendList = new ArrayList<>();
            createSection(position, act, toBeAddAttendList, true);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError();
        }
        callback.onSuccess();
//        if (!FormatUtil.verifyAct(act)) {
//            callback.onError();
//            return;
//        }
//        getActService().createAct(act.getName(), act.getDesc(), act.getLocation(), act.getStartTime(), act.getEndTime())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ApiResult<ActShow>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull ApiResult<ActShow> result) {
//                        if (result.success) {
//                            try {
//                                List<AttendShow> toBeAddAttendList = attendList;
//                                if (attendList == null)
//                                    toBeAddAttendList = new ArrayList<>();
//                                createSection(position, act, toBeAddAttendList, true);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                callback.onError();
//                            }
//                            callback.onSuccess();
//                        } else {
//                            callback.onError();
//                            // callback.onError(IActDetail.ErrorCode.ACT_REPEAT);
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        e.printStackTrace();
//                        callback.onError();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    @Override
    public void quitAct(int sectionHeaderIndex, QuitActCallback callback) {
        getUserActService().quitAct(actList.get(sectionHeaderIndex).getActId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult result) {
                        if (result.success) {
                            try {
                                actAttendMap.remove(actList.get(sectionHeaderIndex));
                                actList.remove(sectionHeaderIndex);
                                data.remove(sectionHeaderIndex);
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback.onError();
                            }
                            callback.onSuccess();
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteAttend(int sectionHeaderIndex, int sectionItemIndex, DeleteAttendCallback callback) {

        try {
            ActShow act = data.get(sectionHeaderIndex).getHeader().getAct();
            getAttendService().deleteAttend(Objects.requireNonNull(actAttendMap.get(act)).get(sectionItemIndex).getAttendId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResult>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull ApiResult result) {
                            if (result.success) {
                                try {
                                    data.remove(sectionHeaderIndex);
                                    Objects.requireNonNull(actAttendMap.get(act)).remove(sectionItemIndex);
                                    createSection(sectionHeaderIndex, act, Objects.requireNonNull(actAttendMap.get(act)), false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    callback.onError();
                                }
                                callback.onSuccess();
                            } else {
                                callback.onError();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                            callback.onError();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.onError();
    }

    @Override
    public void updateAttendTime(int sectionHeaderIndex, int sectionItemIndex, AttendShow attend, UpdateAttendTimeCallback callback) {
        getAttendService().updateAttendTime(attend.getAttendId(), attend.getStartTime(), attend.getEndTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult result) {
                        if (result.success) {
                            try {
                                ActShow act = actList.get(sectionHeaderIndex);
                                List<AttendShow> attendList = actAttendMap.get(act);
                                attendList.get(sectionItemIndex).copyOther(attend);
                                data.get(sectionHeaderIndex).getItemAt(sectionItemIndex).getAttend().copyOther(attend);
                                callback.onSuccess();
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updateAttendType(int sectionHeaderIndex, int sectionItemIndex, AttendShow attend, UpdateAttendTypeCallback callback) {
        getAttendService().updateAttendType(attend.getAttendId(), attend.getAttendType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult result) {
                        if (result.success) {
                            try {
                                ActShow act = actList.get(sectionHeaderIndex);
                                List<AttendShow> attendList = actAttendMap.get(act);
                                attendList.get(sectionItemIndex).copyOther(attend);
                                data.get(sectionHeaderIndex).getItemAt(sectionItemIndex).getAttend().copyOther(attend);
                                callback.onSuccess();
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void insertAttend(int sectionHeaderIndex, boolean isLoadBefore, AttendShow attend, InsertAttendCallback callback) {

        getAttendService().createAttend(attend.getActId(), attend.getStartTime(), attend.getEndTime(), attend.getAttendType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult<AttendShow>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult<AttendShow> result) {
                        if (result.success) {
                            try {
                                ActShow act = actList.get(sectionHeaderIndex);
                                if (isLoadBefore) {
                                    Objects.requireNonNull(actAttendMap.get(act)).add(0, attend);
                                } else {
                                    Objects.requireNonNull(actAttendMap.get(act)).add(attend);
                                }
                                List<SectionItem_Attend> toBeAdd = new ArrayList<>();
                                SectionItem_Attend item = new SectionItem_Attend(attend);
                                toBeAdd.add(item);
                                data.get(sectionHeaderIndex).finishLoadMore(toBeAdd, isLoadBefore, false);
                                callback.onSuccess(result.data);
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 准备实验初始数据 - 创建活动
     */
    private void prepareCreateActTestData() {
        for (int i = 1; i <= 10; i++) {
            ActShow act = new ActShow();
            act.setName(act.getName() + ", Act.No." + i);
            List<AttendShow> attendList = new ArrayList<>();
            if (i != 11) {
                int jSize = new Random().nextInt(3) + 1;
                for (int j = 1; j <= jSize; j++) {
                    AttendShow attend = new AttendShow();
                    attend.setName(attend.getName() + ", Attend.No." + i);
                    attendList.add(attend);
                }
            }
            createSection(data.size(), act, attendList, true);
        }
    }

    /**
     * 准备实验初始数据 - 加入活动
     */
    private void prepareJoinActTestData() {
        for (int i = 1; i <= 3; i++) {
            ActShow act = new ActShow();
            act.setName(act.getName() + ", Act.No." + i);
            List<AttendShow> attendList = new ArrayList<>();
            if (i != 11) {
                int jSize = new Random().nextInt(3) + 1;
                for (int j = 1; j <= jSize; j++) {
                    AttendShow attend = new AttendShow();
                    attend.setName(attend.getName() + ", Attend.No." + i);
                    attendList.add(attend);
                }
            }
            createSection(data.size(), act, attendList, true);
        }
    }

    /**
     * 根据ActShow/AttendShow创建SectionHeader_Act/SectionItem_attend
     *
     * @param act
     * @param attendList
     * @param isFold
     * @return
     */
    private QMUISection<SectionHeader_Act, SectionItem_Attend> createSection(ActShow act, List<AttendShow> attendList, boolean isFold) {
        return createSection(0, act, attendList, isFold);
    }

    /**
     * 根据ActShow/AttendShow创建SectionHeader_Act/SectionItem_attend
     *
     * @param position
     * @param act
     * @param attendList
     * @param isFold
     * @return
     */
    private QMUISection<SectionHeader_Act, SectionItem_Attend> createSection(int position, ActShow act, List<AttendShow> attendList, boolean isFold) {
        actList.add(position, act);
        SectionHeader_Act header = new SectionHeader_Act(act);
        ArrayList<SectionItem_Attend> contents = new ArrayList<>();
        for (AttendShow attend : attendList) {
            contents.add(new SectionItem_Attend(attend));
        }
        actAttendMap.put(act, attendList);
        QMUISection<SectionHeader_Act, SectionItem_Attend> section = new QMUISection<>(header, contents, isFold);
        // if test load more, you can open the code
        section.setExistAfterDataToLoad(false);
//        section.setExistBeforeDataToLoad(true);
        data.add(position, section);
        return section;
    }

    /**
     * 生成Section
     *
     * @param act
     * @param attendList
     * @param isFold
     * @return
     */
    private QMUISection<SectionHeader_Act, SectionItem_Attend> generateSection(ActShow act, List<AttendShow> attendList, boolean isFold) {
        return generateSection(0, act, attendList, isFold);
    }

    /**
     * 生成Section
     *
     * @param position
     * @param act
     * @param attendList
     * @param isFold
     * @return
     */
    private QMUISection<SectionHeader_Act, SectionItem_Attend> generateSection(int position, ActShow act, List<AttendShow> attendList, boolean isFold) {
        SectionHeader_Act header = new SectionHeader_Act(act);
        ArrayList<SectionItem_Attend> contents = new ArrayList<>();
        for (AttendShow attend : attendList) {
            contents.add(new SectionItem_Attend(attend));
        }
        QMUISection<SectionHeader_Act, SectionItem_Attend> section = new QMUISection<>(header, contents, isFold);
        // if test load more, you can open the code
        section.setExistAfterDataToLoad(false);
//        section.setExistBeforeDataToLoad(true);
        return section;
    }

    /**
     * 比较是否已经存在该Act
     *
     * @param actId
     * @return 返回改Act的下标。不存在则返回-1
     */
    private int containSection(Long actId) {
        int len = actList.size();
        for (int i = 0; i < len; i++) {
            ActShow act = actList.get(i);
            if (act.getActId().equals(actId))
                return i;
        }
        return -1;
    }

}
