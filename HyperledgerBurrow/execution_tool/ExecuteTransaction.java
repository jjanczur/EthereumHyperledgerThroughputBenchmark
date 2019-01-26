import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ExecuteTransaction
{
    /* Nodes */
    public static String validator = "";
    public static String participant = "";
    
    /* Commands */
    public static String cmdStartNode = " start --validator-index=0";
    public static String cmdTransaction = " deploy --address " + validator + " -f test.yaml";
    
    /** Usage java ExecuteTransaction path/to/burrow_executable */
    public static void main(String[] args)
    {
        if(args.length != 6)
        {
            System.out.println("Missing parameters. java ExecuteTransaction path/to/burrow_executable validator_ID no_of_iterations url port thread_pool_size");
            System.exit(0);
        }
        validator = args[1];
        cmdTransaction = args[0] + " deploy --address " + validator + " -f test.yaml -u " + args[3] + ":" + args[4];
        System.out.println(cmdTransaction);
        int n = Integer.parseInt(args[2]);
        
        long t0 = System.currentTimeMillis();
        System.out.println("Starting at time: " + t0);
        
        ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(args[5]));
        for(int i = 0; i < n; i++)
        {
            Runnable worker = new Worker(cmdTransaction);
            executor.execute(worker);
        }
        executor.shutdown();
        while(!executor.isTerminated())
        {
            //wait for all threads
        }
        System.out.println("Execution finished!");
    }
}
