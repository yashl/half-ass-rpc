import java.io.*;
import java.util.*;

public class Test
{
    /**
     * Valid 0 indicates false
     * Valid 1 indicates true
     */

    public static void main(String[] args)
    {
        GetLocalOS localOS = new GetLocalOS();
        GetLocalTime localTime = new GetLocalTime();

        /**
         * Testing LocalOS
         */
        System.out.println();
        System.out.println("Testing LocalOS......");

        localOS.valid.setValue('0');
        localOS.execute("127.0.0.1", 8080);
        System.out.print("LocalOS is: ");
        //printOS
        for(int i =0; i < localOS.OS.length; i++)
        {
            System.out.print(localOS.OS[i].getValue());
        }
        System.out.println();
        System.out.println("Valididity Check : " + localOS.valid.getValue());

        /**
         * Testing LocalTime
         */
        System.out.println();
        System.out.println("Testing LocalTime......");

        localTime.valid.setValue('0');
        localTime.execute("127.0.0.1", 9000);
        System.out.println("Recvd Local Time. LocalTime from server: " + localTime.time.getValue());
        System.out.println("Validity Check: " + localTime.valid.getValue());

    }
}
