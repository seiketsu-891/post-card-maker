export { shallowEqual, shallowCopy };

function shallowEqual(obj1, obj2) {
  const keys1 = Object.keys(obj1);
  for (let key of keys1) {
    if (obj1[key] !== obj2[key]) {
      return false;
    }
  }
  return true;
}

function shallowCopy(target, src) {
  const keys = Object.keys(src);
  for (let key of keys) {
    target[key] = src[key];
  }
}
