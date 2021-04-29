package com.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerApplication {

    public static void main(String[] args) {

        final int serverPort = 4444;
        final int clientPort = 4441;
        final String host = "127.0.0.1";
        final BufferedReader input;
        final PrintWriter output;

        try {
            final ServerSocket serverSocket = new ServerSocket(serverPort);
            final Socket listeningServerSocket = serverSocket.accept();
            Socket clientSocket = new Socket(host, clientPort);
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(listeningServerSocket.getInputStream()));
            String line;
            while ( (line = input.readLine()) != null ) {
                System.out.println(line);
                checkToStopConnection(line, clientSocket, serverSocket, input, output);
                answerMessage(enterMessage(), output);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkToStopConnection(final String line, final Socket clientSocket, final ServerSocket serverSocket, final BufferedReader input, final PrintWriter output) throws IOException {
        if ("bye".equals(line.toLowerCase())) {
            input.close();
            output.close();
            clientSocket.close();
            serverSocket.close();
        }
    }

    private static void answerMessage(final String answer, final PrintWriter output) {
        output.println(answer);
    }

    private static String enterMessage() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
