package com.flowable.fltest;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.flowable.engine.IdentityService;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@AllArgsConstructor
@Log4j2
public class RequestFilter implements Filter {

    private IdentityService identityService;

    @Override
    public void doFilter (ServletRequest request,  ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Set user in filter...");
        identityService.setAuthenticatedUserId("alextrs");
        chain.doFilter(request, response);
    }

}
