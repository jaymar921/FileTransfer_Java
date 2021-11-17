package me.JayMar.filetransfer.client;

import me.JayMar.filetransfer.config.ConfigCheck;
import me.JayMar.filetransfer.config.Configuration;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {

    Configuration configuration;
    public ClientHandler(){
        configuration = ConfigCheck.getConfig();
        System.out.println("[Client] - Starting client");
        System.out.println("[Client] - Server address: "+configuration.server+":"+configuration.port);
        String args = "";
        while (!args.equalsIgnoreCase("exit")){
            System.out.println("[Client] - Enter the filename you want to send: [type exit to exit]");
            args = new Scanner(System.in).nextLine();
            selectFile(args);
        }
    }

    private void selectFile(String filename){
        try {
            File file = new File(filename);
            if(!file.exists()){
                System.out.println("Could not find "+filename);
                return;
            }
            System.out.println("[Client] Continue sending "+filename+" to server? [y/n]");
            String args = new Scanner(System.in).nextLine();
            if(!args.contains("y")) {
                System.out.println("[Server] - Cancelled ["+filename+"]");
                return;
            }
            sendFile(file);
        }catch (Exception ignore){}
    }

    private void sendFile(File file){
        try {
            FileInputStream inputStream = new FileInputStream(file);
            //read the contents of the file
            byte[] content = new byte[(int)inputStream.getChannel().size()];
            inputStream.read(content, 0,content.length);
            System.out.println("[Client] - Successfully read file content");
            System.out.println("[Client] - Sending file to server["+configuration.server+"]");
            //connect to server
            Socket socket = new Socket(configuration.server, configuration.port);

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            String filename = file.getName();
            byte[] filename_data = filename.getBytes();
            //send the filename
            System.out.println("[Client] - Sending Filename");
            dataOutputStream.writeInt(filename_data.length);
            dataOutputStream.write(filename_data);
            //send the content
            System.out.println("[Client] - Sending Content");
            dataOutputStream.writeInt(content.length);
            dataOutputStream.write(content);

            System.out.println("[Client] - Closing Connection");
            socket.close();
            dataOutputStream.close();
            inputStream.close();
            System.out.println("[Client] - File was sent successfully");
        }catch (Exception error){
            System.out.println("[Client] - failed to send file\n");
            error.printStackTrace();
        }
    }


}
