
// // 서버 실행 시
// const fs = require('fs');

// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    // local 실행 시
    https: false,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'http://i5d101.p.ssafy.io:8080/',
        // target: 'http://localhost:8080/',
      }
    },
    // historyApiFallback: true,
    // hot: true,
    // disableHostCheck: true,

    // 서버 실행시
    // https: true,
    // key: fs.readFileSync('/etc/letsencrypt/live/i5d101.p.ssafy.io/privkey.pem'),
    // cert: fs.readFileSync('/etc/letsencrypt/live/i5d101.p.ssafy.io/cert.pem'),
    // ca: fs.readFileSync('/etc/letsencrypt/live/i5d101.p.ssafy.io/chain.pem'),
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'http://i5d101.p.ssafy.io:8080/',
        // target: 'http://localhost:8080/',
      }
    },
    historyApiFallback: true,
    hot: true,
    disableHostCheck: true,

  },
  css: {
    requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  },
  transpileDependencies: [
    'element-plus'
  ],
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}
