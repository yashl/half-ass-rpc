import java.io.*;
import java.util.*;

public class Test
{
    public static void main(String[] args)
    {
        //GetLocalOS localOS = new GetLocalOS();

        GetLocalTime localTime = new GetLocalTime();

        System.out.println("Sending TCP message!");
        localTime.execute("127.0.0.1", 8080);
    }
}
