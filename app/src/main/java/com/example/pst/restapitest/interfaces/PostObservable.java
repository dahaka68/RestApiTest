package com.example.pst.restapitest.interfaces;

/**
 * Created by dahak on 07.09.2017.
 */

public interface PostObservable {
    void addObserver(PostObserver postObserver);
    void removeObserver(PostObserver postObserver);
    void notifyObservers();
}
