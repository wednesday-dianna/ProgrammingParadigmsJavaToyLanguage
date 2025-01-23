package model.statements;
//NEW statement

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIHeap;
import model.ADT.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapAllocationStatement implements IStatement{
    private String variable;
    private IExpression expression;
    public HeapAllocationStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public MyIMap<String, IType> typecheck(MyIMap<String,IType> typeEnv) throws StatementException, ADTException, ExpressionException
    {
        IType typevar = typeEnv.getValue(variable);
        IType typexp = expression.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new StatementException("NEW stmt: right hand side and left hand side have different types");
    }


    public IStatement deepCopy() {
        return new HeapAllocationStatement(variable, expression.deepCopy());
    }
    public String toString() {
        return "new(" + variable + ", " + expression + ")";
    }
    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {
        MyIMap<String, IValue> symTable = state.getSymTable();
        if (!symTable.containsKey(variable)) {
            throw new StatementException("Variable " + variable + " not found");
        }
        IValue val = symTable.getValue(variable);
        if (!(val instanceof RefValue)) {
            throw new StatementException("Variable " + variable + " is not a reference");
        }
        MyIHeap heap = state.getHeap();
        IValue exp = expression.eval(symTable, heap);
        if (!(exp.getType().equals(((RefValue) val).getLocationType()))) {
            throw new StatementException("The expression doesn't match with the type of the variable");
        }
        int address = heap.allocate(exp);
        symTable.put(variable, new RefValue(address, ((RefValue)val).getLocationType()));
        return null;
    }
}
