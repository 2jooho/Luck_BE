import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useSelector } from 'react-redux'

const AuthTimer = () => {
    const [time, setTime] = useState(179)
    const { expireAt } = useSelector((state: any) => state.auth.OTP)

    const [counter, setCounter] = useState(0);

    useEffect(() => {
        const interval = setInterval(() => {
            setCounter(prevCounter => prevCounter + 1);
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    const minutes = Math.floor(counter / 60);
    const seconds = counter % 60;

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