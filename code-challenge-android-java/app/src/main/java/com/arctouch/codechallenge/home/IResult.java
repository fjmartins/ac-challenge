package com.arctouch.codechallenge.home;

public interface IResult<T> {

    void onSuccess(T obj);
    void onError(String msg);
}
