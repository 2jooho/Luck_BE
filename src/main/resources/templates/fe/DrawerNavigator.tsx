import React from "react";
import { createDrawerNavigator } from "@react-navigation/drawer";
import StackNavigator from './StackNavigator';
import MainPage from '../screens/MainPage'

const Drawer = createDrawerNavigator();

const DrawerNavigator = () => {
  return (
    <Drawer.Navigator
        initialRouteName="Home"
        backBehavior="history"
        screenOptions={{
          drawerActiveBackgroundColor: '#fb8c00',
          drawerActiveTintColor: '#fff',
        }}
    >
      <Drawer.Screen name="MainPage" component={MainPage} />
    </Drawer.Navigator>
  );
}

export default DrawerNavigator;