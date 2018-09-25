package ru.geekbrains.android3_4.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.geekbrains.android3_4.mvp.model.repo.UsersRepo;
import ru.geekbrains.android3_4.mvp.view.MainView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    UsersRepo usersRepo;
    Scheduler mainThreadScheduler;

    //TODO: list presenter

    public MainPresenter(Scheduler mainThreadScheduler)
    {
        this.mainThreadScheduler = mainThreadScheduler;
        usersRepo = new UsersRepo();
    }

    @Override
    protected void onFirstViewAttach()
    {
        super.onFirstViewAttach();
        loadData();
    }

    @SuppressLint("CheckResult")
    private void loadData()
    {
        usersRepo.getUser("googlesamples")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {

                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());


                    //TODO: отобразить список репозиториев пользователя
                    usersRepo.getUserRepos().subscribe(list -> {
                        //listpresenter.repos = list

                        //getViewState().updateList()
                    });


                }, throwable -> {
                    Timber.e(throwable, "Failed to get user");
                });

    }

    private void loadDataOkHttp()
    {
        Single<String> single = Single.fromCallable(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/users/googlesamples")
                    .build();
            return client.newCall(request).execute().body().string();
        });

        single.subscribeOn(Schedulers.io())
                .subscribe(s -> {
                    Timber.d(s);
                });
    }
}
