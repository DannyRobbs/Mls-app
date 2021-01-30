package com.example.mls;

public class sharedData {
    public static excoitem myexco;
    public static String infokey = "infokey";

    public static excoitem getMyexco() {
        return myexco;
    }

    public static void setMyexco(excoitem myexco) {
        sharedData.myexco = myexco;
    }

    public static String getInfokey() {
        return infokey;
    }
}
