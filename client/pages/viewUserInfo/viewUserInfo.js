const app = getApp();
const util = require("../../utils/util.js");

Page({
  data: {
    colleges: app.colleges,
    grades: app.grades,
    rpx: app.rpx,

    toptips_msg: "",
    toptips_type: "",
    toptips_show: false
  },

  onLoad: function (r) {
    util.tryCatch(this, async () => {
      const myUserID = await util.getUserID();
      const userID = r.userID == undefined ? myUserID : r.userID;
      let data = await util.get("/user/get", {
        sessionID: await util.login(),
        userID: userID
      });
      this.setData({
        myUserID: myUserID,
        userID: userID,
        avatarUrl: util.emptify(data.avatarUrl),
        nickName: util.emptify(data.nickName),
        gender: util.zeroify(data.gender),
        motto: util.emptify(data.motto),
        collegeIndex: util.zeroify(data.collegeIndex),
        gradeIndex: util.zeroify(data.gradeIndex),
        dormitory: util.emptify(data.dormitory),
        studentID: util.emptify(data.studentID),
        realName: util.emptify(data.realName),
        phone: util.emptify(data.phone),
        email: util.emptify(data.email)
      });
      data = await util.get("/user/score", {
        sessionID: await util.login(),
        userID: userID
      });
      this.setData({
        giverScore: data.GiverScore,
        giverCount: data.GiverCount,
        takerScore: data.TakerScore,
        takerCount: data.TakerCount
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
        userID: this.data.userID
      });
    }
  },

  editInfo: function() {
    wx.navigateTo({
      url: "/pages/editUserInfo/editUserInfo",
    });
  },

  viewAllOrders: function() {
    wx.navigateTo({
      url: "/pages/allOrderList/allOrderList?userID=" + this.data.userID
    });
  },

  sendMessage: function() {
    wx.navigateTo({
      url: "/pages/message/message?userID=" + this.data.userID
    });
  }
});