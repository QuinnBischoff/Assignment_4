public class MemoryAgent extends Thread
{
    MainMemory mainMemory;
    WriteBuffer writeBuffer;

    public MemoryAgent(MainMemory mM, WriteBuffer wb)
    {
        this.mainMemory = mM;
        this.writeBuffer = wb;
    }

    //run infinite loop checking if there is a store request waiting in the queue to be serviced
    //Flush a pending store in the write-buffer to memory
    public void run()
    {
        while(true)
        {
            Node nextVar = writeBuffer.buffer.pollFirst();
            writeBuffer.lock = true;

            if(nextVar != null)
            {
                nextVar = writeBuffer.buffer.getFirst();
                mainMemory.store(nextVar.key, nextVar.val);
            }

            writeBuffer.lock = false;
        }
    }
}