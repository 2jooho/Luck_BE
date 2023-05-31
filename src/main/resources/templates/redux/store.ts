import { combineReducers, configureStore } from '@reduxjs/toolkit';
import {
    userDataSlicer,
    RootStateSlicer, ModallStateSlicer,
} from './modules';

const reducer = combineReducers({
    // themeToggleSlicer,
    userDataSlicer,
    RootStateSlicer,
    ModallStateSlicer,
    // authLoadingSlicer,
    // ticketDataSlicer,
});

const store = configureStore({ reducer });

export type RootState = ReturnType<typeof store.getState>;
export default store;
