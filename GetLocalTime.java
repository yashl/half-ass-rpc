import java.io.*;
import java.util.*;
import java.net.*;

public class GetLocalTime
{
    c_int time = new c_int();
    c_char valid = new c_char();

    public GetLocalTime()
    {
        time.setValue(69);
        //valid.setValue(false);
    }

    public int execute(String IP, int port)
    {
        int execute = 0;
        try
        {
            Socket clientSocket = new Socket(IP, port);
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());

            // send request
            byte[] buff = new byte[12];
            buff = prepareBuffer();
            output.write(buff, 0, buff.length);
            output.flush();

            System.out.println("Request Sent! Waiting for reply...");

            byte[] buff2 = new byte[5];
            input.read(buff2);
            String s = new String(buff2);
            time.setValue(Integer.parseInt(s.trim()));
            valid.setValue('1');         

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
        String command = "GetLocalTime";
        c_int length = new c_int();
        length.setValue(5); //total size of time.size and valid.size combined

        byte[] b_command = command.getBytes();
        byte[] b_length = length.toByte();

        byte[] buffer = new byte[109]; //length 100+4+5
        System.arraycopy(b_command, 0, buffer, 0, b_command.length);
        System.arraycopy(b_length, 0, buffer, 100, b_length.length);

        return buffer;
    }
}
