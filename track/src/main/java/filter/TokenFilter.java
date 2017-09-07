package filter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import dao.TokenDao;

public class TokenFilter implements Filter{

	private TokenDao tokenDao;
	private List<String> uris;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;  
		String uri=req.getRequestURI();
		if(uris.contains(uri)){
			chain.doFilter(request,response);
			return;
		}
		int userId=Integer.parseInt(req.getParameter("user_id"));
		String sign=req.getParameter("sign");
		try {
			if(!tokenDao.checkSign(userId, uri, sign)){
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "status wrong");
	            return;
			}
			chain.doFilter(request,response);
		} catch (NoSuchAlgorithmException e) {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(fConfig.getServletContext());
		tokenDao= (TokenDao) wac.getBean("tokenDao");
		uris=new ArrayList<String>();
		uris.add("/track/rest/app/user/clientLogin");
		uris.add("/track/rest/app/user/clientSignup");
		uris.add("/track/rest/app/feed/feedAround");
		uris.add("/track/rest/app/feed/getPublicFeedAfterTime");
		uris.add("/track/rest/app/user/getHomeInfo");
		uris.add("/track/rest/app/user/getFollowing");
		uris.add("/track/rest/app/user/getFollower");
		uris.add("/track/rest/app/user/queryPersonalInfo");
		uris.add("/track/rest/app/feed/commentList");
		uris.add("/track/rest/app/user/getPortraitUrl");
		uris.add("/track/rest/app/user/searchUser");
		uris.add("/track/rest/app/feed/searchFeed");
		uris.add("/track/rest/app/feed/getFeedsNotLoggedIn");
		uris.add("/track/rest/app/feed/getBigPhotoUrls");
		uris.add("/track/rest/app/user/getBigPortraitUrl");
		uris.add("/track/rest/app/feed/getPublicFeedBeforeTime");

		
		uris.add("/track/rest/app/user/changePwd");
	}

	

}
