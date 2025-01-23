package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.ADT.MyMap;
import model.state.PrgState;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.ADT.MyIMap;
import model.ADT.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.values.IValue;
import model.ADT.MyStack;
import exceptions.StatementException;

public class ForkStatement implements IStatement {
    private final IStatement stmt;  // Statement that will be executed in the new thread

    public ForkStatement(IStatement stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        // Create a clone of the symbol table to create a new thread (program state)
        MyIMap<String, IValue> newSymTbl = state.getSymTable().deepCopy();
        MyStack<IStatement> stack = new MyStack<IStatement>();
        stack.push(stmt);
        // Create a new program state for the new thread
        PrgState newPrgState = new PrgState(
                stack,
                newSymTbl,
                state.getOutList(),
                state.getFileTable(),
                stmt,
                state.getHeap(),
                state.getRepo()
        );

        // Add the new program state (thread) to the repository
        //state.getRepo().add(newPrgState);

        //return null;  // The parent program state doesn't change at this point
        return newPrgState;
    }

    public MyIMap<String, IType> typecheck(MyIMap<String,IType> typeEnv) throws StatementException, ADTException, ExpressionException
    {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;

    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}
