const app = getApp();
const wxp = app.wxp;
const util = require("../../utils/util.js");

Page({
  data: {
    remarkInput: "",
    remarks: [],

    tabbarCurrent: 0,
    tabbarCoverBase: 100,
    tabbarCoverDelta: 0,
    tabbarAlpha: 1 / app.rpx / 750 * 200,
    userScrollIntoView: "",

    types: app.orderTypes,
    sizeTypes: app.expressSizeTypes,
    weightTypes: app.expressWeightTypes,
    questionTypes: app.questionTypes,
    timeUnits: app.timeUnits,
    typeIcons: app.orderTypeIcons,
    rpx: app.rpx,

    toptips_msg: "",
    toptips_type: "",
    toptips_show: false
  },

  onLoad: function (r) {
    util.tryCatch(this, async () => {
      const data = await util.get("/order/detail", {
        sessionID: await util.login(),
        orderID: r.orderID
      });
      const userID = await util.getUserID();
      const appliers = [];
      let userType = 3;
      for (const i in data.Appliers) {
        if (userID == data.Appliers[i].ID)
          userType = 2;
        appliers.push({
          id: data.Appliers[i].ID,
          avatarUrl: data.Appliers[i].AvatarUrl,
          name: data.Appliers[i].NickName,
          score: data.Appliers[i].Score,
        });
      }
      if (userID == data.TakerID)
        userType = 1;
      else if (userID == data.GiverID)
        userType = 0;
      const remarks = [];
      for (const i in data.Remarks)
        remarks.push({
          id: data.Remarks[i].ID,
          avatarUrl: data.Remarks[i].AvatarUrl,
          nickName: data.Remarks[i].NickName,
          time: util.formatPassTime(data.Remarks[i].Time),
          agreeCount: data.Remarks[i].AgreeCount,
          agreed: data.Remarks[i].Agreed,
          text: data.Remarks[i].Text,
        });
      this.setData({
        userID: userID,
        orderID: r.orderID,
        userType: userType,
        like: data.Like,
        likeCount: data.LikeCount,

        state: data.State,
        typeIndex: data.TypeIndex,
        title: data.Title,
        reward: data.Reward,
        publishTime: util.formatPassTime(data.PublishTime),
        acceptTime: util.formatPassTime(data.AcceptTime),
        submitTime: util.formatPassTime(data.SubmitTime),
        completeTime: util.formatPassTime(data.CompleteTime),
        deadline: util.formatFutureTime(data.Deadline),
        fromAddr: data.FromAddr,
        toAddr: data.ToAddr,
        isSelf: data.IsSelf,
        sizeIndex: data.SizeIndex,
        weightIndex: data.WeightIndex,
        expressCode: data.ExpressCode,
        questionTypeIndex: data.QuestionTypeIndex,
        simpleDesc: data.SimpleDesc,
        detailedDesc: data.DetailedDesc,
        duration: data.Duration,
        unitIndex: data.UnitIndex,
        returnTime: data.ReturnTime == undefined ? "" : new Date(data.ReturnTime).toLocaleString(),

        giverID: data.GiverID,
        giverAvatarUrl: data.GiverAvatarUrl,
        giverName: data.GiverName,
        giverScore: data.GiverScore,
        takerID: data.TakerID,
        takerAvatarUrl: data.TakerAvatarUrl,
        takerName: data.TakerName,
        takerScore: data.TakerScore,
        appliers: appliers,
        remarks: remarks
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
      tabbarCoverBase: this.data.tabbarCurrent * 200 + 100,
      tabbarCoverDelta: 0
    });
  },

  gotoChooseTaker: function () {
    this.setData({
      tabbarCurrent: 1,
      userScrollIntoView: "applier-list"
    });
  },

  apply: function () {
    util.tryCatch(this, async () => {
      this.setData({
        userType: 2
      });
      await util.get("/apply", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  cancelApply: function () {
    util.tryCatch(this, async () => {
      this.setData({
        userType: 3
      });
      await util.get("/apply/cancel", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  like: function () {
    util.tryCatch(this, async () => {
      this.setData({
        like: true,
        likeCount: this.data.likeCount + 1
      });
      await util.get("/like", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  cancelLike: function () {
    util.tryCatch(this, async () => {
      this.setData({
        like: false,
        likeCount: this.data.likeCount - 1
      });
      await util.get("/like/cancel", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  chooseTaker: function (r) {
    util.tryCatch(this, async () => {
      this.setData({
        state: 2
      });
      await util.get("/giver/choose", {
        sessionID: await util.login(),
        orderID: this.data.orderID,
        takerID: r.currentTarget.dataset.id
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  cancelChooseTaker: function (r) {
    util.tryCatch(this, async () => {
      this.setData({
        state: 1
      });
      await util.get("/giver/choose/cancel", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  withdraw: function () {
    util.tryCatch(this, async () => {
      this.setData({
        state: 0
      });
      await util.get("/giver/withdraw", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  republish: function () {
    util.tryCatch(this, async () => {
      this.setData({
        state: 1
      });
      await util.get("/giver/republish", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  giveup: function () {
    util.tryCatch(this, async () => {
      this.setData({
        state: 1,
        userType: 3
      });
      await util.get("/taker/giveup", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  submit: function () {
    util.tryCatch(this, async () => {
      this.setData({
        state: 3,
      });
      await util.get("/taker/submit", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  cancelSubmit: function () {
    util.tryCatch(this, async () => {
      this.setData({
        state: 2,
      });
      await util.get("/taker/submit/cancel", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  acceptResult: function () {
    util.tryCatch(this, async () => {
      this.setData({
        state: 4,
      });
      await util.get("/giver/accept", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  rejectResult: function () {
    util.tryCatch(this, async () => {
      this.setData({
        state: 2
      });
      await util.get("/giver/reject", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  editOrder: function () {
    util.tryCatch(this, async () => {
      wx.navigateTo({
        url: "/pages/addOrder/addOrder?orderID=" + this.data.orderID,
      });
    });
  },

  sendRemark: function () {
    util.tryCatch(this, async () => {
      const text = this.data.remarkInput;
      this.setData({
        remarkInput: ""
      });
      await util.post("/remark/add", {
        sessionID: await util.login(),
        orderID: this.data.orderID,
        text: text
      });
      this.onLoad({
        orderID: this.data.orderID
      });
      this.setData({
        toptips_msg: "评论成功",
        toptips_type: "success",
        toptips_show: true
      });
    });
  },

  agree: function (r) {
    util.tryCatch(this, async () => {
      const remarkID = r.currentTarget.dataset.id;
      const remarkIndex = r.currentTarget.dataset.index;
      this.setData({
        ["remarks[" + remarkIndex + "].agreed"]: true,
        ["remarks[" + remarkIndex + "].agreeCount"]: this.data.remarks[remarkIndex].agreeCount + 1
      });
      await util.get("/agree", {
        sessionID: await util.login(),
        remarkID: remarkID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  cancelAgree: function (r) {
    util.tryCatch(this, async () => {
      const remarkID = r.currentTarget.dataset.id;
      const remarkIndex = r.currentTarget.dataset.index;
      this.setData({
        ["remarks[" + remarkIndex + "].agreed"]: false,
        ["remarks[" + remarkIndex + "].agreeCount"]: this.data.remarks[remarkIndex].agreeCount - 1
      });
      await util.get("/agree/cancel", {
        sessionID: await util.login(),
        remarkID: remarkID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  gotoRemark: function() {
    this.setData({
      tabbarCurrent: 2
    });
  }
});