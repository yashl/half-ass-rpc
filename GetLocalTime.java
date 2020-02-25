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
        valid.setValue(false);
    }

    public int execute(String IP, int port)
    {
        int execute = 0;

        try
        {
            Socket clientSocket = new Socket(IP, port);

            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            // send request
            byte[] buff = new byte[4];
            buff = time.toByte();
            output.write(buff, 0, 4);
            output.flush();

            System.out.println("Text Sent!");

            // closing outpute stream and socket
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
}
