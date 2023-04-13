import { Axios } from 'libs';
import { RegisterField } from 'types/';
import { API_ROUTE } from 'constants/';

const authAPI = new Axios(true);

export const logIn = async ({userId, password}) => {
    const response = await authAPI.post(API_ROUTE.AUTH.LOG_IN,
        {
         userId,
         password,
        });
    return response;
}
