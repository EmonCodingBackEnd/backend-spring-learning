package com.coding.boot3.integrated.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class AppReactiveUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    DatabaseClient databaseClient;

    // 自定义如何按照用户名取数据库查询用户信息
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.from(databaseClient.sql("""
                        select tu.*, tr.id rid, tr.name rname, tr.value rvalue, tp.id as pid, tp.value as pvalue, tp.uri, tp.description  from t_user tu\s
                        left join t_user_role tur on tur.user_id = tu.id\s
                        left join t_roles tr on tr.id = tur.role_id\s
                        left join t_role_perm trp on trp.role_id  = tr.id\s
                        left join t_perm tp on tp.id = trp.perm_id\s
                        where tu.username = :username
                        order by tu.username, tr.id, tp.id
                        """).bind("username", username)
                .fetch()
                .all()
                .bufferUntilChanged(rowMap -> rowMap.get("username").toString())
                .map(list -> {
                        /*
                            1、不要混用 roles() 和 authorities()，顺序靠后者会覆盖前者。（哪怕只是 authorities() 执行2次，后者也会覆盖前者）
                            2、角色本质是权限：hasRole('admin') 等价于检查 ROLE_admin 权限。
                            3、最佳实践：统一用 authorities() 显式设置所有权限（包括角色），避免隐式行为。
                         */
                    // 如何同时支持角色和权限？
                    User.UserBuilder userBuilder = User.builder().username(list.get(0).get("username").toString())
                            .password(list.get(0).get("password").toString());
                    Collection<? extends GrantedAuthority> roles = userBuilder.roles(list.stream().map(rowMap -> rowMap.get("rname").toString()).distinct().toArray(String[]::new)).build().getAuthorities();
                    Collection<? extends GrantedAuthority> authorities = userBuilder.authorities(list.stream().map(rowMap -> rowMap.get("pvalue").toString()).distinct().toArray(String[]::new)).build().getAuthorities();
                    Collection<SimpleGrantedAuthority> all = new ArrayList<>();

                    roles.forEach(role -> {
                        if (role instanceof SimpleGrantedAuthority simpleGrantedAuthority) {
                            all.add(simpleGrantedAuthority);
                        }
                    });
                    authorities.forEach(role -> {
                        if (role instanceof SimpleGrantedAuthority simpleGrantedAuthority) {
                            all.add(simpleGrantedAuthority);
                        }
                    });

                    return userBuilder.authorities(all).build();
                }));
    }
}
