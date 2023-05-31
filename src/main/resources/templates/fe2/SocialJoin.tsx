import React, {useState, useEffect, useRef} from 'react';
import {
    View,
    Text,
    SafeAreaView,
    FlatList,
    ActivityIndicator,
    StyleSheet,
    StatusBar,
    Image,
    ImageBackground,
    TextInput,
    Alert,
    Platform,
    Button
} from 'react-native';
import BouncyCheckbox from "react-native-bouncy-checkbox";
import CateListHeader from '../components/CateListHeader';
import BestDayAndTime from '../components/BestDayAndTime';
import TodayBestDayAndTime from '../components/TodayBestDayAndTime';
import {widthPercentage, heightPercentage, fontPercentage} from '../constants/ResponsiveSize';
import {TouchableOpacity} from 'react-native-gesture-handler';
import RNPickerSelect from 'react-native-picker-select';
import DatePicker from 'react-native-date-picker'
import 'moment/locale/ko';
import axios from 'axios';
import moment from 'moment';
//선언하지 않아도, 디바이스 혹은 locale의 시간을 불러온다. 
import 'moment/locale/ko';	//대한민국
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from "react-native-responsive-screen";
import Loading from '../components/Loading'
import messaging from '@react-native-firebase/messaging';
import * as RNFS from 'react-native-fs';

