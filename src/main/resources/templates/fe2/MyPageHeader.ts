import React from 'react';
import {StyleSheet, View, Pressable} from 'react-native';
import styled from 'styled-components/native';
import Colors from '../constants/Colors';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";
// import Sizes from '../constants/Sizes';
// import {Image} from 'react-native';

/* 스타일 컴포넌트 */
//전체 할당 뷰
const Container = styled.View`
  /* flex: 1; */
  height: ${hp(6.5)};
  background-color: ${Colors.cateMainHeader};
  flex-direction: row;
  border-bottom-width: 1;
  border-bottom-color: 'rgba(158, 150, 150, .5)'
`;
//메인 로고 뷰
const TitleView = styled.View`
  flex: 1;
  align-items: center;
  /* border-color: ${Colors.black};
  border-width: 1; */
`;
//검색, 메뉴바 서브 뷰
const SubView = styled.View`
  flex-direction: row;
  border-color: ${Colors.black};
  border-width: 1;
  /* flex: 0.2; */
`;
//검색 이미지 뷰
const SerchView = styled.View`
  /* flex: 1; */
  /* border-color: ${Colors.black};
  border-width: 1; */
  align-self: center;
`;
//메뉴바 이미지 뷰
const MenuView = styled.View`
  /* flex: 0.4; */
  /* border-color: black;
  border-width: 1; */
  align-items: flex-end;
  align-self: center;
  margin-right: 5%;
`;
//메인 로고 이미지
const TitleImg = styled.Image`
    width: ${wp(100)};
  /* flex: 0.65; */
`;
//검색 이미지
const SerchImg = styled.Image`
  margin-left: 12;
  width: ${wp(6)};
  height: ${hp(6)};
`;
//메뉴 선택 이미지
const MenuImg = styled.Image`
  width: ${wp(5.5)};
  height: ${hp(5.5)};
`;

const MyPageHeader = ({navigation}) => (
    <Container>
        <SerchView>
            <Pressable onPress={() => navigation.goBack()}>
                <SerchImg
                    style={styles.ImgResizeMode}
                source={require('../assets/images/category/top-001.png')}
                />
            </Pressable>
        </SerchView>
        <TitleView>
            <TitleImg
                style={styles.TopLogo}
            source={require('../assets/images/toplogo.png')}
            />
        </TitleView>
        <MenuView>
            <Pressable onPress={() => navigation.openDrawer()}>
                <MenuImg
                    style={styles.ImgResizeMode}
                source={require('../assets/images/main/topmy.png')}
                />
            </Pressable>
        </MenuView>
    </Container>
            );

/* 정적 스타일 */
const styles = StyleSheet.create({
    //동적 이미지 설정
    ImgResizeMode: {
        resizeMode: 'contain',
    },
    TopLogo: {
        resizeMode: 'contain',
        width: wp(13),
        marginTop: -8
    },

    //검색 이미지 할당 뷰
    // serchView: {
    //   flex: 1,
    //   // borderColor: Colors.black,
    //   // borderWidth: 1,
    //   alignItems: 'flex-end',
    //   marginBottom: 10,
    //   paddingTop: 10,
    // },
    // //메뉴바 이미지 할당 뷰
    // menuView: {
    //   flex: 0.7,
    //   // borderColor: Colors.black,
    //   // borderWidth: 1,
    //   alignItems: 'flex-end',
    //   paddingRight: 10,
    //   marginBottom: 10,
    //   paddingTop: 10,
    // },
});

export default MyPageHeader;