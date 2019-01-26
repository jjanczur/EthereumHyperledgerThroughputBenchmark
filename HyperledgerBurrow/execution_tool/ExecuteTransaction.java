import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ExecuteTransaction
{
    /* Nodes */
    public static String validator = "4474D57D0F6C70FEBDA3D0B0D602A64F68134A0F";
    public static String participant = "AAE79FCC1051D8921723D66272CA49B42B044C83";
    
    /* Commands */
    public static String CmdStartNode = " start --validator-index=0";
    public static String CmdTransaction = " deploy --address " + validator + " -f test.yaml";
    
    /** Usage java ExecuteTransaction path/to/burrow_executable */
    public static void main(String[] args)
    {
        if(args.length != 3)
        {
            System.out.println("Missing parameters. java ExecuteTransaction path/to/burrow_executable validator_ID no_of_iterations");
            System.exit(0);
        }
        validator = args[1];
        CmdTransaction = args[0] + " deploy --address " + validator + " -f test.yaml";;
        System.out.println(CmdTransaction);
        int n = Integer.parseInt(args[2]);
        
        long t0 = System.currentTimeMillis();
        System.out.println("Starting at time: " + t0);
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
