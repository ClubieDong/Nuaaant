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

    tabbarList: [{
        text: "接单",
        iconPath: "/assets/tab1.png",
        selectedIconPath: "/assets/tab1-chosen.png"
      },{
        text: "发布",
        iconPath: "/assets/tab2.png",
        selectedIconPath: "/assets/tab2-chosen.png"
      },{
        text: "聊天",
        iconPath: "/assets/tab3.png",
        selectedIconPath: "/assets/tab3-chosen.png"
      },{
        text: "主页",
        iconPath: "/assets/tab4.png",
        selectedIconPath: "/assets/tab4-chosen.png"
      }
    ],
    tabCurrent: 0,

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
        giverAvatarUrl: data[i].GiverAvatarUrl,
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
  },

  changeFilter: function () {
    util.tryCatch(this, this.getOrders);
  },

  tabChange: function (r) {
    this.setData({
      tabCurrent: 0
    });
    switch (r.detail.index) {
      case 0:
        break;
      case 1:
        wx.navigateTo({
          url: "/pages/addOrder/addOrder"
        });
        break;
      case 2:
        wx.navigateTo({
          url: "/pages/messageList/messageList"
        });
        break;
      case 3:
        wx.navigateTo({
          url: "/pages/viewUserInfo/viewUserInfo"
        });
        break;
      default:
        break;
    }
  }
});