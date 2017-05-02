package io.mindjet.litereader.ui.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.mindjet.jetgear.base.BaseDialog;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.DialogMeBinding;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.other.Me;
import io.mindjet.litereader.service.OtherService;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 关于我 dialog
 * <p>
 * Created by Jet on 5/2/17.
 */

public class MeDialog extends BaseDialog {

    private DialogMeBinding binding;
    private OtherService service = ServiceGen.create(OtherService.class);

    public ObservableField<String> intro = new ObservableField<>("");
    public ObservableField<String> mail = new ObservableField<>("");
    public ObservableField<String> website = new ObservableField<>("");
    public ObservableField<String> github = new ObservableField<>("");

    public MeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void beforeInitView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_me, null, false);
        setContentView(binding.getRoot());
        binding.setData(this);
    }

    @Override
    protected void initView() {
        service.getMe()
                .compose(new ThreadDispatcher<Me>())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleHttpSubscriber<Me>() {
                    @Override
                    public void onNext(Me me) {
                        intro.set(me.getIntro());
                        mail.set(me.getMail());
                        website.set(me.getWebsite());
                        github.set(me.getGithub());
                    }
                });
    }

    @Override
    protected void initData() {

    }
}
