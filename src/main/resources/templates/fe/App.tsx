// import React from 'react';
// import Main from './src/screens/Main';
// import 'react-native-gesture-handler';
// import {NavigationContainer} from '@react-navigation/native';
// import {createStackNavigator} from '@react-navigation/stack';
// // import CateList from './src/screens/CateList';

// interface Props {}
// interface State {}

// const Stack = createStackNavigator();

// function App() {
//   return (
// <NavigationContainer>
//   <Stack.Navigator initialRouteName="main">
//     <Stack.Screen name="main" component={Main} />
//   </Stack.Navigator>
// </NavigationContainer>
//   );
// }

// export default App;

import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import {NavigationContainer} from '@react-navigation/native'; 
import {useEffect, useState} from 'react';
import * as Font from 'expo-font';
import {Text, View, StyleSheet, Alert} from 'react-native';
import SplashScreen from 'react-native-splash-screen';
import AsyncStorage from '@react-native-community/async-storage';
import DrawerNavigator from './src/route/DrawerNavigator';
import StackNavigator from './src/route/StackNavigator';
import { QueryClient, QueryClientProvider } from "react-query";
import { GoogleSignin } from '@react-native-google-signin/google-signin';

//import 목록
//npm install -save axios
// npm i react-native-responsive-screen
// 스플래시 이미지 라이브러리 npm i react-native-splash-screen --save
// 반응형 디자인을 위한 라이브러리 npm install --save react-native-responsive-dimensions
//체크박스 라이브러리 npm i react-native-bouncy-checkbox --save
//shadow 관련 라이브러리 npm install react-native-shadow-2
//셀렉트 라이브러리 npm install @react-native-picker/picker  and npm install react-native-picker-select and npm install watcher and npm install react-native-svg@9
//셀렉트 박스 멀티 라이브러리 npm install react-native-multi-selectbox
//날짜 관련 라이브러리 yarn add @react-native-community/datetimepicker
//날짜 관련 라이브러리 npm install react-native-date-picker
//날짜 형식 변환 라이브러리 npm install moment --save
//asyncStorage 사용 라이브러리 npm i @react-native-community/async-storage

//실행
// npm run start
// react-native run-android

// 클린
//cd android && gradlew clean

//안드로이드 apk 생성 라이브러리
//npx react-native bundle --platform android --dev false --entry-file index.js --bundle-output android/app/src/main/assets/index.android.bundle --assets-dest android/app/src/main/res/

//네비게이션 props 전달 라이브러리
// npm install @react-navigation/native
// npm install @react-navigation/native-stack
// npm install @react-navigation/stack
// npm install react-native-safe-area-context

//디바이스 하드드라이브 데이터 저장
// npm i @react-native-community/async-storage

//fcm라이브러리
//npm install @react-native-firebase/app @react-native-firebase/messaging

//drawer 라이브러리
// npm install @react-navigation/drawer

//리액트 쿼리
// npm i react-query

//리덕스 툴킷
//npm install @reduxjs/toolkit

//구글 로그인
// npm install @react-native-google-signin/google-signin --save

//모달창
//npm install react-native-simple-modal --save

//encrypted-storage
//npm install react-native-encrypted-storage

//핸드폰 정보 조회
//npm install --save react-native-device-info

//앱 버전 정보 조회
//npm install --save react-native-version-check

//env라이브러리
//npm install -dev react-native-dotenv

//파일시스템 처리 라이브러리
//npm install --save react-native-fs
//react-native link react-native-fs


const App: React.FunctionComponent = () => {

  const googleSigninConfigure = () => {
    GoogleSignin.configure({ webClientId: '613725492005-eao3uk13qkbqhllhnguaola7h8e1gh37.apps.googleusercontent.com'})
  }
  useEffect(()=>{
    googleSigninConfigure();
  },[])

// ...code

  const [fontLoad, setFontLoad] = useState(false);
  const [authorizationYn, setAuthorization] = useState('N');
  // font 불러오기
  useEffect(() => {
    try {
      setTimeout(() => {
        SplashScreen.hide();
        AsyncStorage.getItem('Authorization')
        .then((value) => {
          console.log(value);
        })
      }, 1000); //스플래시 활성화 시간 1초
    } catch (e) {
      console.log(e.message);
    }

    const Load = async () => {
      await Font.loadAsync({
        NEXONLv1GothicBold: require('./src/assets/fonts/NEXONLv1GothicBold.ttf'),
      });
      setFontLoad(true);
    };
    Load();
  }, []);

  const queryClient = new QueryClient();

  return fontLoad ? (
    <NavigationContainer>
      <QueryClientProvider client={queryClient}>
        <StackNavigator></StackNavigator>
        <DrawerNavigator></DrawerNavigator>
      </QueryClientProvider>
    </NavigationContainer>
  ) 
  : (
    <View style={styles.appLoading}>
      <Text>Loading...</Text>
    </View>
  );
};

// ' Loading...' 중앙 정렬
const styles = StyleSheet.create({
  appLoading: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default App;
