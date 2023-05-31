import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useSelector } from 'react-redux'

const AuthTimer = ({isSendSms}) => {
    const [time, setTime] = useState(179)
    const { expireAt } = useSelector((state: any) => state.auth.OTP)

    useEffect(() => {
        if(isSendSms == true && time > 0){
            const interval = setInterval(() => {
                const gap = Math.floor((new Date(expireAt).getTime() - new Date().getTime()) / 1000)
                setTime(gap);
            }, 1000);
            return () => clearInterval(interval);
        }
    }, [expireAt, time]);

    const minutes = isSendSms ? Math.floor(time / 60) : 3;
    const seconds = isSendSms ? time % 60 : 0;

    return (
        <View style={styles.container}>
            <Text style={styles.counterText}>
                {minutes.toString().padStart(2, '0')}:
                {seconds.toString().padStart(2, '0')}
            </Text>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    counterText: {
        fontSize: 48,
    },
});

export default AuthTimer;



