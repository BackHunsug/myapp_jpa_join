package com.woori.myapp.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//web.xml 대체 
@Configuration
@PropertySource("classpath:/application.properties")
//@PropertySource("classpath:/application.yaml")

public class WebConfig implements WebMvcConfigurer {
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
    	//CORS정책오류 해결 
        registry.addMapping("/**")
        		.allowedMethods("*")
        		.allowedOriginPatterns("*");
        
    } 
    /*
      registry.addMapping("/**")
        	.allowedOriginPatterns("*")
        	//.allowedOrigins("http://localhost:3000", "http://localhost:8081") // 허용할 출처
            .allowedMethods("GET", "POST") // 허용할 HTTP method
            .allowCredentials(true) // 쿠키 인증 요청 허용
            .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
         */
    
  //application.properties 에 있는 내용 읽어와서 변수에 넣기
  	@Value("${fileUploadPath}")
  	String fileUploadPath;

  	@Override
  	public void addResourceHandlers(ResourceHandlerRegistry registry) {
  		
  		
  		exposeDirectory(fileUploadPath, registry);
  	}
  	
  	void exposeDirectory(String dirName, 
  			ResourceHandlerRegistry registry)
  	{
  		Path uploadDir = Paths.get(dirName);
  		//업로드 폴더의 물리적 구조(절대경로확인)
  		String uploadPath = uploadDir.toFile().getAbsolutePath();
  		
  		if(dirName.startsWith("../"))
  			dirName = dirName.replace("../", "");
  		
  		System.out.println(dirName);
  		System.out.println(uploadPath);
  		
  		registry
  		  .addResourceHandler("/"+dirName+"/**")
  		  .addResourceLocations("file:/"+uploadPath + "/");
  		
  		//spring frame 
  		//특정폴더를 파일서버로 작동시킨다. 
  	    //application.properties  에서  fileUploadPath에 
  		//지정된 폴더값을 읽어서 그 폴더의 권한을 파일서버로 만든다
  		
  	}
}