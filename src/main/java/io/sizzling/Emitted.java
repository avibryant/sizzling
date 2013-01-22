package io.sizzling;

public class Emitted {
  public int tableIndex;
  public String key;
  public byte[] valueBytes;

  public Emitted(int tableIndex, String key, byte[] valueBytes) {
    this.tableIndex = tableIndex;
    this.key = key;
    this.valueBytes = valueBytes;
  }

  public String makeKeyString() {
    return tableIndex + ":" + key;
  }

  public static int getTableIndex(String keyString) {
    return Integer.parseInt(keyString.split(":")[0]);
  }
}