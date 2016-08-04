package GameOfLife.example.configuration;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by sernowm on 04.08.2016.
 */
@Configuration
@EntityScan(basePackageClasses = { GameOfLife.example.entity.User.class })
@EnableJpaRepositories(basePackageClasses = { GameOfLife.example.entity.User.class })
public class DomainsConfiguration {
    public DomainsConfiguration(){
        System.err.println("Test");
    }
}
