import axios from 'axios';
import {EndPoint, DataForm} from 'types';
import { KEY, EXPIRE, METHOD } from 'constants/';
import AsyncStorage from '@react-native-community/async-storage';
import EncryptedStorage from 'react-native-encrypted-storage';

export class Axios {
    #instance;
    #auth;

    /**
     * @param {boolean} isAuthReq
     */
    constructor(isAuthReq = false) {
        this.#instance = axios.create({
            // baseURL: `${process.env.REACT_APP_API_BASE_ROUTE}`,
            baseURL: 'https://www.dev-pureLuck.com',
        });
        // this.#auth = isAuthReq;
        // this.#cookie = new Cookies();
        this.#setInterceptor();
    }

    /* Interceptor */
    #setInterceptor() {
        this.#instance.interceptors.request.use(
            this.#reqMiddleWare.bind(this),
            this.#reqOnError.bind(this)
        );
        // this.#instance.interceptors.response.use(
        //     this.#resMiddleWare.bind(this),
        //     this.#resOnError.bind(this)
        // );
    }

    /* Req */
    #reqMiddleWare(config: any) {
        let newConfig = config;
        if (this.#auth) newConfig = this.#setAuthReq(newConfig);

        return newConfig;
    }

    #setAuthReq(config: any): any {
        const { headers } = config;
        const newConfig = {
            ...config,
            headers: {
                ...headers,
                Authorization: AsyncStorage.getItem("accessToken"),
                // Authorization: `${this.#cookie
                //     .get(COOKIE.KEY.ACCESS_TOKEN)
                //     ?.replace('%', ' ')}`,
            },
        };

        return newConfig;
    }

    #reqOnError(error: any) {
        return Promise.reject(error);
    }

    /* Res */
    // #resMiddleWare(res: any) {
    //     const { accessToken, refreshtoken } = res.headers;
    //
    //     if (accessToken) {
    //         AsyncStorage.setItem('accessToken', accessToken);
    //         // this.#cookie.set(COOKIE.KEY.ACCESS_TOKEN, authorization, {
    //         //     ...COOKIE.CONFIG.DEFAULT,
    //         // });
    //     }
    //
    //     if (refreshtoken) {
    //         EncryptedStorage.setItem('refreshtoken', refreshtoken);
    //         // this.#cookie.set(COOKIE.KEY.REFRESH_TOKEN, refreshtoken, {
    //         //     ...COOKIE.CONFIG.DEFAULT,
    //         // });
    //     }
    //
    //     return res;
    // }
    //
    // #resOnError(error: any) {
    //     return Promise.reject(error);
    // }


    /**
     * @param {string} endPoint
     */
    get(endPoint: EndPoint) {
        return this.#instance({
            method: METHOD.GET,
            url: endPoint,
        });
    }

    /**
     * @param {string} endPoint
     * @param {string} query
     */
    getByParams(endPoint: EndPoint, params: any) {
        return this.#instance({
            method: METHOD.GET,
            url: `${endPoint}/${params}`,
        });
    }

    post(endPoint: EndPoint, data: DataForm) {
        return this.#instance({
            method: METHOD.POST,
            url: `${endPoint}`,
            data,
        });
    }

    // put(endPoint: EndPoint, data: object, id: ID | undefined = undefined) {
    //     return this.#instance({
    //         method: METHOD.PUT,
    //         url: !!id || id === '' || id === 0 ? `${endPoint}/${id}` : endPoint,
    //         data,
    //     });
    // }

    // /**
    //  * @param {string} endPoint
    //  * @param {number} id
    //  */
    // delete(endPoint: EndPoint, id: any) {
    //     return this.#instance({
    //         method: METHOD.DELETE,
    //         url: `${endPoint}/${id}`,
    //     });
    // }

}