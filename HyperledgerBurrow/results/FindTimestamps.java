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
            // Shouldn't use more than 3S for milliseconds. The date gets wrong otherwise
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            format.setLenient(false);
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
            System.out.println("Start");
            while(true)
            {
                idx = s.indexOf(PATTERN_ARRIVAL, idx);
                if(idx == -1)
                    break;
                int idx1 = s.indexOf(PATTERN_TIME, idx)+8;
                int idx2 = s.indexOf("\"", idx1);
                if(idx1 == -1 || idx2 == -1)
                    break;
                startTransactionTimeList.add(s.substring(idx1, idx2));
                //System.out.println(s.substring(idx1, idx2));

                idx1 = s.indexOf(PATTERN_HASH, idx)+11;
                idx2 = s.indexOf("\"", idx1);
                if(idx1 == -1 || idx2 == -1)
                    break;
                hashList.add(s.substring(idx1, idx2));
                //System.out.println(s.substring(idx1, idx2));

                idx = s.indexOf(PATTERN_EXECUTION, idx);
                idx1 = s.indexOf(PATTERN_TIME, idx)+8;
                idx2 = s.indexOf("\"", idx1);
                if(idx == -1 || idx1 == -1 || idx2 == -1)
                    break;
                endTransactionTimeList.add(s.substring(idx1, idx2));
                //System.out.println(s.substring(idx1, idx2));
                indexList.add(idx);

                //System.out.println(idx);
                idx++;
            }

            int count = 0;
            System.out.println(hashList.size() + " transactions found");
            long lastTransaction = 0;
            for (String object : hashList)
            {
                //System.out.println("For Hash '" + object + "' the execution time was:");

                int idxEnd1 = startTransactionTimeList.get(count).indexOf("T");
                int idxEnd2 = startTransactionTimeList.get(count).indexOf("Z");
                String startDateString = getTimeFromString(startTransactionTimeList.get(count), startTransactionTimeList.get(count));
                //System.out.println(startDateString);
                Date startDate = format.parse(startDateString);

                idxEnd1 = endTransactionTimeList.get(count).indexOf("T");
                idxEnd2 = endTransactionTimeList.get(count).indexOf("Z");
                String endDateString = getTimeFromString(endTransactionTimeList.get(count), endTransactionTimeList.get(count));
                //String endDateString = endTransactionTimeList.get(count).substring(0,idxEnd1) + ' ' + endTransactionTimeList.get(count).substring(idxEnd1+1,idxEnd2);
                
                System.out.println("before " + endDateString);
                Date endDate = format.parse(endDateString);
                System.out.println("after " + endDate);
                if(endDate.getTime() > lastTransaction)
                {
                    System.out.println(endDate);
                    lastTransaction = endDate.getTime();
                }
                
                if(endDate.getTime() >= startDate.getTime())
                {
                    executionTimeListInSec.add(endDate.getTime() - startDate.getTime());
                }
                else
                {
                    executionTimeListInSec.add(startDate.getTime() - endDate.getTime());
                }
                
                //get the ms
                String timeToCalc = executionTimeListInSec.get(count).toString();
                for(int i = timeToCalc.length(); i < 9; i++)
                {
                    timeToCalc = "0" + timeToCalc;
                }
                timeToCalc = "0." + timeToCalc;
                //System.out.println("ttC" + timeToCalc + ", " + startDate.getTime() + ", " + endDate.getTime());
                executionTimeListInMs.add(Double.valueOf(timeToCalc) * 1000);
                //System.out.println(executionTimeListInMs.get(count) + "ms");
                System.out.println(executionTimeListInMs.get(count));

                count++;
            }
            // Print time of latest transaction
            System.out.println("t_last = " + lastTransaction);
        }       
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static String getTimeFromString(String start, String end)
    {
        int idxEnd1 = start.indexOf("T");
        int idxEnd2 = end.indexOf(".");
        String first = start.substring(0,idxEnd1);
        String second = end.substring(idxEnd1+1,idxEnd2+3);
        //second += "Z";
        //System.out.println(first + " " + second);
        return first + ' ' + second;
    }
}
