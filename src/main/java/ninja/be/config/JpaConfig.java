package ninja.be.config;

import ninja.be.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
class JpaConfig {

    @Bean
    public AuditorAware<String> auditorProvider(UserRepository userRepository) {
        return new AuditorAwareImpl(userRepository);
    }
}