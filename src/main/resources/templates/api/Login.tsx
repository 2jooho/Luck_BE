import { Axios } from '../libs/Axios';
// import { RegisterField } from 'types/';
import { API_ROUTE } from '../constants/api';

const authAPI = new Axios(true);
const unAuthAPI = new Axios();

export const logIn = async (data) => {
    const response = await unAuthAPI.post(API_ROUTE.AUTH.LOG_IN, data
    );
    return response;
}
