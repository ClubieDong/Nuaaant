import {
  promisifyAll,
  promisify
} from "miniprogram-api-promise";

App({
  wxp: {},
  rpx: wx.getSystemInfoSync().screenWidth / 750,
  colleges: [
    "保密",
    "航空学院",
    "能源与动力学院",
    "自动化学院",
    "电子信息工程学院",
    "机电学院",
    "材料科学与技术学院",
    "民航/飞行学院",
    "理学院",
    "经济与管理学院",
    "人文与社会科学学院",
    "艺术学院",
    "外国语学院",
    "马克思主义学院",
    "航天学院",
    "计算机科学与技术学院",
    "国际教育学院",
  ],
  grades: [
    "保密",
    "2020级",
    "2019级",
    "2018级",
    "2017级",
    "2016级",
    "2015级"
  ],

  onLaunch: function () {
    promisifyAll(wx, this.wxp);
  }
})
