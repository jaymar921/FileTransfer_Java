package me.JayMar.filetransfer;

import me.JayMar.filetransfer.client.ClientHandler;
import me.JayMar.filetransfer.server.ServerHandler;

public class MainClass {

    public static void main(String... args){
        if(args.length==1){
            if(args[0].equalsIgnoreCase("-server")){
                System.out.println("[Server] - Starting Server");
                new ServerHandler();
            }
            if(args[0].equalsIgnoreCase("-client")){
                System.out.println("[Client] - Starting Client");
                new ClientHandler();
            }
        }
    }
}