const Join = ({navigation}) => {

    const getFcmToken = async () => {
        const fcmToken = await messaging().getToken();
        console.log('[FCM Token] ', fcmToken);
        messaging()
            .subscribeToTopic("luck")
            .then(() => {
                Alert.alert(`구독 성공!!`);
            })
            .catch(() => {
                Alert.alert(`구독 실패 ㅠㅠ`);
            });
    };

    useEffect(() => {
        getFcmToken();
        const unsubscribe = messaging().onMessage(async remoteMessage => {
            console.log('[Remote Message] ', JSON.stringify(remoteMessage));
        });
        return unsubscribe;
    }, []);

    // 외부연동
    // axios
    let REQUEST_JOIN_URL = 'https://www.dev-pureluck.com/luck/auth/join';
    const setJoin = async () => {
        setLoading(true);
        try{
            await axios.post(REQUEST_JOIN_URL,
                {
                    userId: userId,
                    password: password,
                    userName: '관리자님aa',
                    nickname: userId,
                    birth: birthDate,
                    birthFlag: birthType,
                    birthTime: birthTime,
                    sex: genderType,
                    phoneNm: phone,
                    loginDvsn: 'B',
                    cateCodeList: '01'
                },
                {
                    withCredentials: true,
                    headers: {
                        'Content-Type' : "application/json"
                    }
                }
            )
                .then((res)=> {
                    console.log(res.data)
                    if(res.status == 200){
                        alert('회원가입 되었습니다.');
                        navigation.navigate('Login');
                    }else{
                        const message = res.data.resultMessage;
                        console.log(message)
                        if(message != null){
                            alert(decodeURI(message));
                            setLoading(false);
                        }else{
                            alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
                            setLoading(false);
                        }
                    }
                })
                .catch((e) => {
                    console.log(e);
                    const statusCode : any = e.response.status;
                    const message : any = JSON.stringify(e.response.headers['resultMessage']);
                    console.log(statusCode +"-"+message);
                    console.log(userId+ password+birthDate+birthType+"/"+birthTime+genderType+phone);
                    if(message != null){
                        alert(decodeURI(message));
                    }else{
                        alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
                    }
                    setLoading(false);
                })
            ;
        }catch(e){
            console.log(e);
            alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
            setLoading(false);
        }
    }

    // 외부연동
    // axios
    let REQUEST_SMS_SEND_URL = 'https://www.dev-pureluck.com/luck/otp/sendSMS';
    const setSmsSend = async () => {
        try{
            await axios.post(REQUEST_SMS_SEND_URL,
                {
                    phoneNm: phone
                },
                {
                    withCredentials: true,
                    headers: {
                        'Content-Type' : "application/json"
                    }
                }
            )
                .then((res)=> {
                    console.log(res.data)
                    if(res.status == 200){
                        alert("["+ phone + "]" + "으로 SMS 전송 완료하였습니다.");
                        setIsSendSms(!isSendSms);
                    }else{
                        const message = JSON.stringify(res.headers['resultMessage']);
                        console.log(message)
                        alert(decodeURI(message));
                    }
                })
                .catch((e) => {
                    console.log(e);
                    const statusCode : any = e.response.status;
                    const message : any = e.response.headers['resultMessage'];
                    console.log(statusCode +"-"+message);
                    if(message != null){
                        alert(decodeURI(message));
                        setLoading(false);
                    }else{
                        alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
                        setLoading(false);
                    }
                })
            ;
        }catch(e){
            console.log(e);
            alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
            setLoading(false);
        }
    }

    // 외부연동
    // axios
    let REQUEST_SMS_CONFIRMS_URL = 'https://www.dev-pureluck.com/luck/sms-certification/confirms';
    const setConfirmsSMS = async () => {
        try{
            await axios.post(REQUEST_SMS_CONFIRMS_URL,
                {
                    phoneNm: phone,
                    authNm: authNm
                },
                {
                    withCredentials: true,
                    headers: {
                        'Content-Type' : "application/json"
                    }
                }
            )
                .then((res)=> {
                    console.log(res.data)
                    if(res.status == 200){
                        alert("인증완료 되었습니다.")
                        setIsAuth(!isAuth);
                    }else{
                        const message = JSON.stringify(res.headers['resultMessage']);
                        console.log(decodeURI(message));
                        if(message != null){
                            alert(decodeURI(message));
                        }else{
                            alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
                        }
                    }
                })
                .catch((e) => {
                    console.log(e);
                    const statusCode : any = e.response.status;
                    const message : any = e.response.headers['resultMessage'];
                    console.log(statusCode +"-"+message);
                    if(message != null){
                        alert(decodeURI(message));
                    }else{
                        alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
                    }
                })
            ;
        }catch(e){
            console.log(e);
            alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
        }
    }

    const values = [
        {label: 'SKT', value: 'SKT'},
        {label: 'KT', value: 'KT'},
        {label: 'LG', value: 'LG'},
    ]
    const birthTypeValue = [
        {label: '양력', value: '1'},
        {label: '음력', value: '2'},
    ]

    const birthTimeValue = [
        {label: '자시 |  23:30 ~ 01:29', value: '1'},
        {label: '축시 |  01:30 ~ 03:29', value: '2'},
        {label: '인시 |  03:30 ~ 05:29', value: '3'},
        {label: '묘시 |  05:30 ~ 07:29', value: '4'},
        {label: '진시 |  07:30 ~ 09:29', value: '5'},
        {label: '사시 |  09:30 ~ 11:29', value: '6'},
        {label: '오시 |  11:30 ~ 13:29', value: '7'},
        {label: '미시 |  13:30 ~ 15:29', value: '8'},
        {label: '신시 |  15:30 ~ 17:29', value: '9'},
        {label: '유시 |  17:30 ~ 19:29', value: '10'},
        {label: '술시 |  19:30 ~ 21:29', value: '11'},
        {label: '해시 |  21:30 ~ 23:29', value: '12'},
    ]

    const emailPathValue = [
        {label: 'google.com', value: '1'},
        {label: 'naver.com', value: '2'},
        {label: 'daum.net', value: '3'},
    ]
    const handleSubmitButton = () => {
        if (!userId) {
            alert('아이디를 입력해주세요');
            return;
        }
        if (!password) {
            alert('비밀번호를 입력해주세요');
            return;
        }
        if (!userPasswordchk) {
            alert('비밀번호 확인을 입력해주세요');
            return;
        }
        if (password != userPasswordchk) {
            alert('비밀번호와 비밀번호 확인이 다릅니다.');
            return;
        }
        if (!phone) {
            alert('핸드폰 번호를 입력해주세요');
            return;
        }
        if (!telType) {
            alert('통신사를 선택해주세요');
            return;
        }
        if (!email) {
            alert('이메일을 입력해주세요');
            return;
        }
        if (!birthType) {
            alert('생년 구분을 선택해주세요');
            return;
        }
        if (!dateString) {
            alert('생년월일을 입력해주세요');
            return;
        }
        if (!terms) {
            alert('약관 동의는 필수입니다.');
            return;
        }
        if (isAuth) {
            alert('핸드폰 인증해주세요');
            return;
        }
        setJoin();
    }

    const confirmsSMS = () => {
        setConfirmsSMS();
    }

    const fnBirthDate = ({Year, month, day}:any)=> {
        let monthNumber = Number(month)
        let dayNumber = Number(day)
        console.log("day:"+month+day);
        let monthValue
        let dayValue
        if(monthNumber < 10){
            monthValue = "0"+ monthNumber
        }
        if(dayNumber < 10){
            dayValue = "0"+ dayNumber
        }

        setBirthDate(Year + monthValue + dayValue)
    }

    const sendSms = () => {
        setSmsSend();
        setIsSendSms(!isSendSms);
    }

    const agreementModal = () => {
        setModalMode('A');
        readFile();
        setIsModalVisible(true);

    }

    const marketingModal = () => {
        setModalMode('M');
        readFile();
        setIsModalVisible(true);
    }

    const readFile = () => {
        var RNFS = require('react-native-fs');
        if (modalMode === 'A') {
            RNFS.readFile('https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/agreement/%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC.txt', 'ascii').then(res => {
                agreementText = res;
            })
                .catch(err => {
                    console.log(err.message, err.code);
                });
        } else {
            RNFS.readFile('https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/agreement/%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80.txt', 'ascii').then(res => {
                agreementText = res;
            })
                .catch(err => {
                    console.log(err.message, err.code);
                });
        }
    }
    let agreementText = "";

    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [userPasswordchk, setUserPasswordchk] = useState('');
    const [phone, setPhone] = useState('');
    const [telType, setTelType] = useState('');
    const [email, setEmail] = useState('');
    const [terms, setTerms] = useState(false);
    const [marketing, setMarketing] = useState(false);
    const [recommand, setRecommand] = useState('');
    const [pickerSelect, setPickerSelect] = useState('');
    const [genderType, setGenderType] = useState('M');
    const [birthType, setBirthType] = useState('');
    const [birthTime, setBirthTime] = useState(0);
    const [date, setDate] = useState(new Date());
    const [birthDate, setBirthDate] = useState('');
    const [dateString, setDateString] = useState('');
    const [open, setOpen] = useState(false)
    const [authNm, setAuthNm] = useState('');
    const [isAuth, setIsAuth] = useState(true); //  editable={disable} selectTextOnFocus={disable}
    const [loading, setLoading] = useState(false);
    const [hidePassword, setHidePassword] = useState(true);
    const [isSendSms, setIsSendSms] = useState(false);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [modalMode, setModalMode] = useState(''); // A:개인정보 동의, M:마케팅 동의
    const [emailPath, setEmailPath] = useState(0);

    const idInputRef = useRef<TextInput | null>(null);
    const passwordInputRef = useRef<TextInput | null>(null);
    const passwordchkInputRef = useRef<TextInput | null>(null);
    const phoneInputRef = useRef<TextInput | null>(null);
    const telTypeInputRef = useRef<TextInput | null>(null);
    const emailInputRef = useRef<TextInput | null>(null);
    const recommandInputRef = useRef<TextInput | null>(null);
    const authNmRef = useRef<TextInput | null>(null);


    return (
        loading ? <Loading /> :
            <SafeAreaView style={styles.container}>
                <View>
                    <ImageBackground style={styles.BackgrounImgView}
                                     source={{uri: 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/login/login_bg-02.jpg'}}  //이미지경로
                                     resizeMode="cover">
                        <View style={styles.TotalView}>
                            <View style={{flex: 0.24}}>
                                <Text style={styles.JoinText}>회원가입</Text>
                                <Text style={styles.JoinSmallText}>membership application</Text>
                                <View style={styles.JoinLine}></View>
                            </View>

                            <View style={{flex: 0.75}}>
                                <View style={{flexDirection: 'row'}}>
                                    <View style={styles.IdTextView}>
                                        <Text style={styles.CommonText}>아이디</Text>
                                    </View>
                                    <TextInput
                                        style={styles.IdTextInput}
                                        onChangeText={(text) => {
                                            setUserId(text)
                                        }}
                                        ref={idInputRef}
                                        returnKeyType="next"
                                        onSubmitEditing={() =>
                                            passwordInputRef.current && passwordInputRef.current.focus()
                                        }
                                        blurOnSubmit={false}
                                    />
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 10, position: 'relative'}}>
                                    <View style={styles.IdTextView}>
                                        <Text style={styles.CommonText}>비밀번호</Text>
                                    </View>
                                    <TextInput
                                        style={styles.IdTextInput}
                                        onChangeText={(password) => setPassword(password)}
                                        secureTextEntry={hidePassword}
                                        ref={passwordInputRef}
                                        returnKeyType="next"
                                        onSubmitEditing={() =>
                                            passwordchkInputRef.current && passwordchkInputRef.current.focus()
                                        }
                                        blurOnSubmit={false}
                                    />
                                    <Pressable onPress={() => setHidePassword(!hidePassword)}>
                                        <Image style={styles.PwSee}
                                            source={{uri : 'https://pureluckupload.s3.ap-northeast-2.amazonaws.com/img/join/pw-see.png'}}>
                                        </Image>
                                    </Pressable>
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={styles.IdTextView}>
                                        <Text style={styles.CommonText}>비밀번호 확인</Text>
                                    </View>
                                    <TextInput
                                        style={styles.IdTextInput}
                                        onChangeText={(userPasswordchk) => {
                                            setUserPasswordchk(userPasswordchk)
                                        }}
                                        secureTextEntry={true}
                                        ref={passwordchkInputRef}
                                        returnKeyType="next"
                                        onSubmitEditing={() =>
                                            phoneInputRef.current && phoneInputRef.current.focus()
                                        }
                                        blurOnSubmit={false}
                                    />
                                </View>
                                {/*<View style={{flex: 0.5, justifyContent: 'center'}}>*/}
                                {/*    {password !== userPasswordchk ? (*/}
                                {/*      <Text>*/}
                                {/*        비밀번호가 일치하지 않습니다.*/}
                                {/*      </Text>*/}
                                {/*    ) : null}*/}
                                {/*</View>*/}
                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={styles.PhoneTextView}>
                                        <Text style={styles.CommonText}>전화번호</Text>
                                    </View>
                                    <TextInput
                                        style={styles.PhoneTextInput}
                                        onChangeText={(text) => {
                                            setPhone(text)
                                        }}
                                        ref={phoneInputRef}
                                        returnKeyType="next"
                                        blurOnSubmit={true}
                                        editable={isAuth}
                                        selectTextOnFocus={isAuth}
                                    />
                                    <View style={styles.TelTypeTextView}>
                                        <Text style={styles.CommonText}>통신사</Text>
                                    </View>
                                    <RNPickerSelect
                                        onValueChange={type => setTelType(type)}
                                        items={values}
                                        useNativeAndroidPickerStyle={false}
                                        fixAndroidTouchableBug={true}
                                        placeholder={{
                                            label: "통신사 선택",
                                        }}
                                        style={pickerSelectStyles}
                                    />
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={styles.IdTextView}>
                                        <Text style={styles.CommonText}>인증번호</Text>
                                    </View>
                                    <TextInput
                                        style={styles.IdTextInput}
                                        onChangeText={(authNm) => {
                                            setAuthNm(authNm);
                                        }}
                                        secureTextEntry={false}
                                        ref={authNmRef}
                                        returnKeyType="done"
                                        blurOnSubmit={false}
                                        editable={isSendSms}
                                    />
                                    <TouchableOpacity
                                        style={styles.AuthNmTouch}
                                        onPress={() => {isSendSms?confirmsSMS():sendSms()}}>
                                        <Text>{isSendSms ? '인증번호 확인' : '인증번호 전송'}</Text>
                                    </TouchableOpacity>
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={styles.PhoneTextView}>
                                        <Text style={styles.CommonText}>성별</Text>
                                    </View>
                                    <TouchableOpacity
                                        style={genderType == 'M' ? styles.AbleGenderBtn : styles.DisableGenderBtn}
                                        onPress={() => setGenderType('M')}>
                                        <Text style={genderType == 'M' ? styles.AbleGender : styles.DisableGender}>남성</Text>
                                    </TouchableOpacity>
                                    <TouchableOpacity
                                        style={genderType == 'W' ? styles.AbleGenderBtn : styles.DisableGenderBtn}
                                        onPress={() => setGenderType('W')}>
                                        <Text style={genderType == 'W' ? styles.AbleGender : styles.DisableGender}>여성</Text>
                                    </TouchableOpacity>
                                </View>

                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={styles.BirthTextView}>
                                        <Text style={styles.CommonText}>생년월일</Text>
                                    </View>
                                    <View style={styles.birthView}>
                                        <RNPickerSelect
                                            onValueChange={value => setBirthType(value)}
                                            items={birthTypeValue}
                                            useNativeAndroidPickerStyle={false}
                                            fixAndroidTouchableBug={true}
                                            placeholder={{
                                                label: "구분",
                                            }}
                                            style={birthTypePickerSelectStyles}
                                        />
                                        <TouchableOpacity
                                            style={{
                                                backgroundColor: '#ffffff',
                                                width: widthPercentage(450),
                                                height: heightPercentage(90),
                                                marginLeft: 5,
                                                borderRadius: 5,
                                                borderColor: 'gray',
                                                borderWidth: 1,
                                                alignItems: 'center',
                                                justifyContent: 'center'
                                            }}
                                            onPress={() => setOpen(true)}>
                                            <Text>{dateString}</Text>
                                        </TouchableOpacity>
                                        <DatePicker
                                            modal
                                            mode='date'
                                            open={open}
                                            date={date}
                                            onConfirm={(date) => {
                                                setOpen(false)
                                                console.log(date.toString())
                                                setDateString(moment(date).format('YYYY.MM.DD'))
                                                setBirthDate(moment(date).format('YYYYMMDD'))

                                            }}
                                            onCancel={() => {
                                                setOpen(false)
                                            }}
                                            locale="ko"


                                        />
                                    </View>
                                </View>

                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={styles.IdTextView}>
                                        <Text style={styles.CommonText}>태어난 시간</Text>
                                    </View>
                                    <RNPickerSelect
                                        onValueChange={value => setBirthTime(value)}
                                        items={birthTimeValue}
                                        useNativeAndroidPickerStyle={false}
                                        fixAndroidTouchableBug={true}
                                        placeholder={{
                                            label: "모름",
                                            value: 0
                                        }}
                                        style={birthTimePickerSelectStyles}
                                    />
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={styles.IdTextView}>
                                        <Text style={styles.CommonText}>이메일</Text>
                                    </View>
                                    <TextInput
                                        style={styles.IdTextInput}
                                        onChangeText={(text) => {
                                            setEmail(text)
                                        }}
                                        ref={emailInputRef}
                                        returnKeyType="next"
                                        onSubmitEditing={() =>
                                            recommandInputRef.current && recommandInputRef.current.focus()
                                        }
                                        blurOnSubmit={false}
                                    />
                                    <Text>@</Text>
                                    <RNPickerSelect
                                        onValueChange={value => setEmailPath(value)}
                                        items={emailPathValue}
                                        useNativeAndroidPickerStyle={false}
                                        fixAndroidTouchableBug={true}
                                        placeholder={{
                                            label: "직접입력",
                                            value: 0
                                        }}
                                        style={birthTimePickerSelectStyles}
                                    />
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 10, alignItems:'flex-end'}}>
                                    <View style={styles.IdTextView}>
                                        <Text style={styles.CommonText}>직접입력</Text>
                                    </View>
                                    <TextInput
                                        style={styles.IdTextInput}
                                        onChangeText={(text) => {
                                            setEmail(text)
                                        }}
                                        ref={emailInputRef}
                                        returnKeyType="next"
                                        onSubmitEditing={() =>
                                            recommandInputRef.current && recommandInputRef.current.focus()
                                        }
                                        blurOnSubmit={false}
                                        editable={emailPath===0 ? true : false}
                                        selectTextOnFocus={emailPath===0 ? true : false}
                                    />
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={styles.IdTextView}>
                                        <Text style={styles.CommonText}>추천인증 코드</Text>
                                    </View>
                                    <TextInput
                                        style={styles.IdTextInput}
                                        onChangeText={(text) => {
                                            setRecommand(text)
                                        }}
                                        ref={recommandInputRef}
                                        blurOnSubmit={true}
                                    />
                                </View>
                                <View style={{alignItems: 'center', marginTop: 10}}>
                                    <View style={{justifyContent: 'row'}}>
                                        <BouncyCheckbox
                                            style={styles.checkbox1}
                                            size={7}
                                            fillColor='#8e4ffa'
                                            unfillColor="#FFFFFF"
                                            text="서비스 이용약관 및 개인정보 수집이용에 동의합니다."
                                            innerIconStyle={{borderRadius: 0}}
                                            iconStyle={{borderRadius: 0}}
                                            textStyle={{fontSize: fontPercentage(8), textDecorationLine: 'none'}}
                                            onPress={(isChecked: boolean) => {
                                                setTerms(isChecked)
                                            }}
                                        />
                                        <TouchableOpacity
                                            style={styles.AuthNmTouch}
                                            onPress={() => agreementModal()}>
                                            <Text>보기</Text>
                                        </TouchableOpacity>
                                    </View>
                                    <View style={{justifyContent: 'row'}}>
                                        <BouncyCheckbox
                                            style={styles.checkbox}
                                            size={7}
                                            fillColor='#8e4ffa'
                                            unfillColor="#FFFFFF"
                                            text="마케팅 정보수신에 동의합니다."
                                            innerIconStyle={{borderRadius: 0}}
                                            iconStyle={{borderRadius: 0}}
                                            textStyle={{fontSize: fontPercentage(8), textDecorationLine: 'none'}}
                                            onPress={(isChecked: boolean) => {
                                                setMarketing(isChecked)
                                            }}
                                        />
                                        <TouchableOpacity
                                            style={styles.AuthNmTouch}
                                            onPress={() => marketingModal()}>
                                            <Text>보기</Text>
                                        </TouchableOpacity>
                                    </View>
                                </View>
                                <TouchableOpacity
                                    style={{
                                        backgroundColor: '#8e4ffa',
                                        width: widthPercentage(700),
                                        height: heightPercentage(100),
                                        alignSelf: 'center',
                                        justifyContent: 'center',
                                        marginTop: 15,
                                        borderRadius: 5
                                    }}
                                    onPress={() => {
                                        handleSubmitButton();
                                    }}>
                                    <Text style={{
                                        fontSize: fontPercentage(15),
                                        color: '#ffffff',
                                        textAlign: 'center'
                                    }}>가입하기</Text>
                                </TouchableOpacity>
                            </View>
                        </View>
                    </ImageBackground>
                </View>

                <Modal //모달창
                    animationType={"none"} //slide, fade, none
                    transparent={true}
                    visible={isModalVisible}
                    onRequestClose={() => { // 뒤로가기 버튼(Android) 또는 메뉴버튼(Apple TV)을 선택할 때 실행할 함수
                        setIsModalVisible(false)
                    }}>
                    <Pressable
                        style={styles.modalOverlay}
                        onPress={() => setIsModalVisible(!isModalVisible)}>
                        <ScrollView contentContainerStyle={{alignItems: 'center'}}>
                            <View style={styles.bottomSheetContainer}>
                                <Text style={modalInnerStyle.recipeTitle}>개인정보 동의서</Text>
                                <Text style={[modalInnerStyle.coin]}>{agreementText}</Text>
                                <Pressable onPress={()=> setIsModalVisible(false)}>
                                    <View style={modalInnerStyle.btnView}>
                                        <Text style={modalInnerStyle.btnText}>확인</Text>
                                    </View>
                                </Pressable>
                            </View>
                        </ScrollView>
                    </Pressable>
                </Modal>
            </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    TotalView: {
        marginTop: widthPercentage(100),
        width: widthPercentage(900),
        height: heightPercentage(1850),
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 20,
        borderColor: 'gray',
        borderWidth: 1,
        backgroundColor: '#ffffff',
    },
    BackgrounImgView: {
        width: '100%',
        height: '100%',
        alignItems: 'center',
        justifyContent: 'center',
    },
    JoinText: {
        fontSize: fontPercentage(25),
        color: '#8e4ffa',
        alignSelf: 'center',
        marginTop: 50,
        fontWeight: 'bold',
    },
    JoinSmallText: {
        fontSize: fontPercentage(13),
        color: '#af9bd4',
        alignSelf: 'center',
    },
    JoinLine: {
        marginTop: 20,
        borderWidth: 1,
        borderColor: '#8e4ffa',
        width: widthPercentage(600),
    },
    IdTextView: {
        width: widthPercentage(240),
        borderWidth: 1,
        borderRadius: 5,
        borderColor: '#b785fa',
        alignItems: 'center',
        justifyContent: 'center',
    },
    IdTextInput: {
        marginLeft: 5,
        padding: 0,
        margin: 0,
        paddingHorizontal: 10,
        width: widthPercentage(560),
        height: heightPercentage(90),
        borderRadius: 5,
        borderColor: 'gray',
        borderWidth: 1
    },
    CommonText: {
        color: '#b785fa',
        fontSize: fontPercentage(10),
    },
    PhoneTextView: {
        width: widthPercentage(150),
        borderWidth: 1,
        borderRadius: 5,
        borderColor: '#b785fa',
        alignItems: 'center',
        justifyContent: 'center',
    },
    PhoneTextInput: {
        padding: 0,
        margin: 0,
        marginLeft: 5,
        paddingHorizontal: 10,
        width: widthPercentage(235),
        height: heightPercentage(90),
        borderRadius: 5,
        borderColor: 'gray',
        borderWidth: 1,
        fontSize: widthPercentage(27)
    },
    TelTypeTextView: {
        marginLeft: 5,
        width: widthPercentage(150),
        borderWidth: 1,
        borderRadius: 5,
        borderColor: '#b785fa',
        alignItems: 'center',
        justifyContent: 'center',
    },
    AbleGenderBtn: {
        alignItems: 'center',
        justifyContent: 'center',
        marginLeft: 5,
        width: widthPercentage(320),
        height: heightPercentage(90),
        borderRadius: 5,
        borderColor: '#b785fa',
        borderWidth: 1,
    },
    DisableGenderBtn: {
        alignItems: 'center',
        justifyContent: 'center',
        marginLeft: 5,
        width: widthPercentage(320),
        height: heightPercentage(90),
        borderRadius: 5,
        borderColor: '#9f9f9f',
        borderWidth: 1,
    },
    AbleGender: {
        color: '#8064a0',
    },
    DisableGender: {
        color: '#9f9f9f ',
        opacity: 0.5
    },
    BirthTextView: {
        width: widthPercentage(150),
        height: heightPercentage(150),
        borderWidth: 1,
        borderRadius: 5,
        borderColor: '#b785fa',
        alignItems: 'center',
        justifyContent: 'center',
    },
    birthView: {
        flexDirection: 'row',
        alignItems: 'center',
        borderColor: 'gray',
        borderWidth: 1,
        width: widthPercentage(660),
        borderRadius: 5,
        marginLeft: 5,
    },
    checkbox: {
        marginTop: 5,
    },
    checkbox1: {
        marginTop: 5,
        left: 39,
    },
    PwSee: {
        resizeMode: 'contain',
        position: 'absolute',
        alignSelf: 'flex-end',
    },
    modalOverlay: {
        flex: 1,
        justifyContent: 'center',
        alignItems:'center',
        backgroundColor: 'rgba(0, 0, 0, 0.4)'
    },
})

