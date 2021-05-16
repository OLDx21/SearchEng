import java.util.HashMap;

public class Nametovar {
    static HashMap hashMap = new HashMap();


    public static HashMap getHashMap() {
        return hashMap;
    }



    public static void setHashMap(HashMap hashMap) {
        Nametovar.hashMap.putAll(hashMap);
    }
}
