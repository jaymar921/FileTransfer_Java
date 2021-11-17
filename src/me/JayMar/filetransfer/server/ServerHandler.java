package me.JayMar.filetransfer.server;

import me.JayMar.filetransfer.config.ConfigCheck;
import me.JayMar.filetransfer.config.Configuration;
import me.JayMar.filetransfer.config.ServerFile;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler {

    Configuration configuration;
    ServerSocket serverSocket;

    public ServerHandler(){
        configuration = ConfigCheck.getConfig();
        ServerFile.createServerFile(configuration.path);
        StartServer();
    }

    public void StartServer(){
        new Thread(
                new Runnable(){
                    @Override
                    public void run() {
                        try {
                            serverSocket = new ServerSocket(configuration.port);
                            System.out.println("[Server] - Waiting for connection");
                            while (!serverSocket.isClosed() && serverSocket.isBound()){
                                Socket socket = serverSocket.accept();
                                System.out.println("Accepted Connection: "+socket.getInetAddress());
                                new ServerThread(socket,configuration);
                            }

                        }catch (Exception error){
                            System.out.println("[Server] - Error: "+error.getMessage());
                        }
                    }
                }).start();
    }
}
