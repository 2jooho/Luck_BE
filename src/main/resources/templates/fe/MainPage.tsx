import React, {useState, useCallback, useEffect} from 'react';
import styled from 'styled-components/native';
import {StatusBar, StyleSheet, TouchableOpacity, SafeAreaView, Text, View, Image, ScrollView, RefreshControl, Pressable, BackHandler, Alert} from 'react-native';
import Header from '../components/Header';
import MainHeader from '../components/MainHeader'
import Colors from '../constants/Colors';
import MainCateTitle from '../components/MainCateTitle';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";
import LuckTextComponent from '../components/LuckTextComponent'
import LuckTextImgComponent from '../components/LuckTextImgComponent'
import CateListComponent from '../components/CateListComponent';
import { color } from 'react-native-reanimated';
import axios from 'axios';
import Loading from '../components/Loading';
import AsyncStorage from '@react-native-community/async-storage';
import { useIsFocused } from '@react-navigation/native';
import { MainPageApi } from 'api';
import { useQuery } from 'react-query';
import { getMainPage } from 'api';
import { useDispatch } from 'react-redux';

const wait = (timeout) => {
    return new Promise(resolve => {
      setTimeout(resolve, timeout);
    })
  }

const MainPage = ({navigation, route}) => {
    type MainDate = {
        nickName: string;
    }
    const [response, setResponse] = useState<MainDate[]>([]);
    const [users, setUsers]: any = useState([]);
    const dispatch = useDispatch();
    // const { data: taskData, refetch } = useQuery<TaskDetailDataForm>(
    //     taskQueryKey,
    //     fetchTaskData,
    //     {
    //         ...QUERY.DEFAULT_CONFIG,
    //         enabled: false,
    //         onSuccess: (data) => {
    //             if (data) dispatch(setTicketData(data?.tickets));
    //             dispatch(setLabel('all'));
    //         },
    //         onError: (error: unknown) => errorHandler(error),
    //     }
    // );
    const {data: userData} = useQuery('MainPage', MainPageApi(userId), {
        retry: false,
        // onSuccess: (data) => {
        //     if(data){
        //         dispatch(setMainPage(data));
        //     }
        // },
        onError: (error:unknown) => alert('error'),
        }
    );

    // 외부연동
    // axios
    let REQUEST_URL = 'http://ec2-3-34-36-9.ap-northeast-2.compute.amazonaws.com:8081/luck/main.do';
    const getRefreshData = async () => {
        setLoading(true);
        try{
            const response = await axios.post(REQUEST_URL,
                {userId: userId},
                {
                    headers : {
                        "Content-Type": "application/json"
                        // "Authorization": "" + JSON.parse(AsyncStorage.getItem("accessToken"))
                        // return await EncryptedStorage.getItem('token').then((response) => {return response});
                    }
                }
                );
            console.log(response);
            setUsers(response.data); // 데이터는 response.data 안에 들어있습니다.
            console.log("user:"+users)
            setLoading(false);
        }catch(e){
            alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
            console.log(e);
            setLoading(false);
        }
    }

    const {params} = route;

    AsyncStorage.getItem('userId')
        .then((value) => {
            const storageUserId = JSON.parse(value);
            setUsers(storageUserId);
            console.log('name is ', storageUserId);
        });


    // const {params} = route;
    useEffect(()=> {
        console.log("params:"+ params + "/params.user:"+params.userId);
        getRefreshData();
    }, [])

    const isFocused = useIsFocused(); // isFoucesd Define
    useEffect(() => {
        if(isFocused){
        const backAction = () => {
          Alert.alert("앱 종료", "앱을 종료하시겠습니까?", [
            {
              text: "취소",
              onPress: () => null,
              style: "cancel"
            },
            { text: "확인", onPress: () => {BackHandler.exitApp();
                                            return true;} }
          ]);
          return true;
        };
    
        BackHandler.addEventListener(
          "hardwareBackPress",
          backAction
        );
    
        return () => {
            BackHandler.removeEventListener('hardwareBackPress', backAction)
          }
        }
      }, [isFocused]);

    const [recommendImgUrl, setRecommendImgUrl] = useState(null);
    const [refreshing, setRefreshing] = useState(false);
    const [userId, setUserId] = useState(params.userId);
    const [loading, setLoading] = useState(false);

    //refreshcontrol을 호출할 때 실행되는 callback함수
    const onRefresh = useCallback(() => {
      setRefreshing(true);
      wait(1000).then(() => setRefreshing(false));
    }, [])

    // const recommandImgList = users.recommandCateList.map((cateList) => (
    //     <Pressable onPress={() => navigation.navigate('CateList2')}><Image source={{uri : cateList.cateImgUrl}} style={styles.ImgView}></Image></Pressable>
    // ))

    return(
        loading ? <Loading /> :
        <SafeAreaView style={styles.safeView}>
                    {/* 상단 메뉴바 */}
                    <StatusBar barStyle="light-content" />
                    <MainHeader navigation={navigation} />
            <ScrollView contentContainerStyle={styles.scrollview}>

                {/* 대표 텍스트 */}
                 <View style={styles.luckText}>
                    <LuckTextComponent title={users.pureCntnt}></LuckTextComponent>
                </View>

                {/* 대표 이미지 */}
                <View style={styles.luckTextImg}>
                    <LuckTextImgComponent charactorFlag = {users.charactorFlag}></LuckTextImgComponent>
                </View>
            
                {/* 추천 */}
                <View style={styles.RecommandView}>
                    {/* <CateListComponent title="추천" props={recommendImgUrl} ></CateListComponent> */}
                    {/* {users.cateDtoList.cateImgDto.imgUrl}  */}
                    <Text style={styles.RecommandText}>추천</Text>
                    <View style={styles.RecommandTextView}></View>
                </View>
                <ScrollView horizontal={true}>
                <View style={{marginLeft: 7}}></View>
                {
                    users.recommandCateList ?
                    users.recommandCateList.map((cateList, i) => (
                        <View key={i}>
                            <Pressable onPress={() => navigation.navigate('CateList2')}><Image source={{uri : cateList.cateImgUrl}} style={styles.ImgView}></Image></Pressable>
                        </View>
                    ))
                    : null
                }
                {/* <Pressable onPress={() => navigation.navigate('CateList2')}><Image source={require('../assets/images/main/image-middle-001.jpg')} style={styles.ImgView}></Image></Pressable>
                    <Image source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/image-middle-002.jpg'}} style={styles.ImgView}></Image>
                    <Image source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/image-middle-003.jpg'}} style={styles.ImgView}></Image> */}
                <View style={{marginLeft: 7}}></View>
                </ScrollView>

                <View style={styles.CategoryView}>
                    <Text style={styles.CategoryText}>질문 카테고리</Text>
                    <View style={styles.CateTextView}></View>
                </View>
                <View style={{width: '100%', height: 'auto'}}>
                    <CateListComponent navigation={navigation} cateDtoList={users.cateDtoList}></CateListComponent>
                </View>
                {/* 캘린더 이미지 */}
                <Pressable onPress={() => alert("오픈 준비중")}><Image source={require('../assets/images/main/image-low-001.jpg')} style={styles.ScheduleImgView}></Image></Pressable>
            </ScrollView>
        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    safeView: {
        flex: 1,
        bottom: 0
    },
    scrollview: {
        alignItems: 'center',
        // justifyContent: 'center'
    },
    // header: {
    //     width: wp('100%'),
    //     height: hp('1%'),
    //     backgroundColor: 'orange',
    // },
    luckText: {
        width: wp('80'),
        height: hp('12'),
        // borderColor: '#ff0000',
        // borderWidth: 1,
    }
    ,
    luckTextImg: {
        // top: hp('1%'),
        width: wp('100%'),
        height: hp('22%'),
        alignItems: 'center',
    },
    RecommandView: {
        width: wp('100%'),
        height: hp('5%'),
        flexDirection:'row',
        alignItems: 'center',
        fontSize: 30,
        marginBottom: 5,
    },
    RecommandText: {
        fontSize: 20,
        color: '#000000',
        fontFamily: 'NEXONLv1GothicBold',
        marginLeft: 30,
        position: 'absolute',
        zIndex: 1,
    },
    RecommandTextView: {
        borderRadius: 10,
        backgroundColor: '#eccb38',
        width: wp('20%'),
        height: hp('2.4%'),
        position: 'absolute',
        zIndex: 0,
        left: 9,
        bottom: 0.8,
    },
    CategoryView: {
        width: wp('100%'),
        height: hp('5%'),
        flexDirection:'row',
        alignItems: 'center',
        marginTop: 30,
        marginBottom: 15,
    },

    CategoryText: {
        fontSize: 20,
        color: '#000000',
        fontFamily: 'NEXONLv1GothicBold',
        marginLeft: 30,
        position: 'absolute',
        zIndex: 1,
        
    },
    CateTextView: {
        borderRadius: 10,
        backgroundColor: '#eccb38',
        width: wp('42%'),
        height: hp('2.4%'),
        position: 'absolute',
        zIndex: 0,
        left: 9,
        bottom: 0.8,
    },
    ImgView: {
        // width: wp('100%'),
        // height: hp('15%'),        
        // resizeMode: 'contain',
        // overflow: 'hidden',
        width: 150,
        height: 150,
        resizeMode: 'cover',
        margin: 10,
    },
    ScheduleImgView: {
        width: wp('100%'),
        height: hp('15%'),
        resizeMode: 'contain',
        
    },

})

export default MainPage;
