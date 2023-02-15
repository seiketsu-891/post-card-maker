import { post } from "./axiosBase";

export function login(user) {
  return post("/sessions", user);
}

export function register(user) {
  return post("/users", user);
}
