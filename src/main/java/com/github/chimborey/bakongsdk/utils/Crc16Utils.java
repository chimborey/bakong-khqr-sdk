package com.github.chimborey.bakongsdk.utils;

public class Crc16Utils {

    // Algorithm ស្តង់ដារសម្រាប់គណនាផលបូកពិនិត្យកំហុស (CRC16) របស់ Bakong
    public static String calculate(String data) {
        int crc = 0xFFFF;
        int polynomial = 0x1021;

        for (byte b : data.getBytes()) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }
        return String.format("%04X", crc & 0xFFFF);
    }
}
