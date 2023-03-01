import { get } from "./axiosBase";
import { post } from "./axiosBase";
import { del } from "./axiosBase";
export function login(user) {
  return post("/double-tokens", user);
}

export function register(user) {
  return post("/users", user);
}

export function ifPremium() {
  return get("/premiums");
}

export function sendCode(email, purpose) {
  return post("/verification-codes?email=" + email + "&purpose= " + purpose);
}

export function resetPassword(user) {
  return post("/passwords", user);
}

export function logout() {
  return del("/refresh-tokens");
}

export function becomePremium() {
  return post("/premiums");
}
