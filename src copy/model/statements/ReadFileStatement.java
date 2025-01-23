package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{
    private IExpression fileName;
    private String variable;
    public ReadFileStatement(IExpression fileName, String variable) {
        this.fileName = fileName;
        this.variable = variable;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {
        MyIMap<String, IValue> symTable = state.getSymTable();
        if (!symTable.containsKey(variable)) {
            throw new StatementException("Variable not found");
        }
        IValue v = symTable.getValue(variable);
        if (!v.getType().equals(new IntType())) {
            throw new StatementException("Variable '" + variable + "' is not a number");
        }
        IValue result = fileName.eval(symTable, state.getHeap());
        if (!result.getType().equals(new StringType())) {
            throw new StatementException("The expression must be a string");
        }
        StringValue s = (StringValue) result;
        MyIMap<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.containsKey(s))
        {
            throw new StatementException("The file isn't open for reading");
        }
        BufferedReader reader = fileTable.getValue(s);
        try
        {
            String line = reader.readLine();
            if (line == null)
            {
                line = "0";
            }
            int p = Integer.parseInt(line);
            IntValue intv = new IntValue(p);
            symTable.put(variable, intv);
        }
        catch (IOException e)
        {
            throw new StatementException(e.getMessage());
        }
        return null;
    }

    public MyIMap<String, IType> typecheck(MyIMap<String,IType> typeEnv) throws StatementException, ADTException, ExpressionException
    {
        if(typeEnv.containsKey(this.variable))
        {
            IType typevar = typeEnv.getValue(this.variable);
            IType typexp = fileName.typecheck(typeEnv);

            if(!typevar.equals(new IntType()))
            {
                throw new StatementException("Variable '" + this.variable + "' is not a number");
            }

            if(!typexp.equals(new StringType()))
            {
                throw new StatementException("File name must be a string");
            }
            return typeEnv;
        }
        else
            throw new StatementException("Variable '" + this.variable + "' not defined");
    }
    public IStatement deepCopy() {
        return new ReadFileStatement(fileName.deepCopy(), variable);
    }

    public String toString() {
        return "reading from (" + fileName + ") into variable (" + variable + ")";
    }
}

