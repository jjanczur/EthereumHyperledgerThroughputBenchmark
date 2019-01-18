import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class FindTimestamps
{
    public static String PATTERN_ARRIVAL = "\"CheckTxSync\",";
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
            ArrayList<String> startTransactionTimeList = new ArrayList<String>();
            ArrayList<String> endTransactionTimeList = new ArrayList<String>();
            ArrayList<String> hashList = new ArrayList<String>();
            ArrayList<Integer> executionTimeList = new ArrayList<Integer>();
            int idx = 0;
            while(true)
            {
                idx = s.indexOf(PATTERN_ARRIVAL, idx);
                if(idx == -1)
                    break;
                int idx1 = s.indexOf(PATTERN_TIME, idx)+8;
                int idx2 = s.indexOf("\"", idx1);
                startTransactionTimeList.add(s.substring(idx1, idx2));
                //System.out.println(s.substring(idx1, idx2));

                idx1 = s.indexOf(PATTERN_HASH, idx)+11;
                idx2 = s.indexOf("\"", idx1);
                hashList.add(s.substring(idx1, idx2));
                //System.out.println(s.substring(idx1, idx2));

                idx = s.indexOf(PATTERN_EXECUTION, idx);
                idx1 = s.indexOf(PATTERN_TIME, idx)+8;
                idx2 = s.indexOf("\"", idx1);
                endTransactionTimeList.add(s.substring(idx1, idx2));
                //System.out.println(s.substring(idx1, idx2));
                indexList.add(idx);

                //System.out.println(idx);
                idx++;
            }

            int count = 0;
            for (String object : hashList) {
              System.out.println("For Hash '" + object + "' the execution time was:");
              int minStart = Integer.parseInt(startTransactionTimeList.get(count).substring(14,16));
              int secStart = Integer.parseInt(startTransactionTimeList.get(count).substring(17,19));
              int msecStart = Integer.parseInt(startTransactionTimeList.get(count).substring(20,29));

              int minEnd = Integer.parseInt(endTransactionTimeList.get(count).substring(14,16));
              if (minEnd == 0) //if there is a timejump from 59 to 00 - i consider that the ececution time will always be less than one minute!
                minEnd=60;
              int secEnd = Integer.parseInt(endTransactionTimeList.get(count).substring(17,19));
              if (secEnd == 60) //if there is a timejump from 59 to 20
                secStart = secStart - 60;
              int msecEnd = Integer.parseInt(endTransactionTimeList.get(count).substring(20,29));

              executionTimeList.add(60000*(minEnd-minStart)+1000*(secEnd-secStart)+msecEnd-msecStart);
              System.out.println(executionTimeList.get(count) + "ms");

              count++;
            }
        }
        catch(Exception ex)
        {

        }
    }
}
