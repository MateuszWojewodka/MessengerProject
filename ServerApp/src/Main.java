import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import serialization.Student;

public class Main {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12900, 100,
                InetAddress.getByName("localhost"));

        while (true) {
            System.out.println("Waiting for a connection...");

            final Socket activeSocket = serverSocket.accept();

            System.out.println("Received a connection from " + activeSocket);
            Runnable runnable = () -> handleClientRequest(activeSocket);
            new Thread(runnable).start();
        }
    }

    public static void handleClientRequest(Socket socket) {
        try{
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Student student = (Student) in.readObject();
            System.out.println (student.nrIndexu);
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
