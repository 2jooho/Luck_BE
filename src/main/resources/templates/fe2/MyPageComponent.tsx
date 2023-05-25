import axios from 'axios';
import React, { useState, useEffect } from 'react';
import {View, Text, SafeAreaView, FlatList, ActivityIndicator, StyleSheet, StatusBar, Image, Clipboard} from 'react-native';
import { Colors } from 'react-native/Libraries/NewAppScreen';
import FeedSection from '../components/FeedSection';
import CateListHeader from '../components/CateListHeader';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";
import Loading from '../components/Loading'
import {myPage, myPageApi} from "../api/MypageApi";
import MyPageHeader from "./MyPageHeader";
import MyPageLuck from "./MyPageLuck";
import {MyStarApi} from "../api/MyStarApi";

const MyPageComponent = ({userInfo, setPageMode, setLoading}) => {
    const copyToClipboard = ({copyData}:any) => {
        Clipboard.setString(copyData);
    };

    return (
        <SafeAreaView style={styles.container}>
            <ImageBackground source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/mypageView/mainbox01.png'}} style={styles.MyPageBoxView}>
                <View>
                    <Text>'나'라는 사람은?</Text>
                </View>
                <View style={styles.Line}></View>
                {/*천간*/}
                <View style={styles.TopView}>
                    <MyPageLuck data = {userInfo.myLuckTopDto}></MyPageLuck>
                </View>
                {/*지지*/}
                <View>
                    <MyPageLuck data = {userInfo.myLuckBtmDto}></MyPageLuck>
                </View>
            </ImageBackground>
            {/*추천코드*/}
            <View><Text>{userInfo.recomendCode}</Text></View>
            {/*선택버튼*/}
            <View>
                <Pressable onPress={() => copyToClipboard(userInfo.recomendCode)}>
                    <View><Text>추천코드복사</Text></View></Pressable>
                {/*<Pressable onPress={() => copyToClipboard("")}><View><Text>친구초대하기</Text></View></Pressable>*/}
            </View>
            {/*리워드 이동 버튼*/}
            <Pressable onPress={() => {setPageMode('S'); setLoading(true);}}><View></View></Pressable>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    UserLuckData: {
        fontSize: wp(3),
        fontWeight: 300,
        color: '#000000'
    },
    MyPageBoxView: {
        width: wp(10),
        height: hp(10),
        resizeMode: 'contain',
    },
    Line:{
        width: wp(10),
        height: hp(1),
        backgroundColor: '#8064a0',
        alignSelf: 'center'
    },
    TopView: {

    }
})

export default MyPageComponent;