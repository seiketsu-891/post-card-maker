import { get } from "./axiosBase";
import { post } from "./axiosBase";

export function getTasks() {
  return get("/tasks");
}

export function completeTask(taskId) {
  return post("/user-tasks?taskId=" + taskId);
}
