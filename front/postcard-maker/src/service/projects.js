import { get } from "./axiosBase";

export function getProjects(pageNum, pageSize) {
  return get("/projects?pageNum=" + pageNum + "&pageSize=" + pageSize);
}
