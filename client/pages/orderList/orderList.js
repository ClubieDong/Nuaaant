const app = getApp();
const wxp = app.wxp;
const util = require("../../utils/util.js");

Page({
  data: {
    filters: [],
    orders: [{
      id: 1,
      typeIndex: 1,
      timeLeft: "还剩xxx分钟",
      title: "标题标题标题",
      reward: 3.50,
      avatarUrl: "https://thirdwx.qlogo.cn/mmopen/vi_32/iax58ricXItSayb1gOwYASAU4Cw1C2gyZYMSDibaGq3zUPHG9u0MA9VLY2uMu4DLbOGbB9Wb2trwXd5picARqyOYgg/132",
      giverName: "Clubie",
      giverScore: 4.5,
      takerCount: 5,
      likeCount: 5,
      remarkCount: 5
    }],

    searchText: "",
    typeIndex: 0,
    sortIndex: 0,
    filterIndex: 0,
    
    orderTypes: app.orderTypes,
    sortTypes: [
      "默认",
      "金额",
      "剩余时间",
      "热度",
      "评分",
    ],
    orderTypeIcons: app.orderTypeIcons,
    rpx: app.rpx,

    toptips_msg: "",
    toptips_type: "",
    toptips_show: false
  },

  onLoad: async function() {
    await this.getOrders();
    const orderTypes = this.data.orderTypes;
    orderTypes[0] = "全部";
    this.setData({
      orderTypes: orderTypes
    });
  },

  getOrders: async function() {
    const data = await util.get("/order/list", {
      sessionID: await util.login(),
      searchText: this.data.searchText,
      typeIndex: this.data.typeIndex,
      sortIndex: this.data.sortIndex,
      filterID: this.data.filterIndex
    });
    console.log(data);
    const orders = [];
    for (const i in data)
      orders.push({
        id: data[i].ID,
        typeIndex: data[i].TypeIndex,
        timeLeft: data[i].Deadline == null ? "无截止期限" : util.formatTime(data[i].Deadline),
        title: data[i].Title,
        reward: data[i].Reward,
        avatarUrl: data[i].AvatarUrl,
        giverName: data[i].GiverName,
        giverScore: data[i].GiverScore,
        takerCount: data[i].TakerCount,
        likeCount: data[i].LikeCount,
        remarkCount: data[i].RemarkCount
      });
    this.setData({
      orders: orders
    });
  }

});