package com.wk.libs.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;

import com.playcar.WKApplication;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * 接口类
 */
public class WKHttpRequest {

	private boolean isCookie = false;
	private boolean isLoginCookie = false;
	private String loginCookie = "";

	/**
	 * 超时时间，默认10秒
	 */
	private int connectTimeout = 10 * 1000;
	/**
	 * 默认的参数：比如城市id
	 */
	private ArrayList<NameValuePair> baseParams;
	/**
	 * 上下文
	 */
	private Context mContext;

	/**
	 * cookie
	 */
	public String cookie = "";

	public WKHttpRequest(Context context) {
		baseParams = new ArrayList<NameValuePair>();
		mContext = context;
	}

	public WKApplication app() {
		return (WKApplication) mContext.getApplicationContext();
	}

	/**
	 * 设置是否缓存Cookie
	 */
	public void setCookie(boolean isCookie) {
		this.isCookie = isCookie;
	}

	/**
	 * 保存登录的Cookie缓存
	 */
	public void setLoginCookie(boolean isLoginCookie) {
		this.isLoginCookie = isLoginCookie;
	}

	/**
	 * jkjikuuuu 参数列表获取参数String
	 */
	public String getParams(ArrayList<NameValuePair> params) {
		StringBuffer sb = new StringBuffer();
		try {
			if (params != null && params.size() > 0) {
				for (NameValuePair nvp : params) {
					sb.append(nvp.getName())
							.append('=')
							.append(URLEncoder.encode(nvp.getValue(), "utf-8")
									.replaceAll("\\+", "%20")).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			WKApplication.showToast("参数异常：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 通过HttpURLConnection进行get请求
	 * 
	 * @param params
	 *            想服务器提交的参数(编码问题自行解决)
	 * @param url
	 *            服务器地址
	 * @return 服务器返回的输入流
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws HttpException
	 */

	public String getConnRequest(String params, String url,
			boolean... readLocal) throws IOException {
		String strURL = (TextUtils.isEmpty(params)) ? url
				: (url + "?" + params);
		String result = "";
		// 从本地直接读取
		if (readLocal != null && readLocal.length > 0 && readLocal[0]) {
			return app().sp.getCache("" + strURL + app().getCacheKey());
		}
		// 从网络获取
		if (WKApplication.isHttps) {
			result = getConnRequestHttps(params, url);
		} else {
			result = getConnRequestHttp(params, url);
		}
		// 保存http的返回数据
		if (app().isSaveHttpData) {
			app().sp.saveCache("" + strURL + app().getCacheKey(), "" + result);
		}
		return result;
	}

	/**
	 * 通过HttpURLConnection进行get请求
	 * 
	 * @param params
	 *            想服务器提交的参数(编码问题自行解决)
	 * @param url
	 *            服务器地址
	 * @return 服务器返回的输入流
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws HttpException
	 */
	public String getConnRequestHttp(String params, String url)
			throws IOException {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		String strURL = (TextUtils.isEmpty(params)) ? url
				: (url + "?" + params);
		if (WKApplication.SHOW_NET_LOG) {
			Log.i("WK", "net:" + strURL);
		}
		HttpURLConnection connect = null;
		try {
			connect = (HttpURLConnection) new URL(strURL).openConnection();
			// 设置cookie
			if (isCookie) {
				connect.setRequestProperty("cookie", "" + cookie);
				// if (WKApplication.isShowCookie) {
				// Log.i("wk", "lazy set cookie=" + cookie);
				// }
			}
			// 设置登录cookie
			if (isLoginCookie) {
				loginCookie = app().sp.getString("set-cookie");
				connect.setRequestProperty("cookie", "" + loginCookie);
				if (WKApplication.isShowCookie) {
					Log.i("wk", "lazy set loginCookie=" + loginCookie);
				}
			}
			connect.setRequestMethod("GET");
			connect.setConnectTimeout(connectTimeout);
			connect.setReadTimeout(connectTimeout);
			connect.connect();
			// 保存cookie
			if (isCookie && app().isOnlySavePostCookie) {
				cookie = connect.getHeaderField("set-cookie");
				if (WKApplication.isShowCookie) {
					Log.i("wk", "lazy save cookie=" + cookie);
				}
			}
			int code = connect.getResponseCode();
			if (code == 200) {
				String str = inputToString(connect.getInputStream(), "utf-8");
				connect.disconnect();
				return str;
			} else {
				WKApplication.showToast(WKApplication.NO_NET_TIP);
				throw new IOException("Error Response:" + code);
			}
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException
					|| e instanceof UnknownHostException) {
				throw new SocketTimeoutException("网络连接超时或找不到DNS服务器");
			} else {
				throw e;
			}
		} finally {
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 通过HttpURLConnection进行post请求
	 * 
	 * @param param
	 *            要提交的字符参数(编码问题自行解决)
	 * @param url
	 *            服务器地址
	 * @return 服务器返回的输入流
	 * @throws SocketTimeoutExceptionn
	 *             服务器连接超时
	 * @throws IOException
	 *             网络连接异常
	 */
	public String postConnRequest(String param, String url) throws IOException {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		Log.e("url", "" + url);
		if (TextUtils.isEmpty(param))
			throw new IOException("Error : 传入参数不能为空");
		HttpURLConnection connect = null;
		try {
			connect = (HttpURLConnection) new URL(url).openConnection();
			if (isLoginCookie) {
				loginCookie = app().sp.getString("set-cookie");
				connect.setRequestProperty("cookie", "" + loginCookie);
				if (WKApplication.isShowCookie) {
					Log.i("wk", "lazy post get cookie=" + loginCookie);
				}
			}
			connect.setConnectTimeout(connectTimeout);
			connect.setReadTimeout(connectTimeout);// lazytest
			connect.setDoInput(true);
			connect.setDoOutput(true);
			byte[] formData = param.getBytes();
			connect.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connect.setRequestProperty("Content-Length", formData.length + "");
			connect.connect();
			OutputStream output = connect.getOutputStream();
			output.write(formData);
			output.flush();
			output.close();
			// 保存cookie
			try {
				if (isLoginCookie) {
					if (TextUtils.isEmpty(loginCookie)) {
						cookie = connect.getHeaderField("Set-Cookie");
						List<String> cookieList = connect.getHeaderFields()
								.get("Set-Cookie");
						for (int i = 0; i < cookieList.size(); i++) {
							loginCookie += cookieList.get(i) + ";";
						}
						app().sp.setString("set-cookie", loginCookie);
						if (WKApplication.isShowCookie) {
							Log.i("wk", "lazy post set cookie=" + loginCookie);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			int code = connect.getResponseCode();
			if (code == 200) {
				String str = inputToString(connect.getInputStream(), "utf-8");
				connect.disconnect();
				Log.e("POS", "post_result:" + str);
				return str;
			} else {
				throw new IOException("服务器返回错误：" + code);
			}
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException) {
				throw new SocketTimeoutException("网络连接超时");
			} else {
				throw e;
			}

		} finally {
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 通过HttpURLConnection进行post请求
	 * 
	 * @param params
	 *            一个存放NameValuePair的List集合
	 * @param url
	 *            服务器地址
	 * @return 服务器返回流
	 * @throws IOException
	 */
	public String postConnRequest(List<NameValuePair> params)
			throws IOException {
		if (params == null || params.size() == 0)
			throw new IOException("Error : 传入参数不能为空");

		StringBuffer sb = new StringBuffer();
		for (NameValuePair nvp : params) {
			if (nvp.getValue() != null) {
				continue;
			}
			String value = URLEncoder.encode(nvp.getValue(), HTTP.UTF_8)
					.replaceAll("\\+", "%20");
			sb.append(nvp.getName()).append('=').append(value).append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		return new String(sb);
	}

	/**
	 * 将服务器返回的流转化成字符串
	 * 
	 * @param inputStream输入流
	 * @param encoding
	 *            字符编码类型,如果encoding传入null，则默认使用utf-8编码。
	 * @return 字符串
	 * @throws IOException
	 */
	public static String inputToString(InputStream inputStream, String encoding)
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		inputStream.close();
		bos.close();
		if (TextUtils.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		String str;
		try {
			str = new String(bos.toByteArray(), encoding);
		} catch (Exception e) {
			str = new String(bos.toByteArray(), encoding);
		}
		return str;
	}

	/**
	 * 通过HttpURLConnection进行get请求
	 * 
	 * @param params
	 *            想服务器提交的参数(编码问题自行解决)
	 * @param url
	 *            服务器地址
	 * @return 服务器返回的输入流
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws HttpException
	 */
	public String getConnRequestHttps(String params, String url)
			throws IOException {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		String strURL = (TextUtils.isEmpty(params)) ? url
				: (url + "?" + params);
		Log.i("WK_NET", "" + strURL);

		HttpURLConnection connect = null;
		try {

			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new MyTrustManager() },
					new SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection
					.setDefaultHostnameVerifier(new MyHostnameVerifier());
			connect = (HttpURLConnection) new URL(strURL).openConnection();
			// 设置cookie
			if (WKApplication.isOpenCookie4Http) {
				connect.setRequestProperty("cookie", "" + cookie);
			}
			connect.setRequestMethod("GET");
			connect.setConnectTimeout(connectTimeout);
			connect.setReadTimeout(connectTimeout);// lazytest
			connect.connect();
			// 保存cookie
			if (WKApplication.isOpenCookie4Http) {
				cookie = connect.getHeaderField("set-cookie");
			}
			int code = connect.getResponseCode();
			if (code == 200) {
				String str = inputToString(connect.getInputStream(), "utf-8");
				connect.disconnect();
				return str;
			} else {
				WKApplication.showToast("服务器异常(" + code + ")");
				throw new IOException("Error Response:" + code);
			}
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException
					|| e instanceof UnknownHostException) {
				WKApplication.showToast("链接超时或找不到DNS服务器");
				throw new SocketTimeoutException("网络连接超时或找不到DNS服务器");
			} else {
				WKApplication.showToast("服务器异常");
				throw e;
			}

		} catch (Exception e) {
			e.printStackTrace();
			WKApplication.showToast("服务器异常");
		} finally {
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "";

	}

	private class MyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * HTTPS请求
	 */
	private class MyTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

}
