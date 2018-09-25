package ru.geekbrains.android3_4.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.mvp.model.image.IImageLoader;
import ru.geekbrains.android3_4.mvp.model.image.android.GlideImageLoader;
import ru.geekbrains.android3_4.mvp.presenter.MainPresenter;
import ru.geekbrains.android3_4.mvp.view.MainView;

public class MainActivity extends MvpAppCompatActivity implements MainView
{
    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;

    @InjectPresenter
    MainPresenter presenter;

    IImageLoader<ImageView> imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageLoader = new GlideImageLoader();
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter(){
        return new MainPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    public void setUsernameText(String username)
    {
        usernameTextView.setText(username);
    }

    @Override
    public void loadImage(String url)
    {
        imageLoader.loadInto(url, avatarImageView);
    }
}
