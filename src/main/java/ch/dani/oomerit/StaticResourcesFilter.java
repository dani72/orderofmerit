/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.dani.oomerit;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author dani
 */
@Component
public class StaticResourcesFilter implements Filter {

    private final static String PATH_PREFIX = "/static/";
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if( request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
            if( httpRequest.getRequestURI().startsWith( PATH_PREFIX)) {
                var path = httpRequest.getRequestURI().substring( 1);
                
                System.out.println( "Serving " + path);
                
                try( var in = this.getClass().getClassLoader().getResourceAsStream( path)) {
                    if( in == null) {
                        httpResponse.setStatus( HttpServletResponse.SC_NOT_FOUND);
                    }
                    else {
                        httpResponse.setContentType( "image/png");
                        try( var out = httpResponse.getOutputStream()) {
                            IOUtils.copy( in, out);
                        }
                    }
                }
                
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
