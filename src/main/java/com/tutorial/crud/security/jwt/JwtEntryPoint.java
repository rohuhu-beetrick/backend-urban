package com.tutorial.crud.security.jwt;

/*Comprueba si hay un token valido si no devuelve error 404*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtEntryPoint  implements AuthenticationEntryPoint {

    /*Logger, para dev, para ver qué método da error*/
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);


    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Fail en el método commence");
        //Rechaza todas las peticiones que no esten autenticadas.
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No autorizado");
    }
}
