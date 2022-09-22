package co.develhope.Login.configuration;

import co.develhope.Login.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/").permitAll()

                // allow anonymous resource requests
                .antMatchers(
                        HttpMethod.POST,
                        "/auth/signup"
                ).permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().
                antMatchers("/auth/**").permitAll()
                /*.antMatchers("/admin/**").hasAnyRole("ROLE_")
                .antMatchers("/app/**").hasAnyRole("ROLE_" + Roles.REGISTERED)
                .antMatchers("/admin/global/all-data-eraser").hasAnyRole("ROLE_SUPER_ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN", "ROLE_OWNER", "ROLE_SUPER_ADMIN")
                .antMatchers("/blog/**").hasRole("ROLEEDITOR")
                .antMatchers("/dev-tools/**").hasAnyAuthority("DO_DEV_TOOLS")
                .antMatchers("/dev-tools-bis/**").hasAuthority("DO_DEV_TOOLS_READ")*/
                .anyRequest().authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();

        //add JWT token filter
        http.addFilterBefore(
                jwtTokenFilter, UsernamePasswordAuthenticationFilter.class
        );
    }
}
