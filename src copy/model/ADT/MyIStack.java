package model.ADT;

import exceptions.ADTException;
import exceptions.EmptyStackException;

public interface MyIStack <T>{
    public T pop() throws ADTException;
    public void push(T value);
    public boolean isEmpty();
}
