package httpClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xiao on 2016/8/16.
 */

public class token {

    private static final String[] a = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static String a(byte paramByte)
    {
        int i = paramByte;
        if (paramByte < 0) {
            i = paramByte + 256;
        }
        paramByte = (byte) (i / 16);
        return a[paramByte] + a[(i % 16)];
    }

    public static String a(String paramString)
    {
        try
        {
            String str = new String(paramString);
            paramString = a(MessageDigest.getInstance("MD5").digest(paramString.getBytes()));
            return paramString;

        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException1)

        {
            localNoSuchAlgorithmException1.printStackTrace();
            paramString = null;
        }
        return paramString;
    }

    private static String a(byte[] paramArrayOfByte)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        int i = 0;
        while (i < paramArrayOfByte.length)
        {
            localStringBuffer.append(a(paramArrayOfByte[i]));
            i += 1;
        }
        return localStringBuffer.toString();
    }
}

