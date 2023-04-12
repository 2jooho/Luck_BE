import React, {Component} from 'react';
import {NavigationActions} from 'react-navigation';
import {ScrollView, Text, View} from 'react-native';

class SideMenu extends Component {
    navigateToScreen = (route) => () => {
        const navigateAction = NavigationActions.navigate({
            routeName: route
        });
        this.props.navigation.dispatch(navigateAction);
    }

    render () {
        return (
            <View >
                <ScrollView>
                    <View>
                        <Text>
                            Section 1
                        </Text>
                        <View >
                            <Text  onPress={this.navigateToScreen('Main_View')}>
                                메인
                            </Text>
                        </View>
                    </View>
                    <View>
                        <Text>
                            Section 2
                        </Text>
                        <View >
                            <Text onPress={this.navigateToScreen('Intro_Main')}>
                                회사소개
                            </Text>
                            <Text  onPress={this.navigateToScreen('CalenderMain')}>
                                일정
                            </Text>
                            <Text  onPress={this.navigateToScreen('BoardMain')}>
                                게시판
                            </Text>

                            <Text  onPress={this.navigateToScreen('UserAccount')}>
                                테스트
                            </Text>
                        </View>
                    </View>
                </ScrollView>
                <View >
                    <Text>This is my fixed footer</Text>
                </View>
            </View>
        );
    }
}

export default SideMenu;