import { get } from "./axiosBase";
import { post } from "./axiosBase";

// 获取字体列表
export function getFonts() {
  return get("/fonts");
}

// 获取形状
export function getShapes() {
  return get("/shapes");
}

// 获取图片集
export function getAlbums(pageNum, pageSize) {
  const params = {
    pageSize,
    pageNum,
  };
  return get("/albums", params);
}

// 获取图片
export function getIllustrations(
  pageNum,
  pageSize,
  albumId = "",
  keyword = ""
) {
  const params = {
    pageNum,
    pageSize,
    albumId,
    keyword,
  };
  return get("/illustrations", params);
}

// 上传文件
export function uploadFile(file) {
  return post("/files", file);
}

// 获取用户上传的图片素材
export function getUserPics(pageNum, pageSize) {
  return get("/user-pics?pageNum=" + pageNum + "&pageSize=" + pageSize);
}
