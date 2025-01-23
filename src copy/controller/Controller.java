package controller;

import exceptions.ADTException;
import exceptions.ControllerException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.ADT.MyIStack;
import model.state.PrgState;
import model.statements.IStatement;
import repo.IRepository;
import repo.Repository;

import java.util.List;
import java.util.concurrent.*;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;
    private boolean displayFlag = false;

    public Controller(IRepository repo, ExecutorService executor) {
        this.repo = repo;
        this.executor = executor;
    }

    public void setDisplayFlag(boolean flag) {
        this.displayFlag = flag;
    }

    public boolean getDisplayFlag() {
        return this.displayFlag;
    }

    public void addPRGState(PrgState state) {
        this.repo.add(state);
    }

    public List<PrgState> removeCompletedPrograms(List<PrgState> programs) {
        return programs.stream().filter(PrgState::isNotCompleted).toList();
    }

    public void oneStepForAll(List<PrgState> prg) throws ControllerException {
        prg.stream().forEach(state -> repo.logPrg(state));
        List<Callable<PrgState>> callableList = prg.stream().map(state ->
                (Callable<PrgState>) () -> state.oneStep()
        ).toList();
        try {
            executor.invokeAll(callableList).stream().map(future -> {
                try {
                    return future.get();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).filter(state -> state != null).forEach(state -> repo.add(state));
        } catch (RuntimeException | InterruptedException e) {
            throw new ControllerException(e.getMessage());
        }
        prg.stream().forEach(state -> repo.logPrg(state));
    }

   public void allStep() throws ControllerException {
        executor = Executors.newFixedThreadPool(2);
//remove the completed programs
        List<PrgState> prgList=removeCompletedPrograms(repo.getPrgList());
        while(prgList.size() > 0){
            oneStepForAll(prgList);
//remove the completed programs
            prgList=removeCompletedPrograms(repo.getPrgList());
        }
        executor.shutdownNow();
//HERE the repository still contains at least one Completed Prg
// and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
//setPrgList of repository in order to change the repository
// update the repository state
        repo.setStates(prgList);
    }
}
