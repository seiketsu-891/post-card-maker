// const { defineConfig } = require("@vue/cli-service");
module.exports = {
  //  give a try to solve the debugging problem
  productionSourceMap: true,
  configureWebpack: {
    devtool: "source-map",
  },
  css: {
    loaderOptions: {
      less: {
        lessOptions: {
          javascriptEnabled: true,
          modifyVars: {
            // "primary-color": "#4561F3",
            // "info-color": "#4561F3",
          },
        },
      },
    },
  },
};
