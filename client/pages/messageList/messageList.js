const app = getApp();
const wxp = app.wxp;
const util = require("../../utils/util.js");

Page({
  data: {
    messageList: [],

    rpx: app.rpx,

    toptips_msg: "",
    toptips_type: "",
    toptips_show: false
  },

  onLoad: function (r) {
    util.tryCatch(this, async () => {
      const data = await util.get("/message/list", {
        sessionID: await util.login()
      });
      const messageList = [];
      for (const i in data)
        messageList.push({
          userID: data[i].UserID,
          nickName: data[i].NickName,
          avatarUrl: data[i].AvatarUrl,
          time: util.time2str(new Date(data[i].Time)),
          text: data[i].Text,
          notRead: data[i].NotRead
        });
      this.setData({
        messageList: messageList
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

  sendMessage: function(r) {
    wx.navigateTo({
      url: "/pages/message/message?userID=" + r.currentTarget.dataset.id
    });
  },
});