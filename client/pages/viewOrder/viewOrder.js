const app = getApp();
const wxp = app.wxp;
const util = require("../../utils/util.js");

Page({
  data: {
    remarkCount: 0,

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
      const data = await util.get("/order", {
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
      if (userID == data.TaverID)
        userType = 1;
      else if (userID == data.GiverID)
        userType = 0;
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
        returnTime: new Date(data.ReturnTime).toLocaleString(),

        giverID: data.GiverID,
        giverAvatarUrl: data.GiverAvatarUrl,
        giverName: data.GiverName,
        giverScore: data.GiverScore,
        takerID: data.TaverID,
        takerAvatarUrl: data.TaverAvatarUrl,
        takerName: data.TaverName,
        takerScore: data.TaverScore,
        appliers: appliers
      });
    });
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
      await util.get("/order/apply", {
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
      await util.get("/order/apply/cancel", {
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
      await util.get("/order/like", {
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
      await util.get("/order/like/cancel", {
        sessionID: await util.login(),
        orderID: this.data.orderID
      });
      this.onLoad({
        orderID: this.data.orderID
      });
    });
  },

  chooseTaker: function (r) {
    console.log(r.currentTarget.dataset.id);
    // util.tryCatch(this, async () => {
    //   await util.get("/order/chooseTaker", {
    //     sessionID: await util.login(),
    //     orderID: this.data.orderID,
    //     takerID: r.currentTarget.dataset.id
    //   });
    //   this.onLoad({
    //     orderID: this.data.orderID
    //   });
    // });
  }
});