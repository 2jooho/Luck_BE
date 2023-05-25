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

const MyStar = ({userId, setLoading, loading, setPageMode}) => {
    const arr = [];
    // 외부연동
    // axios
    //리액트쿼리 useMutation(post, delete, put 방식에 많이 사용된다.)
    const {data: userStarInfo} = useQuery('MyStar', MyStarApi(userId), {
            retry: false,
            onSuccess: (data) => {
                for (let i = 0; i < data.myRecommandStarCnt; i++) {
                    arr.push(i);
                }
                setLoading(false);
            },
            onError: (error:unknown) => {
                if(error != null){
                    alert(error);
                    setLoading(false);
                }else{
                    alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
                    setLoading(false);
                }
            },
        }
    );


    return (
        loading ? <Loading /> :
        <SafeAreaView style={styles.container}>
            <ImageBackground source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/mypageView/mainbox01.png'}} style={styles.MyPageBoxView}>
                <View>
                    <Text style={styles.TitleText}>나의 행운의 별</Text>
                </View>
                <View style={styles.Line}></View>
                {/*상단 현황판*/}
                <View style={styles.BoardView}>
                    <ImageBackground source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/myStar/mainbox-in01.png'}} style={styles.MyStarBoxView}>
                        <FlatList
                            data={arr}
                            keyExtractor={(_, index) => index.toString()}
                            renderItem = {({ item }) => {
                                return (
                                    <View key = {item} style = {styles.StarView}>
                                        item === 1 ?
                                        <Image style={styles.Star}
                                               source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/myStar/mainbox-in01.png'}}  //이미지경로
                                               resizeMode="contain">
                                        </Image>
                                        :(item === 5 ?
                                        <Image style={styles.Star}
                                               source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/myStar/starfive.png'}}  //이미지경로
                                               resizeMode="contain">
                                        </Image>
                                        :(item === 10 ?
                                        <Image style={styles.Star}
                                               source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/myStar/starten.png'}}  //이미지경로
                                               resizeMode="contain">
                                        </Image>
                                        :
                                        <Image style={styles.Star}
                                               source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/myStar/starone.png'}}  //이미지경로
                                               resizeMode="contain">
                                        </Image>
                                        )
                                        )
                                    </View>
                                )
                            }}
                        />
                    </ImageBackground>
                </View>
                {/*리워드 이동 버튼*/}
                <Pressable onPress={() => {setPageMode('M')}}><View></View></Pressable>
            </ImageBackground>

            {/*하단 알림*/}
            <View style={styles.NoticeView}>
                <Image style={styles.NoticeImg}
                       source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/myStar/mainbox07.png'}}>
                </Image>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor:'#8064a0',
    },
    MyPageBoxView: {
        width: wp(10),
        height: hp(10),
        resizeMode: 'contain',
        alignItems: 'center',
    },
    Line:{
        width: wp(10),
        height: hp(1),
        backgroundColor: '#8064a0',
    },
    TitleText:{
        fontSize: wp(2),
        fontWeight: 200,
        color: '#000000',
        textAlign: 'center'
    },
    MyStarBoxView: {
      width: wp(5),
      height: hp(5),
    },
    BoardView: {
        width: wp(10),
        height: hp(10),
    },
    NoticeView: {
        width: wp(10),
        height: hp(10),
        borderRadius: 10,
        borderColor: '#8064a0',
        borderWidth: 5,
    },
    NoticeImg:{
        resizeMode: 'contain',
    },
    StarView: {

    },
    Star:{

    }

})

export default MyStar;