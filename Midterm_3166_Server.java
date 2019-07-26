/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm_3166_server;

//import java packages
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;

/**
 *
 * @author Eli Solomon
 */
public class Midterm_3166_Server {

    //create a static server socket and a static socket
    static ServerSocket server;
    static Socket client;

    /**
     * MAIN SERVER METHOD START SERVER FIRST BEFORE STARTING CLIENT
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //try block to catch socketing errors
        try {
            //display message on server that it has started
            System.out.println("SERVER IS UP AND RUNNING");
            //int to store clients choice and the balance on the account and the amount for deposit or withdraw
            int clientChoice;
            int balance = 0;
            int amount;
            //create a new server socket on the specified port
            server = new ServerSocket(9899);
            //display msg on server letting the server admin know socket was created
            System.out.println("NEW SOCKET CREATED");

            //accept the connection from the client
            client = server.accept();
            //display msg on server letting the adim know the connection was accepted
            System.out.println("CLIENT CONNECTED TO SERVER");

            //create a new Input and Output stream and assign it to retrieve from the client socket
            DataInputStream reader = new DataInputStream(client.getInputStream());
            DataOutputStream writer = new DataOutputStream(client.getOutputStream());

            //do while loop for calcuation logic
            do {
                //get the clients choice from the client
                clientChoice = reader.readInt();

                //if client chooses option one display balance
                if (clientChoice == 1) {
                    //send the balance from the server to be displayed on the client
                    writer.writeInt(balance);

                }//end if for option 1 display balance
                //else if option 2 deposit
                else if (clientChoice == 2) {
                    //get the amout the user want to deposit 
                    amount = reader.readInt();
                    //add the amount to the balance and update it
                    balance += amount;

                }//end else if option 2 deposit
                //else if for option 3 withdraw
                else if (clientChoice == 3) {
                    //send the current balance to the client to make sure the client has enough funds
                    writer.writeInt(balance);
                    //after input is validated on the clients side get the amount for withdraw and asign it to the amount int
                    amount = reader.readInt();
                    //update the balance by the amount 
                    balance -= amount;

                }//end else if logic for option 3 withdraw

                //end loop when client sends option 4 exit
            } while (clientChoice != 4);

            //close the client and server connection and display message to server admin
            client.close();
            System.out.println("CLIENT DISCONNECTED");
            server.close();
            System.out.println("SERVER IS DOWN");
            //close reader and writers
            reader.close();
            writer.close();
            
            //close
            System.exit(0);
            

            //catch block for errors and display msg to server admin
        } catch (IOException e) {
            System.out.println("SERVER FAILD TO INITALIZE: ERROR");
            System.out.println(e);
        }//end catch block for socket errors

    }//end main

}//end class
