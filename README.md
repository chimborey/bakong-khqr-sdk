# បណ្ណាល័យ Bakong KHQR Java SDK 🇰🇭

បណ្ណាល័យកូដ Java ស្តង់ដារ (Enterprise-grade SDK) សម្រាប់ជំនួយដល់ Developer ក្នុងការបង្កើត **Bakong KHQR String** និង **រូបភាព QR Code (Base64)** ដែលត្រឹមត្រូវតាមបច្គេកទេសស្តង់ដារ **EMVCo** របស់ធនាគារជាតិនៃកម្ពុជា (NBC) យ៉ាងរហ័ស និងមានសុវត្ថិភាពខ្ពស់។

---

## 📥 របៀបដំឡើង (Installation)

เพื่อយកបណ្ណាល័យកូដនេះទៅប្រើប្រាស់ក្នុងគម្រោង Java របស់អ្នក (ដូចជា Spring Boot, គម្រោង Java ធម្មតា ឬ Android) អ្នកគ្រាន់តែជ្រើសរើសវិធីដំឡើងទៅតាម Build System របស់គម្រោងអ្នកដូចខាងក្រោម៖

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
    implementation 'com.github.chimborey:bakong-khqr-sdk:v1.0.0'
}
```

---

## 🚀 របៀបយកទៅសរសេរកូដប្រើប្រាស់ (Usage Example)

បងប្អូនអាចបង្កើត Class ថ្មីមួយ (ឧទាហរណ៍៖ `PaymentService.java`) រួចកូពីទម្រង់កូដគំរូទទេខាងក្រោមនេះ យកទៅបំពេញទិន្នន័យក្នុងរង្វង់ក្រចក `()` ដោយខ្លួនឯងបានភ្លាមៗ៖

```java
import com.github.chimborey.bakongsdk.BakongQR;
import com.github.chimborey.bakongsdk.model.MerchantInfo;

public class PaymentService {

    // មុខងារសម្រាប់បង្កើតតែអត្ថបទកូដស្តង់ដារ Bakong KHQR String
    public String getMyBakongString() {
        
        MerchantInfo merchant = new MerchantInfo.Builder()
                .accountId("")       // គណនីបាកង ID ឬលេខទូរស័ព្ទ
                .merchantName("")     // ឈ្មោះហាង
                .merchantCity("")     // ទីក្រុងរបស់ហាង (លំនាំដើម៖ Phnom Penh)
                .amount()             // ចំនួនទឹកប្រាក់ (ឧទាហរណ៍៖ 15.50)
                .currency("")         // ប្រភេទលុយ "USD" ឬ "KHR"
                .billNumber("")       // លេខវិក្កយបត្រ (មិនដាក់ក៏បាន)
                .storeLabel("")       // ស្លាកឈ្មោះបញ្ជរ (មិនដាក់ក៏បាន)
                .build();

        return BakongQR.generateString(merchant);
    }

    // មុខងារសម្រាប់បង្កើតរូបភាព QR Code ជាប្រភេទ Base64 String ភ្លាមៗ
    public String getMyBakongImage() throws Exception {
        
        MerchantInfo merchant = new MerchantInfo.Builder()
                .accountId("")       // គណនីបាកង ID ឬលេខទូរស័ព្ទ
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

---

## 💡 ការយកទិន្នន័យរូបភាពទៅបង្ហាញនៅលើទំព័រ Web ឬ Mobile App

នៅពេលដែលហៅប្រើប្រាស់មុខងារ `BakongQR.generateImageBase64()` វានឹងហៅត្រឡប់មកវិញជាអត្ថបទកូដរូបភាពវែង (Base64)។ អ្នកអាចយកវាទៅបង្ហាញលើអេក្រង់បានយ៉ាងងាយស្រួល៖

* **សម្រាប់ទំព័រ Web (HTML):** យកទៅដាក់ក្នុង Attribute `src` នៃ Tag `<img>` ផ្ទាល់តែម្ដង៖
  ```html
  <img src="data:image/png;base64,iVBORw0KGgoAAAANSU..." alt="Bakong QR Code" width="300" />
  ```
* **传统 Mobile App (Flutter / React Native):** យកអត្ថបទកូដរូបភាពនោះទៅហៅបង្ហាញតាមរយៈ Image Memory Component ដោយមិនបាច់ចំណាយទំហំផ្ទុករូបភាពនៅក្នុង Server ឡើយ។

---

## 🔒 ប្រព័ន្ធការពារសុវត្ថិភាពទិន្នន័យ (Input Validation)

បណ្ណាល័យនេះមានប្រព័ន្ធត្រួតពិនិត្យទិន្នន័យបញ្ចូល (Built-in Validation) យ៉ាងហ្មត់ចត់៖
* ប្រសិនបើមិនបានបំពេញ `accountId` ឬ `merchantName` នោះប្រព័ន្ធនឹងលោតសារ Error ព្រមានភ្លាមៗ។
* ប្រសិនបើបំពេញចំនួនទឹកប្រាក់អវិជ្ជមាន (តូចជាង ឬស្មើ ០) ប្រព័ន្ធនឹងទប់ស្កាត់មិនឱ្យបង្កើត QR Code ឡើយ ដើម្បីធានាសុវត្ថិភាពដាច់ខាតក្នុងប្រតិបត្តិការសាច់ប្រាក់។

---
## 📄 អាជ្ញាប័ណ្ណ (License)
គម្រោងនេះបើកចំហកូដជាសាធារណៈ (Open-source) ក្រោមលក្ខខណ្ឌ **MIT License**។
