package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIMap;
import model.state.PrgState;
import model.types.IType;
import model.values.IValue;

public class VarDecStatement implements IStatement {
    private String variable;
    private IType type;

    public VarDecStatement(String variable, IType type) {
        this.variable = variable;
        this.type = type;
    }

    public PrgState execute(PrgState state) throws StatementException {
        MyIMap<String, IValue> symTable = state.getSymTable();
        if (symTable.containsKey(variable)) {
            throw new StatementException(variable + " is already declared");
        }
        symTable.put(this.variable, type.getDefaultValue());
        return null;
    }

    public MyIMap<String, IType> typecheck(MyIMap<String,IType> typeEnv) throws StatementException, ADTException, ExpressionException
    {
        typeEnv.put(variable, type);
        return typeEnv;
    }

    public IStatement deepCopy() {
        return new VarDecStatement(this.variable, this.type);
    }

    public String toString() {
        return this.type.toString() + " " + this.variable;
    }
}
