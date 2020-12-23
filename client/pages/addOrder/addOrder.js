const app = getApp();
const wxp = app.wxp;
const util = require("../../utils/util.js");

Page({
  data: {
    templateList: [],

    typeIndex: 0,
    title: "",
    reward: "",
    haveDdl: false,
    ddlDate: util.date2str(new Date()),
    ddlTime: util.time2str(new Date()),

    fromAddr: "",
    toAddr: "",
    isSelf: false,
    isNotSelf: false,
    sizeIndex: 0,
    weightIndex: 0,
    expressCode: "",

    questionTypeIndex: 0,
    questionSimpleDesc: "",
    questionDetailedDesc: "",

    borrowSimpleDesc: "",
    duration: "",
    unitIndex: 0,
    haveReturnTime: false,
    returnDate: util.date2str(new Date()),
    returnTime: util.time2str(new Date()),
    borrowDetailedDesc: "",

    buySimpleDesc: "",
    buyDetailedDesc: "",

    serviceSimpleDesc: "",
    serviceDetailedDesc: "",

    otherSimpleDesc: "",
    otherDetailedDesc: "",

    types: app.orderTypes,
    sizeTypes: app.expressSizeTypes,
    weightTypes: app.expressWeightTypes,
    questionTypes: app.questionTypes,
    timeUnits: app.timeUnits,
    orderTypeIcons: app.orderTypeIcons,
    today: new Date().toISOString().substr(0, 10),
    rpx: app.rpx,

    toptips_msg: "",
    toptips_type: "",
    toptips_show: false
  },

  onLoad: function (r) {
    util.tryCatch(this, async () => {
      await this.loadTemplate();
    });
  },

  loadTemplate: async function () {
    const data = await util.get("/template/list", {
      sessionID: await util.login()
    });
    const templateList = [];
    for (let i = 0; i < data.length; ++i)
      templateList.push({
        idx: i,
        id: data[i].ID,
        typeIndex: data[i].TypeIndex,
        userDefine: data[i].UserDefine == 1,
        title: data[i].Title
      });
    this.setData({
      templateList: templateList
    });
  },

  chooseTemplate: function (r) {
    util.tryCatch(this, async () => {
      const data = await util.get("/template/get", {
        sessionID: await util.login(),
        templateID: r.currentTarget.dataset.id
      });
      this.setData({
        typeIndex: data.typeIndex,
        title: data.title,
        reward: util.emptify(data.reward),
        haveDdl: data.deadline != null,
        ddlDate: util.date2str(data.deadline == null ? new Date() : new Date(data.deadline)),
        ddlTime: util.time2str(data.deadline == null ? new Date() : new Date(data.deadline)),
        fromAddr: util.emptify(data.fromAddr),
        toAddr: util.emptify(data.toAddr),
        isSelf: data.isSelf != null && data.isSelf,
        isNotSelf: data.isSelf != null && !data.isSelf,
        sizeIndex: util.zeroify(data.sizeIndex),
        weightIndex: util.zeroify(data.weightIndex),
        expressCode: util.emptify(data.expressCode),
        questionTypeIndex: util.zeroify(data.questionTypeIndex),
        questionSimpleDesc: data.typeIndex == 2 ? data.simpleDesc : "",
        questionDetailedDesc: data.typeIndex == 2 ? data.detailedDesc : "",
        borrowSimpleDesc: data.typeIndex == 3 ? data.simpleDesc : "",
        duration: util.emptify(data.duration),
        unitIndex: util.zeroify(data.unitIndex),
        haveReturnTime: data.returnTime != null,
        returnDate: util.date2str(data.returnTime == null ? new Date() : new Date(data.returnTime)),
        returnTime: util.time2str(data.returnTime == null ? new Date() : new Date(data.returnTime)),
        borrowDetailedDesc: data.typeIndex == 3 ? data.detailedDesc : "",
        buySimpleDesc: data.typeIndex == 4 ? data.simpleDesc : "",
        buyDetailedDesc: data.typeIndex == 4 ? data.detailedDesc : "",
        serviceSimpleDesc: data.typeIndex == 5 ? data.simpleDesc : "",
        serviceDetailedDesc: data.typeIndex == 5 ? data.detailedDesc : "",
        otherSimpleDesc: data.typeIndex == 6 ? data.simpleDesc : "",
        otherDetailedDesc: data.typeIndex == 6 ? data.detailedDesc : "",
      });
    });
  },

  deleteTemplate: function (r) {
    util.tryCatch(this, async () => {
      console.log(r.currentTarget.dataset.id);
      const res = await wxp.showModal({
        title: "警告",
        content: "确认要删除模板吗？",
        confirmText: "删除",
        confirmColor: "#FA5151"
      });
      if (!res.confirm)
        return;
      await util.del("/template/delete", {
        sessionID: await util.login(),
        templateID: r.currentTarget.dataset.id
      });
      this.setData({
        toptips_msg: "模板删除成功",
        toptips_type: "success",
        toptips_show: true
      });
      await this.loadTemplate();
    });
  },

  addDdl: function () {
    this.setData({
      haveDdl: true
    });
  },
  removeDdl: function () {
    this.setData({
      haveDdl: false
    });
  },
  addReturnDate: function () {
    this.setData({
      haveReturnTime: true
    });
  },
  removeReturnDate: function () {
    this.setData({
      haveReturnTime: false
    });
  },

  saveAsTemplate: function () {
    util.tryCatch(this, async () => {
      if (this.data.title == "")
        throw "标题不能为空";
      if (this.data.reward != "" && !/^\d+(\.\d+)?$/.test(this.data.reward))
        throw "金额格式不正确";
      const data = {
        sessionID: await util.login(),
        typeIndex: this.data.typeIndex,
        title: this.data.title,
        reward: this.data.reward
      };
      if (this.data.haveDdl)
        data.deadline = this.data.ddlDate + " " + this.data.ddlTime;
      switch (Number(this.data.typeIndex)) {
        case 1:
          data.fromAddr = this.data.fromAddr;
          data.toAddr = this.data.toAddr;
          if (this.data.isSelf || this.data.isNotSelf)
            data.isSelf = this.data.isSelf;
          data.sizeIndex = this.data.sizeIndex;
          data.weightIndex = this.data.weightIndex;
          data.expressCode = this.data.expressCode;
          break;
        case 2:
          data.questionTypeIndex = this.data.questionTypeIndex;
          data.simpleDesc = this.data.questionSimpleDesc;
          data.detailedDesc = this.data.questionDetailedDesc;
          break;
        case 3:
          if (this.data.duration != "" && !/^\d+$/.test(this.data.duration))
            throw "租借时长格式不正确";
          data.simpleDesc = this.data.borrowSimpleDesc;
          data.duration = this.data.duration;
          data.unitIndex = this.data.unitIndex;
          if (this.data.haveReturnTime)
            data.returnTime = this.data.returnDate + " " + this.data.returnTime;
          data.detailedDesc = this.data.borrowDetailedDesc;
          break;
        case 4:
          data.simpleDesc = this.data.buySimpleDesc;
          data.detailedDesc = this.data.buyDetailedDesc;
          break;
        case 5:
          data.simpleDesc = this.data.serviceSimpleDesc;
          data.detailedDesc = this.data.serviceDetailedDesc;
          break;
        case 6:
          data.simpleDesc = this.data.otherSimpleDesc;
          data.detailedDesc = this.data.otherDetailedDesc;
          break;
        default:
          break;
      }
      util.deleteNull(data);
      await util.post("/template/add", data);
      this.setData({
        toptips_msg: "模板添加成功",
        toptips_type: "success",
        toptips_show: true
      });
      await this.loadTemplate();
    });
  },

  submit: function () {
    util.tryCatch(this, async () => {
      if (this.data.typeIndex == 0)
        throw "未选择需求类别";
      if (this.data.title == "")
        throw "标题不能为空";
      if (!/^\d+(\.\d+)?$/.test(this.data.reward))
        throw "金额格式不正确";
      const data = {
        sessionID: await util.login(),
        typeIndex: this.data.typeIndex,
        title: this.data.title,
        reward: this.data.reward
      };
      if (this.data.haveDdl)
        data.deadline = this.data.ddlDate + " " + this.data.ddlTime;
      switch (Number(this.data.typeIndex)) {
        case 1:
          if (this.data.fromAddr == "")
            throw "取件地址不能为空";
          if (this.data.toAddr == "")
            throw "送货地址不能为空";
          if (!this.data.isSelf && !this.data.isNotSelf)
            throw "未选择是否为自提柜";
          if (this.data.sizeIndex == 0)
            throw "未选择快递尺寸";
          if (this.data.weightIndex == 0)
            throw "未选择快递重量";
          if (this.data.expressCode == "")
            throw "取件码不能为空";
          data.fromAddr = this.data.fromAddr;
          data.toAddr = this.data.toAddr;
          data.isSelf = this.data.isSelf;
          data.sizeIndex = this.data.sizeIndex;
          data.weightIndex = this.data.weightIndex;
          data.expressCode = this.data.expressCode;
          break;
        case 2:
          if (this.data.questionTypeIndex == 0)
            throw "未选择问题类别";
          data.questionTypeIndex = this.data.questionTypeIndex;
          data.simpleDesc = this.data.questionSimpleDesc;
          data.detailedDesc = this.data.questionDetailedDesc;
          break;
        case 3:
          if (this.data.duration == "" && this.data.unitIndex == 0 && !this.data.haveReturnTime)
            throw "租借时长或归还时间不能为空";
          if (this.data.duration != "" || this.data.unitIndex != 0) {
            if (this.data.duration == "")
              throw "租借时长不能为空";
            if (!/^\d+$/.test(this.data.duration))
              throw "租借时长格式不正确";
            if (this.data.unitIndex == 0)
              throw "时间单位未选择";
          }
          data.duration = this.data.duration;
          data.unitIndex = this.data.unitIndex;
          data.simpleDesc = this.data.borrowSimpleDesc;
          if (this.data.haveReturnTime)
            data.returnTime = this.data.returnDate + " " + this.data.returnTime;
          data.detailedDesc = this.data.borrowDetailedDesc;
          break;
        case 4:
          data.simpleDesc = this.data.buySimpleDesc;
          data.detailedDesc = this.data.buyDetailedDesc;
          break;
        case 5:
          data.simpleDesc = this.data.serviceSimpleDesc;
          data.detailedDesc = this.data.serviceDetailedDesc;
          break;
        case 6:
          data.simpleDesc = this.data.otherSimpleDesc;
          data.detailedDesc = this.data.otherDetailedDesc;
          break;
        default:
          break;
      }
      util.deleteNull(data);
      await util.post("/order/add", data);
      this.setData({
        toptips_msg: "需求发布成功",
        toptips_type: "success",
        toptips_show: true
      });
      await this.loadTemplate();
    });
  }

});