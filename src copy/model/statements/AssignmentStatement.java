package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIMap;
import model.expressions.*;
import model.state.PrgState;
import model.types.IType;
import model.values.IValue;

public class AssignmentStatement implements IStatement {
    private String variable;
    private IExpression expression;

    public AssignmentStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {
        if (!state.getSymTable().containsKey(variable)) {
            throw new StatementException("Variable " + variable + " not found");
        } else {
            IValue value = expression.eval(state.getSymTable(), state.getHeap());
            if (!value.getType().equals(state.getSymTable().getValue(variable).getType())) {
                throw new StatementException("The types do not correspond");
            }
            state.getSymTable().put(variable, value);
        }
        return null;
    }

    public MyIMap<String, IType> typecheck(MyIMap<String,IType> typeEnv) throws StatementException, ADTException, ExpressionException
    {
        IType typevar = typeEnv.getValue(variable);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new StatementException("Assignment: right hand side and left hand side have different types ");
    }
    public String toString() {
        return variable + " = " + expression;
    }

    public IStatement deepCopy() {
        return new AssignmentStatement(variable, expression.deepCopy());
    }
}
