# បណ្ណាល័យ Bakong Open API Java SDK 🇰🇭

បណ្ណាល័យកូដ Java ស្តង់ដារសុវត្ថិភាពខ្ពស់ (Bank-Grade SDK) សម្រាប់ជំនួយដល់ Developer ក្នុងការភ្ជាប់ទៅកាន់ **Bakong Open API ផ្លូវការ** របស់ធនាគារជាតិនៃកម្ពុជា (NBC)។ បណ្ណាល័យនេះជួយសម្រួលដល់ការផ្ញើ Request ទៅសុំបង្កើត QR Code ពី Server របស់បាកងដោយផ្ទាល់ និងផ្តល់មុខងារផ្ទៀងផ្ទាត់ហត្ថលេខាឌីជីថល (Digital Signature Verification) នៅលើប្រព័ន្ធ Payment Gateway Webhook យ៉ាងរឹងមាំបំផុត។

---

## 📥 របៀបដំឡើង (Installation)

ដើម្បីយកបណ្ណាល័យកូដនេះទៅប្រើប្រាស់ក្នុងគម្រោង Java របស់អ្នក (ដូចជា Spring Boot, គម្រោង Java ធម្មតា) អ្នកគ្រាន់តែជ្រើសរើសវិធីដំឡើងទៅតាម Build System របស់គម្រោងអ្នក៖

### ១. សម្រាប់អ្នកប្រើប្រាស់ Maven (`pom.xml`)

សូមបើកឯកសារ `pom.xml` នៅក្នុងគម្រោងរបស់អ្នក រួចបន្ថែមប្រព័ន្ធទាញយករបស់ JitPack ទៅក្នុង Tag `<repositories>`៖

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

បន្ទាប់មក បន្ថែមឈ្មោះបណ្ណាល័យនេះទៅក្នុង Tag `<dependencies>` របស់គម្រោងអ្នក៖

```xml
<dependency>
    <groupId>com.github.chimborey</groupId>
    <artifactId>bakong-khqr-sdk</artifactId>
    <version>v2.0.0</version>
</dependency>
```

### ២. សម្រាប់អ្នកប្រើប្រាស់ Gradle (`build.gradle`)

សូមបើកឯកសារ `build.gradle` រួចបន្ថែមទម្រង់ខាងក្រោម៖

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

បន្ទាប់មក បន្ថែមឈ្មោះបណ្ណាល័យទៅក្នុងទម្រង់ `dependencies`៖

```groovy
dependencies {
    implementation 'com.github.chimborey:bakong-khqr-sdk:v2.0.0'
}
```

---

## 🚀 របៀបយកទៅសរសេរកូដប្រើប្រាស់ (Usage Guide)

បណ្ណាល័យនេះដើរតួជាស្ពានចម្លងទំនាក់ទំនងជាមួយ Server របស់បាកង តាមរយៈប្រព័ន្ធ Open API៖

### ផ្នែកទី ១៖ ការសុំបង្កើត QR Code ពី Server របស់បាកង (Generate QR via Open API)
អ្នកអាចហៅប្រើប្រាស់មុខងារនេះ នៅក្នុង Class ណាមួយនៃគម្រោងរបស់អ្នក ដើម្បីបាញ់ទិន្នន័យទៅសុំ QR ផ្លូវការពី NBC ដោយផ្ទាល់តាមអ៊ីនធឺណិត៖

