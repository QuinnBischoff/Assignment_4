import java.util.concurrent.ConcurrentHashMap;

public class MainMemory
{
    ConcurrentHashMap<String, Integer> criticalVariables;

    public MainMemory()
    {
        criticalVariables = new ConcurrentHashMap<String, Integer>();
    }

    public int load(String x)
    {
        return criticalVariables.get(x);
    }

    public void store(String x, int v)
    {
        criticalVariables.put(x,v);
    }
}