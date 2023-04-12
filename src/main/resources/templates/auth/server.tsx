import client from './client';
import {randomPhotoState, searchUserState, RegisterField} from './type';

export const getPhoto = async () => {
    const response = await client.get<randomPhotoState[]>('/photos/random', {
        params: {
            count: 3,
            query: 'war',
        },
    });
    return response.data;
};

export const getUser = async () => {
    const response = await client.get<randomPhotoState[]>('/photos/random', {
        params: {
            count: 12,
        },
    });
    return response.data;
};

export const searchUser = async (text: string) => {
    const response = await client.get<searchUserState>('/search/users?', {
        params: {
            query: text,
        },
    });
    return response.data.results;
};



export const Login = async ({userId, password}:RegisterField) => {
    const response = await authAPI.post(API_ROUTE.AUTH.LOG_IN,
        {
            userId,
            password,
        });
    return response;
}