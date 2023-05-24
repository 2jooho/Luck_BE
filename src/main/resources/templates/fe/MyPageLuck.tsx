
import React, {useState} from 'react';
import {StyleSheet, Image, Text, SafeAreaView, View, ImageBackground} from 'react-native';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";

const MyPageLuck = ({data}: any) => {
  return (
      <SafeAreaView style={styles.container}>
          <View>
              <View>
                  <Image style={styles.FirstImg}
                         source={{uri : `https://pureluckupload.s3.ap-northeast-2.amazonaws.com${data.timeLuckImg}`}}  //이미지경로
                         resizeMode="contain">
                  </Image>
                  <Text>{data.timeLuckKorean}{data.timeLuckChinese}</Text>
              </View>
              <View>
                  <Image style={styles.FirstImg}
                         source={{uri : `https://pureluckupload.s3.ap-northeast-2.amazonaws.com${data.dayLuckImg}`}}  //이미지경로
                         resizeMode="contain">
                  </Image>
                  <Text>{data.dayLuckKorean}{data.dayLuckChinese}</Text>
              </View>
              <View>
                  <Image style={styles.FirstImg}
                         source={{uri : `https://pureluckupload.s3.ap-northeast-2.amazonaws.com${data.monthLuckImg}`}}  //이미지경로
                         resizeMode="contain">
                  </Image>
                  <Text>{data.monthLuckKorean}{data.monthLuckChinese}</Text>
              </View>
              <View>
                  <Image style={styles.FirstImg}
                         source={{uri : `https://pureluckupload.s3.ap-northeast-2.amazonaws.com${data.yearLuckImg}`}}  //이미지경로
                         resizeMode="contain">
                  </Image>
                  <Text>{data.yearLuckKorean}{data.yearLuckChinese}</Text>
              </View>
          </View>
      </SafeAreaView>
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
})

export default MyPageLuck;

