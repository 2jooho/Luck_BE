import { combineReducers, configureStore } from '@reduxjs/toolkit';
import {
    userDataSlicer,
} from './modules';

const reducer = combineReducers({
    // themeToggleSlicer,
    userDataSlicer,
    // authLoadingSlicer,
    // ticketDataSlicer,
});

const store = configureStore({ reducer });

export type RootState = ReturnType<typeof store.getState>;
export default store;
