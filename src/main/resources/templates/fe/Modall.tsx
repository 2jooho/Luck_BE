// import Modal from 'react-native-simple-modal';
import React, { useState, useEffect, useRef } from 'react';
import {BackHandler ,Modal,View, Text, SafeAreaView, FlatList, ActivityIndicator, StyleSheet, StatusBar, Image, ImageBackground, TextInput, Alert, BackHandler} from 'react-native';

const Modall = ({openYn}) => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    useEffect(()=>{
        setIsModalVisible(openYn);
    }, openYn)

    return(
        <View style={styles.container}> //가장 큰 컨테이너
            <Modal //모달창
                animationType={"none"} //slide, fade, none
                transparent={true}
                visible={isModalVisible}
                onRequestClose={() => { // 뒤로가기 버튼(Android) 또는 메뉴버튼(Apple TV)을 선택할 때 실행할 함수
                    setIsModalVisible(false)
                    console.log("modal appearance")
                }}
            >
                <View> //모달창에서 보여줄 화면 꾸미기
                    <Text style={{fontSize: 20}}>모달창이요!</Text>
                    <Text style={{fontSize: 20}}>너무 어려워요!</Text>
                    <TouchableOpacity style={{margin: 3}} onPress={() => BackHandler.exitApp()}> //누르면 모달창을 닫아주는 버튼
                        <Text style={styles.text}>닫으시요</Text>
                    </TouchableOpacity>
                </View>
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
    container: {
        zIndex:3,
        position:'absolute',
        height:'100%',
        width:'100%',
        justifyContent:"center",
        alignContent:"center",
        alignItems:"center",
        paddingTop: 50
    },
    buttontext:{

        position:'relative',
        left:170,
        bottom:350,
        fontSize:20,
    },
    text:{
        position:'relative',
        fontSize:15,
        fontWeight:'700',
        left:'40%',
    }

})