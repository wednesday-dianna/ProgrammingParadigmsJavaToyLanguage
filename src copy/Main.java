import controller.Controller;
import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIMap;
import model.ADT.MyMap;
import model.expressions.*;
import model.state.PrgState;
import model.statements.*;
import model.types.*;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.RefValue;
import model.values.StringValue;
import repo.IRepository;
import repo.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Program started\n");
        ExecutorService executor = Executors.newFixedThreadPool(2); // Use 2 threads for simplicity
        TextMenu menu = new TextMenu();
        IStatement stmt1 = new CompStatement(new VarDecStatement("v", new IntType()),new CompStatement(new AssignmentStatement("v", new ValueEvalExpression(new IntValue(2))),new PrintStatement(new VariableEvalExpression("v"))));
        IStatement stmt2 = new CompStatement(new VarDecStatement("a", new IntType()),new CompStatement(new VarDecStatement("b", new IntType()),new CompStatement(new AssignmentStatement("a",new ArithmeticalExpression(new ValueEvalExpression(new IntValue(2)),ArithmeticalOperator.PLUS,new ArithmeticalExpression(new ValueEvalExpression(new IntValue(3)),ArithmeticalOperator.MULTIPLY, new ValueEvalExpression(new IntValue(5))))),new CompStatement(new AssignmentStatement("b",new ArithmeticalExpression(new VariableEvalExpression("a"), ArithmeticalOperator.PLUS, new ValueEvalExpression(new IntValue(1)))),new PrintStatement(new VariableEvalExpression("b"))))));
        IStatement stmt3 = new CompStatement(new VarDecStatement("a", new BoolType()),new CompStatement(new VarDecStatement("v", new IntType()),new CompStatement(new AssignmentStatement("a", new ValueEvalExpression(new BoolValue(true))),new CompStatement(new IfStatement(new LogicalExpression(new VariableEvalExpression("a"),LogicalOperator.AND,new RelationalExpression(new ValueEvalExpression(new IntValue(1)),RelationalOperator.LESS_EQUAL,new ValueEvalExpression(new IntValue(2)))),new AssignmentStatement("v", new ValueEvalExpression(new IntValue(2))),new AssignmentStatement("v", new ValueEvalExpression(new IntValue(3)))),new PrintStatement(new VariableEvalExpression("v"))))));
        IStatement stmt4 = new CompStatement(new VarDecStatement("varf", new StringType()),new CompStatement(new AssignmentStatement("varf", new ValueEvalExpression(new StringValue("test.in"))),new CompStatement(new OpenFileStatement(new VariableEvalExpression("varf")),new CompStatement(new VarDecStatement("varc",new IntType()),new CompStatement(new ReadFileStatement(new VariableEvalExpression("varf"),"varc"),new CompStatement(new PrintStatement(new VariableEvalExpression("varc")),new CompStatement(new ReadFileStatement(new VariableEvalExpression("varf"),"varc"),new CompStatement(new PrintStatement(new VariableEvalExpression("varc")),new CloseFileStatement(new VariableEvalExpression("varf"))))))))));
        IStatement stmt5 = new CompStatement(
                new VarDecStatement("v", new IntType()),new CompStatement(
                new AssignmentStatement("v", new ValueEvalExpression(new IntValue(4))),new CompStatement(
                new WhileStatement(new RelationalExpression(new VariableEvalExpression("v"), RelationalOperator.GREATER_THAN, new ValueEvalExpression(new IntValue(0))), new CompStatement(
                        new PrintStatement(new VariableEvalExpression("v")), new AssignmentStatement("v", new ArithmeticalExpression(new VariableEvalExpression("v"), ArithmeticalOperator.MINUS, new ValueEvalExpression(new IntValue(1))))
                )),
                new PrintStatement(new VariableEvalExpression("v"))
        )
        )
        );
        CompStatement stmt6 = new CompStatement(
                new VarDecStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueEvalExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDecStatement("a", new RefType(new RefType(new IntType()))), // Declare a as Ref(Ref(int))
                                new CompStatement(
                                        new HeapAllocationStatement("a", new VariableEvalExpression("v")), // Allocate a with Ref(v)
                                        new CompStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableEvalExpression("v"))), // Print rH(v)
                                                new PrintStatement(new ArithmeticalExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableEvalExpression("a"))), ArithmeticalOperator.PLUS, new ValueEvalExpression(new BoolValue(true)))) // Print rH(rH(a)) + 5
                                        )
                                )
                        )
                )
        );
        try {
            stmt6.typecheck(new MyMap<String, IType>());
            PrgState prg6 = new PrgState(stmt6);
            IRepository repo6 = new Repository("logFile6.txt");
            repo6.add(prg6);
            Controller controller6 = new Controller(repo6, executor);

            menu.addCommand(new RunExample("6", stmt6.toString(),controller6));
        } catch (Exception e) {
            System.out.println("Typecheck failed!:(");

        }
        CompStatement stmt7 = new CompStatement(
                new VarDecStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueEvalExpression(new IntValue(20))),
                        new CompStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableEvalExpression("v"))),
                                new CompStatement(
                                        new WriteHeapStatement("v",new ValueEvalExpression(new IntValue(5))),
                                        new PrintStatement(new ReadHeapExpression(new VariableEvalExpression("v")))
                                )
                        )
                )
        );

        IStatement stmt8 = new CompStatement(
                new VarDecStatement("v", new IntType()),
                new CompStatement(
                        new AssignmentStatement("v", new ValueEvalExpression(new IntValue(10))),
                        new CompStatement(
                                new VarDecStatement("a", new RefType(new IntType())),
                                new CompStatement(
                                        new HeapAllocationStatement("a", new ValueEvalExpression(new IntValue(22))),
                                        new CompStatement(new ForkStatement(
                                                new CompStatement(
                                                        new AssignmentStatement("v", new ValueEvalExpression(new IntValue(30))),
                                                        new CompStatement(
                                                                new PrintStatement(new VariableEvalExpression("v")),
                                                                new PrintStatement(new ReadHeapExpression(new VariableEvalExpression("a")))
                                                        )
                                                )
                                        ), new CompStatement(new PrintStatement(new VariableEvalExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableEvalExpression("a"))
                                )
                        )
                )
        ))));

        //    public MyIMap<String, IType> typecheck(MyIMap<String,IType> typeEnv) throws StatementException, ADTException, ExpressionException


        PrgState prg1 = new PrgState(stmt1);
        PrgState prg2 = new PrgState(stmt2);
        PrgState prg3 = new PrgState(stmt3);
        PrgState prg4 = new PrgState(stmt4);
        PrgState prg5 = new PrgState(stmt5);

        PrgState prg7 = new PrgState(stmt7);
        PrgState prg8 = new PrgState(stmt8);

        IRepository repo1 = new Repository("logFile1.txt");
        IRepository repo2 = new Repository("logFile2.txt");
        IRepository repo3 = new Repository("logFile3.txt");
        IRepository repo4 = new Repository("logFile4.txt");
        IRepository repo5 = new Repository("logFile5.txt");

        IRepository repo7 = new Repository("logFile7.txt");
        IRepository repo8 = new Repository("logFile8.txt");

        repo1.add(prg1);
        repo2.add(prg2);
        repo3.add(prg3);
        repo4.add(prg4);
        repo5.add(prg5);

        repo7.add(prg7);
        repo8.add(prg8);


        Controller controller1 = new Controller(repo1, executor);
        Controller controller2 = new Controller(repo2, executor);
        Controller controller3 = new Controller(repo3, executor);
        Controller controller4 = new Controller(repo4, executor);
        Controller controller5 = new Controller(repo5, executor);

        Controller controller7 = new Controller(repo7, executor);
        Controller controller8 = new Controller(repo8, executor);


        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", stmt1.toString(),controller1));
        menu.addCommand(new RunExample("2", stmt2.toString(),controller2));
        menu.addCommand(new RunExample("3", stmt3.toString(),controller3));
        menu.addCommand(new RunExample("4", stmt4.toString(),controller4));
        menu.addCommand(new RunExample("5", stmt5.toString(),controller5));
        menu.addCommand(new RunExample("7", stmt7.toString(),controller7));
        menu.addCommand(new RunExample("8", stmt8.toString(),controller8));

        menu.show();
    }
}