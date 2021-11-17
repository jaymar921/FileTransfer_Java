package me.JayMar.filetransfer.config;

import java.io.File;

public class ServerFile {

    public static void createServerFile(String path){
        create(path);
    }

    private static void create(String path){
        try {
            File file = new File(path);
            if(!file.exists()){
                if(file.mkdir())
                    System.out.println("[Server] - Created server path: "+path);
                else
                    System.out.println("[Server] - Failed to create path: "+path);
            }
        }catch (Exception ignore){}
    }
}
