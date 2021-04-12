package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("进入了过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String url = request.getRequestURL().toString();
        System.out.println(url+"-------");
        if(request.getMethod()=="OPTIONS"){
            return null;
        }
        if(url.indexOf("/admin/login")>0){
            return null;
        }
        String authHeader = request.getHeader("Authorization");
        if(authHeader!=null&&authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            String roles = (String) claims.get("roles");
            if(roles!=null&&roles.equals("admin")){
                requestContext.addZuulRequestHeader("Authorization",authHeader);
                return null;
            }

        }
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(401);
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
