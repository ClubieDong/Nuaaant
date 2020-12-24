const app = getApp();
const wxp = app.wxp;
const util = require("../../utils/util.js");

Page({
  data: {
    messages: [],
    input: "",

    rpx: app.rpx,

    toptips_msg: "",
    toptips_type: "",
    toptips_show: false
  },

  onLoad: function (r) {
    util.tryCatch(this, async () => {
      const userID = r.userID;
      let data = await util.get("/user/basic", {
        sessionID: await util.login(),
        userID: userID
      });
      wx.setNavigationBarTitle({
        title: data.NickName,
      });
      const receiverAvatarUrl = data.AvatarUrl;
      data = await util.get("/user/basic", {
        sessionID: await util.login(),
        userID: await util.getUserID(),
      });
      const senderAvatarUrl = data.AvatarUrl;
      this.setData({
        userID: userID,
        senderAvatarUrl: senderAvatarUrl,
        receiverAvatarUrl: receiverAvatarUrl
      });
      data = await util.get("/message/get", {
        sessionID: await util.login(),
        userID: userID
      });
      const messages = [];
      for (const i in data) {
        const item = {
          id: data[i].ID,
          senderID: data[i].SenderID,
          text: data[i].Text
        };
        if (i == 0 || (new Date(data[i].Time) - new Date(data[i - 1].Time)) > 5 * 60 * 1000)
          item.time = util.time2str(new Date(data[i].Time));
        messages.push(item);
      }
      this.setData({
        messages: messages
      });
    });
  },

  send: function(r) {
    util.tryCatch(this, async () => {
      const text = this.data.input;
      this.setData({
        input: ""
      });
      await util.post("/message/add", {
        sessionID: await util.login(),
        receiverID: this.data.userID,
        text: text
      });
      this.onLoad({
        userID: this.data.userID,
      });
    });
  }
});