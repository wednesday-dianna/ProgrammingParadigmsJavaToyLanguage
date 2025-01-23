package repo;

import exceptions.RepoException;
import model.state.PrgState;

import java.util.List;

public interface IRepository
{
    void add(PrgState state);
    void logPrg(PrgState state) throws RepoException;
    List<PrgState> getStates() throws RepoException;
    void setStates(List<PrgState> states) throws RepoException;
    List<PrgState> getPrgList();
}
