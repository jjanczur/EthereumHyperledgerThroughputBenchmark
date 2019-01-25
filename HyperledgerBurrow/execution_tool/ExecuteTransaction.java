import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ExecuteTransaction
{
    /* Nodes */
    public static final String validator = "4474D57D0F6C70FEBDA3D0B0D602A64F68134A0F";
    public static final String participant = "AAE79FCC1051D8921723D66272CA49B42B044C83";
    
    /* Commands */
    public static String CmdStartNode = " start --validator-index=0";
    public static String CmdTransaction = " deploy --address " + validator + " -f test.yaml";
    
    /** Usage java ExecuteTransaction path/to/burrow_executable */
    public static void main(String[] args)
    {
        if(args.length != 1)
        {
            System.out.println("Missing parameters. java ExecuteTransaction path/to/burrow_executable");
            System.exit(0);
        }
        CmdTransaction = args[0] + CmdTransaction;
        
        int n = 1000;
        long t0 = System.currentTimeMillis();
        //System.out.println(CmdTransaction);
        try
        {
            for(int i = 0; i < n; i++)
            {
                String s = "";
                Process p = Runtime.getRuntime().exec(CmdTransaction);
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
                System.out.println ("exit: " + p.exitValue());
                p.destroy();
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
}
