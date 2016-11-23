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
            petersonCritical();
            petersonExit(i);
        }
    }

    public void petersonEntry(int i)
    {
        System.out.println("in entry");
        for(int k=0; k<10; k++)
        {
            writeBuffer.store("flag"+i, k);
            writeBuffer.store("turn"+k, i);
            for(int j=0;j<10; j++)
            {
                if(i==j) continue;
                while(loopCondition(j,k,i))
                {
                   //nop
                }
            }
        }
    }

    public boolean loopCondition(int j, int k, int i)
    {
        int flagj;
        try {
            flagj = writeBuffer.load("flag"+j);
        }
        catch (NotInBufferException e){
            flagj = mainMemory.load("flag"+j);
        }

        int turnk;
        try {
            turnk = writeBuffer.load("turn"+k);
        }
        catch (NotInBufferException e){
            turnk = mainMemory.load("turn"+k);
        }

        if(flagj>=k && turnk == i)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public void petersonCritical()
    {
        System.out.println("in critical");
        int val;
        try {
            val = writeBuffer.load("globalVariable");
        }
        catch (NotInBufferException e){
           val = mainMemory.load("globalVariable");
        }
        val += 2;
        writeBuffer.store("globalVariable", val);
    }


    public void petersonExit(int i)
    {
        writeBuffer.store("flag"+i, -1);
    }
}