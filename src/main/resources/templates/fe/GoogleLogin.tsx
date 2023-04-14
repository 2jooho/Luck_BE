import React from 'react';
import styled from "styled-components/native";
import {colors} from "../Color"
import {Ionicons} from "@expo/vector-icons";

import auth from '@react-native-firebase/auth';
import { GoogleSignin, GoogleSigninButton, statusCodes } from '@react-native-google-signin/google-signin';

const SocialSign = styled.TouchableOpacity`
    flex-direction: row;
    width: 70%;
    height: 45px;
    margin: 10px 0px;
    border-radius: 15px;
    align-items: center;
    justify-content: center;
`
const SocialText = styled.Text`
    color: white;
    font-size: 23px;
    font-family: "SDChild";
    font-size: 20px;
    padding: 0px 10px ;
`

const GoogleLogin = () => {
    const [isLoadingEnd, setIsLoadingEnd] = useState(false);

    const checkLoggedIn = () => {
        auth().onAuthStateChanged((user) => {
                if (user) {
                    setLoggedIn(true)
                    console.log("loggedIn")
                } else {
                    setLoggedIn(false)
                    console.log("loggedOut")
                }
            }
        )
    }

    useEffect(()=> {
        checkLoggedIn();
    }, [])

    //구글소셜로그인
    const onGoogleButtonPress = async () => {
        // 첫번째 줄은 구글에 로그인하면서 유저의 idToken을 가져온다.
        //     두번째 줄은 가져온 유저의 idToken을 이용하여 Google credential을 생성한다.
        //     마지막 줄은 생성된 credential을 이용해 사용자를 앱으로 로그인 시킨다.
        const { idToken } = await GoogleSignin.signIn();
        console.log("idToken"+idToken);
        const googleCredential = auth.GoogleAuthProvider.credential(idToken);
        return auth().signInWithCredential(googleCredential);
    }

    return (
        <>
            <SocialSign style={{backgroundColor:colors.BLUE}} onPress={() => onGoogleButtonPress()}>
                <Ionicons name="logo-google" size={22} color="white" /><SocialText>구글계정으로 시작하기</SocialText>
            </SocialSign>
        </>
    )
};
export default GoogleLogin;