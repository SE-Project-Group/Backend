/*功能：用户登录
 *@GET
 *@Path("/clientLogin")
 *http://192.168.1.13:8088/track/rest/app/clientLogin?user_name=**&password=**
 *返回：APPLICATION_JSON json格式的token
 
{
    "token": "1771fcf8a5c149e48da138099fe2ff53",
    "userId": 2
}
 */
 
 
/*
 *功能：用户登出
 *@GET
 *
 *http://192.168.1.13:8088/track/rest/app/clientLogout?user_ID=**&sign=**
 *user_ID为用户id，sign为签名
 *返回：TEXT_PLAIN success或者error
 */
 
 /*
 *功能：用户注册
 *@POST
 *
 *http://192.168.1.13:8088/track/rest/app/clientSignup
 *
 *返回：TEXT_PLAIN success或者error{“existing phone”，“existing user name”，“existing phone and user name”}
 */

 {"user_name":"String",
   "password":"String",
   "phone":"String"
 }
 
 /*
 *功能：查找用户个人信息
 *@GET
 *
 *http://192.168.1.13:8088/track/rest/app/queryPersonalInfo?user_ID=**&sign=**
 *
 *返回：TEXT_PLAIN  user的信息或者"no sign" "no such client"
 */

 /*
 *功能：修改用户个人信息
 *@PUT
 *接受：APPLICATION_JSON
 *http://192.168.1.13:8088/track/rest/app/ModifyPersonalInfo?user_id=**&sign=**
 *
 *返回：TEXT_PLAIN  user的信息或者"no sign" "no such client"
 */
  {"user_name":"String",
   "password":"String",
   "phone":"String",
   "gender":"String",
   "email":"String",
   "birthday":"String"
 }
 /*
 *功能：添加新动态
 *@POST
 *接受：APPLICATION_JSON
 *http://192.168.1.13:8088/track/rest/app/NewFeed?user_ID=**&sign=**
 *
 *返回：TEXT_PLAIN  “success”
 */
  {"user_id":int,
   "text":"String",
   "phone":"String",
   "showLocation":boolean,
   "location":{"longitude":double,"latitude":double},
   "shareArea":"String",
   "mentionList":{int,int},
   "position":"String"
 }
 
  /*
 *功能：更新动态
 *@PUT
 *接受：APPLICATION_JSON
 *http://192.168.1.13:8088/track/rest/app/UpdateFeed?user_ID=**&sign=**
 *
 *返回：TEXT_PLAIN  “success”
 */
  {"_id":"String",
   "user_id":int,
   "text":"String",
   "phone":"String",
   "showLocation":boolean,
   "location":{"longitude":double,"latitude":double},
   "shareArea":"String",
   "mentionList":{int,int},
   "picList":{int,int},
   "shareCount":int,
   "commentCount":int
   "likeCount":int
   "likeList":{}//怎么传过去怎么传过来
   "commentList":{}//怎么传过去怎么传过来
   "position":"String"
 }
 
  /*
 *功能：删除动态
 *@DELETE
 *接受：APPLICATION_JSON
 *http://192.168.1.13:8088/track/rest/app/RemoveFeed?user_ID=**&sign=**
 *
 *返回：TEXT_PLAIN  “success”
 */
  {"_id":"String"
 }
  /*
 *功能：获取个人动态
 *@GET
 *
 *http://192.168.1.13:8088/track/rest/app/MyFeed?user_ID=**&sign=**
 *
 *返回：text/html  newfeed.toString()
 */
 
 /*
 *功能：获取某一时间之后的public动态
 *@GET
 *
 *http://192.168.1.13:8088/track/rest/app/GetFeedFromTime?user_ID=**&sign=**
 *
 *返回：text/html  feedlist.toString()
 */
 {"time":"String"
 }
 
 
 /*
 *功能：获取该位置周围的动态
 *@GET
 *
 *http://192.168.1.13:8088/track/rest/app/FeedAround？longitude=**&latitude=**&user_ID=**&sign=**
 *
 *返回：APPLICATION_JSON  JSONArray.fromObject(res).toString()
 */

/*功能：点赞  
 *@PUT
 *
 *http://192.168.1.13:8088/track/rest/app/IncLikeFeed?user_ID=**&sign=**
 *返回："success"
 */
 
{"_id":"595f1f902f217d22e83985ed",
"user_id":44
}
/*功能：评论
 *@PUT
 *http://192.168.1.13:8088/track/rest/app/NewComment?user_ID=**&sign=**
 *返回："success"
 */
{"_id":"595f1f902f217d22e83985ed",
"user_id":44,
"text":"helloworld",
"reply_id":0
}
/*功能：获取好友某一时间之后的动态
 *@GET
 *http://192.168.1.13:8088/track/rest/app/getFriendFeedList?user_ID=**&sign=**
 *返回：text/html 动态list
 */
{"time":"YYYY-MM-DD HH-MM-SS"
}
/*功能：获取关注的人某一时间之后的动态
 *@GET
 *http://192.168.1.13:8088/track/rest/app/getFollowingFeedList?user_ID=**&sign=**
 *返回：text/html 动态list
 */
{"time":"YYYY-MM-DD HH-MM-SS"
}
/*功能：获取朋友信息列表
 *@GET
 *http://192.168.1.13:8088/track/rest/app/getMyFriendInformationById?user_ID=**&sign=**
 *返回：text/html 好友信息list
 */
 
 /*功能：获取我关注的人的信息列表
 *@GET
 *http://192.168.1.13:8088/track/rest/app/getMyFollowingInformationById?user_ID=**&sign=**
 *返回：text/html 我关注的人的信息列表
 */
 
 /*功能：获取关注我的人的信息列表
 *@GET
 *http://192.168.1.13:8088/track/rest/app/getFollowingMeInformationById?user_ID=**&sign=**
 *返回：text/html 关注的我人的信息列表
 */
 
 /*功能：获取某人的信息列表
 *@GET
 *http://192.168.1.13:8088/track/rest/app/getInfo?user_ID=**&sign=**&id=**
 *返回：text/html ReturnUserInfo对象
 */