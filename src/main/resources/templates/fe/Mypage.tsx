import axios from 'axios';
import React, { useState, useEffect } from 'react';
import {View, Text, SafeAreaView, FlatList, ActivityIndicator, StyleSheet, StatusBar, Image} from 'react-native';
import { Colors } from 'react-native/Libraries/NewAppScreen';
import FeedSection from '../components/FeedSection';
import CateListHeader from '../components/CateListHeader';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";
import Loading from '../components/Loading'
import {myPage, myPageApi} from "../api/MypageApi";

const Mypage = ({navigation}) => {
    const [loading, setLoading] = useState(false);
    const [refreshing, setRefreshing] = useState(false);
    const [error, setError] = useState(null);
    const [users, setUsers]: any = useState([]);
    const data = [{title: 'test', imgUrl:'../assets/images/main/logo.png'}, {title: 'test', imgUrl:'../assets/images/main/logo.png'}, {title: 'test', imgUrl:'../assets/images/main/logo.png'}, {title: 'test', imgUrl:'../assets/images/main/logo.png'}]
    const [request, setRequest] = useState({
        userId: '2week',
        cateCode: '02',
        page: 0,
        pageingSize: 10
    });


    const userData = useSelector((state) => state.userDataSlicer.userData)
    const userId = userData.userId
    // 외부연동
    // axios
    //리액트쿼리 useMutation(post, delete, put 방식에 많이 사용된다.)
    const {data: any} = useQuery('MyPage', myPageApi(userId), {
            retry: false,
            // onSuccess: (data) => {
            //     if(data){
            //         dispatch(setMainPage(data));
            //     }
            // },
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

    const [cateCode, setCateCode] = useState('01');
    const [page, setPage] = useState(0);
    const [pageingSize, setPageingSize] = useState(10);
    
    useEffect(()=> {
        getRefreshData();
    }, [])

    const onEndReached = () => {
        // if(!loading) {
        //     // setPage(page+1);
        //     // setPageingSize(pageingSize+10);
        //     getRefreshData();
        // }
    }
    const _renderItem = ({item}) => (
        <View style={{borderBottomWidth:1, marginTop: 20}}>
          {/* <Image source={require('../' + {item.imgUrl})} style={{ height: 200}} /> */}
          <Text style={{ height: 200}}>{item.imgUrl}</Text>
          <Text>{item.title}</Text>
        </View>
      );

    return (
        loading ? <Loading /> :
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle="light-content" />
                    <CateListHeader navigation ={navigation}/>
            <View style={styles.TopView}>
                <Text style={styles.TopText}>다른 관련 컨텐츠 같이보기</Text>
                
            </View>

            <FlatList
                data={users.cateDetailList}
                // style={styles.container}

                keyExtractor={(_) => _.title}
                // renderItem={_renderItem}
                renderItem = {({ item }) => {
                                      // const { title, content } = item;
                                      return (
                                          <FeedSection item={item.cateImgUrl} navigation={navigation} />
                                      )
                                  }}

                onEndReached={onEndReached}
                onEndReachedThreshold={0.8}

                // ListFooterComponent={loading && <ActivityIndicator />}
                // onRefresh={onRefresh}
                // refreshing={refreshing}
                // horizontal
                // ListHeaderComponent={<StorySection />}
            />
        </SafeAreaView>
    );
};


const styles = StyleSheet.create({
    container: {
        flex: 1
    },
    TopView: {
        marginTop: 20,
        backgroundColor:'#000009',
        width:wp(65),
        height:hp(5),
        alignSelf:'center',
        flexDirection: 'row',
        justifyContent:'center',
        alignItems:'center',
        borderTopLeftRadius:10,
        borderTopRightRadius:10,
    },
    TopText: {
        fontSize:16.5,
        color:'#6d6c23',
        fontWeight: 'bold',
    },
})

export default CateList2;