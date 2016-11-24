/**
 * Created by Quinn on 2016-11-20.
 */
public class Main
{

    public static void main(String[] args)
    {
        MainMemory mainMem = new MainMemory();
        mainMem.store("globalVariable", 0);

        WriteBuffer[] buffers = new WriteBuffer[10];
        MemoryAgent[] memoryAgents = new MemoryAgent[10];
        Processor[] processors = new Processor[10];

        for(int i=0; i<=9; i++)
        {
            buffers[i] = new WriteBuffer(true);
            memoryAgents[i] = new MemoryAgent(mainMem, buffers[i]);
            processors[i] = new Processor(mainMem, buffers[i], memoryAgents[i], i);
        }

        for(int i=0; i<processors.length; i++)
        {
            mainMem.store("flag"+processors[i].getProcessorNumber(), -1);
            mainMem.store("turn"+processors[i].getProcessorNumber(), -1);
        }

        System.out.println("Part 2:");

        for(int i=0; i<memoryAgents.length; i++)
        {
            memoryAgents[i].start();
        }


        for(int i=0; i<processors.length; i++)
        {
            processors[i].start();
        }



        /*
        for(int i=0; i<processors.length; i++)
        {
            processors[i].kill();
        }
        */
        /*
        for(int i=0; i<memoryAgents.length; i++)
        {
            memoryAgents[i].kill();
        }
        */
    }
}
