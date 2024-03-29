import { post, get } from "./axiosBase";

export function savePostcard(content) {
  return post("/postcards", content);
}

export function getPostcard(id) {
  // 如果没有传id，而是在路径后直接+id的话，会传undefined, 而controller里要求参数是long类型，所以也会报类型错误
  const paramPath = id ? "/" + id : "";
  return get("/postcards" + paramPath);
}

export function performUndo(id) {
  console.log(id);
  return post("/undo/" + id);
}

export function performRedo(id) {
  return post("/redo/" + id);
}
export function getProjects(pageNum, pageSize) {
  return get("/postcards/list?pageNum=" + pageNum + "&pageSize=" + pageSize);
}
