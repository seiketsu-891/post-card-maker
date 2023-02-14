import { post } from "./axiosBase";

export function login(user) {
  return post("/sessions", user);
}
