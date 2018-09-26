package ru.geekbrains.android3_4.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.mvp.model.image.IImageLoader;
import ru.geekbrains.android3_4.mvp.model.image.android.GlideImageLoader;
import ru.geekbrains.android3_4.mvp.presenter.MainPresenter;
import ru.geekbrains.android3_4.mvp.view.MainView;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectPresenter
    MainPresenter presenter;

    private CompositeDisposable d;
    private IImageLoader<ImageView> imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageLoader = new GlideImageLoader();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RepoListAdapter adapter = new RepoListAdapter(presenter.getListPresenter());
        recyclerView.setAdapter(adapter);

        d = new CompositeDisposable();
        d.add(adapter.getClicks().subscribe(position -> presenter.onListItemClick(position)));
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        return new MainPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    public void setUsernameText(String username) {
        usernameTextView.setText(username);
    }

    @Override
    public void loadImage(String url) {
        imageLoader.loadInto(url, avatarImageView);
    }

    @Override
    public void updateList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        d.dispose();
    }
}
