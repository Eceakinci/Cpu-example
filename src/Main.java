import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author EceAkinci
 * Date: 29/06/2020
 *
 * */

public class Main {
    //a is the third value of each line
    private static int a;
    private static String methodName;
    private static int programCounter;

    private static int accumulator = 0;
    //ax is here
    private static int ax = 155;
    private static int [] memory = new int[256];
    private static int flag;
    //hold, holds the instructions in text file
    //hold2, holds the numbers and memory locations
    //i choose to store elements in arraylist, because number of instructions may change
    private static ArrayList<String> hold = new ArrayList();
    private static ArrayList<Integer> hold2 = new ArrayList<>();

    private static Stack<Integer> stack = new Stack<>();

    public static void main(String[] args) throws IOException {
        //For putting input file from console

        Scanner scan = new Scanner(new File(args[0]));

        String data;

        while (scan.hasNextLine()) {
            data = scan.nextLine();
            String[] token = data.split(" ");

            programCounter = Integer.parseInt(token[0]);
            methodName = token[1];
            if (token.length == 2) {
                token = new String[3];
                token[2] = String.valueOf(0);
            }
            a = Integer.parseInt(token[2]);

            //when lines splitted by space, seperate them and hold them in arrays
            //programcounter and line numbers of text file work here in same meaning
            //hold[],holds method names(START, LOAD, JMP etc)
            //hold2[], holds third value of each line in text file
            hold.add(programCounter, methodName);
            hold2.add(programCounter, a);
        }
        scan.close();
        //checks first element of text file, if it is "START" instruction starts
        programCounter = 0;
        if (hold.get(0).equals("START")) {
            programCounter = 1;
            //firstly take third value in text file, then call "methods" method and go that method
            while (programCounter < hold.size() - 1) {
                a = hold2.get(programCounter);
                methods(hold.get(programCounter));
            }
        } else {
            System.out.println("Program always start with START");
        }

    }


    public static void methods(String methodName) throws IOException {
        switch (methodName){
            case "LOAD" : LOAD(a);
                break;
            case "STORE" : STORE(a);
                break;
            case "CMPM" : CMPM(a);
                break;
            case "CJMP" : CJMP(a);
                break;
            case "LOADM" : LOADM(a);
                break;
            case "ADDM" : ADDM(a);
                break;
            case "JMP" : JMP(a);
                break;
            case "ADD": ADD(a);
                break;
            case "SUBM" : SUBM(a);
                break;
            case "SUB": SUB(a);
                break;
            case "MUL": MUL(a);
                break;
            case "MULM": MULM(a);
                break;
            case "DISP": DISP();
                break;
            case "HALT": HALT();
                break;
            case "DASC" : DASC(accumulator);
                break;
            case "LOADI" : LOADI();
                break;
            case "STOREI" : STOREI();
                break;
            case "SWAP" : SWAP();
                break;
            case "PUSH":PUSH(a);
                break;
            case "POP" : POP();
                break;
            case "RETURN" :RETURN();
                break;
        }
    }


    public static void LOAD(int x){
        accumulator = x;
        programCounter++;
    }
    //x will be ax value
    public static void LOADI(){
        accumulator = memory[ax];
        programCounter++;
    }

    public static void LOADM(int x){
        accumulator = memory[x];
        programCounter++;
    }
    public static void STOREI(){
        memory[ax] = accumulator;
        programCounter++;
    }

    //For swap operation i could implement it with 3 line like
    //int temp = accumulator;
    //accumulator = ax;
    //ax = temp;
    //but i choose bitwise xor
    public static void SWAP(){
        int temp = accumulator;
        accumulator = ax;
        ax = temp;
        programCounter++;
    }
    public static void STORE(int x){
        memory[x] = accumulator;
        programCounter++;
    }

    public static void CMPM(int x){
        if(accumulator > memory[x]){
            flag = 1;
            programCounter++;
        }
        if(accumulator < memory[x]){
            flag = -1;
            programCounter++;
        }if(accumulator == memory[x]) {
            flag = 0;
            programCounter++;
        }
    }
    //there is no need for loop as JMP instruction
    //so check flag value and if flag greater than 0, change program counter value as CJMP instruction's value(in example its 15)
    //and by default go that line-go that program counter value
    public static void CJMP(int x) throws IOException {
        if(flag > 0){
            programCounter = x;
            a = hold2.get(programCounter);
            methods(hold.get(programCounter));

        }else{
            programCounter++;
        }
    }
    //just change program counter as JMP instructon's value
    //and go that line with main method's second while statement
    public static void JMP(int x){

        programCounter = x;
    }
    public static void ADD(int x){
        accumulator = accumulator +x;
        programCounter++;
    }

    public static void ADDM(int x){
        accumulator = accumulator + memory[x];
        programCounter++;
    }
    public static void DISP(){
        programCounter++;
        System.out.print(accumulator + " ");

    }
    public static void SUBM(int x){
        accumulator = accumulator - memory[x];
        programCounter++;
    }
    public static void SUB(int x){
        accumulator = accumulator - x;
        programCounter++;
    }
    public  static void MUL(int x){
        accumulator = accumulator *x;
        programCounter++;
    }
    public static void MULM(int x){
        accumulator = accumulator * memory[x];
        programCounter++;
    }
    public static void HALT(){
        System.exit(0);
    }

    //ascii number to ascii char
    //void may change, sout may change by return type
    public static void DASC(int x){
        String b = Character.toString((char) x);
        System.out.println(b);
    }
    public static void PUSH(int x){
        stack.push(x);
        programCounter++;
    }
    public static void POP(){
        accumulator = stack.pop();
        programCounter++;
    }
    public static void RETURN(){
        memory[255] = accumulator;
        programCounter = stack.pop();
    }
}