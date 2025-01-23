package repo;

import exceptions.RepoException;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> states;
    private String filePath;
    public Repository(String filePath)
    {
        this.filePath = filePath;
        this.states = new ArrayList<>();
    }

    public void add(PrgState state)
    {

        this.states.add(state);
    }

    public void logPrg(PrgState state) throws RepoException
    {
        try
        {
            PrintWriter logfile = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
            logfile.println(state.toString());
            if (state.getExeStack().isEmpty())
            {
                logfile.println("================================================================\n");
            }
            else {
                logfile.println("----------------------------------------------------------------\n");
            }
            logfile.close();
        }
        catch (IOException e)
        {
            throw new RepoException(e.getMessage());
        }
    }

    public List<PrgState> getStates()
    {
        return states;
    }

    public List<PrgState> getPrgList()
    {
        return states;

    }

    public void setStates(List<PrgState> states)
    {
        this.states = states;
    }
}
