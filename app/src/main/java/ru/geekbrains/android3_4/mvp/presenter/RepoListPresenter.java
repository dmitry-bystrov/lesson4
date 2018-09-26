package ru.geekbrains.android3_4.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.android3_4.mvp.model.entity.Repo;
import ru.geekbrains.android3_4.mvp.view.RowView;

public class RepoListPresenter {
    private List<Repo> repos;

    public RepoListPresenter() {
        this.repos = new ArrayList<>();
    }

    public void onBindListRow(int position, RowView rowView) {
        rowView.setRepoName(repos.get(position).getName());
        rowView.setRepoDescription(repos.get(position).getDescription());
    }

    public int getReposCount() {
        return repos.size();
    }

    public void setRepos(List<Repo> repos) {
        this.repos = repos;
    }

    public Repo getRepo(int index) {
        return repos.get(index);
    }
}
