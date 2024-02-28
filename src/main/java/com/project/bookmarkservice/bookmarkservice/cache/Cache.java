package com.project.bookmarkservice.bookmarkservice.cache;

import java.util.Optional;

public interface Cache{
    public int get(int key);
    public void put(int key,int value);

    public int size=0;
}
