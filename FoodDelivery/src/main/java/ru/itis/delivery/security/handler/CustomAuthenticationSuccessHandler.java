package ru.itis.delivery.security.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itis.delivery.services.CartService;
import ru.itis.delivery.services.clazz.SessionBag;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final CartService cartService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ADMIN")) {
            response.sendRedirect("/admin");
        } else if(roles.contains("COURIER")){
            response.sendRedirect("/courier");
        } else {

            HttpSession session = request.getSession();

            SessionBag sessionBag = (SessionBag) session.getAttribute("bag");

            if(sessionBag != null){
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                cartService.mergeCartFromSession(sessionBag, userDetails.getUsername());
            }

            response.sendRedirect("/");
        }
    }
}
