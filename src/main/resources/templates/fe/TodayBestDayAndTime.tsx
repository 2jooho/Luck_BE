
import React, {useState} from 'react';
import {StyleSheet, Image, Text, SafeAreaView, View} from 'react-native';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";

const BestDayAndTime = ({bestDayAndTimeDate} : any) => {

  return (
    <SafeAreaView style={styles.container}>
      <View>
      <Image style={styles.characterImg}
          source={require("../assets/images/pureMain/back-character.png")}  //이미지경로
          resizeMode="contain">
          </Image>
          <Image style={styles.FirstViewImg}
              source={require("../assets/images/pureMain/box-004.png")}  //이미지경로
              resizeMode="contain">
        </Image>
          <Image style={styles.FirstIconImg}
                 source={{uri : bestDayAndTimeDate.todayBestTimeList[0]?.timeVersYearImg}}  //이미지경로
                 resizeMode="contain">
          </Image>
          <Text style={styles.FirstDayText}>오늘은
            <Text style={{fontSize:wp(4.3)}}> {bestDayAndTimeDate.todayBestTimeList[0]?.bestTime}</Text>
        시가 좋아요!
        </Text>
        {/* <Image style={styles.SecondViewImg}
                source={require("../assets/images/pureMain/box-004.png")}  //이미지경로
                resizeMode="contain">
        </Image>
        <Image style={styles.SecondIconImg}
                source={require("../assets/images/pureMain/icon-002.png")}  //이미지경로
                resizeMode="contain">
        </Image>
        <Text style={styles.SecondDayText}>오늘은
        <Text>15~17시</Text>
        가 좋아요!
        </Text> */}
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
    container: {
      flex: 1
  },
  characterImg: {
    width: wp(22),
    alignSelf: 'flex-end',
    right: wp(14),
    top: hp(-4.2),
    position: 'absolute',
  },
  FirstViewImg: {
    width: wp(80),
    alignSelf: 'center',
    marginTop: hp(4),
  },
  SecondViewImg: {
    width: wp(80),
    marginTop : hp(-4.6),
    alignSelf: 'center',
  },
  FirstIconImg: {
    
    top: hp(5.7),
    left: wp(15),
    position: 'absolute',
    width: wp(13),
  },
  SecondIconImg: {
    top: hp(16),
    left: wp(15),
    position: 'absolute',
    width: wp(13),
    zIndex: 1,
  },
  FirstDayText: {
    top: hp(9.5),
    paddingLeft: wp(12),
    fontSize:wp(4.5),
    color:'rgb(77, 76, 76)',
    fontWeight: 'bold',
    alignSelf:'center',
    position: 'absolute'
  },
  SecondDayText: {
    top: hp(20),
    paddingLeft: wp(12),
    fontSize:wp(4.5),
    color:'rgb(77, 76, 76)',
    fontWeight: 'bold',
    alignSelf:'center',
    position: 'absolute'
  },
})

export default BestDayAndTime;

