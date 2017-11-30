package com.java.sys;
/**
 * 微信相关常量类
 * @author Administrator
 *
 */
public class WXConstant {
	
	/**
	 * 项目访问域名
	 */
	public static final String IP = "http://yupan.imwork.net:16827";
	
	/**
	 * 微信公众号凭证 （CorpID是企业号的标识，每个企业号拥有一个唯一的CorpID）
	 */
	public static final String CORPID = "wx26b16b773612873a";
	
	/**
	 * 微信公众号凭证秘钥（Secret是管理组凭证密钥）
	 */
	public static final String CORPSECRET = "3NeXopWBzb8KwqZ6RuIV3C1zZbRQfVISWFyzFcMsWUvicuftSbWhnIhZAUYLczGp";
	
	
	/**
	 *  获取AccessToken的请求url   [参数说明] corpi:企业Id  corpsecret:管理组的凭证密钥
	 */
	public static final String URL_GETTOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";

	/**
	 * 身份验证，OAuth2.0验证接口，企业获取code
	 */
	public static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	/**
	 * 获取企业号登录用户信息，  根据code获取成员信息
	 */
	public static final String URL_GETUSERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	
	/**
	 *  微信登录授权页
	 */
	public static final String URL_LOGINPAGE = "https://qy.weixin.qq.com/cgi-bin/loginpage?corp_id=CORPID&redirect_uri=REDIRECT_URI&state=STATE&usertype=USERTYPE";
	
	/**
	 *  微信登录授权页授权后，获取企业号登录用户信息
	 */
	public static final String URL_GETLOGININFO = "https://qyapi.weixin.qq.com/cgi-bin/service/get_login_info?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取成员    [参数说明] access_token:企业号id  userid:成员id
	 */
	public static final String URL_GET = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
	
	/**
	 * 更新成员 
	 */
	public static final String URL_UPDATE = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取指定部门列表
	 */
	public static final String URL_GETDEPTLIST = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";
	
	/**
	 * 获取当前部门下所有用户  [参数说明] department_id:部门ID  fetch_child:1/0：是否递归获取子部门下面的成员    status:0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	 */
	public static final String URL_GETDEPTUSER = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
	
	/**
	 * 获取当前部门下所有用户(详情)  [参数说明] department_id:部门ID  fetch_child:1/0：是否递归获取子部门下面的成员    status:0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	 */
	public static final String URL_GETDEPTUSERMORE = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
	
	/**
	 * 发消息接口
	 */
	public static final String URL_SEND = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
}
