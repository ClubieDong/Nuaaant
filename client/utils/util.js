const app = getApp();
const wxp = app.wxp;

export const url = "http://localhost:8080";

export const to = (promise) => {
  return promise
    .then(data => [null, data])
    .catch(err => [err, null]);
}

export const tryCatch = async (that, func) => {
  try {
    await func();
  } catch (err) {
    that.setData({
      toptips_msg: err.errMsg,
      toptips_type: "error",
      toptips_show: true
    });
  }
}

export const login = async () => {
  let [err, data] = await to(wxp.checkSession());
  let sessionID = null;
  if (err != null) {
    data = await wxp.login();
    data = await wxp.request({
      url: url + "/login",
      data: {
        code: data.code
      }
    });
    data = data.data;
    if (!data.success)
      throw data;
    sessionID = data.data;
    await wxp.setStorage({
      key: "SessionID",
      data: sessionID
    });
  } else {
    data = await wxp.getStorage({
      key: "SessionID",
    });
    sessionID = data.data;
  }
  return sessionID;
};