/*功能：用户登录
 *@GET
 *@Path("/ClientLogin")
 *http://192.168.1.13:8088/track/rest/app/ClientLogin？user_name=**&password=**
 *返回：APPLICATION_JSON json格式的token
 */
 
 
/*
 *功能：用户登出
 *@GET
 *
 *http://192.168.1.13:8088/track/rest/app/ClientLogout？user_ID=**&sign=**
 *user_ID为用户id，sign为签名
 *返回：TEXT_PLAIN success或者error
 */
 
 /*
 *功能：用户注册
 *@POST
 *
 *http://192.168.1.13:8088/track/rest/app/ClientSignup
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
 *http://192.168.1.13:8088/track/rest/app/QueryPersonalInfo?user_ID=**&sign=**
 *
 *返回：TEXT_PLAIN  user的信息或者"no sign" "no such client"
 */

 /*
 *功能：修改用户个人信息
 *@PUT
 *接受：APPLICATION_JSON
 *http://192.168.1.13:8088/track/rest/app/ModifyPersonalInfo?user_ID=**&sign=**
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
 *@POST
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
 *@POST
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
 *@POST
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
 *@POST
 *
 *http://192.168.1.13:8088/track/rest/app/IncLikeFeed?user_ID=**&sign=**
 *返回："success"
 */
 
{"_id":"595f1f902f217d22e83985ed",
"user_id":44
}
/*功能：评论
 *@POST
 *http://192.168.1.13:8088/track/rest/app/NewComment?user_ID=**&sign=**
 *返回："success"
 */
{"_id":"595f1f902f217d22e83985ed",
"user_id":44,
"text":"helloworld",
"reply_id":0
}