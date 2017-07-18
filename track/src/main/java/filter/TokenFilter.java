package filter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;  
		int userId=Integer.parseInt(req.getParameter("user_ID"));
		String sign=req.getParameter("sign");
		try {
			if(!tokenDao.checkSign(userId, req.getRequestURI(), sign)){
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
	}

	

}
