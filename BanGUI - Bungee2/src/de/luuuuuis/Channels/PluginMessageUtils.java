package de.luuuuuis.Channels;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PluginMessageUtils {
  public static Object[] readData(byte[] data) {
    List<String> read = new ArrayList<String>();
    DataInputStream di = new DataInputStream(new ByteArrayInputStream(data));
    for (int i = 0; i < 255; i++) {
      try
      {
        String dr = di.readUTF();
        read.add(dr);
      }
      catch (IOException e)
      {
        if (read.size() != 0) {
          return read.toArray(new String[read.size()]);
        }
        return new String[] { new String(data, Charset.forName("UTF-8")) };
      }
    }
    Object[] out = read.toArray(new String[read.size()]);
    
    return out;
  }
}