import {
  promisifyAll,
  promisify
} from 'miniprogram-api-promise';

App({
  wxp: {},
  onLaunch: function () {
    promisifyAll(wx, this.wxp);
  }
})