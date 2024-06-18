import axios from 'axios'
import ElementUI from "element-ui";

const request = axios.create({
    baseURL: 'http://localhost:9090',
    timeout: 5000
})

// request 拦截器
request.interceptors.request.use(config => {
    let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {};
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    if(user){
        config.headers['token'] = user.token;
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回文件
        if(response.config.responseType === 'blob'){
            return res
        }
        // 兼容服务端返回的字符串
        if(typeof res === 'string'){
            res = res ? JSON.parse(res) : res
        }
        // 当权限不通过的时候给出提示
        if(res.code === '401'){
            ElementUI.Message({
                message: res.msg,
                type: 'error'
            })
        }
        return res
    },
    error => {
        console.log('err' + error)
        return Promise.reject(error)
    }
)

export default request