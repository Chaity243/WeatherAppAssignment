package com.example.cricone.Network;

public interface NetworkResponseCallback<T>
{
    void resultSucesss(T t);
    void resultError(String msg , NetworkErrors networkErrors);
}
