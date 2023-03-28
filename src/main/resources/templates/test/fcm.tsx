import messaging from '@react-native-firebase/messaging';
import Toast from 'react-native-simple-toast';

// 구독하기
const callApiSubscribeTopic= (topic: string = 'Car') => {

    const getFcmToken = useCallback(async () => {
        const fcmToken = await messaging().getToken();
        await Alert.alert(fcmToken);
        await dispatch(actions.requestNotification(fcmToken));
      }, [dispatch]);

    //   return instance.post('/push');
    return messaging()
        .subscribeToTopic(topic)
        .then(() => {
            Toast.showWithGravity(`${topic} 구독 !!`, Toast.LONG, Toast.TOP);
        })
        .catch(() => {
            Toast.showWithGravity(`${topic} 구독ㄴ`, Toast.LONG, Toast.TOP);
        });
}
export default callApiSubscribeTopic;