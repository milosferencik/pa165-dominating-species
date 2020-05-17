package cz.muni.fi.mvc.security;

import cz.muni.fi.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Protects administrative part of application.
 *
 * @author Milos Ferencik
 */
@WebFilter(urlPatterns = {"/animal/*", "/user/*", "/environment/*", "/foodchain/*"})
public class ProtectFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(ProtectFilter.class);

    private static final Set<String> ALLOWED_USER_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/user/update", "/user/password", "user/detail")));

    private static final Set<String> ALLOWED_ADMIN_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/animal/update", "/animal/delete", "/user/update", "/user/password", "/user/delete", "/environment/update", "/environment/delete", "user/detail")));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        String last_part = path.substring(path.lastIndexOf('/') + 1);
        System.out.println("last part : " + last_part);
        Long id = null;
        if (isNumeric(last_part)){
            id = Long.parseLong(last_part);
            path = path.substring(0, path.lastIndexOf('/'));
        }

        System.out.println("path: " + path);

        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("authenticatedUser");

        if (userDTO == null) {
            log.warn("user is not authenticate");
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        if (!userDTO.isAdmin() && userDTO.getId().equals(id) && ALLOWED_USER_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!userDTO.isAdmin() && ALLOWED_ADMIN_PATHS.contains(path)) {
            log.warn("authenticate user is not admin");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("Running in {}", filterConfig.getServletContext().getServerInfo());
    }

    @Override
    public void destroy() {

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
