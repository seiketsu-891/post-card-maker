/*****
 * RSA加密解密工具类
 * **/
import JSEncrypt from "jsencrypt";
// 公钥
const publicKey = `-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0A50MBEkse+DfMMKj7rB
LzjYiO//N77w4LPev4Db/FHj1ej/12vydZfP8S2eXfNLi3jSBmwiMwfm8DyGm+EB
OzR1j54dxs8sZ9IjYnrmPyFb+4AhAQxcxs4y4C/XfCD+D7CVJLaHfp9ijxrS3TzT
eQEOt8iWtOsOFyLYtr+ZtRgLVrfiWl9XsdeM/rNUbEqzRm8mWw+zPmV3qFBEamhi
GNN8WbZ9eVz1gEYOR7VH8W6Uy7Nl5AW0hxt7oENvrWx/1r5JnEzCfV94aU6e+Uil
zazln7V98GdL/BK7ZuFnbZg9fVq3BqofnH+tKjxPiRfCAg0nT1gnI7ifTUVjLX9m
0QIDAQAB
-----END PUBLIC KEY-----`;

// 这个是公钥对象，而不是字符串

export function encrypt(data) {
  const encryptor = new JSEncrypt();
  encryptor.setPublicKey(publicKey);
  const encrypted = encryptor.encrypt(data);
  return encrypted;
}
