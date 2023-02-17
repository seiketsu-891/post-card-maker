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

// 以delete为名会提示是关键字
export function del(url, body) {
  return axios.delete(url, body).then((res) => res.data);
}
