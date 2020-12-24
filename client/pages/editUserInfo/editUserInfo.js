const app = getApp();
const util = require("../../utils/util.js");

Page({
  data: {
    avatarUrl: "",
    nickName: "",
    isMale: false,
    isFemale: false,
    unknownGender: true,
    motto: "",
    collegeIndex: 0,
    gradeIndex: 0,
    dormitory: "",
    studentID: "",
    realName: "",
    phone: "",
    email: "",

    colleges: app.colleges,
    grades: app.grades,
    rpx: app.rpx,

    toptips_msg: "",
    toptips_type: "",
    toptips_show: false
  },

  onLoad: function (r) {
    util.tryCatch(this, async () => {
      const data = await util.get("/user/get", {
        sessionID: await util.login(),
        userID: await util.getUserID()
      });
      this.setData({
        avatarUrl: util.emptify(data.avatarUrl),
        nickName: util.emptify(data.nickName),
        isMale: data.gender == 1,
        isFemale: data.gender == 2,
        unknownGender: data.gender == 0,
        motto: util.emptify(data.motto),
        collegeIndex: util.zeroify(data.collegeIndex),
        gradeIndex: util.zeroify(data.gradeIndex),
        dormitory: util.emptify(data.dormitory),
        studentID: util.emptify(data.studentID),
        realName: util.emptify(data.realName),
        phone: util.emptify(data.phone),
        email: util.emptify(data.email)
      });
    });
  },

  getUserInfo: function (r) {
    const data = r.detail.userInfo;
    this.setData({
      avatarUrl: data.avatarUrl,
      nickName: data.nickName,
      isMale: data.gender == 1,
      isFemale: data.gender == 2,
      unknownGender: data.gender == 0
    });
  },

  submit: function () {
    util.tryCatch(this, async () => {
      if (this.data.studentID != "" && !/^\d{9}$/.test(this.data.studentID))
        throw "学号格式不正确";
      if (this.data.phone != "" && !/^\d{11}$/.test(this.data.phone))
        throw "手机号格式不正确";
      if (this.data.email != "" && !/^[\w-]+@[\w-]+(\.[\w-]+)+$/.test(this.data.email))
        throw "邮箱格式不正确";
      const data = {
        sessionID: await util.login(),
        avatarUrl: this.data.avatarUrl,
        nickName: this.data.nickName,
        gender: this.data.isMale ? 1 : this.data.isFemale ? 2 : 0,
        motto: this.data.motto,
        collegeIndex: this.data.collegeIndex,
        gradeIndex: this.data.gradeIndex,
        dormitory: this.data.dormitory,
        studentID: this.data.studentID,
        realName: this.data.realName,
        phone: this.data.phone,
        email: this.data.email,
      };
      util.deleteNull(data);
      await util.post("/user/set", data);
      this.setData({
        toptips_msg: "用户信息更新成功",
        toptips_type: "success",
        toptips_show: true
      });      
      await util.sleep(1000);
      wx.navigateBack();
    });
  }
});