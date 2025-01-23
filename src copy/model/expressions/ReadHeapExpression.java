package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIHeap;
import model.ADT.MyIMap;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExpression implements IExpression {
    private IExpression expression;
    public ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }

    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    public String toString() {
        return "Read heap (" + expression.toString() + ")";
    }

    public IValue eval(MyIMap<String, IValue> symTable, MyIHeap heap) throws ExpressionException, ADTException
    {
        IValue v = expression.eval(symTable, heap);
        if (!(v instanceof RefValue))
        {
            throw new ExpressionException("Expression does not evaluate to a RefValue");
        }
        RefValue refV = (RefValue)v;
        if (!heap.containsKey(refV.getAddress()))
        {
            throw new ExpressionException("Heap does not contain that address");
        }
        return heap.getValue(refV.getAddress());
    }

    public IType typecheck(MyIMap<String,IType> typeEnv) throws ExpressionException, ADTException, StatementException
    {
        IType typ = expression.typecheck(typeEnv);
        if (typ instanceof RefType)
        {
            RefType reft =(RefType) typ;
            return reft.getInnerType();
        }
        else
            throw new ExpressionException("the rH argument is not a Ref Type");
    }
}
