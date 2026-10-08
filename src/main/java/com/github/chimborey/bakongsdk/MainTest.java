# បណ្ណាល័យ Bakong KHQR Java SDK 🇰🇭

បណ្ណាល័យកូដ Java ស្តង់ដារ (Enterprise-grade SDK) សម្រាប់ជំនួយដល់ Developer ក្នុងការរួមបញ្ចូលប្រព័ន្ធទូទាត់ប្រាក់បាកង។ បណ្ណាល័យនេះជួយសម្រួលដល់ការបង្កើត **Bakong KHQR String** និង **រូបភាព QR Code (Base64)** ដែលត្រឹមត្រូវតាមបច្ចេកទេសស្តង់ដារ **EMVCo** របស់ធនាគារជាតិនៃកម្ពុជា (NBC) ព្រមទាំងផ្តល់មុខងារផ្ទៀងផ្ទាត់ការបង់ប្រាក់ (Payment Gateway Webhook) យ៉ាងរហ័ស និងមានសុវត្ថិភាពខ្ពស់។

---

## 📥 របៀបដំឡើង (Installation)

ដើម្បីយកបណ្ណាល័យកូដនេះទៅប្រើប្រាស់ក្នុងគម្រោង Java របស់អ្នក (ដូចជា Spring Boot, គម្រោង Java ធម្មតា ឬ Android) អ្នកគ្រាន់តែជ្រើសរើសវិធីដំឡើងទៅតាម Build System របស់គម្រោងអ្នកដូចខាងក្រោម៖

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
    <version>v1.0.1</version>
</dependency>
```

### ២. សម្រាប់អ្នកប្រើប្រាស់ Gradle (`build.gradle`)

សូមបើកឯកសារ `build.gradle` (ឬ `settings.gradle` ទៅតាមជំនាន់ Gradle របស់អ្នក) រួចបន្ថែមទម្រង់ខាងក្រោម៖

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

បន្ទាប់មក បន្ថែមឈ្មោះបណ្ណាល័យទៅក្នុងទម្រង់ `dependencies`៖

```groovy
dependencies {
    implementation 'com.github.chimborey:bakong-khqr-sdk:v1.0.1'
}
```

---

## 🚀 របៀបយកទៅសរសេរកូដប្រើប្រាស់ (Usage Guide)

បណ្ណាល័យនេះត្រូវបានបែងចែកជាពីរផ្នែកធំៗ គឺផ្នែកបង្កើត QR Code និងផ្នែកផ្ទៀងផ្ទាត់សាច់ប្រាក់ (Payment Gateway)៖

### ផ្នែកទី ១៖ ការបង្កើត QR Code ទូទាត់ប្រាក់
អ្នកអាចសរសេរកូដដើម្បីបង្កើតទាំងអត្ថបទកូដ (String) ឬរូបភាព (Base64) នៅក្នុង Class ណាមួយនៃគម្រោងរបស់អ្នក ដោយគ្រាន់តែកូពីទម្រង់គំរូទទេខាងក្រោមនេះទៅបំពេញទិន្នន័យ៖

```java
import com.github.chimborey.bakongsdk.BakongQR;
import com.github.chimborey.bakongsdk.model.MerchantInfo;

public class PaymentService {

    // មុខងារសម្រាប់បង្កើតអត្ថបទកូដស្តង់ដារ Bakong KHQR String
    public String getBakongQRString() {
        MerchantInfo merchant = new MerchantInfo.Builder()
                .accountId("")       // គណនីបាកង ID ឬលេខទូរស័ព្ទ (ឧទាហរណ៍៖ "chimborey")
                .bankName("")        // ឈ្មោះធនាគារ (ឧទាហរណ៍៖ "aba", "acleda") -> ប្រព័ន្ធនឹងតភ្ជាប់ជា @aba ឱ្យ auto
                .merchantName("")     // ឈ្មោះហាង (ជាអក្សរឡាតាំង)
                .merchantCity("")     // ទីក្រុងរបស់ហាង (លំនាំដើម៖ Phnom Penh)
                .amount()             // ចំនួនទឹកប្រាក់ (ឧទាហរណ៍៖ 15.50 - លុបបន្ទាត់នេះចេញបើចង់ឱ្យម៉ូយវាយលុយខ្លួនឯង)
                .currency("")         // ប្រភេទលុយ "USD" ឬ "KHR"
                .billNumber("")       // លេខវិក្កយបត្រសម្គាល់ការទូទាត់ (មិនដាក់ក៏បាន)
                .storeLabel("")       // ស្លាកឈ្មោះបញ្ជរ ឬឈ្មោះសាខា (មិនដាក់ក៏បាន)
                .build();

        return BakongQR.generateString(merchant);
    }

