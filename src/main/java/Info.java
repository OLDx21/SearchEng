import java.util.HashMap;

public class Info {

    static int size;

    static HashMap[] hashMaps = new HashMap[10];


    public static void setSize(int size) {
        Info.size = size;
    }

    Info(){


    }

    public static HashMap[] getHashMaps() {
        return hashMaps;
    }

    public static void setHashMaps(HashMap hashMapss, int n) {
        hashMaps[n]=hashMapss;
    }



}
