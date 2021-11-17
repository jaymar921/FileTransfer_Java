package me.JayMar.filetransfer.server;

import me.JayMar.filetransfer.config.Configuration;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{

    Socket socket;
    Configuration configuration;
    public ServerThread(Socket socket, Configuration configuration){
        this.socket = socket;
        this.configuration = configuration;
        start();
    }

    @Override
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(in);

            //get the filename
            System.out.println("[Server] - Reading Filename");
            byte[] filename = new byte[dataInputStream.readInt()];
            dataInputStream.readFully(filename,0,filename.length);

            //get the content
            System.out.println("[Server] - Reading Content");
            byte[] content  = new byte[dataInputStream.readInt()];
            dataInputStream.readFully(content,0,content.length);

            //save the file
            System.out.println("[Server] - Saving File");
            File file = new File(configuration.path+"/"+new String(filename));
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content);
            outputStream.flush();
            System.out.println("[Server] - Transferred "+new String(filename)+" from "+socket.getInetAddress());
            outputStream.close();
            dataInputStream.close();
            in.close();

        }catch (Exception err){
            System.out.println("[Server] - There was an issue copying file\n"+err.getMessage());
        }
    }
}
