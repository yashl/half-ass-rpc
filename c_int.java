import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class c_int
{
    byte[] buf = new byte[4];

    public c_int(){}

    // public c_int(int x)
    // {
    //     buf = this.setValue(x);
    // }

    // public c_int(bytes[] b)
    // {
    //     buf = b;
    // }

    public int getSize()
    {
        return buf.length;
    }

    public int getValue()
    {
        ByteBuffer wrapped = ByteBuffer.wrap(buf);
        return wrapped.getInt();
    }

    public void setValue(byte[] b)
    {
        buf = b;
    }

    public void setValue(int v)
    {
        buf = ByteBuffer.allocate(4).putInt(v).array();
    }

    public byte[] toByte()
    {
        return buf;
    }
}
