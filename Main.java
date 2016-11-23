/**
 * Created by Quinn on 2016-11-20.
 */
public class Main
{

    public static void main(String[] args)
    {
        MainMemory mainMem = new MainMemory();
        mainMem.store("globalVariable", 0);

        WriteBuffer writeBuffer1 = new WriteBuffer(true);
        MemoryAgent memoryAgent1 = new MemoryAgent(mainMem, writeBuffer1);
        Processor processor1 = new Processor(mainMem, writeBuffer1, memoryAgent1, 0);
        processor1.start();
        processor1.notDead=false;
    }

}
