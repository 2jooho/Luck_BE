package com.example.luck_project.secretsmanager;

public interface AwsSecretsManager {
    void init();
    String getServiceSecretValue();
    String getReceiptKeyValue();
}
