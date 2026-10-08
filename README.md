# បណ្ណាល័យ Bakong KHQR Java SDK 🇰🇭

បណ្ណាល័យកូដ Java ស្តង់ដារ (Enterprise-grade SDK) សម្រាប់ជំនួយដល់ Developer ក្នុងការបង្កើត **Bakong KHQR String** និង **រូបភាព QR Code (Base64)** ដែលត្រឹមត្រូវតាមបច្ចេកទេសស្តង់ដារ **EMVCo** របស់ធនាគារជាតិនៃកម្ពុជា (NBC) យ៉ាងរហ័ស និងមានសុវត្ថិភាពខ្ពស់។

---

## 📥 របៀបដំឡើង (Installation)

ដើម្បីយកបណ្ណាល័យកូដនេះទៅប្រើប្រាស់ក្នុងគម្រោង Java របស់អ្នក (ដូចជា Spring Boot, មេរៀន Java ធម្មតា ឬ Android) អ្នកគ្រាន់តែជ្រើសរើសវិធីដំឡើងទៅតាម Build System របស់គម្រោងអ្នកដូចខាងក្រោម៖

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
    <version>v1.0.0</version>
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
    implementation 'com.github.chimborey:bakong-khqr-sdk:v1.0.0'
}
```

---

## 🚀 របៀបយកទៅសរសេរកូដប្រើប្រាស់ (Usage Example)

បន្ទាប់ពីដំឡើងរួចរាល់ អ្នកគ្រាន់តែកូពី (Copy) ទម្រង់កូដខាងក្រោមនេះ ទៅដាក់ក្នុងថតកូដរបស់អ្នក ដើម្បីដំណើរការបង្កើត QR Code ភ្លាមៗ៖

```java
import com.github.chimborey.bakongsdk.BakongQR;
import com.github.chimborey.bakongsdk.model.MerchantInfo;

public class Application {
    public static void main(String[] args) {
        try {
            // ជំហានទី ១៖ បំពេញព័ត៌មានគណនី និងហាងរបស់អ្នកលក់ (Merchant Info)
            MerchantInfo merchant = new MerchantInfo.Builder()
                    .accountId("chimborey@aba")       // លេខគណនី Bakong ID ឬលេខទូរស័ព្ទគណនីបាកង
                    .merchantName("Borey Pizza Shop") // ឈ្មោះហាងរបស់អ្នក (ជាអក្សរឡាតាំង)
                    .merchantCity("Phnom Penh")        // ទីក្រុងរបស់ហាង (លំនាំដើម៖ Phnom Penh)
                    .amount(15.50)                     // ចំនួនទឹកប្រាក់ដែលត្រូវគិតលុយ (ឧទាហរណ៍៖ ១៥.៥ ដុល្លារ)
                    .currency("USD")                   // ប្រភេទលុយដែលត្រូវទទួល "USD" ឬ "KHR"
                    .billNumber("INV-2026-001")        // លេខវិក្កយបត្រសម្គាល់ការទូទាត់ (មិនដាក់ក៏បាន)
                    .storeLabel("Main Counter")        // ស្លាកឈ្មោះបញ្ជរ ឬឈ្មោះសាខា (មិនដាក់ក៏បាន)
                    .build();

            // ជំហានទី ២៖ ហៅបង្កើតជាអត្ថបទកូដស្តង់ដារ Bakong KHQR String
            // (សម្រាប់យកទៅប្រើប្រាស់ជាមួយមុខងារ Dynamic Deep Link ឬប្រព័ន្ធផ្សេងៗ)
            String khqrString = BakongQR.generateString(merchant);
            System.out.println("១. លទ្ធផល KHQR String៖ \n" + khqrString);

            // ជំហានទី ៣៖ ហៅបង្កើតជារូបភាព QR Code ជាប្រភេទ Base64 String ភ្លាមៗ
            // ដោយប្ដូរទំហំ ទទឹង និងកម្ពស់ (Width, Height) បានតាមចិត្ត (ឧទាហរណ៍៖ 350x350 ភីកសែល)
            String qrCodeBase64 = BakongQR.generateImageBase64(merchant, 350, 350);
            System.out.println("\n២. លទ្ធផលកូដរូបភាព Base64 ៖ \n" + qrCodeCodeBase64);

        } catch (Exception e) {
            System.err.println("មានបញ្ហាក្នុងការបង្កើត QR Code: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

## 💡 ការយកទិន្នន័យទៅបង្ហាញនៅលើទំព័រ Web ឬ Mobile App

នៅពេលដែលអ្នកហៅប្រើប្រាស់មុខងារ `BakongQR.generateImageBase64()` អ្នកនឹងទទួលបានអត្ថបទកូដរូបភាពវែងមួយ (Base64)។ អ្នកអាចយកវាទៅបង្ហាញលើអេក្រង់បានយ៉ាងងាយស្រួល៖

* **សម្រាប់ទំព័រ Web (HTML):** យកទៅដាក់ក្នុង Attribute `src` នៃ Tag `<img>` ផ្ទាល់តែម្ដង៖
  ```html
  <img src="data:image/png;base64,iVBORw0KGgoAAAANSU..." alt="Bakong QR Code" width="350" />
  ```
* **សម្រាប់ Mobile App (Flutter/React Native):** យកអត្ថបទកូដរូបភាពនោះទៅហៅបង្ហាញតាមរយៈ Image Network/Memory Component ដោយមិនបាច់រក្សាទុករូបភាពនៅក្នុង Server ឡើយ។

---

## 🔒 ប្រព័ន្ធការពារសុវត្ថិភាពទិន្នន័យ (Input Validation)

បណ្ណាល័យនេះមានប្រព័ន្ធត្រួតពិនិត្យទិន្នន័យបញ្ចូល (Built-in Validation) យ៉ាងហ្មត់ចត់៖
* ប្រសិនបើមិនបានបំពេញ `accountId` ឬ `merchantName` នោះប្រព័ន្ធនឹងលោតសារ Error ព្រមានភ្លាមៗ។
* ប្រសិនបើបំពេញចំនួនទឹកប្រាក់អវិជ្ជមាន (តូចជាង ឬស្មើ ០) ប្រព័ន្ធនឹងទប់ស្កាត់មិនឱ្យបង្កើត QR Code ឡើយ ដើម្បីធានាសុវត្ថិភាពដាច់ខាតក្នុងប្រតិបត្តិការសាច់ប្រាក់។
