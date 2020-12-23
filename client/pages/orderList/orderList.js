const app = getApp();
const wxp = app.wxp;
const util = require("../../utils/util.js");

Page({
  data: {
    filters: [],
    orders: [],

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

  onLoad: async function () {
    util.tryCatch(this, async () => {
      await this.getOrders();
      const orderTypes = this.data.orderTypes;
      orderTypes[0] = "全部";
      this.setData({
        orderTypes: orderTypes
      });
    });
  },
  
  onHide: function () {
    this.setData({
      hiding: true
    });
  },

  onShow: function () {
    if (this.data.hiding) {
      this.setData({
        hiding: false
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    }
  },


  getOrders: async function () {
    const data = await util.get("/order/list", {
      sessionID: await util.login(),
      searchText: this.data.searchText,
      typeIndex: this.data.typeIndex,
      sortIndex: this.data.sortIndex,
      filterID: this.data.filterIndex
    });
    const orders = [];
    for (const i in data)
      orders.push({
        id: data[i].ID,
        typeIndex: data[i].TypeIndex,
        timeLeft: util.formatFutureTime(data[i].Deadline),
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
  },

  selectOrder: function (r) {
    wx.navigateTo({
      url: "/pages/viewOrder/viewOrder?orderID=" + r.currentTarget.dataset.id,
    });
  }

});