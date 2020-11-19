const util = require("../../utils/util.js");

Page({
  data: {
    toptips_msg: "",
    toptips_type: "",
    toptips_show: false
  },

  login: function () {
    util.tryCatch(this, async () => {
      console.log(await util.login());
    });
  }
})