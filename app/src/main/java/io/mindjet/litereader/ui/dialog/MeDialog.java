package io.mindjet.litereader.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.Window;

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
    private OtherService service;

    public ObservableField<Boolean> visible = new ObservableField<>(true);
    public ObservableField<String> intro = new ObservableField<>("");
    public ObservableField<String> mail = new ObservableField<>("");
    public ObservableField<String> website = new ObservableField<>("");
    public ObservableField<String> github = new ObservableField<>("");

    public MeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void beforeInitView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_me, null, false);
        setContentView(binding.getRoot());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.setData(this);
    }

    @Override
    protected void initView() {
        service = ServiceGen.create(OtherService.class);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Courgette-Regular.ttf");
        binding.header.setTypeface(typeface);
        getData();
    }

    private void getData() {
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
                        visible.set(false);
                    }

                    @Override
                    protected void onFailed() {
                        getData();
                    }
                });
    }

    @Override
    protected void initData() {

    }

    public void onSendMail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail.get()});
        intent.putExtra(Intent.EXTRA_SUBJECT, getContext().getResources().getString(R.string.app_name));
        getContext().startActivity(Intent.createChooser(intent, "选择发送邮件的应用"));
    }

    public void onWebsite() {
        browse(website.get());
    }

    public void onGithub() {
        browse(github.get());
    }

    private void browse(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        getContext().startActivity(Intent.createChooser(intent, "选择打开网页的应用"));
    }

}
