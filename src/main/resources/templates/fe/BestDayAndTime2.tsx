
import React, {useState} from 'react';
import {StyleSheet, Image, Text, SafeAreaView, View, ImageBackground} from 'react-native';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";

const BestDayAndTime = ({data}: any) => {
  return (
      <SafeAreaView style={styles.container}>
          <View>
              <View style={styles.TopView}>
                  <Text style={styles.TopText}>가장 좋은 날과 시간 BEST</Text>
              </View>
              <ImageBackground style={styles.BestMainImage}
                               source={require("../assets/images/pureMain/box-001.png")}  //이미지경로
                               resizeMode="contain">
                <FlatList
                    data={data}
                    keyExtractor={(_, index) => index.toString()}
                    renderItem = {({ item }) => {
                      return (
                          <View style = {styles.containerView}>
                            <Image style={styles.FirstImg}
                                   source={require("../assets/images/pureMain/box-002.png")}  //이미지경로
                                   resizeMode="contain">
                            </Image>
                            <Text style={styles.FirstDayText}>{item.bestDate}</Text>
                            <Text style={styles.FirstTimeText}>{item.bestTime}시</Text>
                            <Image style={styles.FirstIconImg}
                                   source={{uri : item.versYearImgUrl}}  //이미지경로
                                   resizeMode="contain">
                            </Image>
                          </View>
                      )
                    }}
                />
              </ImageBackground>
          </View>
      </SafeAreaView>

        {/*// <Image style={styles.SecondImg}*/}
        {/*//         source={require("../assets/images/pureMain/box-003.png")}  //이미지경로*/}
        {/*//         resizeMode="contain">*/}
        {/*// </Image>*/}
        {/*// <Text style={styles.SecondDayText}>{data.bestDayAndTimeList[1]?.bestDate}</Text>*/}
        {/*// <Text style={styles.SecondTimeText}>{data.bestDayAndTimeList[1]?.bestTime}시</Text>*/}
        {/*// <Image style={styles.SecondIconImg}*/}
        {/*//         source={{uri : data.bestDayAndTimeList[1]?.versYearImgUrl}}  //이미지경로*/}
        {/*//         resizeMode="contain">*/}
        {/*// </Image>*/}



  );
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
    },
    containerView: {
      flex: 1,
      marginTop: hp(3)
    },
    TopView: {
        marginTop: hp(7),
        marginLeft: wp(10),
        backgroundColor: '#000009',
        width: wp(65),
        height: hp(4.5),
        alignSelf: 'flex-start',
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        borderTopLeftRadius: 10,
        borderTopRightRadius: 10,
    },
    TopText: {
        fontSize: wp(4.5),
        color:'#6d6c23',
        fontWeight: 'bold',
    },
    FirstIconImg: {
      top: hp(4.5),
      left: wp(20),
      position: 'absolute',
      width: wp(13),
    },
    FirstDayText: {
      top: hp(6.3),
      paddingLeft: wp(7),
      fontSize: wp(6),
      color:'rgb(77, 76, 76)',
      fontWeight: 'bold',
      alignSelf:'center',
      position: 'absolute'
    },
    FirstTimeText: {
      top: hp(9.5),
      left: wp(38),
      fontSize: wp(5.4),
      color: 'rgb(199, 21, 175)',
      fontWeight: 'bold',
      alignSelf:'center',
      position: 'absolute'
    },
    FirstImg: {
      marginTop: hp(2.7),
      width: wp(75),
      height: hp(15),
      alignSelf:'center',
    },
    // SecondIconImg: {
    //   top: hp(19),
    //   left: wp(20),
    //   position: 'absolute',
    //   width: wp(13),
    // },
    // SecondTimeText: {
    //   // 182
    //   top: wp(47.5),
    //   left: wp(38),
    //   fontSize: wp(5.4),
    //   color: 'rgb(44, 33, 148)',
    //   fontWeight: 'bold',
    //   alignSelf:'center',
    //   position: 'absolute'
    // },
    // SecondDayText: {
    //   top: wp(41),
    //   paddingLeft: wp(7),
    //   fontSize:wp(6),
    //   color:'rgb(77, 76, 76)',
    //   fontWeight: 'bold',
    //   alignSelf:'center',
    //   position: 'absolute'
    // },
    // SecondImg: {
    //   width: wp(75),
    //   height: hp(15),
    //   alignSelf:'center',
    // }
})

export default BestDayAndTime;

