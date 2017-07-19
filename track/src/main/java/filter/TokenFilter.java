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
		System.out.println(uri);
		System.out.println(req.getRequestURL());
		System.out.println(uris.contains(uri));
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
		uris.add("/track/rest/app/clientLogin");
		uris.add("/track/rest/app/clientSignup");
		uris.add("/track/rest/app/feedAround");
		uris.add("/track/rest/app/getFeedFromTime");
	}

	

}
