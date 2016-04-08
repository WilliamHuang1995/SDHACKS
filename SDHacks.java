import java.io.*;
import java.util.*;
 
public class SDHacks {
    public int[] days;
    public ArrayList<CollegeClass> class1, class2, class3, class4, class5;
    public ArrayList<ArrayList<CollegeClass>> allClasses;
    Scanner in;
 
    public SDHacks() {
 
        allClasses = new ArrayList<ArrayList<CollegeClass>>();
        days = new int[5];
 
        // schedules
        //person1 = new ArrayList<CollegeClass>();
        //person2 = new ArrayList<CollegeClass();
 
 
        class1 = new ArrayList<CollegeClass>();
        class2 = new ArrayList<CollegeClass>();
        class3 = new ArrayList<CollegeClass>();
        class4 = new ArrayList<CollegeClass>();
        class5 = new ArrayList<CollegeClass>();
 
        ArrayList<String> classNames = getInput(); // gets user input
        // put all possible blocks into class list
 
 
        readFile("C:\\Users\\Joanne\\IdeaProjects\\hackathonPlanner\\src\\mockFiles", classNames.get(0), class1);
        readFile("C:\\Users\\Joanne\\IdeaProjects\\hackathonPlanner\\src\\mockFiles", classNames.get(1), class2);
        readFile("C:\\Users\\Joanne\\IdeaProjects\\hackathonPlanner\\src\\mockFiles", classNames.get(2), class3);
        //readFile("C:\\Users\\Joanne\\IdeaProjects\\hackathonPlanner\\src\\mockFiles", classNames.get(3), class4);
        //readFile("C:\\Users\\Joanne\\IdeaProjects\\hackathonPlanner\\src\\mockFiles", classNames.get(4), class5);
        allClasses.add(class1);
        allClasses.add(class2);
        allClasses.add(class3);
        allClasses.add(class4);
        allClasses.add(class5);
        //System.out.println(allClasses.size());
    }
 
    public static void main(String[] args) {
        SDHacks tester = new SDHacks();
        ArrayList<Schedule> allPotentialSchedules = tester.makeAllCombinations();
 
        int size = allPotentialSchedules.size();
        for (int i = 0; i < size; i++) {
            if (allPotentialSchedules.get(i).hasAnyOverlaps()) {
                System.out.println("I AM REMOVING");
                allPotentialSchedules.remove(i);
                i--;
                size--;
            }
        }
 
        for (int i = 0; i < allPotentialSchedules.size(); i++) {
            allPotentialSchedules.get(i).printSchedule();
        }
        if (allPotentialSchedules.isEmpty()) {
            System.out.println("SORRY NO SCHEDULE FOR YAH");
        }
 
    }
 
