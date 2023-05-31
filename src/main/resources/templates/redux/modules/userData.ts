import { createSlice } from '@reduxjs/toolkit';
import { UserDataForm } from 'types/';

const initialState: UserDataForm | null = null;

const userDataSlicer = createSlice({
    name: 'userDataSlicer',
    initialState,
    reducers: {
        setUserData: (state, action) => {
            // const { payload } = actions;
            // return payload || null;
            state.userData = action.payload
        },
        setSocialToken: (state, action) => {
            // const { payload } = actions;
            // return payload || null;
            state.idToken = action.payload
        },
        removeUserData: () => null,
    },
});

export default userDataSlicer.reducer;
export const { setUserData, setSocialToken, removeUserData } = userDataSlicer.actions;