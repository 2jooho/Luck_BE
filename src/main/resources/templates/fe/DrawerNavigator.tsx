import React from "react";
import { createDrawerNavigator } from "@react-navigation/drawer";
import StackNavigator from './StackNavigator';
import MainPage from '../screens/MainPage'
import SideMenu from './SideMenu';
const Drawer = createDrawerNavigator();

const DrawerNavigator = () => {
  return (
    <Drawer.Navigator
        initialRouteName="Home"
        drawerPosition="right"
        drawerWidth: Dimensions.get("window").width * 0.3
        contentComponent : SideMenu

        //drawerContent={(props) => <CustomDrawerContent {...props} />}>

                // drawerContent={({navigation}) => (
                //     <SafeAreaView>
                //       <Text>A Custom Drawer</Text>
                //       <Button
                //           onPress={() => navigation.closeDrawer()}
                //           title="Drawer 닫기"
                //       />
                //     </SafeAreaView>
                // )}>
    >
      <Drawer.Screen name="MainPage" component={MainPage} />
    </Drawer.Navigator>
  );
}

export default DrawerNavigator;