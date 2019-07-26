/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm_3166_server;

//import java packages
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.InputMismatchException;

/**
 *MAIN CLIENT METHOD START SERVER BEFORE STARTING CLIENT
 * @author Eli Solomon
 */
public class Midterm_3166_Client {

    //create a static socket called client
    static Socket client;

    public static void main(String[] args) {

        //create a new scanner object
        Scanner input = new Scanner(System.in);

        //try catch block for the socket
        try {
            // TODO code application logic here

            //declare the socket to connect to the specified port
            client = new Socket("localhost", 9899);
            System.out.println("Connection Created");
            //create a temp int to send amounts to the server to update your balance
            int tempNum = 0;
            //iny call choice to store input from the keyboard and transmit the users selection to the server
            int choice = 0;
            //boolean for input validation loops
            boolean validInput;

            //create a new Input and Output stream and assign it to retrieve from the client socket
            DataInputStream reader = new DataInputStream(client.getInputStream());
            DataOutputStream writer = new DataOutputStream(client.getOutputStream());
            System.out.println("CONNECTED TO SERVER");

            //inital do-while loop to dispay menu and following logic if input is valid
            do {

                //display menu
                System.out.println("Please choose from the following options:");
                System.out.println("1: CHECK BALANCE");
                System.out.println("2: DEPOSIT FUNDS");
                System.out.println("3: WITHDRAW FUNDS");
                System.out.println("4: EXIT");

                //INPUT VALIDATION HERE choice must be int, keep prompting for input until input is valid
                do {
                    //try block for valid input 
                    try {
                        System.out.println("Enter your selection: ");//prompt user for input
                        choice = input.nextInt();                    //re-assign choice to be input from the keyboard
                        validInput = true;                           //VALID INPUT IS NOW TRUE HURRAY
                        //catch block if input is not an integer
                    } catch (InputMismatchException e) {
                        System.out.println(e);                          //display auto generated error msg
                        System.out.println("Must enter an number 1-4!");//display custom error msg
                        input.next();                                   // discard the bad input
                        validInput = false;                             // :( INVALID INPUT STILL
                    }//end catch block for input validation
                } while (!validInput);                                    //keep on prompting for input

                //extra error msg if they DID accually pick and integer but the wrong int
                if (choice < 1 && choice > 4) {
                    //this program done only has 4 options, so tell the user
                    System.out.println("PLEASE CHOOSE AN OPTION 1-4");
                }//end extra error msg

                //begin option 1 display balance
                if (choice == 1) {

                    //is it beter to be less dynamic but hard code the int sent to the server as 1?
                    writer.writeInt(choice); //write the choice to the writer to send to the server

                    //read the balance sent from the server and display it
                    System.out.println("Your current balance is $" + reader.readInt());

                }//end if statement for choice==1
                //begin option 2 deposit funds
                else if (choice == 2) {
                    //send that users selection to the server
                    writer.writeInt(choice);
                    //make sure valid input is false for the upcoming input validation
                    validInput = false;

                    //INPUT VALIDATION
                    do {
                        //try block for valid input 
                        try {
                            System.out.println("Enter the amount of funds for deposit:");
                            System.out.println("$");
                            tempNum = input.nextInt();                //re-assign choice to be input from the keyboard
                            if (tempNum >= 0) {                           //if statement to make sure input>=0
                                validInput = true;                       //VALID INPUT IS NOW TRUE HURRAY
                            } else if (tempNum < 0) {                       //else input<0
                                System.out.println("AMOUNT CAN NOT BE NEGATIVE");
                                validInput = false;                       //input is still invalid then
                            }
                            //catch block if input is not an integer
                        } catch (InputMismatchException e) {
                            System.out.println(e);                          //display auto generated error msg
                            System.out.println("Must enter an valid amout");//display custom error msg
                            input.next();                                   // discard the bad input
                            validInput = false;                             // :( INVALID INPUT STILL
                        }//end catch block for input validation
                    } while (!validInput);                                    //keep on prompting for input

                    //input is now valid send the amount to the server to update the balance
                    writer.writeInt(tempNum);
                }//end else if statement for option 2
                //else if option 3
                else if (choice == 3) {
                    //send the server the users choice
                    writer.writeInt(choice);
                    //create a temp int and get the current balance from the server and assign it to that
                    int currentBalance = reader.readInt();

                    //valid input is false for upcomming validation
                    validInput = false;
                    //INPUT VALIDATION
                    do {
                        //try block for valid input 
                        try {
                            //promt user for input
                            System.out.println("Enter the amount of funds for withdraw:");
                            System.out.println("$");

                            tempNum = input.nextInt();                //re-assign choice to be input from the keyboard
                            if (tempNum >= 0) {                           //if statement to make sure input>=0
                                validInput = true;                       //VALID INPUT IS NOW TRUE HURRAY
                            } else if (tempNum < 0) {                       //else input<0
                                System.out.println("AMOUNT CAN NOT BE NEGATIVE");
                                validInput = false;                       //input is still invalid then
                            }
                            //catch block if input is not an integer
                        } catch (InputMismatchException e) {
                            System.out.println(e);                          //display auto generated error msg
                            System.out.println("Must enter an valid amout");//display custom error msg
                            input.next();                                   // discard the bad input
                            validInput = false;                             // :( INVALID INPUT STILL
                        }//end catch block for input validation
                    } while (!validInput);                                    //keep on prompting for input
                    //while loop to make sure the user has enough funds
                    while (currentBalance < tempNum) {
                        //prompt user for input
                        System.out.println("YOU DO NOT HAVE ENOUGH FUNDS AVALIBLE FOR THIS TRANSACTION");
                        System.out.println("YOU CURRENT BALANCE AVALIBLE FOR WITHDRAW IS $" + currentBalance);
                        validInput = false;
                        //do while loop to check for valid input
                        do {
                            try {
                                System.out.println("Enter the amount of funds for withdraw:");
                                tempNum = input.nextInt();
                                if (tempNum >= 0) {                           //if statement to make sure input>=0
                                    validInput = true;                       //VALID INPUT IS NOW TRUE HURRAY
                                } else if (tempNum < 0) {                       //else input<0
                                    System.out.println("AMOUNT CAN NOT BE NEGATIVE");
                                    validInput = false;                       //input is still invalid then
                                }

                            } catch (InputMismatchException e) {
                                System.out.println(e);                          //display auto generated error msg
                                System.out.println("Must enter an valid amout");//display custom error msg
                                input.next();                                   // discard the bad input
                                validInput = false;
                            }// end catch block
                        } while (!validInput);
                    }//end while loop to make sure the user has funds to cover the transaction

                    //input is finally validated send the amout to the server to update the balance
                    writer.writeInt(tempNum);

                }//end else if for option 3
                //end loop through menu once the user picks option 4
            } while (choice != 4);
            //the user has chosen option 4 (exit program) send it to the server
            writer.writeInt(4);
            //close the client socket
            client.close();

            //close reader and writer    
            reader.close();
            writer.close();

            //exit
            System.exit(0);
            
        }//end try block for the socket errors
        catch (IOException ex) {
            
            //display error
            System.out.println(ex);
        }//end catch error msg

    }//end main

}//end class
