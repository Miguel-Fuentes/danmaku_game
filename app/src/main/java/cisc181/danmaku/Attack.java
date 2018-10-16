package cisc181.danmaku;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import cisc181.danmaku.sequencePkg.*;

/**
 * Created by Brandon on 12/1/2017.
 */

public class Attack {
    private String name;
    private int music_id;
    private int timeLimit;
    private ArrayList<Step> sequence;

    public Attack(Scanner scanner){
        sequence = new ArrayList<>();
        try {
            scanFile(scanner);
        }
        catch (Exception e){
            sequence.clear();
            name = "none";
            music_id = 0;
            timeLimit = 10;
            sequence.add(new Yield());
            sequence.add(new GoTo(0,0));
        }
        sequence.add(new Terminate());
    }

    private void scanFile(Scanner read) throws Exception{

        readName(read);
        readMusic(read);
        readTime(read);
        read.nextLine();

        int lineNumber = 0;
        try{
            while(read.hasNextLine()){
                lineNumber++;
                processStep(read.nextLine());
            }
        }
        catch (Exception e){
            System.out.println("Error detected among lines of Danmaku Code");
            System.out.println("Please check line " + lineNumber);
            throw new Exception();
        }
    }

    private void readName(Scanner txtFile) throws Exception{
        boolean hadNextLine = txtFile.hasNextLine();
        try{
            String nameLine = txtFile.nextLine();
            int length = nameLine.length();
            if(nameLine.startsWith("NAME: ")){
                name = nameLine.substring(6,length);
            }
            else{
                throw new Exception();
            }
        }
        catch (Exception e){
            System.out.print("Name could not be found -");
            if(hadNextLine){
                System.out.println(" Please check the first line");
            }
            else{
                System.out.println(" No name found, file is empty?");
            }
            throw e;
        }
    }
    private void readMusic(Scanner txtFile) throws Exception{
        boolean hadNextLine = txtFile.hasNextLine();
        try{
            String nameLine = txtFile.nextLine();
            int length = nameLine.length();
            if(nameLine.startsWith("MUSIC: ")){
                String number = nameLine.substring(7,length);
                music_id = Integer.parseInt(number);
            }
            else{
                throw new Exception();
            }
        }
        catch (Exception e){
            System.out.print("Music ID could not be found -");
            if(hadNextLine){
                System.out.println(" Please check the second line");
            }
            else{
                System.out.println(" No name found, rest of file is empty?");
            }
            throw e;
        }

    }
    private void readTime(Scanner txtFile) throws Exception{
        boolean hadNextLine = txtFile.hasNextLine();
        try{
            String nameLine = txtFile.nextLine();
            int length = nameLine.length();
            if(nameLine.startsWith("TIME: ")){
                timeLimit = Integer.parseInt(nameLine.substring(6,length));
            }
            else{
                throw new Exception();
            }
        }
        catch (Exception e){
            System.out.print("Time Limit could not be found -");
            if(hadNextLine){
                System.out.println(" Please check the third line");
            }
            else{
                System.out.println(" No time limit found, rest of file is empty?");
            }
            throw e;
        }

    }
    private void processStep(String line) throws Exception{
        line = line.substring(4,line.length());
        if(line.startsWith("YIELD")){
            sequence.add(new Yield());
        }
        else if(line.startsWith("GOTO")){
            String[] newLine = findParameters(line).split(",");
            int n1 = Integer.parseInt(newLine[0]);
            int n2 = Integer.parseInt(newLine[1]);
            sequence.add(new GoTo(n1,n2));
        }
        else if(line.startsWith("TERMINATE")){
            sequence.add(new Terminate());
        }
        else if(line.startsWith("SPAWN_")){
            int length = line.length();
            processSpawn(line.substring(6,length));
        }
        else{
            System.out.println("Error - No such command exists for: " + line);
            throw new Exception();
        }
    }

    private void processSpawn(String spawn) throws Exception{
        double[] parameters;
        double[] p;
        try {
            parameters = processParameters(findParameters(spawn));
            p = parameters;
        }
        catch (Exception e){
            System.out.print("Was not provided with valid values in parameters");
            throw e;
        }
        if(spawn.startsWith("ARC")){
            Spawner_Arc newArc = new Spawner_Arc(p[0], p[1], p[2], (int) p[3], (int) p[4], p[5], p[6], p[7], p[8]);
            sequence.add(newArc);
        }
        if(spawn.startsWith("BOUNCECIRCLE")){
            Spawner_BounceCircle newCircle = new Spawner_BounceCircle(p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7]);
            sequence.add(newCircle);
        }
        else if(spawn.startsWith("RIVER")){
            Spawner_River newRiver = new Spawner_River(p[0],p[1],p[2],p[3],p[4],p[5],p[6]);
            sequence.add(newRiver);
        }
        else if(spawn.startsWith("SHOTGUN")){
            Spawner_Shotgun newShotgun = new Spawner_Shotgun(p[0],p[1],p[2],p[3],p[4],p[5],p[6],(int) p[7]);
            sequence.add(newShotgun);
        }
        else if(spawn.startsWith("SNIPER")){
            Spawner_Sniper newSniper = new Spawner_Sniper(p[0],p[1],p[2],p[3]);
            sequence.add(newSniper);
        }
        else if(spawn.startsWith("SPIRAL")){
            Spawner_Spiral newSpiral = new Spawner_Spiral(p[0],p[1],p[2],p[3],p[4],p[5],p[6]);
            sequence.add(newSpiral);
        }
        else if(spawn.startsWith("TRIAL")){
            Spawner_Trial newTrial = new Spawner_Trial(p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7]);
            sequence.add(newTrial);
        }
        else{
            System.out.print("Error in line: SPAWN_" + spawn + ", not a recognized command");
            throw new Exception();
        }
    }

    private String findParameters(String line) throws Exception{
        int start = 0;
        int end = 0;
        char[] convertedLine = line.toCharArray();
        int size = convertedLine.length;

        while((start < size) & convertedLine[start] != '('){
            start++;
        }
        end = start;
        while((end < size) & convertedLine[end] != ')'){
            end++;
        }
        if(end == size){
            System.out.print("Parameters not found?");
            throw new Exception();
        }
        else {
            return line.substring(start + 1, end);
        }
    }
    private double[] processParameters(String line){
        String[] splitString = line.split(",");
        int length = splitString.length;
        double[] result = new double[length];
        try {
            for (int index = 0; index < length; index++) {
                result[index] = Double.parseDouble(splitString[index]);
            }
        }
        catch (Exception e){
            System.out.print("Error in processing parameters");
            throw e;
        }
        return result;
    }

    public String getName(){
        return name;
    }
    public int getMusic_id(){
        return music_id;
    }
    public int getTimeLimit(){ return timeLimit; }
    public ArrayList<Step> getSequence(){
        ArrayList<Step> result = new ArrayList<>();
        for(Step s : sequence){
            result.add(s.copy());
        }
        return result;
    }
}
