import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");
            File file = new File(args[0]);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String s = new String(fileContent);
            ArrayList<Integer> indexList = new ArrayList<Integer>();
            ArrayList<String> startTransactionTimeList = new ArrayList<String>();
            ArrayList<String> endTransactionTimeList = new ArrayList<String>();
            ArrayList<String> hashList = new ArrayList<String>();
            //Stores the time in secounds, but its the digits after the point with length of nine
            ArrayList<Long> executionTimeListInSec = new ArrayList<Long>();
            //Stores the time in milisecounds
            ArrayList<Double> executionTimeListInMs = new ArrayList<Double>();
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
            for (String object : hashList)
            {
                System.out.println("For Hash '" + object + "' the execution time was:");

                int idxEnd1 = startTransactionTimeList.get(count).indexOf("T");
                int idxEnd2 = startTransactionTimeList.get(count).indexOf("Z");
                String startDateString = getTimeFromString(startTransactionTimeList.get(count), startTransactionTimeList.get(count));
                //System.out.println(startDateString);
                Date startDate = format.parse(startDateString);

                idxEnd1 = endTransactionTimeList.get(count).indexOf("T");
                idxEnd2 = endTransactionTimeList.get(count).indexOf("Z");
                String endDateString = getTimeFromString(endTransactionTimeList.get(count), endTransactionTimeList.get(count));
                //String endDateString = endTransactionTimeList.get(count).substring(0,idxEnd1) + ' ' + endTransactionTimeList.get(count).substring(idxEnd1+1,idxEnd2);
                
                //System.out.println(endDateString);
                Date endDate = format.parse(endDateString);
                executionTimeListInSec.add(endDate.getTime() - startDate.getTime());
                
                //get the ms
                String timeToCalc = executionTimeListInSec.get(count).toString();
                for(int i = timeToCalc.length(); i < 9; i++)
                {
                    timeToCalc = "0" + timeToCalc;
                }
                timeToCalc = "0." + timeToCalc;

                executionTimeListInMs.add(Double.valueOf(timeToCalc) * 1000);
                System.out.println(executionTimeListInMs.get(count) + "ms");

                count++;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static String getTimeFromString(String start, String end)
    {
        int idxEnd1 = start.indexOf("T");
        int idxEnd2 = end.indexOf("Z");
        String first = start.substring(0,idxEnd1);
        String second = end.substring(idxEnd1+1,idxEnd2);
        for(int i = second.length(); i < 18; i++)
        {
            second += "0";
        }
        second += "Z";
        return first + ' ' + second;
    }
}
