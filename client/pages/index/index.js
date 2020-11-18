const app = getApp();
const wxp = app.wxp;

Page({
  data: {
    msg: ""
  },

  tap: function () {
    const that = this;

    wxp.request({
      url: 'http://localhost:8080/hello'
    }).then(r => {
      that.setData({
        msg: r.data
      });
    });

  }
})