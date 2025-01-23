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
import model.values.IntValue;

import javax.management.relation.Relation;

public class RelationalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private RelationalOperator operator;
    public RelationalExpression(IExpression left, RelationalOperator operator, IExpression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
    public IExpression deepCopy() {
        return new RelationalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    public String toString()
    {
        return left.toString() + " " + operator.toString() + " " + right.toString();
    }

    public IValue eval(MyIMap<String, IValue> symTable, MyIHeap heap) throws ADTException, ExpressionException {
        IValue leftVal = left.eval(symTable, heap);
        IValue rightVal = right.eval(symTable, heap);
        if (!leftVal.getType().equals(new IntType()) || !rightVal.getType().equals(new IntType())) {
            throw new ExpressionException("Only int values can be compared");
        }
        IntValue leftInt = (IntValue) leftVal;
        IntValue rightInt = (IntValue) rightVal;
        if (operator == RelationalOperator.EQUAL)
        {
            return new BoolValue(leftInt.equals(rightInt));
        }
        if (operator == RelationalOperator.NOT_EQUAL)
        {
            return new BoolValue(!leftInt.equals(rightInt));
        }
        if (operator == RelationalOperator.LESS_THAN)
        {
            return new BoolValue(leftInt.getValue() < rightInt.getValue());
        }
        if (operator == RelationalOperator.GREATER_THAN)
        {
            return new BoolValue(leftInt.getValue() > rightInt.getValue());
        }
        if (operator == RelationalOperator.GREATER_EQUAL)
        {
            return new BoolValue(leftInt.getValue() >= rightInt.getValue());
        }
        if (operator == RelationalOperator.LESS_EQUAL)
        {
            return new BoolValue(leftInt.getValue() <= rightInt.getValue());
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
}
