package model.ADT;

import exceptions.EmptyStackException;

import java.util.Stack;

public class MyStack <T> implements MyIStack<T> {
    private Stack<T> stack;
    public MyStack() {
        this.stack = new Stack<>();
    }
    @Override
    public T pop() throws EmptyStackException
    {
        if (stack.isEmpty()) throw new EmptyStackException("Stack is empty");
        return this.stack.pop();
    }
    @Override
    public void push(T value)
    {
        this.stack.push(value);
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Execution Stack {\n");
        for (T item : stack)
        {
            sb.append(item).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
