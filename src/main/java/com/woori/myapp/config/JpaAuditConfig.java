package com.woori.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//메인 클래스(@SpringBootApplication)에 직접 @EnableJpaAuditing을 붙여도 되지만, 그러면 @WebMvcTest 같은 JPA와 상관없는 다른 테스트를 할 때도 JPA 설정을 자꾸 불러와서 에러가 발생할 수 있습니다. 그래서 실무에서는 위와 같이 별도의 Config 클래스로 분리하는 방식을 선호합니다.
@Configuration
@EnableJpaAuditing
//@EnableJpaAuditing은 한마디로 **"JPA가 엔티티의 생성/수정 시점을 자동으로 감시하고 기록하게 만드는 스위치"**입니다.
//이 어노테이션을 켜지 않으면, 엔티티에 @CreatedDate나 @LastModifiedDate를 아무리 붙여놓아도 값이 채워지지 않고 null로 남게 됩니다.

public class JpaAuditConfig {
}
