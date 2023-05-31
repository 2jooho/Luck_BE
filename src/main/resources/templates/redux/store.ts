import { combineReducers, configureStore } from '@reduxjs/toolkit';
import {
    userDataSlicer,
    RootStateSlicer,
} from './modules';

const reducer = combineReducers({
    // themeToggleSlicer,
    userDataSlicer,
    RootStateSlicer,
    // authLoadingSlicer,
    // ticketDataSlicer,
});

const store = configureStore({ reducer });

export type RootState = ReturnType<typeof store.getState>;
export default store;
