package com.example.luck_project.batch.controller;

import com.example.luck_project.batch.dto.RequestPushMessage;
import com.example.luck_project.batch.service.FirebaseCloudMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.example.luck_project.controller.constants.ApiUrl.BASE_URL_BATCH;
import static com.example.luck_project.controller.constants.ApiUrl.FCM_TOPICS_URL;

@RestController
@RequestMapping(BASE_URL_BATCH)
public class NotificationsController {

    @Autowired
    FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping(value = FCM_TOPICS_URL)
    public void notificationTopics(@PathVariable("topic") String topic, @RequestBody RequestPushMessage data) {
        try {
            firebaseCloudMessageService.sendMessageTo(topic, data.getTitle(), data.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    /**
//     * 토픽푸시
//     * @param topic
//     * @param data
//     */
//    @PostMapping(value = "pushs/topics/{topic}")
//    public void notificationTopics(@PathVariable("topic") String topic, @RequestBody RequestPushMessage data) {
//        Notification notification = Notification.builder().setTitle(data.getTitle()).setBody(data.getBody()).setImage(data.getImage()).build();
//
//        Message.Builder builder = Message.builder();
//
//        Optional.ofNullable(data.getData()).ifPresent(sit -> builder.putAllData(sit));
//
//        Message msg = builder.setTopic(topic).setNotification(notification).build();
//
//        fcmService.sendMessage(msg);
//
//    }
//
//
//    /**
//     * 전고객 푸시
//     * @param data
//     * @throws IOException
//     * @throws FirebaseMessagingException
//     */
//    @PostMapping(value = "/pushs/users")
//    public void notificationUsers(@RequestBody RequestPushMessage data) throws IOException, FirebaseMessagingException {
//        List<CoreUser> targetUser = null == data.getUserNos() ? coreUserService.findAllByEnabledAndPushTokenIsNotNull(UseCd.USE001) : coreUserService.findAllByEnabledAndPushTokenIsNotNullAndNoIn(UseCd.USE001, data.getUserNos());
//
//        AtomicInteger counter = new AtomicInteger();
//        Collection<List<CoreUser>> sendUserGroups = targetUser.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / multicastMessageSize.longValue())).values();
//
//        for (List<CoreUser> it : sendUserGroups) {
//            Notification notification = Notification.builder().setTitle(data.getTitle()).setBody(data.getBody()).setImage(data.getImage()).build();
//            MulticastMessage.Builder builder = MulticastMessage.builder();
//            Optional.ofNullable(data.getData()).ifPresent(sit -> builder.putAllData(sit));
//            MulticastMessage message = builder
//                    .setNotification(notification)
//                    .addAllTokens(it.stream().map(sit -> sit.getPushToken()).collect(Collectors.toList()))
//                    .build();
//            this.fcmService.sendMessage(message);
//        }
//    }
//
//
//    /**
//     * 특정 고객푸시
//     * @param userId
//     * @param data
//     * @throws FirebaseMessagingException
//     */
//    @PostMapping(value = "/pushs/users/{userId}")
//    public void notificationUser(@PathVariable("userId") String userId, @RequestBody RequestPushMessage data) throws FirebaseMessagingException {
//        Optional<CoreUser> user = coreUserService.findById(no);
//        if (user.isPresent()) {
//            CoreUser it = user.get();
//            Notification notification = Notification.builder().setTitle(data.getTitle()).setBody(data.getBody()).setImage(data.getImage()).build();
//            Message.Builder builder = Message.builder();
//            Optional.ofNullable(data.getData()).ifPresent(sit -> builder.putAllData(sit));
//            Message msg = builder.setToken(it.getPushToken()).setNotification(notification).build();
//            fcmService.sendMessage(msg);
//        }
//    }

}
