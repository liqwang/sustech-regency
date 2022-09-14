import axios from "axios";

const request = axios.create({
  baseURL: "quanquancho.com:8080"
});

request.interceptors.request.use(function (config) {
  return config;
}, function (error) {
  return Promise.reject(error);
})

request.interceptors.response.use(function (response) {
  return response;
}, function (error) {
  return Promise.reject(error);
})

export default request;