```java
import com.github.chimborey.bakongsdk.BakongOpenAPI;
import com.github.chimborey.bakongsdk.model.BakongRequest;
import com.github.chimborey.bakongsdk.model.BakongResponse;

public class PaymentService {

    public void createPaymentCheckout() {
        try {
            // ១. ដាក់លេខ API Token អាថ៌កំបាំងដែលទទួលបានពីធនាគារជាតិ NBC
            String bakongToken = ""; 

            // ២. បំពេញព័ត៌មានទឹកប្រាក់ និង ID ហាងទៅក្នុងប្រអប់ទទេ ()
            BakongRequest request = new BakongRequest();
            request.setMerchantId("");   // លេខ ID ហាង (ឧទាហរណ៍៖ "aba_shop_borey")
            request.setAmount();         // ចំនួនទឹកប្រាក់ (ឧទាហរណ៍៖ 25.00)
            request.setCurrency("");     // ប្រភេទលុយ "USD" ឬ "KHR"
            request.setStoreLabel("");   // ស្លាកឈ្មោះបញ្ជរ (មិនដាក់ក៏បាន)
            request.setTerminalId("");   // លេខម៉ាស៊ីនគិតលុយ (មិនដាក់ក៏បាន)

            // ៣. ហៅប្រើបណ្ណាល័យដើម្បីបាញ់ទៅកាន់ Bakong Open API ផ្លូវការ
            BakongResponse response = BakongOpenAPI.generateQR(bakongToken, request);

            // ៤. ទទួលលទ្ធផលខ្សែអក្សរ QR String សុវត្ថិភាពពីធនាគារជាតិ NBC
            if (response.getResponseCode() == 0) {
                String officialQrString = response.getData().getQrString();
                System.out.println("ទទួលបាន KHQR String ផ្លូវការពី NBC៖ " + officialQrString);
            } else {
                System.err.println("ធនាគារជាតិបដិសេធ៖ " + response.getResponseMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### ផ្នែកទី ២៖ ការផ្ទៀងផ្ទាត់ Webhook លុយចូល (Advanced Security Gateway)
នៅពេលអតិថិជនស្កេនរួច Server របស់បាកងនឹងបាញ់ទិន្នន័យលុយចូលមកកាន់ Server របស់អ្នក។ ដើម្បីការពារមិនឱ្យ Hacker ក្លែងបន្លំទិន្នន័យបោកប្រាស់ប្រព័ន្ធ បណ្ណាល័យនេះផ្តល់មុខងារផ្ទៀងផ្ទាត់ហត្ថលេខាឌីជីថល (HMAC-SHA256) តាមច្បាប់របស់ NBC យ៉ាងម៉ត់ចត់៖

```java
import com.github.chimborey.bakongsdk.utils.BakongSecurity;

public class WebhookController {

    // មុខងារសម្រាប់ចាំស្ដាប់ និងឆែកមើលលុយចូលដោយស្វ័យប្រវត្ត និងសុវត្ថិភាពបំផុត
    public void handleBakongWebhook() {
        
        String secretKey = "";           // លេខ Secret Key អាថ៌កំបាំងដែល NBC ផ្ដល់ឱ្យក្រុមហ៊ុនអ្នក
        String signatureHeader = "";     // ហត្ថលេខាឌីជីថលដែលជាប់មកជាមួយ Header (X-Bakong-Signature)
        String jsonPayload = "";         // អត្ថបទ JSON ទិន្នន័យលុយចូលទាំងស្រុងដែលបាកងបាញ់មក

        // ហៅបណ្ណាល័យមកផ្ទៀងផ្ទាត់សោឌីជីថល ការពារការ Hack
        boolean isAuthentic = BakongSecurity.verifyWebhookSignature(jsonPayload, secretKey, signatureHeader);

        if (isAuthentic) {
            System.out.println("✅ ហត្ថលេខាត្រឹមត្រូវ! លុយចូលកុងពិតប្រាកដ ១០០% ផ្ញើចេញពីធនាគារជាតិ!");
            // ទៅសរសេរកូដ Update ស្ថានភាពវិក្កយបត្រក្នុង Database របស់អ្នកទៅជា "PAID" រួចកាត់ទំនិញឱ្យម៉ូយ auto ភ្លាម
        } else {
            System.err.println("❌ ព្រមាន៖ ហត្ថលេខាក្លែងក្លាយ! ទិន្នន័យនេះត្រូវបានបន្លំដោយ Hacker!");
        }
    }
}
```

---

## 🔒 គោលការណ៍ណែនាំផ្នែកសុវត្ថិភាព (Security Best Practices)

ដោយសារបណ្ណាល័យនេះដំណើរការផ្ទាល់ជាមួយប្រព័ន្ធ Open API ផ្លូវការ៖
1. **ដាច់ខាតកុំវាយលេខ Token ជាប់ក្នុងកូដ៖** ត្រូវរក្សាទុក `bakongToken` និង `secretKey` នៅក្នុងឯកសារសម្ងាត់ខាងក្រៅកូដ (Environment Variables ដូចជា `.env` ឬ `application.properties` របស់ Spring Boot) ដើម្បីការពារការលេចធ្លាយព័ត៌មានសម្ងាត់ទៅកាន់ GitHub។
2. **ការការពារហត្ថលេខាឌីជីថល៖** មុខងារ `verifyWebhookSignature` ធានាថា រាល់ការប្តូរស្ថានភាពវិក្កយបត្រផ្ទេរលុយទាំងអស់ គឺត្រូវបានត្រួតពិនិត្យភាពត្រឹមត្រូវចេញពីធនាគារជាតិផ្ទាល់ ដោយគ្មាន Hacker ណាអាចមកលួចកុហកប្រព័ន្ធបានឡើយ។
