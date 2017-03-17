package io.mindjet.litereader.viewmodel.detail;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityInterface;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ActivityDoubanDetailBinding;
import io.mindjet.litereader.model.other.Imdb;
import io.mindjet.litereader.reactivex.RxAction;
import io.mindjet.litereader.service.OtherService;
import io.mindjet.litereader.util.RottenTomatoUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/17/17.
 */

public class DoubanMovieDetailViewModel extends BaseViewModel<ActivityInterface<ActivityDoubanDetailBinding>> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_douban_detail;
    }

    @Override
    public void onViewAttached(View view) {
//        OtherService service = ServiceGen.create(OtherService.class);
//        service.getImdbData("Logan", "2017")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Imdb>() {
//                    @Override
//                    public void call(Imdb imdb) {
//                        Toaster.toast(getContext(), imdb.imdbRating, 3000);
//                    }
//                }, RxAction.onError());

        RottenTomatoUtil.getRottenTomatoesData(getSelfView().getBinding().tv1, getSelfView().getBinding().tv2, "Logan_2017", "2017");
    }

}
