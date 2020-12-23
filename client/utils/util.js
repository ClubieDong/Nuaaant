const app = getApp();
const wxp = app.wxp;

export const url = "http://localhost:8080";

export const to = (promise) => {
  return promise
    .then(data => [null, data])
    .catch(err => [err, null]);
};

export const tryCatch = async (that, func) => {
  try {
    await func();
  } catch (err) {
    console.error(err);
    that.setData({
      toptips_msg: err.errMsg == undefined ? err : err.errMsg,
      toptips_type: "error",
      toptips_show: true
    });
  }
};

export const get = async (subUrl, data) => {
  const response = await wxp.request({
    url: url + subUrl,
    data: data,
  });
  if (response.statusCode != 200)
    throw response.data;
  return response.data;
};
export const post = async (subUrl, data) => {
  const response = await wxp.request({
    url: url + subUrl,
    method: "POST",
    header: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    data: data,
  });
  if (response.statusCode != 200)
    throw response.data;
  return response.data;
};
export const del = async (subUrl, data) => {
  const response = await wxp.request({
    url: url + subUrl,
    method: "DELETE",
    header: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    data: data,
  });
  if (response.statusCode != 200)
    throw response.data;
  return response.data;
};

export const login = async () => {
  let [errSudo, dataSudo] = await to(wxp.getStorage({
    key: "Sudo"
  }));
  if (errSudo == null)
    return dataSudo.data;
  let [err1] = await to(wxp.checkSession());
  let [err2, data] = await to(wxp.getStorage({
    key: "SessionID"
  }));
  if (err1 != null || err2 != null) {
    data = await wxp.login();
    data = await get("/user/login", {
      code: data.code
    });
    await wxp.setStorage({
      key: "SessionID",
      data: data.sessionID,
    });
    await wxp.setStorage({
      key: "UserID",
      data: data.userID,
    });
    return data.sessionID;
  }
  return data.data;
};

export const getUserID = async () => {
  while (true) {
    let [err, data] = await to(wxp.getStorage({
      key: "UserID"
    }));
    if (err == null)
      return data.data;
    await login();
  }
};

export const date2str = d => d.toLocaleDateString().replace("/", "-").replace("/", "-");
export const time2str = d => d.toTimeString().substr(0, 5);

export const emptify = d => d == null ? "" : d;
export const zeroify = d => d == null ? 0 : d;

export const deleteNull = x => {
  for (const key in x)
    if (x[key] == null || x[key] === "")
      delete x[key];
};

export const formatFutureTime = t => {
  if (t == null)
    return "";
  if (typeof (t) == "string")
    t = new Date(t);
  let delta = t - new Date();
  if (delta < 0)
    return "已截止";
  delta = Math.floor(delta / 1000);
  if (delta > 60 * 60 * 24 * 7)
    return t.toLocaleDateString();
  let s;
  if (delta < 60)
    s = Math.floor(delta) + "秒";
  else if (delta < 60 * 60)
    s = Math.floor(delta / 60) + "分钟";
  else if (delta < 60 * 60 * 24)
    s = Math.floor(delta / 60 / 60) + "小时";
  else if (delta < 60 * 60 * 24 * 7)
    s = Math.floor(delta / 60 / 60 / 24) + "天";
  return "还剩" + s;
};

export const formatPassTime = t => {
  if (t == null)
    return "";
  if (typeof (t) == "string")
    t = new Date(t);
  let delta = new Date() - t;
  if (delta < 0)
    return "未开始";
  delta = Math.floor(delta / 1000);
  if (delta > 60 * 60 * 24 * 7)
    return t.toLocaleDateString();
  let s;
  if (delta < 60)
    s = Math.floor(delta) + "秒";
  else if (delta < 60 * 60)
    s = Math.floor(delta / 60) + "分钟";
  else if (delta < 60 * 60 * 24)
    s = Math.floor(delta / 60 / 60) + "小时";
  else if (delta < 60 * 60 * 24 * 7)
    s = Math.floor(delta / 60 / 60 / 24) + "天";
  return s + "之前";
};

const sudo = i => {
  if (i == undefined) {
    wx.removeStorageSync("Sudo");
    wx.removeStorageSync("SessionID");
  }
  else {
    wx.setStorageSync("Sudo", i);
    wx.setStorageSync("UserID", i);
  }
};