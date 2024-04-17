package com.zhuchops.geomark;

public class IdGenerator {
    public static String generateId() {
        String uniqueID = Float.toString(System.nanoTime());
        return uniqueID;
    }
}
