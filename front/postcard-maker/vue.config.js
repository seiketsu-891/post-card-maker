// const { defineConfig } = require("@vue/cli-service");
module.exports = {
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
