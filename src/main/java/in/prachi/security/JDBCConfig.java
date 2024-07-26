package in.prachi.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class JDBCConfig {
private static final String ADMIN="ADMIN";
private static final String USER="USER";
@Autowired
private DataSource dataSource; 
@Autowired
public void authManager (AuthenticationManagerBuilder auth)throws Exception{
	JdbcUserDetailsManager jdbc= new JdbcUserDetailsManager(dataSource);
	
	//we aren't customizing the authentication manager it will use http basic auth
	auth.jdbcAuthentication()
	.dataSource(dataSource)
	.passwordEncoder(new BCryptPasswordEncoder())
	.usersByUsernameQuery("select username, password, enabled from users where usename=?")
	.authoritiesByUsernameQuery("select username, authority from authorities where username=?");
}
        @Bean
		public SecurityFilterChain securityConfig(HttpSecurity http)throws Exception{
			http.authorizeHttpRequests((req)-> req
					.requestMatchers("/admin").hasRole(ADMIN)
					.requestMatchers("/user").hasAnyRole(ADMIN,USER)
					.requestMatchers("/").permitAll()
					.anyRequest().authenticated()
					).formLogin();
			return http.build();
		}
		

	
}
