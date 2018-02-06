package cn.wanglin.sgw.exchange.utils;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class WebUtils {
    public static String toQueryString(Map<String,Object> map) {
        if(map.isEmpty())return "";
        TreeMap<String, Object> sortedMap   = new TreeMap(map);
        String                  queryString = "";
        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            if (null != entry.getValue()) {
                queryString += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return queryString.substring(1);
    }

    public static String streamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader        reader   = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    public static byte[] streamAsByte(InputStream stream,Long length, String charset) throws IOException {
        try {
            ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
            byte[] buff=new byte[1024];
            long total=0;
            int once=0;
            while (total<length) {
                once=stream.read(buff);
                bos.write(buff,0,once);
                total+=once;
            }

            return bos.toByteArray();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

}
