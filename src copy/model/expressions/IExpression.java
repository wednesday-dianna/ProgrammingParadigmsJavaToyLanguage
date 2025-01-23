package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIHeap;
import model.ADT.MyIMap;
import model.types.IType;
import model.values.IValue;

public interface IExpression {
    IValue eval(MyIMap<String, IValue> symTable, MyIHeap heap) throws ExpressionException, ADTException;
    IType typecheck(MyIMap<String,IType> typeEnv) throws ExpressionException, ADTException, StatementException;
    IExpression deepCopy();
}
