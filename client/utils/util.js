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
  let [err1] = await to(wxp.checkSession());
  let [err2, data] = await to(wxp.getStorage({
    key: "SessionID"
  }));
  if (err1 != null || err2 != null) {
    data = await wxp.login();
    data = await get("/login", {
      code: data.code
    });
    await wxp.setStorage({
      key: "SessionID",
      data: data
    });
    return data;
  }
  return data.data;
};

export const date2str = d => d.toLocaleDateString().replace("/", "-").replace("/", "-");
export const time2str = d => d.toTimeString().substr(0, 5);

export const emptify = d => d == null ? "" : d;
export const zeroify = d => d == null ? 0 : d;

export const deleteNull = x => {
  for (const key in x)
    if (x[key] == null || x[key] === "")
      delete x[key];
}