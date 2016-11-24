import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class WriteBuffer
{
    public boolean tso;
    public ConcurrentLinkedDeque<Node> buffer;

    public WriteBuffer(boolean tso)
    {
        this.tso = tso;
        buffer = new ConcurrentLinkedDeque<Node>();
    }

    //returns value of x read from the buffer, or throws a notinbufferexception
    public synchronized int load(String arg) throws NotInBufferException
    {
        Iterator<Node> iterator = buffer.descendingIterator(); //changed to descending
        while (iterator.hasNext()) {
            Node tempNode = iterator.next();
            if (tempNode.key.equals(arg)) {
                return tempNode.val;
            }
        }
        throw new NotInBufferException();
    }

    public synchronized void store(String x, int v)
    {
        Node storeNode = new Node(x,v); //create a new node to hold the data

        //if TSO scheme==true add the node to the back of the deque
        if(tso)
        {
            buffer.add(storeNode);
        }
        else //PSO scheme
        {
            boolean inBuffer = false;
            Iterator<Node> iterator = buffer.iterator();

           /* while(iterator.hasNext()) {
                //if the type is already in the deque, add the node to the back of the deque
                if (storeNode.key.equals(iterator.next().key)) {
                    inBuffer=true;
                    buffer.add(storeNode);
                    break;
                }
            }
            //if the type isn't in the deque, we add it to the front
            if(!inBuffer)
            {
                buffer.addFirst(storeNode);
            }*/
            try
            {
                this.load(x);
                buffer.add(storeNode);
            }
            catch(NotInBufferException e)
            {
                buffer.addFirst(storeNode);
            }
        }
    }

    public void setToPSOMODE()
    {
        tso = false;
    }


    /*public void swapAtomic(String x, int v)
    {

    }*/


}



   /*
   WRONG WAY TO DO IT
    //stores the item in the buffer in a position dependent on tco
    public void store(String x, int v)
    {
        Node tempNode = new Node(x,v); //create a new node to hold the data

        //if tso==true add the node to the back of the deque
        if(tso)
        {
            buffer.add(tempNode);
        }
        //if it's not, we need to insert the new node to the position after the last occurrence of its type.
        else
        {
            Iterator<Node> downIterator = buffer.descendingIterator();
            Iterator<Node> upIterator = buffer.iterator();
            Node matchNode;
            ConcurrentLinkedDeque<Node> tempDeque = new ConcurrentLinkedDeque<Node>();

            //find the last occurance of type x in the buffer
            int position=buffer.size()-1;
            if(position>-1) {
                while (downIterator.hasNext()) {
                    matchNode = downIterator.next();
                    if (matchNode.s.equals(x)) {
                        break;
                    }
                    position--;
                }

                //create a new deque with the item inserted in the correct position
                int count = 0;
                while (upIterator.hasNext()) {
                    if (count == position) {
                        tempDeque.add(tempNode);
                    } else {
                        tempDeque.add(upIterator.next());
                    }
                    count++;
                }
            }
            else
            {
                tempDeque.add(tempNode);
            }
            buffer = tempDeque; //set the buffer to the deque with new item inserted
        }
    }
    */