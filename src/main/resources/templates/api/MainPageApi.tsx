import { Axios } from 'libs';
import { RegisterField } from 'types/';
import { API_ROUTE } from 'constants/';

const authAPI = new Axios(true);

export const MainPageApi = async (userId) => {
    const response = await authAPI.get(API_ROUTE.AUTH.MAIN_PAGE(userId));
    return response;
}
