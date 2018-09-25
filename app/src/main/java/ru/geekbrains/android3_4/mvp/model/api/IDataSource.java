package ru.geekbrains.android3_4.mvp.model.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.geekbrains.android3_4.mvp.model.entity.User;

public interface IDataSource
{
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String username);
}
