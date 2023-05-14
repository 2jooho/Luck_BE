package com.example.luck_project.batch.service;

import com.example.luck_project.batch.dto.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageService {

    @Value("${project.properties.firebase-create-scoped}")
    String fireBaseCreateScoped;

    @Value("${project.properties.firebase-api-url}")
    String API_URL;

    private final ObjectMapper objectMapper;

    private String instance;
    private FirebaseMessaging instance2;

    /**
     * 토큰 생성
     * @return
     * @throws IOException
     */
    @PostConstruct
    private void getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";
        log.info("토큰 생성");
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(fireBaseCreateScoped));

        googleCredentials.refreshIfExpired();
        this.instance = googleCredentials.getAccessToken().getTokenValue();
        log.info("토큰 생성 완 : {}", instance);
//        FileInputStream serviceAccount =
//                new FileInputStream("firebase/firebase_service_key.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);
    }

    /**
     * 메시지 전송
     * @param targetToken
     * @param title
     * @param body
     * @throws IOException
     */
    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        log.info("메시지 세팅 : {}/{}/{}", targetToken, title, body);
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + instance)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }

    /**
     * 메시지 생성
     * @param targetToken
     * @param title
     * @param body
     * @return
     * @throws JsonProcessingException
     */
    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
//                        .token(targetToken)
                                .topic(targetToken)
                                .notification(FcmMessage.Notification.builder()
                                        .title(title)
                                        .body(body)
                                        .image(null)
                                        .build()
                                )
                                .build()
                )
                .validate_only(false)
                .build();
        log.info("메시지 세팅 완 : {}", fcmMessage);
        return objectMapper.writeValueAsString(fcmMessage);
    }

//    public BatchResponse sendMessage(MulticastMessage message) throws FirebaseMessagingException {
//        return this.instance2.sendMulticast(message);
//    }

}
