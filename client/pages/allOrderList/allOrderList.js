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
      "热度"
    ],
    orderTypeIcons: app.orderTypeIcons,
    rpx: app.rpx,

    tabbarCurrent: 0,
    tabbarCoverBase: 90,
    tabbarCoverDelta: 0,
    tabbarAlpha: 1 / app.rpx / 750 * 200,

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

  selectTab: function (r) {
    this.setData({
      tabbarCurrent: Number(r.currentTarget.dataset.tab)
    });
  },
  tabbarTransition: function (r) {
    this.setData({
      tabbarCoverDelta: r.detail.dx * this.data.tabbarAlpha
    });
  },
  tabbarTransitionEnd: function () {
    this.setData({
      tabbarCoverBase: this.data.tabbarCurrent * 200 + 90,
      tabbarCoverDelta: 0
    });
  },

  getOrders: async function () {
    const data = await util.get("/order/list/user", {
      sessionID: await util.login(),
      userID: await util.getUserID(),
      searchText: this.data.searchText,
      typeIndex: this.data.typeIndex,
      sortIndex: this.data.sortIndex,
      filterID: this.data.filterIndex
    });
    const finished = [], ongoing = [], waiting = [];
    for (const i in data) {
      const item = {
        id: data[i].ID,
        state: data[i].State,
        typeIndex: data[i].TypeIndex,
        timeLeft: util.formatFutureTime(data[i].Deadline),
        title: data[i].Title,
        reward: data[i].Reward,
        takerAvatarUrl: data[i].TakerAvatarUrl,
        takerName: data[i].TakerName,
        takerScore: data[i].TakerScore,
        takerCount: data[i].TakerCount,
        likeCount: data[i].LikeCount,
        remarkCount: data[i].RemarkCount,
        publishTime: util.formatPassTime(data[i].PublishTime),
        acceptTime: util.formatPassTime(data[i].AcceptTime),
        submitTime: util.formatPassTime(data[i].SubmitTime),
        completeTime: util.formatPassTime(data[i].CompleteTime)
      };
      if (item.state == 1)
        waiting.push(item);
      else if (item.state == 2 || item.state == 3)
        ongoing.push(item);
      else if (item.state == 4)
        finished.push(item);
    }
    this.setData({
      waitingOrders: waiting,
      ongoingOrders: ongoing,
      finishedOrders: finished
    });
  },

  selectOrder: function (r) {
    wx.navigateTo({
      url: "/pages/viewOrder/viewOrder?orderID=" + r.currentTarget.dataset.id,
    });
  },

  changeFilter: function() {
    util.tryCatch(this, this.getOrders);
  }

});