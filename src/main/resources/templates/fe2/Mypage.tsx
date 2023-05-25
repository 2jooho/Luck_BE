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
import MyStar from "./MyStar";

const Mypage = ({navigation}) => {
    const [loading, setLoading] = useState(false);
    const userData = useSelector((state) => state.userDataSlicer.userData)
    const userId = userData.userId;
    const [userBirth, setUserBirth] = useState('');
    const [pageMode, setPageMode] = useState('M'); //M : 마이페이지, S: 나의 별

    // 외부연동
    // axios
    //리액트쿼리 useMutation(post, delete, put 방식에 많이 사용된다.)
    const {data: userInfo} = useQuery('MyPage', myPageApi(userId), {
            retry: false,
            onSuccess: (data) => {
                const birth = data.birth;
                setUserBirth(birth.substr(0,4) + "년" + birth.substr(5,2) + "월" + birth.substr(7,2) + "일")
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
            {/* 상단 메뉴바 */}
            <StatusBar barStyle="light-content" />
            <MyPageHeader navigation={navigation} />

            {/*본체*/}
            <View style={styles.TopView}>
                <View style={styles.ProfileView}>
                    <Image style={styles.ProfileImg}
                           source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/mypage/myprofile.png'}}>
                    </Image>
                </View>
                <View style={styles.UserInfoView}>
                    {/*정보수정 버튼*/}
                    <View>
                        <Pressable onPress={() => navigation.navigate('CateList2')}>
                            <View style={styles.EditBtnView}><Text style={styles.EditBtnText}>정보 수정</Text></View>
                        </Pressable>
                    </View>
                    {/*정보*/}
                    <View style={styles.UserInfoSmallView}>
                        <Text style={styles.UserIdText}>{userId}</Text>
                        <Text style={styles.UserBirth}>{userBirth}</Text>
                        <Text style={styles.UserLuckData}>{userInfo.userLuckData}</Text>
                    </View>
                </View>
            </View>

            {/*하단(mypage | mystar)*/}
            <View>
                {
                    pageMode === 'M' ?
                        <MyPageComponent userInfo={userInfo} setLoading={setLoading} setPageMode={setPageMode}></MyPageComponent>
                    :   <MyStar userId={userId} setLoading={setLoading} loading={loading} setPageMode={setPageMode}></MyStar>
                }
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor:'#8064a0',
    },
    ProfileView: {
      width: wp(10),
      height: hp(10),
      alignItems: 'center',
    },
    ProfileImg: {
        resizeMode: 'contain',
    },
    UserInfoView: {
      width: wp(10),
      height: hp(10),
      flexDirection: 'row'
    },
    EditBtnView: {
      width: '100%',
      height: hp(3),
      alignItems: 'center',
      borderColor: '#ffffff',
      backgroundColor: '#8064a0',
    },
    EditBtnText: {
        textAlign: 'center',
        color: '#ffffff',
        fontSize: wp(2),
        fontWeight: 300,
    },
    UserInfoSmallView: {
        width: '100%',
        height: '100%',
        alignItems: 'flex-start',
    },
    UserIdText: {
        fontSize: wp(5),
        fontWeight: 700,
        color: '#000000'
    },
    UserBirth: {
        fontSize: wp(4),
        fontWeight: 500,
        color: '#000000'
    },
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

export default Mypage;