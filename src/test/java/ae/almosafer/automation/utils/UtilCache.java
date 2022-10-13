package ae.almosafer.automation.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UtilCache {

  public static Map<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();

  public static void put(String key, String value) {
    cacheMap.put(key, value);
  }

  public static Object getValue(String key) {
    return cacheMap.get(key);
  }
}
