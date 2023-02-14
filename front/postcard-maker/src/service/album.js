import { get } from "./axiosBase";

export function getAlbums(pageNum, pageSize) {
  return get("/albums?pageNum=" + pageNum + "&&pageSize=" + pageSize);
}
