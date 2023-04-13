import axios from 'axios';
import {EndPoint, DataForm} from 'types';
import { KEY, EXPIRE, METHOD } from 'constants/';
export class Axios {
    #instance;

    post(endPoint: EndPoint, data: DataForm) {
        return this.#instance({
            method: METHOD.POST,
            url: `${endPoint}`,
            data,
        });
    }


}