package ru.geekbrains.android3_4.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.subjects.PublishSubject;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.mvp.view.RowView;

public class RepoListViewHolder extends RecyclerView.ViewHolder implements RowView {
    private TextView repoName;
    private TextView repoDescription;

    public RepoListViewHolder(View itemView, PublishSubject<Integer> clicks) {
        super(itemView);
        repoName = itemView.findViewById(R.id.repo_name);
        repoDescription = itemView.findViewById(R.id.repo_description);

        RxView.clicks(itemView).map(o -> getAdapterPosition()).subscribe(clicks);
    }

    @Override
    public void setRepoName(String name) {
        repoName.setText(name);
    }

    @Override
    public void setRepoDescription(String description) {
        if (description == null) {
            repoDescription.setText(R.string.description_empty);
        } else {
            repoDescription.setText(description);
        }
    }
}
