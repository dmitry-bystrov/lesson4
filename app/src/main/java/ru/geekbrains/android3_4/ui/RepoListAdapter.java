package ru.geekbrains.android3_4.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.reactivex.subjects.PublishSubject;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.mvp.presenter.RepoListPresenter;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListViewHolder> {
    private PublishSubject<Integer> clicks;
    private RepoListPresenter presenter;

    public RepoListAdapter(RepoListPresenter presenter) {
        this.presenter = presenter;
        this.clicks = PublishSubject.create();
    }

    @NonNull
    @Override
    public RepoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_list_item, parent, false), clicks);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoListViewHolder holder, int position) {
        presenter.onBindListRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getReposCount();
    }

    public PublishSubject<Integer> getClicks() {
        return clicks;
    }
}
