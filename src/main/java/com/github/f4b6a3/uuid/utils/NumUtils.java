package com.github.f4b6a3.uuid.utils;

import java.util.UUID;

public class NumUtils {

    public static void toBytes(long value, byte[] bytes, int beginOffset) {
        // MSB
        for (int i = 0, offset = beginOffset + 7; i < 8; i++, offset--) {
            bytes[offset] = (byte) (value >> i * 8);
        }
    }

    public static void toBytes(int value, byte[] bytes, int beginOffset) {
        // MSB
        for (int i = 0, offset = beginOffset + 4; i < 4; i++, offset--) {
            bytes[offset] = (byte) (value >> i * 8);
        }
    }

    public static byte[] toBytes(UUID uuid) {
        byte[] result = new byte[16];

        NumUtils.toBytes(uuid.getMostSignificantBits(), result, 0);
        NumUtils.toBytes(uuid.getLeastSignificantBits(), result, 8);

        return result;
    }

}
