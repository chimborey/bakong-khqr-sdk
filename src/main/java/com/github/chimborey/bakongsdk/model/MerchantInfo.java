package com.github.chimborey.bakongsdk.model;

import java.util.Objects;

public class MerchantInfo {
    private final String accountId;     // លេខគណនី Bakong ID (ឧទាហរណ៍៖ chimborey@aba) ឬ លេខទូរស័ព្ទ
    private final String merchantName;   // ឈ្មោះហាង
    private final String merchantCity;   // ទីក្រុងរបស់ហាង (លំនាំដើម៖ Phnom Penh)
    private final Double amount;         // ចំនួនទឹកប្រាក់
    private final String currency;       // ប្រភេទលុយ "USD" ឬ "KHR"
    private final String billNumber;     // លេខវិក្កយបត្រ (Invoice Number)
    private final String storeLabel;     // ស្លាកឈ្មោះបញ្ជរ ឬឈ្មោះសាខា

    // Constructor (លាក់ទុកសម្រាប់ប្រើជាមួយ Builder ខាងក្រោម)
    private MerchantInfo(Builder builder) {
        this.accountId = builder.accountId;
        this.merchantName = builder.merchantName;
        this.merchantCity = builder.merchantCity;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.billNumber = builder.billNumber;
        this.storeLabel = builder.storeLabel;
    }

    // --- ផ្នែក Getters សម្រាប់អនុញ្ញាតឱ្យ Class ផ្សេងទាញយកទិន្នន័យ ---
    public String getAccountId() { return accountId; }
    public String getMerchantName() { return merchantName; }
    public String getMerchantCity() { return merchantCity; }
    public Double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getBillNumber() { return billNumber; }
    public String getStoreLabel() { return storeLabel; }

    // --- ផ្នែក Builder Pattern សម្រាប់ជួយសម្រួលដល់ Developer ងាយស្រួលហៅប្រើ ---
    public static class Builder {
        private String accountId, merchantName, merchantCity, billNumber, storeLabel;
        private String currency = "USD"; // កំណត់លំនាំដើមជាលុយដុល្លារ
        private Double amount;

        public Builder accountId(String id) { this.accountId = id; return this; }
        public Builder merchantName(String name) { this.merchantName = name; return this; }
        public Builder merchantCity(String city) { this.merchantCity = city; return this; }
        public Builder amount(Double amount) { this.amount = amount; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder billNumber(String bill) { this.billNumber = bill; return this; }
        public Builder storeLabel(String store) { this.storeLabel = store; return this; }

        public MerchantInfo build() {
            // == ផ្នែក Validation ការពារសុវត្ថិភាពទិន្នន័យបញ្ចូល ==
            Objects.requireNonNull(accountId, "Bakong Account ID or Phone number cannot be null");
            Objects.requireNonNull(merchantName, "Merchant Name cannot be null");

            if (merchantCity == null || merchantCity.trim().isEmpty()) {
                this.merchantCity = "Phnom Penh"; // បើមិនដាក់ទីក្រុង វានឹងយកភ្នំពេញស្វ័យប្រវត្តិ
            }
            if (amount != null && amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }
            if (!"USD".equalsIgnoreCase(currency) && !"KHR".equalsIgnoreCase(currency)) {
                throw new IllegalArgumentException("Currency must be either USD or KHR");
            }

            return new MerchantInfo(this);
        }
    }
}
