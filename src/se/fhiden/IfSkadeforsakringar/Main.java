package se.fhiden.IfSkadeforsakringar;

import java.io.*;

public class Main {
    String information;
    File logfile, datafile;
    BufferedWriter output, log;
    BufferedReader input;
    public static void main(String[] args) {
        InputStream io = System.in;
        BufferedReader br = new BufferedReader(new InputStreamReader(io));

        Main main = new Main();

        System.out.println("Welcome!");

        boolean found = main.findFiles();
        main.createLogWriter(found);
        main.writeToLog("program starting... Ready", false);
        if (found){
            String msg = main.readFile();
            System.out.println("Your last message was the following: \n");
            System.out.println(msg+"\n");
        }
        System.out.print("Write your message: ");
        try {
            String data = br.readLine();
            System.out.println("\nLine has been read. saving.... Done");
            main.writeFile(data);

            main.log.close();
            main.output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*** Find file checks if the log file and the data file already exists.
     *  return: It will return a boolean that is true if the files already exists.
     * */
    private boolean findFiles(){
        logfile = new File("log");
        datafile = new File("rememberData");

        boolean exists = logfile.exists();
            try {
                if (!exists)
                    logfile.createNewFile();
                if (!datafile.exists())
                    datafile.createNewFile();
            } catch (IOException e){
                System.out.println("failed to create necessary files." );
            }
        return exists;
    }
    /*** Creates the bufferedWriter for the Log file so we can write to the log without overwriting the file every time.
     *  boolean exists: a boolean that tells the function if the file exists or not. if it doesnt the function creates the file before we try to access it.
     *  returns: nothing
     * */
    private void createLogWriter( boolean exists){

        logfile = new File("log");

        try {
            OutputStreamWriter writerOutputStream =
                    new OutputStreamWriter(
                            new FileOutputStream(logfile, true),
                            "UTF-8");
            if (!exists)
                logfile.createNewFile();
            log = new BufferedWriter(writerOutputStream);
            if (!exists){
                writeToLog("Executing program for the first time... creating Log... Done", false);
            }
            else {
                writeToLog("Accessing log... Done",false);
            }
        } catch (IOException e) {
            System.out.println("Failed to find and create a log file... Exiting...");
        }
    }
    /*** Reads the data file so we get the data before it gets overwritten.
     *  returns: It returns a string with the first line of the data document.
     * */
    private String readFile(){
        try {
            input = new BufferedReader(new FileReader("rememberData"));
            writeToLog("Reading remember file... Done", false);
            return input.readLine();
        } catch (IOException e) {
            writeToLog("Could not connect to file.", true);
            return "";
        }

    }

    /*** overwrites the data document with the new data we want to save.
     *  String data: the data we want to save until next run.
     * */
    private boolean writeFile(String data){
        try {
            datafile = new File("rememberData");
            output = new BufferedWriter(new FileWriter(datafile));
            output.write(data);
            output.close();
            writeToLog("Writing message to remember file... Done", false);
            return true;
        } catch (IOException e) {
            writeToLog("Failed to write to file.", true);
        return false;
        }
    }
    /*** A helper function that lets us write to the log and to the console at the same time if we want to.
     * String logData : The data we want to write to the log.
     * boolean verbose : a boolean that lets the function know if we want to write it in the console too.
     * */
    private void writeToLog(String logData, boolean verbose){
        try {
            log.append(logData+"\n");
            log.flush();
            if (verbose)
                System.out.println(logData);
        } catch (IOException e) {
            System.out.println("Could not write to log....");
        }
    }
}
