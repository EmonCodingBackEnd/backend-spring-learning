package com.coding.boot3.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 1、自定义请求授权规则：http.authorizeHttpRequests()
 * 2、自定义登录规则：http.formLogin()
 * 3、自定义用户信息查询规则：http.userDetailsService()
 * 4、开启方法级别的精确权限控制： @EnableMethodSecurity + @PreAuthorize("hasAuthority('world_exec')")
 */
@EnableMethodSecurity // 开启方法权限控制
@Configuration
public class AppSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 请求授权
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/").permitAll() // 1、首页所有人都允许
//                    .requestMatchers("/admin/**").hasRole("ADMIN")
//                    .requestMatchers("/user/**").hasRole("USER")
                    .anyRequest().authenticated(); // 2、剩下的任意请求都需要认证（登录）
        });

        // 表单登录
        // 表单登录功能：开启默认表单登录功能；Spring Security提供默认登录页
        http.formLogin(formLogin -> {
            formLogin.loginPage("/login").permitAll(); // 3、自定义登录页面，并允许所有人访问
        });
        return http.build();
    }

    // 查询用户信息
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        return username -> {
//            if ("admin".equals(username)) {
//                return new User(
//                        "admin",
//                        "{noop}123456",
//                        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
//            }
//            return null;
//        };

        // 一旦配置了passwordEncoder，则需要使用加密后的密码进行登录， {noop} 表示明文密码，不再可用  Encoded password does not look like BCrypt
        UserDetails zsDetails = User.withUsername("zs").password(passwordEncoder.encode("123456")).roles("admin", "hr").authorities("file_read", "file_write").build();
        UserDetails lsDetails = User.withUsername("ls").password("{noop}123456").roles("hr").authorities("file_read").build();
        UserDetails wwDetails = User.withUsername("ww").password(passwordEncoder.encode("123456")).roles("admin").authorities("file_write", "world_exec").build();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(zsDetails, lsDetails, wwDetails);
        return manager;
    }

    @Bean // 密码加密器
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