const pickerSelectStyles = StyleSheet.create({
    inputIOS: {
        textAlign: 'center',
        marginLeft: 5,
        fontSize: fontPercentage(10),
        width: widthPercentage(235),
        height: heightPercentage(90),
        color: '#9f9f9f',
        borderColor: 'gray',
        borderWidth: 1,
        borderRadius: 5,
    },
    inputAndroid: {
        textAlign: 'center',
        marginLeft: 5,
        fontSize: fontPercentage(10),
        width: widthPercentage(235),
        height: heightPercentage(90),
        color: '#9f9f9f',
        borderColor: 'gray',
        borderWidth: 1,
        borderRadius: 5,
    },
});

const birthTypePickerSelectStyles = StyleSheet.create({
    inputIOS: {
        textAlign: 'center',
        marginLeft: 5,
        fontSize: fontPercentage(10),
        width: widthPercentage(180),
        height: heightPercentage(90),
        color: '#9f9f9f',
        borderColor: 'gray',
        borderWidth: 1,
        borderRadius: 5,
    },
    inputAndroid: {
        textAlign: 'center',
        marginLeft: 5,
        fontSize: fontPercentage(10),
        width: widthPercentage(150),
        height: heightPercentage(90),
        color: '#9f9f9f',
        borderColor: 'gray',
        borderWidth: 1,
        borderRadius: 5,
    },
});

const birthTimePickerSelectStyles = StyleSheet.create({
    inputIOS: {
        textAlign: 'center',
        marginLeft: 5,
        fontSize: fontPercentage(10),
        width: widthPercentage(570),
        height: heightPercentage(90),
        color: '#8064a0',
        borderColor: 'gray',
        borderWidth: 1,
        borderRadius: 5,
    },
    inputAndroid: {
        textAlign: 'center',
        marginLeft: 5,
        fontSize: fontPercentage(10),
        width: widthPercentage(570),
        height: heightPercentage(90),
        color: '#8064a0',
        borderColor: 'gray',
        borderWidth: 1,
        borderRadius: 5,
    },
});

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
});
export default Join;