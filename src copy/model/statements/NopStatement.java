package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIMap;
import model.state.PrgState;
import model.types.IType;

public class NopStatement implements IStatement{
    public PrgState execute(PrgState state) {
        return null;
    }

    public IStatement deepCopy() {
        return null;
    }

    public MyIMap<String, IType> typecheck(MyIMap<String,IType> typeEnv) throws StatementException, ADTException, ExpressionException
    {
        return null;
    }

    public String toString() {
        return "NopStatement";
    }
}
