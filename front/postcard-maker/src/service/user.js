import { post } from "./axiosBase";

export function login(user) {
  return post("/double-tokens", user);
}

export function register(user) {
  return post("/users", user);
}
