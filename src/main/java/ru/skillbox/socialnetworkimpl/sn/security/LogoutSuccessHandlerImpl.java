package ru.skillbox.socialnetworkimpl.sn.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ReportApi;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    ObjectMapper mapper;

    public LogoutSuccessHandlerImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        mapper.writeValue(
                response.getWriter(),
                ResponsePlatformApi.builder()
                        .error("string")
                        .timestamp(new Date().getTime())
                        .data(new ReportApi("ok"))
                        .build()
        );
    }
}
