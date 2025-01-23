package model.expressions;


import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIHeap;
import model.ADT.MyIMap;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private LogicalOperator operator;
    public LogicalExpression(IExpression left, LogicalOperator operator, IExpression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
    public IValue eval(MyIMap<String, IValue> symTable, MyIHeap heap) throws ExpressionException, ADTException
    {
        IValue leftresult = this.left.eval(symTable, heap);
        IValue rightresult = this.right.eval(symTable, heap);
        if (!leftresult.getType().equals(rightresult.getType()))
        {
            throw new ExpressionException("Right doesn't have the same type as left");
        }
        BoolValue rightBool = (BoolValue) rightresult;
        BoolValue leftBool = (BoolValue) leftresult;
        if (operator == LogicalOperator.OR)
        {
            return new BoolValue(rightBool.getValue() || leftBool.getValue());
        }
        if (operator == LogicalOperator.AND)
        {
            return new BoolValue(leftBool.getValue() && rightBool.getValue());
        }
        throw new ExpressionException("Unknown operator");
    }
    public IType typecheck(MyIMap<String,IType> typeEnv) throws ExpressionException, ADTException, StatementException
    {
        IType typ1, typ2;
        typ1=left.typecheck(typeEnv);
        typ2=right.typecheck(typeEnv);
        if(typ1.equals(new IntType()))
        {
            if(typ2.equals(new IntType()))
            {
                return new IntType();
            }
            else
                throw new ExpressionException("second operand is not an integer");
        }
        else
            throw new ExpressionException("first operand is not an integer");
    }
    public String toString()
    {
        return left.toString() + " " + operator.toString() + " " + right.toString();
    }
    public IExpression deepCopy()
    {
        return new LogicalExpression(left.deepCopy(), operator, right.deepCopy());
    }
}