    // មុខងារសម្រាប់បង្កើតរូបភាព QR Code ជាប្រភេទ Base64 String ភ្លាមៗ
    public String getBakongQRImage() throws Exception {
        MerchantInfo merchant = new MerchantInfo.Builder()
                .accountId("")       // គណនីបាកង ID ឬលេខទូរស័ព្ទ
                .bankName("")        // ឈ្មោះធនាគារ
                .merchantName("")     // ឈ្មោះហាង
                .merchantCity("")     // ទីក្រុងរបស់ហាង
                .amount()             // ចំនួនទឹកប្រាក់
                .currency("")         // ប្រភេទលុយ "USD" ឬ "KHR"
                .build();

        // បង្កើតជារូបភាព ដោយកំណត់ទំហំ ទទឹង និងកម្ពស់ (ឧទាហរណ៍៖ 300x300 ភីកសែល)
        return BakongQR.generateImageBase64(merchant, 300, 300);
    }
}
```

### ផ្នែកទី ២៖ ការផ្ទៀងផ្ទាត់លុយចូល (Payment Gateway Webhook)
នៅពេលអតិថិជនស្កេនទូទាត់រួច ប្រព័ន្ធធនាគារនឹងបាញ់ទិន្នន័យ JSON (Webhook) មកកាន់ Server របស់អ្នក។ អ្នកអាចប្រើប្រាស់ Class ជំនួយរបស់បណ្ណាល័យនេះដើម្បីធ្វើការឆែកមើល និងផ្ទៀងផ្ទាត់សាច់ប្រាក់ដោយស្វ័យប្រវត្តិតែមួយបន្ទាត់គត់៖

```java
import com.github.chimborey.bakongsdk.utils.BakongWebhook;
import java.util.Map;

public class WebhookController {

    // មុខងារសម្រាប់ទទួល និងផ្ទៀងផ្ទាត់ទិន្នន័យលុយចូលពីធនាគារ
    public void handleBankCallback(Map<String, Object> bankJsonData) {
        
        double expectedAmount = 15.50;       // ចំនួនទឹកប្រាក់ដែលប្រព័ន្ធយើងរំពឹងទុក (តម្លៃទំនិញ)
        String expectedBill = "INV-9999";   // លេខវិក្កយបត្រដែលប្រព័ន្ធយើងរំពឹងទុក

        // ហៅបណ្ណាល័យមកផ្ទៀងផ្ទាត់គណិតវិទ្យាសុវត្ថិភាពភ្លាមៗ
        boolean isPaidSuccess = BakongWebhook.verifyPayment(bankJsonData, expectedAmount, expectedBill);

        if (isPaidSuccess) {
            System.out.println("🎉 លុយចូលត្រឹមត្រូវពិតប្រាកដហើយ! បញ្ជាឱ្យប្រព័ន្ធកាត់ទំនិញឱ្យម៉ូយ auto ភ្លាម!");
            // ទៅសរសេរកូដ Update ស្ថានភាពក្នុង Database របស់អ្នកទៅជា "PAID"
        } else {
            System.err.println("❌ ព្រមាន៖ ការទូទាត់មិនត្រឹមត្រូវ ឬមានការបន្លំទិន្នន័យ!");
        }
    }
}
```

---

## 💡 ការយកទិន្នន័យរូបភាពទៅបង្ហាញនៅលើទំព័រ Web ឬ Mobile App

នៅពេលដែលអ្នកហៅប្រើប្រាស់មុខងារ `generateImageBase64()` អ្នកនឹងទទួលបានអត្ថបទកូដរូបភាពទម្រង់វែង (Base64 String)។ វិធីយកទៅបង្ហាញ៖

* **សម្រាប់ទំព័រ Web (HTML):** យកអត្ថបទកូដនោះទៅដាក់ក្នុង Attribute `src` នៃ Tag `<img>` ផ្ទាល់តែម្ដង វានឹងលោតចេញជារូបភាព auto៖
  ```html
  <img src="data:image/png;base64,iVBORw0KGgoAAAANS..." alt="Bakong QR" width="300" />
  ```
* **សម្រាប់ Mobile App (Flutter / React Native):** យកអត្ថបទកូដរូបភាពនោះទៅហៅបង្ហាញតាមរយៈ Image Memory Component ដោយមិនបាច់ចំណាយទំហំផ្ទុករូបភាពនៅក្នុង Server នាំតែធ្ងន់ម៉ាស៊ីនឡើយ។

---

## 🔒 ប្រព័ន្ធការពារសុវត្ថិភាពទិន្នន័យ (Input Validation)

បណ្ណាល័យនេះមានប្រព័ន្ធត្រួតពិនិត្យទិន្នន័យបញ្ចូល (Built-in Data Validation) យ៉ាងហ្មត់ចត់៖
* ប្រសិនបើមិនបានបំពេញ `accountId` ឬ `merchantName` នោះប្រព័ន្ធនឹងបដិសេធ និងលោតសារ Error ព្រមានភ្លាមៗដើម្បីការពារការបង្កើត QR ខូច។
* ប្រសិនបើបំពេញចំនួនទឹកប្រាក់អវិជ្ជមាន (តូចជាង ឬស្មើ ០) ប្រព័ន្ធនឹងទប់ស្កាត់មិនឱ្យបង្កើត QR Code ឡើយ ដើម្បីធានាសុវត្ថិភាពដាច់ខាតក្នុងប្រតិបត្តិការសាច់ប្រាក់។

---
## 📄 អាជ្ញាប័ណ្ណ (License)
គម្រោងនេះបើកចំហកូដជាសាធារណៈ (Open-source) ក្រោមលក្ខខណ្ឌ ផ្ដល់សិទ្ធិដោយ **MIT License**។
