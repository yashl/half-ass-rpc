import java.io.*;
import java.util.*;
import java.net.*;

public class GetLocalOS 
{   
    c_char[] OS = new c_char[16];
    c_char valid = new c_char();

    public GetLocalOS()
    {
        for(int i = 0; i < 16; i++)
        {
            OS[i] = new c_char();
        }    
    }

    public int execute(String IP, int port)
    {
        int execute = 0;
        try
        {
            Socket clientSocket = new Socket(IP, port);
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());

            byte[] buff = new byte[12];
            buff = prepareBuffer();
            
            output.write(buff, 0, buff.length);
            output.flush();
            
            System.out.println("Request Sent! Waiting for reply...");
            
            byte[] buff2 = new byte[17];
            input.read(buff2);
            
            for(int i = 0; i <17; i++)
            {
                //System.out.println(i + " : " + buff2[i]);
            }
            String s = new String(buff2);
            //System.out.println("Length of string : " + s.length());
            //System.out.println("Reply from server - Local OS: " + s);
            System.out.println("Server response stored data in data structure!");

            valid.setValue('1');
            for(int i = 0; i < OS.length; i++)
            {
                OS[i].setValue(s.charAt(i));
            }

            // closing output stream and socket
            output.close();
            clientSocket.close();
            execute = 1;
        }
        catch (Exception e)
        {
            System.out.println("Exception " + e + " got!");
        }
        return execute;
    }

    public byte[] prepareBuffer()
    {
        String command = "GetLocalOS";
        c_int length = new c_int();
        length.setValue(17); //total size of OS.size and valid.size combined

        byte[] b_command = command.getBytes();
        byte[] b_length = length.toByte();

        byte[] buffer = new byte[124]; //length 100+4+20
        System.arraycopy(b_command, 0, buffer, 0, b_command.length);
        System.arraycopy(b_length, 0, buffer, 100, b_length.length);

        return buffer;
    }

    public void printOS()
    {
        System.out.print("Local OS is: ");
        for(int i = 0; i< 16; i++)
        {
            System.out.print(OS[i].getValue());
        }
        System.out.println();
    }
}
