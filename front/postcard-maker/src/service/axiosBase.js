import axios from "axios";
const baseURL = "http://localhost:8081/";
axios.defaults.baseURL = baseURL;

export function get(url, params) {
  return axios
    .get(url, {
      params,
    })
    .then((res) => {
      const resp = res.data;
      if (resp.code == 200) {
        return resp.data;
      } else {
        // error handle
      }
    });
}

export function post(url, body) {
  return axios.post(url, body).then((res) => {
    const resp = res.data;
    if (resp.code == 200) {
      return resp.data;
    } else {
      // error handle
    }
  });
}
