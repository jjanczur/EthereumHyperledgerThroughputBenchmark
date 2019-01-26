import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Worker implements Runnable
{
    private String cmdTransaction = "";
    
    public Worker(String cmdTransaction)
    {
        this.cmdTransaction = cmdTransaction;
    }
    
    @Override
    public void run()
    {
        try
        {
            //System.out.println("START command " + cmdTransaction);
            String s = "";
            Process p = Runtime.getRuntime().exec(cmdTransaction);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            long time = System.currentTimeMillis();
            
            while ((s = stdError.readLine()) != null)
            {
                if(s.startsWith("Transaction Hash"))
                {
                    time = System.currentTimeMillis() - time;
                    System.out.println(time + "");
                    int idx = s.indexOf("=>") + 3;
                    System.out.println("" + s.substring(idx, s.length()-1) + " " + time + "ms");
                }
            }
            
            p.waitFor();
            //System.out.println ("exit: " + p.exitValue());
            p.destroy();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
}
