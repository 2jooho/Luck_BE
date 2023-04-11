import React from "react";
import { createStackNavigator } from "@react-navigation/stack";

import Main from '../screens/Main';
import MainPage from '../screens/MainPage'
import CateList from '../screens/CateList';
import CateList2 from '../screens/CateList2'
import PureLuckMain from '../screens/PureLuckMain'
import Login from '../screens/Login'
import Join from '../screens/Join'
import FirstLoading from '../screens/FirstLoading'
const Stack = createStackNavigator();

const StackNavigator = () => {
    return (
        <Stack.Navigator>
            <Stack.Screen
            name="FirstLoading"
            component={FirstLoading}
            options={{
                headerShown: false,
            }}
            />
            <Stack.Screen
            name="Login"
            component={Login}
            options={{
                headerShown: false,
            }}
            />
            <Stack.Screen
            name="MainPage"
            component={MainPage}
            
            options={{
                headerShown: false,
            }}
            />
            <Stack.Screen
            name="Join"
            component={Join}
            options={{
                headerShown: false,
            }}
            />
            
            <Stack.Screen
            name="CateList2"
            component={CateList2}
            options={{
                headerShown: false,
            }}
            />
            <Stack.Screen
            name="PureLuckMain"
            component={PureLuckMain}
            options={{
                headerShown: false,
            }}
            />
      </Stack.Navigator>
    );
      
}

export default StackNavigator;