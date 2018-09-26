package ru.geekbrains.android3_4.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_4.mvp.model.repo.UsersRepo;
import ru.geekbrains.android3_4.mvp.view.MainView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private CompositeDisposable d;
    private Scheduler mainThreadScheduler;
    private RepoListPresenter listPresenter;
    private UsersRepo usersRepo;

    public MainPresenter(Scheduler mainThreadScheduler) {
        this.d = new CompositeDisposable();
        this.mainThreadScheduler = mainThreadScheduler;
        this.listPresenter = new RepoListPresenter();
        this.usersRepo = new UsersRepo();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    private void loadData() {
        d.add(usersRepo.getUser("googlesamples")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {

                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());

                    d.add(usersRepo.getRepos(user.getLogin())
                            .subscribeOn(Schedulers.io())
                            .observeOn(mainThreadScheduler)
                            .subscribe(list -> {

                                listPresenter.setRepos(list);
                                getViewState().updateList();

                            }, throwable -> Timber.e(throwable, "Failed to get repos")));

                }, throwable -> Timber.e(throwable, "Failed to get user")));
    }

    public RepoListPresenter getListPresenter() {
        return listPresenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        d.dispose();
    }

    public void onListItemClick(Integer position) {
        getViewState().showToast(listPresenter.getRepo(position).getName());
    }
}
