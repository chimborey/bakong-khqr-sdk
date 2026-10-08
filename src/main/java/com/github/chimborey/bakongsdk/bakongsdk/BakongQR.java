package com.github.chimborey.bakongsdk.bakongsdk;

import com.github.chimborey.bakongsdk.model.MerchantInfo;
import com.github.chimborey.bakongsdk.utils.Crc16Utils;
import com.github.chimborey.bakongsdk.utils.EmvcoUtils;
import com.github.chimborey.bakongsdk.utils.QrCodeGenerator;

public class BakongQR {

    // មុខងារចម្បងទី ១៖ សម្រាប់បង្កើតតែទម្រង់អត្ថបទ KHQR String
    public static String generateString(MerchantInfo info) {
        StringBuilder qr = new StringBuilder();

        // 1. កំណត់ទម្រង់ Core Tags ថេរ
        qr.append(EmvcoUtils.buildTag("00", "01")); // Payload Format Indicator
        qr.append(EmvcoUtils.buildTag("01", "12")); // Point of Initiation Method (12 = Dynamic QR)

        // 2. Tag 30: Merchant Account Information (បេះដូងរបស់ Bakong KHQR)
        // តាមច្បាប់ធនាគារជាតិ NBC ត្រូវមាន Tag 00 បញ្ជាក់ប្រព័ន្ធ និង Tag 01 បញ្ជាក់លេខគណនី
        String globalId = EmvcoUtils.buildTag("00", "kh.gov.nbc.bakong");
        String accountId = EmvcoUtils.buildTag("01", info.getAccountId());
        String bakongMerchantData = globalId + accountId;

        qr.append(EmvcoUtils.buildTag("30", bakongMerchantData));

        // 3. Merchant Category Code
        qr.append(EmvcoUtils.buildTag("52", "5999")); // 5999 = General Store

        // 4. កំណត់ប្រភេទលុយ (USD = 840, KHR = 116)
        String currencyCode = "USD".equalsIgnoreCase(info.getCurrency()) ? "840" : "116";
        qr.append(EmvcoUtils.buildTag("53", currencyCode));

        // 5. កំណត់ចំនួនទឹកប្រាក់ (Amount)
        if (info.getAmount() != null) {
            // បើ USD មានក្បៀស ២ ខ្ទង់ (.2f) បើ KHR ជាលេខមូលគ្មានក្បៀស (.0f)
            String amtStr = "840".equals(currencyCode)
                    ? String.format("%.2f", info.getAmount())
                    : String.format("%.0f", info.getAmount());
            qr.append(EmvcoUtils.buildTag("54", amtStr));
        }

        // 6. ព័ត៌មានទីតាំងហាង
        qr.append(EmvcoUtils.buildTag("58", "KH")); // Country Code
        qr.append(EmvcoUtils.buildTag("59", info.getMerchantName()));
        qr.append(EmvcoUtils.buildTag("60", info.getMerchantCity()));

        // 7. Tag 62: Additional Data (លេខវិក្កយបត្រ ឬ ស្លាកឈ្មោះបញ្ជរ)
        StringBuilder tag62Data = new StringBuilder();
        if (info.getBillNumber() != null) tag62Data.append(EmvcoUtils.buildTag("01", info.getBillNumber()));
        if (info.getStoreLabel() != null) tag62Data.append(EmvcoUtils.buildTag("03", info.getStoreLabel()));

        if (tag62Data.length() > 0) {
            qr.append(EmvcoUtils.buildTag("62", tag62Data.toString()));
        }

        // 8. Tag 63: គណនាផលបូកពិនិត្យកំហុស (CRC16 Checksum)
        qr.append("6304"); // 63 ជា Tag និង 04 ជាប្រវែងលទ្ធផលកូដ CRC

        String crc = Crc16Utils.calculate(qr.toString());
        return qr.toString() + crc;
    }

    // មុខងារចម្បងទី ២៖ សម្រាប់បង្កើតរូបភាព QR Code ចេញជា Base64 ភ្លាមៗ
    public static String generateImageBase64(MerchantInfo info, int width, int height) throws Exception {
        String qrText = generateString(info);
        return QrCodeGenerator.toBase64(qrText, width, height);
    }
}
