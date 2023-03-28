//package com.example.luck_project.secretsmanager;
//
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.secretsmanager.AWSSecretsManager;
//import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
//import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
//import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
//import com.example.luck_project.exception.CustomException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.nio.ByteBuffer;
//import java.util.Map;
//
//@Slf4j
//@Component
//@Profile("!unit-test & !local")
//public class AwsSecretsManagerProvider implements AwsSecretsManager {
//    //    @Value()
//    public String saltSecretName;
//    //    @Value()
//    public String saltKeyName;
//
//    //    @Value()
//    public String giftSecretName;
//    //    @Value()
//    public String giftKeyName;
////    @Value()
//    public String serviceSecretName;
//    //    @Value()
//    public String serviceKeyName;
//    //    @Value()
//    public String receiptSecretName;
//    //    @Value()
//    public String receiptKeyName;
//
//    private static final String endpoint = "secretsmanager.amazonaws.com";
//    private static final String region = "ap";
//    private static final String version_stage = "AWS";
//
//    private AWSSecretsManager secretsManagerClient;
//    private ObjectMapper objectMapper;
//
//    public String saltSecretValue;
//    public String giftSecretValue;
//    public String serviceSecretValue;
//    public String receiptKeyValue;
//
//    @Override
//    @PostConstruct
//    public void init() {
//        this.objectMapper = new ObjectMapper();
//        this.createSecretsManagerClient();
//
//        this.saltSecretValue = this.getValueBySecretName(saltSecretName, saltKeyName);
//        this.giftSecretValue = this.getValueBySecretName(giftSecretName, giftKeyName);
//        this.serviceSecretValue = this.getValueBySecretName(serviceSecretName, serviceKeyName);
//        this.receiptKeyValue = this.getValueBySecretName(receiptSecretName, receiptKeyName);
//    }
//
//    @Override
//    public String getServiceSecretValue() {
//        return this.serviceSecretValue;
//    }
//
//    @Override
//    public String getReceiptKeyValue() {
//        return this.receiptKeyValue;
//    }
//
//    private String getValueBySecretName(String secretName, String keyName) {
//        String result = null;
//        try {
//            Map<String, String> map = objectMapper.readValue(this.getSecretValue(secretName), Map.class);
//            result = map.get(keyName);
//        } catch (JsonProcessingException e) {
//            log.error("AwsSecretsManagerProvider readValue Error ", e);
//        }
//        return result;
//    }
//
//    /**
//     * 시크릿 매니저 키로 값을 가져오기 위한 클라이언트 세팅
//     */
//    private void createSecretsManagerClient() {
//        log.info("AWS 시크릿 매니저 키로 값을 가져오기 위한 Client 세팅");
//        AwsClientBuilder.EndpointConfiguration configuration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
//        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
//        clientBuilder.setEndpointConfiguration(configuration);
//        this.secretsManagerClient = clientBuilder.build();
//    }
//
//    private GetSecretValueRequest createSecretValueRequest(String secretName) {
//        return new GetSecretValueRequest().withSecretId(secretName).withVersionStage(version_stage);
//    }
//
//    private String getSecretValue(String secretName) {
//        GetSecretValueRequest request = this.createSecretValueRequest(secretName);
//        GetSecretValueResult result;
//        try {
//            result = this.secretsManagerClient.getSecretValue(request);
//        } catch (Exception e) {
//            log.error("AWS Secret Manager getValue Error ", e);
//            throw new CustomException();
//        }
//
//        if(StringUtils.isNotBlank(result.getSecretString())) {
//            return result.getSecretString();
//        }
//
//        ByteBuffer binaryResult = result.getSecretBinary();
//        return binaryResult.toString();
//    }
//
//}
