/*****
 * RSA加密解密工具类
 * **/
import JSEncrypt from "jsencrypt";
// 公钥
const PUBLIC_KEY =
  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQk33iNdA8Iey7J6XrBsidqn6u8EDLWPHsfEUgLQ3qiTikhPKDTzZkpAfU/O0x6NvSKa7Dp0+uqWT3vnW1De0+3u8mCYdVfOdH94VG4xg5U5UrRJei8HhPiXuvKQ+6NBtebCCW5adZ4pBgOiU14cJLhVmm+dYiLo3IDD5LqrlomQIDAQAB";

export function encrypt(data) {
  const encryptor = new JSEncrypt();
  encryptor.setPublicKey(PUBLIC_KEY);
  const encrypted = encryptor.encrypt(data);
  console.log(encrypted);
  return encrypted;
}
