package com.github.chimborey.bakongsdk;

import com.github.chimborey.bakongsdk.bakongsdk.BakongQR;
import com.github.chimborey.bakongsdk.model.MerchantInfo;

public class MainTest {
    public static void main(String[] args) {
        try {
            System.out.println("=== 🔍 ចាប់ផ្ដើមធ្វើតេស្ត Bakong KHQR SDK ===");

            // ១. បង្កើតព័ត៌មានគំរូសម្រាប់ហាង
            MerchantInfo merchant = new MerchantInfo.Builder()
                    .accountId("chimborey@aba")       // គណនី Bakong ID
                    .merchantName("Borey Pizza Shop") // ឈ្មោះហាង
                    .merchantCity("Phnom Penh")
                    .amount(15.50)                     // ទឹកប្រាក់ ១៥.៥ ដុល្លារ
                    .currency("USD")
                    .billNumber("INV-2026-001")
                    .build();

            // ២. ហៅបង្កើតជា KHQR String
            String qrString = BakongQR.generateString(merchant);
            System.out.println("\n✅ ១. ទទួលបាន Bakong KHQR String ៖");
            System.out.println(qrString);

            // ៣. ហៅបង្កើតជារូបភាព QR Code ជាប្រភេទ Base64 String
            String qrImageBase64 = BakongQR.generateImageBase64(merchant, 300, 300);
            System.out.println("\n✅ ២. ទទួលបានរូបភាព QR Code (Base64) ៖");
            System.out.println(qrImageBase64.substring(0, 100) + "... [កូដរូបភាពវែងខ្លាំងត្រូវបានលាក់]");

            System.out.println("\n=========================================");
            System.out.println("🎉 ជោគជ័យ! បណ្ណាល័យរបស់អ្នកដំណើរការបានល្អឥតខ្ចោះ។");

        } catch (Exception e) {
            System.err.println("❌ មានបញ្ហាក្នុងការបង្កើត QR Code: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
