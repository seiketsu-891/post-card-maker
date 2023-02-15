import axios from "@/main.js";

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
