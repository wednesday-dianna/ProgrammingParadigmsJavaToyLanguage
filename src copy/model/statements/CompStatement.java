package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIMap;
import model.ADT.MyIStack;
import model.state.PrgState;
import model.types.IType;

public class CompStatement implements IStatement{
    private IStatement left;
    private IStatement right;

    public CompStatement(IStatement left, IStatement right) {
        this.left = left;
        this.right = right;
    }

    public MyIMap<String, IType> typecheck(MyIMap<String,IType> typeEnv) throws StatementException, ADTException, ExpressionException
    {
        return right.typecheck(left.typecheck(typeEnv));
    }

    public IStatement deepCopy()
    {
        return new CompStatement(left.deepCopy(), right.deepCopy());
    }

    public PrgState execute(PrgState state)
    {
        MyIStack<IStatement> exeStack = state.getExeStack();
        exeStack.push(this.right);
        exeStack.push(this.left);
        return null;
    }

    public String toString()
    {
        return "(" + left.toString() + "; " + right.toString() + ")";
    }
}
