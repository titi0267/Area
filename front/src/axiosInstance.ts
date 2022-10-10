import axios from 'axios'

const axios_instance = axios.create({
    timeout: 1000,
    withCredentials: true,
    baseURL: 'http://127.0.0.1:3000/',
    headers: {
        "Access-Control-Allow-Origin": "http://localhost:8080",
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});

export default axios_instance;
