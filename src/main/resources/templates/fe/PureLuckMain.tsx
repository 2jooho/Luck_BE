import axios from 'axios';
import React, { useState, useEffect } from 'react';
import {View, Text, SafeAreaView, FlatList, ActivityIndicator, StyleSheet, StatusBar, Image, ImageBackground} from 'react-native';
import { Colors } from 'react-native/Libraries/NewAppScreen';
import FeedSection from '../components/FeedSection';
import CateListHeader from '../components/CateListHeader';
import BestDayAndTime from '../components/BestDayAndTime';
import TodayBestDayAndTime from '../components/TodayBestDayAndTime';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";
import Loading from '../components/Loading'

const PureLuckMain = ({navigator}) => {
    const [loading, setLoading] = useState(false);
    const [refreshing, setRefreshing] = useState(false);
    const [error, setError] = useState(null);
    const [users, setUsers] : any = useState([]);
    // const data = [{title: 'test', imgUrl:'../assets/images/main/logo.png'}, {title: 'test', imgUrl:'../assets/images/main/logo.png'}, {title: 'test', imgUrl:'../assets/images/main/logo.png'}, {title: 'test', imgUrl:'../assets/images/main/logo.png'}]

    // const [request, setRequest] = useState({
    //     userId: '2week',
    //     cateCode: '02',
    //     page: 0,
    //     pageingSize: 10
    // });
   
    const [userId, setUserId] = useState('2WEEK');
    const [pureCnctn, setPureCnctn] = useState('인술');
    const [todayVersYear, setTodayVersYear] = useState('술');
    const [cateCode, setCateCode] = useState('01');
    const [cateDetailCode, setCateDetailCode] = useState('A0001');

    // 외부연동
    // axios
    let REQUEST_URL = 'http://ec2-3-34-36-9.ap-northeast-2.compute.amazonaws.com:8081/luck/pureLuckMain.do';
    const getRefreshData = async () => {
        setLoading(true);
        try{
            const response = await axios.post(REQUEST_URL,
                {
                 userId: userId,
                 pureCnctn: pureCnctn,
                 todayVersYear: todayVersYear,
                 cateCode: cateCode,
                 cateDetailCode: cateDetailCode
                }
                );
            console.log(response);
            setUsers(response.data); // 데이터는 response.data 안에 들어있습니다.
            setLoading(false);
        }catch(e){
            alert("서버 오류가 발생하였습니다.");
            alert(e);
            console.log(e);
            setLoading(false);
        }
    }

    useEffect(()=> {
        getRefreshData();
    }, [])

    return loading ? <Loading /> :
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle="light-content" />
                    <CateListHeader navigation={navigator} />
            <View>
                <ImageBackground style={styles.BackgrounImgView}
                    source={require("../assets/images/pureMain/background.jpg")}  //이미지경로
                    resizeMode="cover">

                <View style={styles.BestDayAndTimeView}>
                    <BestDayAndTime2 data = {users.bestDayAndTimeList}></BestDayAndTime2>
                </View>

                <View>
                <Text style={styles.TodayText}> 당장 급하면 오늘과 내일 {'\n     '}좋은 시간이라도..</Text>
                    <TodayBestDayAndTime2 data = {users.todayBestTimeList}></TodayBestDayAndTime2>
                </View>

            </ImageBackground>
            </View>
            
        </SafeAreaView>
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    BackgrounImgView: {
        width: wp(100),
        height: hp(100),
    },
    TodayText: {
        alignSelf: 'center',
        fontSize: wp(5),
        color: 'rgb(77, 76, 76)',
        fontFamily: 'NEXONLv1GothicBold',
        marginTop: hp(29),
    },
    BestDayAndTimeView: {
        alignSelf: 'center',
        width: wp(100),
        height: hp(25),
    },
    appLoading: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      },
    BestMainImage: {
        width: wp(100),
        height: wp(75),
    },
})

export default PureLuckMain;