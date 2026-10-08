package com.github.chimborey.bakongsdk.utils;

public class EmvcoUtils {

    // មុខងារស្វ័យប្រវត្តិកំណត់ប្រវែងអក្សរ (Length) ឱ្យគ្រប់ ២ ខ្ទង់ជានិច្ចតាមស្តង់ដារ EMVCo
    public static String buildTag(String tag, String value) {
        if (value == null || value.trim().isEmpty()) {
            return "";
        }
        return tag + String.format("%02d", value.length()) + value;
    }
}
