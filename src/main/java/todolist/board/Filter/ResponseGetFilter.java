package todolist.board.Filter;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ResponseGetFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if("GET".equalsIgnoreCase(httpRequest.getMethod()))
        {
            ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);
            
            chain.doFilter(request, responseWrapper);

            String originalResponse = new String(responseWrapper.getResponseData());
            String modifiedResponse = "{ \"Body\": " + originalResponse + " }";

            httpResponse.setContentLength(modifiedResponse.length());
            OutputStream out = httpResponse.getOutputStream();
            out.write(modifiedResponse.getBytes());
            out.flush();
        }
    }

    @Override
    public void destroy(){}
}
