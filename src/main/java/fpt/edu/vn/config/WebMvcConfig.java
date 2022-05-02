package fpt.edu.vn.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private VersionInterceptor versionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(versionInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      Path uploadDir = Paths.get("./uploads");
      String customerUploadProfileImage = uploadDir.toFile().getAbsolutePath();

      registry
        .addResourceHandler("/uploads/**")
        .addResourceLocations("file:/" + customerUploadProfileImage + "/");
    }
    
}