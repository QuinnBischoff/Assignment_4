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

    public int getProcessorNumber()
    {
        return processorNumber;
    }

    public void run()
    {
        while(notDead)
        {
            petersonEntry(processorNumber);
            petersonCritical();
            petersonExit(processorNumber);

        }
    }

    public void petersonEntry(int i)
    {
        System.out.println("in entry" + processorNumber);
        for(int k=0; k<10-1; k++)
        {
            writeBuffer.store("flag"+i, k);
            writeBuffer.store("turn"+k, i);
            try{
                sleep(3);
            }catch(InterruptedException e){}

            for(int j=0;j<10; j++)
            {
                if(i!=j)
                {
                    while(loopCondition(j, k, i)) {
                        //nop
                    }
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
        System.out.println("in critical:" + processorNumber);
        int val;
        try {
            val = writeBuffer.load("globalVariable");
        }
        catch (NotInBufferException e){
            val = mainMemory.load("globalVariable");
        }
        val = processorNumber;
        writeBuffer.store("globalVariable", val);
    }


    public void petersonExit(int i)
    {
        System.out.println("in exit"+ processorNumber);
        writeBuffer.store("flag"+i, -1);
    }

    public void swapAtomic(String x, int v)
    {

    }

    public void kill()
    {
        notDead=false;
    }
}