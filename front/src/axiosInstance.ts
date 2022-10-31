import axios from "axios";
import store from "./store";

const axios_instance = axios.create({
  timeout: 1000,
  withCredentials: true,
  baseURL: `http://${process.env.VUE_APP_URL}:${process.env.VUE_APP_PORT || 8081}`,
  headers: {
    "Access-Control-Allow-Origin": 'http://localhost:8081',
    Accept: "application/json",
    "Content-Type": "application/json",
    Authorization: store.getters.userToken || localStorage.getItem("usr-token") || "noToken",
  },
});

export default axios_instance;
