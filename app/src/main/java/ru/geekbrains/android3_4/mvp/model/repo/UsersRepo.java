package ru.geekbrains.android3_4.mvp.model.repo;

import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.android3_4.mvp.model.api.ApiHolder;
import ru.geekbrains.android3_4.mvp.model.entity.Repo;
import ru.geekbrains.android3_4.mvp.model.entity.User;

public class UsersRepo
{
    public Observable<User> getUser(String username)
    {
        return ApiHolder.getApi().getUser(username);
    }

    public Observable<List<Repo>> getRepos(String username)
    {
        return ApiHolder.getApi().getRepos(username);
    }
}
