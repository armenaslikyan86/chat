package com.chat.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {

    public static void main(String[] args) {

        final int serverPort = 4444;
        final int clientPort = 4441;
        final String host = "127.0.0.1";
        final Scanner scanner = new Scanner(System.in);
        PrintWriter output;
        String line;

        try {
            final Socket clientSocket = new Socket(host, serverPort);

            while (true) {
                output = new PrintWriter(clientSocket.getOutputStream(), true);
                sendMessage(scanner.nextLine(), output);
                final ServerSocket serverSocket = new ServerSocket(clientPort);
                final Socket serverSocket2 = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(serverSocket2.getInputStream()));
                while ( (line = input.readLine()) != null) {
                    System.out.println(line);
                    checkToStopConnection(line, clientSocket, input, output);
                    sendMessage(scanner.nextLine(), output);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void checkToStopConnection(final String line, final Socket clientSocket, final BufferedReader input, final PrintWriter output) throws IOException {
        if ("bye".equals(line.toLowerCase())) {
            input.close();
            output.close();
            clientSocket.close();
        }
    }

    private static void sendMessage(final String message, final PrintWriter output) {
        output.println(message);
    }

}
