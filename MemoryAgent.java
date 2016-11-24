import java.util.NoSuchElementException;

public class MemoryAgent extends Thread
{
    MainMemory mainMemory;
    WriteBuffer writeBuffer;
    boolean notDead = true;

    public MemoryAgent(MainMemory mM, WriteBuffer wb)
    {
        this.mainMemory = mM;
        this.writeBuffer = wb;
    }

    //run infinite loop checking if there is a store request waiting in the queue to be serviced
    //Flush a pending store in the write-buffer to memory
    public void run()
    {
        while(notDead)
        {
            try{
                sleep(1);
            }catch(InterruptedException e){}
            if(!writeBuffer.buffer.isEmpty())
            {
                Node nextVar = writeBuffer.buffer.poll();
                mainMemory.store(nextVar.key, nextVar.val);
            }
        }
    }

    public void kill()
    {
        notDead=false;
    }

}