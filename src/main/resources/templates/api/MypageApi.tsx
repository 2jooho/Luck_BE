import { Axios } from '../libs/Axios';
// import { RegisterField } from 'types/';
import { API_ROUTE } from '../constants/api';

const authAPI = new Axios(true);
const unAuthAPI = new Axios();

export const myPageApi = async (userId) => {
    const response = await authAPI.get(API_ROUTE.AUTH.MYPAGE(userId));
    return response;
}
