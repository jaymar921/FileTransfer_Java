package me.JayMar.filetransfer.config;

import java.io.*;

public class ConfigCheck {

    public static Configuration getConfig(){
        Configuration conf = new Configuration();
        if(!exist())
            createConfig();
        return getConfig(conf);
    }

    private static boolean exist(){
        try {
            File file = new File("config.txt");
            return file.exists();
        }catch (Exception ignore){}
        return false;
    }

    private static void createConfig(){
        try {
            File file = new File("config.txt");

            OutputStream outputStream = new FileOutputStream(file);

            String config_data = """
                    # File Transfer Server by Jayharron Abejar
                    # Modify the config below
                    server: 192.168.1.1
                    port: 25560
                    server_root_path: server
                    """;
            byte[] bytes = config_data.getBytes();

            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            System.out.println("[Server] - created config.txt");
        }catch (Exception ignore){}
    }

    private static Configuration getConfig(Configuration configuration){
        try {
            File file = new File("config.txt");
            InputStream in = new FileInputStream(file);

            int _b;
            StringBuilder data = new StringBuilder();
            while ((_b = in.read()) != -1){
                data.append((char) _b);
            }

            String[] data_ = data.toString().split("\n");
            for(String d : data_){
                if(!d.contains(":"))
                    continue;
                String[] fragments = d.split(":");
                if(fragments.length==2){
                    if(fragments[0].equals("server"))
                        configuration.server = fragments[1].trim();
                    if(fragments[0].equals("port"))
                        configuration.port = Integer.parseInt(fragments[1].trim());
                    if(fragments[0].equals("server_root_path"))
                        configuration.path = fragments[1].trim();
                }
            }
            System.out.println("[Server] - configuration read");
        }catch (Exception error){
            System.out.println("[Server] - There was an issue reading config.txt");
            System.out.println("[Server] - Setting default");
            configuration.server = "127.0.0.1";
            configuration.port = 25560;
            configuration.path = "server\\files\\";
        }
        System.out.println("[Server] - Server: "+configuration.server);
        System.out.println("[Server] - Port: "+configuration.port);
        System.out.println("[Server] - Server Path: "+configuration.path);
        return configuration;
    }
}
