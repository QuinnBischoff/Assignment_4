import java.util.Random;

public class Processor extends Thread
{
    public WriteBuffer writeBuffer;
    public MemoryAgent memoryAgent;
    public MainMemory mainMemory;
    private int processorNumber;
    boolean notDead = true;

    public Processor(MainMemory mM, WriteBuffer wb, MemoryAgent mA, int processorNumber)
    {
        this.writeBuffer = wb;
        this.memoryAgent = mA;
        this.mainMemory = mM;
        this.processorNumber = processorNumber;
    }

    public void run()
    {
        int i =(int) Thread.currentThread().getId();
        while(notDead) {
            petersonEntry(i);
            petersonCritical(i);
            petersonExit(i);
        }
    }

    public void petersonEntry(int i)
    {
        System.out.println("in entry");
        for(int k=0; k<10-2; k++)
        {
            do
            {
                writeBuffer.store("flag"+i, k);
                writeBuffer.store("turn"+k, i);
            }while()
        }

    }


    public void petersonCritical()
    {
        System.out.println("in critical");
    }

    public void petersonExit(int i)
    {
        writeBuffer.store("flag"+i, -1);
    }
}