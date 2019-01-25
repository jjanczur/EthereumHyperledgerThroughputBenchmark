import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecuteTransaction
{
    public static final String validator = "4474D57D0F6C70FEBDA3D0B0D602A64F68134A0F";
    public static final String participant = "AAE79FCC1051D8921723D66272CA49B42B044C83";
    public static void main(String[] args)
    {
        /*
        long l = 564897;
        String st = ""+l;
        for(int i = st.length(); i < 9; i++)
        {
            st = "0" + st;
        }
        st = "0." + st;
        double d = Double.parseDouble(st);
        System.out.println(d);
        System.exit(0);
        */
        String CmdStartNode = "./burrow start --validator-index=0";
        String CmdTransaction = "./burrow deploy --address " + validator + " -f test.yaml";
        try
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
        catch(Exception ex)
        {
        
        }
    }
}
