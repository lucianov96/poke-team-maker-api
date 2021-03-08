package com.poketeammaker.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebMvc
class WebConfig : Filter, WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val response = res as HttpServletResponse
        val request = req as HttpServletRequest
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Expose-Headers", "Authorization")
        response.addHeader("Access-Control-Expose-Headers", "responseType")
        response.addHeader("Access-Control-Expose-Headers", "observe")
        if (!request.method.equals("OPTIONS", ignoreCase = true)) {
            chain.doFilter(req, res)
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*")
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT")
            response.setHeader("Access-Control-Max-Age", "3600")
            response.setHeader(
                "Access-Control-Allow-Headers",
                "Access-Control-Expose-Headers" + "Authorization, content-type," +
                    "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with,responseType,observe"
            )
            response.status = HttpServletResponse.SC_OK
        }
    }
}
