import serialization.Student;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        String sentence;
        String modifiedSentence;
        Student std = new Student (269940);
        Socket clientSocket = new Socket("localhost", 12900);
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(std);
        clientSocket.close();
    }
}
