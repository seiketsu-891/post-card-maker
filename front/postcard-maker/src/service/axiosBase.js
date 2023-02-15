import axios from "axios";
const baseURL = "http://localhost:8081/";
axios.defaults.baseURL = baseURL;

export function get(url, params) {
  return axios
    .get(url, {
      params,
    })
    .then((res) => res.data);
}

export function post(url, body) {
  return axios.post(url, body).then((res) => res.data);
}
