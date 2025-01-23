package view;

import controller.Controller;
import model.state.PrgState;
import model.statements.IStatement;
import repo.IRepository;
import repo.Repository;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class View {
    private Controller controller;
    private final List<IStatement> statementList;

    public View(List<IStatement> statementList) {
        this.statementList = statementList;
        IRepository repo = new Repository("../Interpreter/log.txt");
        ExecutorService executor = Executors.newFixedThreadPool(2); // Create an ExecutorService with 2 threads
        this.controller = new Controller(repo, executor); // Pass both the repo and executor to the Controller
    }

    public void printMenu() {
        System.out.println("\nMenu: ");
        System.out.println("1: Execute program 1");
        System.out.println("2: Execute program 2");
        System.out.println("3: Execute program 3");
        System.out.println("4: Switch display flag (initially off)");
        System.out.println("Press anything else to exit");
    }

    public void execute1() {
        IStatement statement = this.statementList.get(0);
        PrgState state = new PrgState(statement);
        controller.addPRGState(state);
        try {
            controller.allStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute2() {
        IStatement statement = this.statementList.get(1);
        PrgState state = new PrgState(statement);
        controller.addPRGState(state);
        try {
            controller.allStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute3() {
        IStatement statement = this.statementList.get(2);
        PrgState state = new PrgState(statement);
        controller.addPRGState(state);
        try {
            controller.allStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void runView() {
        boolean alive = true;
        while (alive) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    execute1();
                    break;
                case "2":
                    execute2();
                    break;
                case "3":
                    execute3();
                    break;
                case "4":
                    boolean dispFlag = controller.getDisplayFlag();
                    controller.setDisplayFlag(!dispFlag);
                    break;
                default:
                    alive = false;
            }
        }
    }
}
