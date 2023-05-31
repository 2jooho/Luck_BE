// import Modal from 'react-native-simple-modal';
import React, { useState, useEffect, useRef } from 'react';
import {BackHandler ,Modal,View, Text, SafeAreaView, FlatList, ActivityIndicator, StyleSheet, StatusBar, Image, ImageBackground, TextInput, Alert, TouchableOpacity} from 'react-native';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";


const Modall = () => {
    const dispatch = useDispatch(); //리덕스 툴킷
    const isModallOpen = useSelector((state) => state.modallState.isOpen)

    return(
        <View style={styles.container2}>
            <Modal //모달창
                animationType={"none"} //slide, fade, none
                transparent={true}
                visible={isModallOpen}
                onRequestClose={() => { // 뒤로가기 버튼(Android) 또는 메뉴버튼(Apple TV)을 선택할 때 실행할 함수
                    dispatch(isOpen(false));
                }}
            >
                <Pressable
                    style={styles.modalOverlay}
                    onPress={() => dispatch(isOpen(false))}>

                    <View style={styles.bottomSheetContainer}>
                        <Text style={modalInnerStyle.recipeTitle}>알림</Text>
                        <Text style={[modalInnerStyle.coin]}>오픈 준비중 입니다.</Text>
                        <Pressable onPress={()=> dispatch(isOpen(false))}>
                            <View style={modalInnerStyle.btnView}>
                                <Text style={modalInnerStyle.btnText}>확인</Text>
                            </View>
                        </Pressable>
                    </View>
                </Pressable>
            </Modal>
        </View>
    )
    // return(
    //
    //             <Modal //모달창
    //                 // offset={this.state.offset}
    //                 open={openYn} //상태가 오픈이어야함.
    //                 modalDidOpen={() => console.log('modal did open')} //모달이 열릴경우 콘솔창에 안내문을 띄운다.
    //                 modalDidClose={() => BackHandler.exitApp()} //모달창을 닫을 경우 앱 종료
    //                 style={{alignItems: 'center'}}>
    //                 <View> //모달창에서 보여줄 화면 꾸미기
    //                     <Text style={{fontSize: 20}}>모달창이요!</Text>
    //                     <Text style={{fontSize: 20}}>너무 어려워요!</Text>
    //                     <TouchableOpacity style={{margin: 3}} onPress={() => BackHandler.exitApp()}> //누르면 모달창을 닫아주는 버튼
    //                         <Text style={styles.text}>닫으시요</Text>
    //                     </TouchableOpacity>
    //                 </View>
    //             </Modal>
    //         </View>
    // )

}
export default Modall;


const styles = StyleSheet.create({
    container2: {
        zIndex:3,
        position:'absolute',
        height:'100%',
        width:'100%',
        justifyContent:"center",
        alignContent:"center",
        alignItems:"center",
        paddingTop: 50
    },
})


const modalInnerStyle = StyleSheet.create({
    recipeTitle: {
        fontSize: wp(7),
        fontWeight: '700'
    },
    coin: {
        fontSize: wp(5),
        fontWeight: '700',
        paddingTop: hp(3),
        textAlign: 'center'
    },
    btnView: {
        width: wp(15),
        height: hp(5),
        backgroundColor: '#e6c4fc',
        borderRadius: 5,
        alignSelf:'center',
        justifyContent:'center',
        marginTop: hp(4)
    },
    btnText: {
        fontSize: wp(4),
        fontWeight: '700',
        textAlign: 'center',
        color: '#fff',
    },
})