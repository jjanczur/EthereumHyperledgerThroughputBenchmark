import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class FindTimestamps
{
    public static String PATTERN_ARRIVAL = "\"CheckTx\",";
    public static String PATTERN_EXECUTION = "TxExecution in";
    public static String PATTERN_TIME = "\"time\":\"";
    public static String PATTERN_HASH = "\"tx_hash\":";
    
    /**
     *  usage: javac FindTimestamps.java && java FindTimestamps out.json
     *
    **/
    public static void main(String[] args)
    {
        if(args.length != 1)
        {
            System.out.println("Missing parameters. java FindTimestamps filename.json");
        }
        try
        {
            File file = new File(args[0]);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String s = new String(fileContent);
            ArrayList<Integer> indexList = new ArrayList<Integer>();
            int idx = 0;
            while(true)
            {
                idx = s.indexOf(PATTERN_EXECUTION, idx);
                if(idx == -1)
                    break;
                int idx1 = s.indexOf(PATTERN_TIME, idx)+8;
                int idx2 = s.indexOf("\"", idx1);
                indexList.add(idx);
                System.out.println(s.substring(idx1, idx2));
                //System.out.println(idx);
                idx++;
            }
        }
        catch(Exception ex)
        {
            
        }
    }
}
