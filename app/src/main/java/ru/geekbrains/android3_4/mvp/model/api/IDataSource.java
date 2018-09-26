package ru.geekbrains.android3_4.mvp.model.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.geekbrains.android3_4.mvp.model.entity.Repo;
import ru.geekbrains.android3_4.mvp.model.entity.User;

public interface IDataSource
{
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String username);

    @GET("/users/{user}/repos")
    Observable<List<Repo>> getRepos(@Path("user") String username);
}
