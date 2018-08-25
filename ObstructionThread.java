
public class ObstructionThread extends Thread
{
    int[][] noMove;
    public ObstructionThread(int[][] b)
    {
        super();
        noMove=b;
    }

    public void setObs(int[][] b)
    {
        noMove=b;
    }
    
    public void run()
    {
        
    }
}
