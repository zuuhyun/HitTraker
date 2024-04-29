package me.zuuhyun.youtubeproject.util;

import java.security.SecureRandom;

public class AdvertisementUtil {

    public static long generateRandomNum(int start, int end) {
        SecureRandom secureRandom = new SecureRandom();
        return start + secureRandom.nextInt(end + 1);
    }
}
