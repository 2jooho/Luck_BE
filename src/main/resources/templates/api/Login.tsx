import { Axios } from 'libs';
import { RegisterField } from 'types/';
import { API_ROUTE } from 'constants/';

const authAPI = new Axios(true);

export const Login = async ({userId, password}:RegisterField) => {
    const response = await authAPI.post(API_ROUTE.AUTH.LOG_IN,
        {
         userId,
         password,
        });
    return response;
}




let REQUEST_URL = 'http://ec2-3-34-36-9.ap-northeast-2.compute.amazonaws.com:8081/luck/auth/login';
const setLogin = async () => {
    setLoading(true);
    try{
        return await axios.post(REQUEST_URL,
            {
                userId: userId,
                password: password,
                deviceId: 'test',
                osType: '2',
                osVer: '1.0',
                loginType: 'M',
                loginDvsn: 'B'
            },
            {withCredentials: true,
                headers: {
                    'Content-Type' : "application/json"
                }}
        )
            .then((res)=> {
                console.log(res);
                setUsers(res.data); // 데이터는 response.data 안에 들어있습니다.
                // AsyncStorage.multiSet([
                //     ['accessToken', response.data.accessToken],
                //     ['refreshToken', response.data.refreshToken]
                // ])
                ///response.headers.Authorization.split('Bearer ')[1]
                // alert(JSON.stringify(res.headers));
                const accessToken = JSON.stringify(res.headers['authorization']);
                const refreshToken = JSON.stringify(res.headers['refreshtoken']);

                console.log("accessToken:"+accessToken);
                console.log("refreshToken:"+refreshToken);
                AsyncStorage.setItem('accessToken', accessToken);
                AsyncStorage.setItem('refreshToken', refreshToken);
                AsyncStorage.setItem('userId', userId);
                if(loading){
                    AsyncStorage.setItem('loging', 'Y');
                }

                navigation.navigate('MainPage', {userId: userId});
            })
            .catch((e)=>{
                console.log(e);
                const statusCode : any = e.response.status;
                const message : any = e.response.headers.get("resultMessage");
                alert(message);
                console.log(statusCode +"-"+message);
                if(message != null){
                    alert(message);
                    setLoading(false);
                }else{
                    alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
                    setLoading(false);
                }
            })
            ;
    }catch(e){
        console.log(e);
        alert("서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.");
        setLoading(false);
    }
}