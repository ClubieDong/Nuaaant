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
  orderTypes: [
    "未指定",
    "代取快递",
    "询问问题",
    "租借物品",
    "收购物品",
    "请求帮忙",
    "其他类别"
  ],
  expressSizeTypes: [
    "未指定",
    "超小件(<10³cm³)",
    "小件(10³cm³~20³cm³)",
    "中件(20³cm³~40³cm³)",
    "大件(40³cm³~80³cm³)",
    "超大件(>80³cm³)"
  ],
  expressWeightTypes: [
    "未指定",
    "超轻件(<1kg)",
    "轻件(1kg~2kg)",
    "中件(2kg~4kg)",
    "重件(4kg~8kg)",
    "超重件(>8kg)"
  ],
  questionTypes: [
    "未指定",
    "专业类",
    "学习类",
    "服装类",
    "美食类",
    "校园类",
    "出行类",
    "社团类"
  ],
  timeUnits: [
    "单位",
    "分钟",
    "小时",
    "天",
    "星期",
    "月",
    "年"
  ],
  orderTypeIcons: [
    "add2",
    "shop",
    "info",
    "transfer2",
    "download",
    "sticker",
    "more2"
  ],

  onLaunch: function () {
    promisifyAll(wx, this.wxp);
  }
})