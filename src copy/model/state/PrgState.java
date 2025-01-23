package model.state;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.*;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;
import repo.IRepository;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStatement> exeStack;
    private MyIMap<String, IValue> symTable;
    private MyIList<IValue> outList;
    private MyIMap<StringValue, BufferedReader> fileTable;
    private IStatement originalState;
    private MyIHeap heap;
    private int id;
    public static int nextId;
    private IRepository repo;

    public PrgState(IStatement OGStatement) {
        this.exeStack = new MyStack<IStatement>();
        this.symTable = new MyMap<String, IValue>();
        this.outList = new MyList<IValue> ();
        this.fileTable = new MyMap<StringValue, BufferedReader>();
        this.originalState = OGStatement.deepCopy();
        this.exeStack.push(originalState);
        this.heap = new MyHeap();
        this.id = this.getNextId();
    }
    public PrgState(MyIStack<IStatement> exeStack, MyIMap<String, IValue> symTable, MyIList<IValue> outList,
                    MyIMap<StringValue,BufferedReader> fileTable, IStatement originalState, MyIHeap heap, IRepository repo)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.outList = outList;
        this.fileTable = fileTable;
        this.originalState = originalState;
        this.heap = heap;
        this.repo = repo;
    }

    public IRepository getRepo() {
        return repo;
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public synchronized int  getNextId(){
        return nextId++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PRGState with id " + this.id + "\n");
        sb.append(this.exeStack.toString()).append("\n");
        sb.append("SymTable: \n").append(this.symTable.toString()).append("\n");
        sb.append(this.outList.toString()).append("\n");
        sb.append(this.fileTableToString()).append("\n");
        sb.append("Heap: \n").append(this.heap.toString()).append("\n");
        return sb.toString();
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public MyIMap<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOutList() {
        return outList;
    }

    public MyIMap<StringValue, BufferedReader> getFileTable() { return this.fileTable; }

    public String fileTableToString() {
        StringBuilder sb = new StringBuilder();
        for (StringValue path : fileTable.getKeys())
        {
            sb.append(path).append("\n");
        }
        return sb.toString();
    }

    public boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

   public PrgState oneStep() throws ADTException, StatementException, ExpressionException {
        MyIStack<IStatement> exeStack = this.getExeStack();
        if (exeStack.isEmpty()) throw new ADTException("exeStack is empty");
        IStatement statement = exeStack.pop();
        return statement.execute(this);
    }

}