    public ArrayList<CollegeClass> readFile(String fileName, String className, ArrayList<CollegeClass> list) {
        try {
            in = new Scanner(new File(fileName));
            while (in.hasNextLine()) {
                in.nextLine();
                if (in.hasNext() && in.next().equals(className)) {
                    // read line for input
                    //String classname = in.next();
                    String lecDays = in.next();
                    String professor = in.next();
                    while (!(in.hasNextInt())) {
                        professor += " " + in.next();
                    }
                    int lecStartTime = in.nextInt();
                    int lecEndTime = in.nextInt();
                    String lecLocation = in.next();
                    String disDays = in.next();
                    int disStartTime = in.nextInt();
                    int disEndTime = in.nextInt();
                    String disLocation = in.next();
                    CollegeClass temp = new CollegeClass(className, professor, lecDays, lecStartTime, lecEndTime, lecLocation, disDays, disStartTime, disEndTime, disLocation);
                    //System.out.println(temp.printClass());
                    list.add(temp);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error :" + e.getMessage());
        }
        return list;
    }
 
    /**
     * Allows user to input the name of classes to take; user should input names in alphabetical order.
     *
     * @return ArrayList containing names of classes to take
     */
    public ArrayList<String> getInput() {
        ArrayList<String> listClasses = new ArrayList<String>(3);
        Scanner userinput = new Scanner(System.in);
        System.out.println("Hello and welcome to UCSD Class Planner v1!");
        System.out.println("This is a program designed to create a potential" +
                " Schedule based on your personal preference and" +
                " the classes you choose!");
        System.out.println("How many classes would you like to take for this" +
                " quarter? (Note: you have to take at least 3)");
        if (userinput.hasNext()) {
            String input = userinput.next();
            boolean notInteger = true;
            int numClasses = 0;
            while (notInteger) {
                try {
                    numClasses = Integer.parseInt(input);
                    notInteger = false;
                } catch (NumberFormatException e) {
                    System.out.println("You need to enter a number. Try again.");
                    input = userinput.next();
                }
            }
            notInteger = true;
            do {
                while (numClasses < 3) {
                    System.out.println("Sorry, you have to take at least 3" +
                            " classes to be considered as a full" +
                            " time college student.");
                    System.out.println("Please enter a valid number:");
                    input = userinput.next();
                    while (notInteger) {
                        try {
                            numClasses = Integer.parseInt(input);
                            notInteger = false;
                        } catch (NumberFormatException e) {
                            System.out.println("You need to enter a number. Try again.");
                            input = userinput.next();
                        }
                    }
                    notInteger = true;
                }
                System.out.println("You have decided to take " + numClasses +
                        " classes this quarter, is this correct?" +
                        " Y / N");
                input = userinput.next();
                if (input.equalsIgnoreCase("N")) {
                    System.out.println("OK, how many classes would you like to" +
                            " take instead?");
                    input = userinput.next();
                    while (notInteger) {
                        try {
                            numClasses = Integer.parseInt(input);
                            notInteger = false;
                        } catch (NumberFormatException e) {
                            System.out.println("You need to enter a number. Try again.");
                            input = userinput.next();
                        }
                    }
                    numClasses = Integer.parseInt(input);
                }
            } while (!input.equalsIgnoreCase("Y"));
            //only exits while loop when you enter Y
 
            System.out.println("Please enter M if you prefer morning classes," +
                    " A if you prefer afternoon classes, " +
                    "or DM if you don't mind. Case insensitive");
 
            input = userinput.next();
            while (!((input.equalsIgnoreCase("M") || input.equalsIgnoreCase("A")
                    || input.equalsIgnoreCase("DM")))) {
                System.out.println("You did not enter a correct code, " +
                        "please try again");
                input = userinput.next();
            }
            String userPreference = input;
            System.out.println("OK, your preference has been saved");
            System.out.println("Now enter the " + numClasses + " classes " +
                    "that you would like to take next quarter");
            System.out.println("For example:\nCSE20\nHIEA131\nMATH109 ");
            System.out.println("Note that there is no space between class name and number");
            int i = numClasses; //initialize an index
            //initialize String array that will store each class
            listClasses = new ArrayList<String>(numClasses);
            while (numClasses > 0) {
                input = userinput.next();
                System.out.println("Checking if " + input.toUpperCase() + " is available next quarter...");
                boolean found = false;
                try {
                    found = find(input);
                } catch (FileNotFoundException e) {
                }
                if (!checkMatch(input, listClasses)) {
                    if (found) { //if we find the input
                        System.out.println("Success, the course is offered next quarter! ");
                        System.out.println("Your class preference has been updated.");
                        listClasses.add(input); //store the class in the arraylist
                        numClasses--;
                    } else {//if we don't find it
                        System.out.println("Oops, the course does not exist/is not offered" +
                                " next quarter! Your class preference has not" +
                                " not been changed.");
                    }
                } else {
                    System.out.println("You've already saved that class, please input another one: ");
                }
 
                if (numClasses > 0) {
                    System.out.println("Please enter " + numClasses + " more classes that you " +
                            "would like to take next quarter\n");
                }
            }
 
            //at this point you have recorded all the classes that the user wants to take
            System.out.println("The classes that you want to take are the following: ");
 
            for(int j=0; j<i-1;j++){
                System.out.print(listClasses.get(j)+", ");
            }
            System.out.print(listClasses.get(i - 1).toUpperCase());
            System.out.println();
 
            System.out.println("Generating potential schedule for given list of classes...");
 
        }
        return listClasses;
        }
    private static boolean find (String classname)throws FileNotFoundException {
 
        Scanner scanner = new Scanner(new File("C:\\Users\\Joanne\\IdeaProjects\\hackathonPlanner\\src\\mockFiles"));
        while (scanner.hasNextLine()) {
            String list = scanner.next();
            if (list.equalsIgnoreCase(classname)) {
                return true;
            }
        }
        return false;
    }
 
    private static boolean checkMatch ( String input, ArrayList<String> list ) {
 
        for (int i = 0; i < list.size(); ++i) {
 
            if (input.equalsIgnoreCase(list.get(i))) {
                return true;
            }
        }
        return false;
    }
 
    public ArrayList<Schedule> makeAllCombinations(){
        int size = 0;
        for(int i = 0; i < allClasses.size(); i++){
            //System.out.println(allClasses.get(i).size());
            if(allClasses.get(i).size() != 0){
                size++;
            }
        }
 
        //System.out.println("NUM CLASSES IS " + size);
 
        ArrayList<Schedule> allCombinations = new ArrayList<Schedule>();
        int total = 0;
 
        if (size == 3){
            for (int i = 0; i < allClasses.get(0).size(); i++){
                for (int j = 0; j < allClasses.get(1).size(); j++){
                    for (int k = 0; k < allClasses.get(2).size(); k++){
                        Schedule newSched = new Schedule(3);
                        newSched.add(class1.get(i));
                        newSched.add(class2.get(j));
                        newSched.add(class3.get(k));
                        allCombinations.add(newSched);
                    }
                }
            }
 
 
        }else if (size == 4){
            for (int i = 0; i < allClasses.get(0).size(); i++){
                for (int j = 0; j < allClasses.get(1).size(); j++){
                    for (int k = 0; k < allClasses.get(2).size(); k++){
                        for(int l = 0; l < allClasses.get(3).size(); l++){
                            Schedule newSched = new Schedule(4);
                            newSched.add(class1.get(i));
                            newSched.add(class2.get(j));
                            newSched.add(class3.get(k));
                            newSched.add(class3.get(l));
                            allCombinations.add(newSched);
                        }
                    }
                }
            }
 
        }else if (size == 5){
            for (int i = 0; i < allClasses.get(0).size(); i++){
                for (int j = 0; j < allClasses.get(1).size(); j++){
                    for (int k = 0; k < allClasses.get(2).size(); k++){
                        for(int l = 0; l < allClasses.get(3).size(); l++){
                            for(int m = 0; m <allClasses.get(4).size(); m++){
                                Schedule newSched = new Schedule(5);
                                newSched.add(class1.get(i));
                                newSched.add(class2.get(j));
                                newSched.add(class3.get(k));
                                newSched.add(class3.get(l));
                                newSched.add(class3.get(m));
                                allCombinations.add(newSched);
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(allCombinations.size());
        return allCombinations;
    }
}
 
 
class CollegeClass{
    String course, professor, lecLoc, disLoc, lecDay, disDay;
    int lecStartTime, lecEndTime, disStartTime, disEndTime;
 
 
    public CollegeClass(String course, String professor, String lecDay, int lecStart, int lecEnd, String lecLoc, String disDay, int disStart, int disEnd, String disLoc){
 
        this.course = course;
        this.professor = professor;
        this.lecDay = lecDay;
        this.disDay = disDay;
        lecStartTime = lecStart;
        lecEndTime = lecEnd;
        this.lecLoc = lecLoc;
        disStartTime = disStart;
        disEndTime = disEnd;
        this.disLoc = disLoc;
    }
 
    public String printClass(){
        String classDesc = course + " " + professor + " " + lecDay
                + " " + lecStartTime + " " + lecEndTime
                + " " + lecLoc + disDay +
                " " + disStartTime + " " + disEndTime + " "
                + disLoc;
        return classDesc;
    }
 
    public String getCourse(){
        return course;
    }
 
    public String getProfessor(){
        return professor;
    }
 
    public String getLecDay(){
        if (lecDay.equals("MWF")){
            return "Monday Wednesday Friday";
        }
        else if (lecDay.equals("MW")){
            return "Monday Wednesday";
        }
        else if (lecDay.equals("TuTh")){
            return "Tuesday Thursday";
        }
        else{
            return lecDay;
        }
    }
 
    public String getDisDay(){
        if (disDay.equals("MWF")){
            return "Monday Wednesday Friday";
        }
        else if (disDay.equals("MW")){
            return "Monday Wednesday";
        }
        else if (disDay.equals("TuTh")){
            return "Tuesday Thursday";
        }
        else{
            return disDay;
        }
    }
    public int getLecStart(){
        return lecStartTime;
    }
 
    public int getLecEnd(){
        return lecEndTime;
    }
 
    public int getDisStart(){
        return disStartTime;
    }
 
    public int getDisEnd(){
        return disEndTime;
    }
 
/*
    public String getLocation(){
        return location;
    }
    */
 
   
/*
    public void print(){
        System.out.print("Course: "+getCourse()+"Professor: "+ getProfessor());
        System.out.print("Location: "+ getLocation()+"\n");
        System.out.print("Classes occur on: "+getDay());
        System.out.print("Class starts at: "+getStart()+"Ends at: "+ getEnd());
        System.out.print("Duration: "+getElapsedTime());
    }
    */
 
    // other should be M, T, W, Th, or F
    public boolean lecEquals(String other){
        if (getLecDay().indexOf(other) >= 0){
            return true;
        }
        return false;
    }
 
    public boolean disEquals(String other){
        if (getDisDay().indexOf(other) >= 0){
            return true;
        }
        return false;
    }
}
 
class Schedule{
    ArrayList<CollegeClass> allClasses = new ArrayList<CollegeClass>();
    int numClasses = 0;
 
    public void printSchedule(){
        for(int i = 0; i < allClasses.size(); i++){
            System.out.println(allClasses.get(i).printClass());
        }
        System.out.println(" ");
    }
    public Schedule(int numClasses){
        this.numClasses = numClasses;
    }
 
    public void add(CollegeClass className){
        allClasses.add(className);
    }
 
    public int getNumClasses(){
        return numClasses;
    }
 
    public boolean overLapClassTime(int start1, int end1, int start2, int end2){
        if (start2 < start1 && end2 < start1 || start2 > end1 && end2 > end1){
            return false;
        }
        System.out.println("OVERLAPPING");
        return true;
    }
    public boolean Monday(){
        boolean isDay = this.hasOverlaps("M");
        return isDay;
    }
    public boolean Tuesday(){
        boolean isDay = this.hasOverlaps("Tu");
        return isDay;
    }
    public boolean Wednesday(){
        boolean isDay = this.hasOverlaps("W");
        return isDay;
    }
    public boolean Thursday(){
        boolean isDay = this.hasOverlaps("Th");
        return isDay;
    }
    public boolean Friday(){
        boolean isDay = this.hasOverlaps("F");
        return isDay;
    }
 
    public boolean hasAnyOverlaps(){
        return Monday() || Tuesday() || Wednesday() || Thursday() || Friday();
    }
 
    public boolean hasOverlaps(String day){
        int start1 = 0;
        int end1 = 0;
        int start2 = 0;
        int end2 = 0;
        for (int i = 0; i < allClasses.size(); i++){
            for (int j = i + 1; j < allClasses.size(); j++){
                boolean sameDay = true;
                //int start1, end1, start2, end2;
                CollegeClass temp1 = allClasses.get(i);
                CollegeClass temp2 = allClasses.get(j);
                if (temp1.lecEquals(day)){
                    start1 = temp1.getLecStart();
                    end1 = temp1.getLecEnd();
                }else if(temp1.disEquals(day)){
                    start1 = temp1.getDisStart();
                    end1 = temp1.getDisEnd();
                }else{
                    sameDay = false;
                }
 
                if (temp2.lecEquals(day)){
                    start2 = temp2.getLecStart();
                    end2 = temp2.getLecEnd();
                }else if(temp2.disEquals(day)){
                    start2 = temp2.getDisStart();
                    end2 = temp2.getDisEnd();
                }else{
                    sameDay = false;
                }
                if (sameDay && overLapClassTime(start1, end1, start2, end2)){
                    System.out.println("CONFLICT" + " " + start1 + " " +   end1);
                    return true;
                }
            }
        }
        return false;
    }
}
