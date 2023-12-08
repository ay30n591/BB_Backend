package com.jjans.BB.Oauth2;

import com.jjans.BB.Entity.Users;
import com.jjans.BB.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.*;
@Getter
@AllArgsConstructor
public class UserPrincipal  implements OAuth2User, UserDetails{
    private Long id;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    @Setter
    private Map<String, Object> attributes;
    public UserPrincipal(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }
    public static UserPrincipal create(Users user, Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
        UserPrincipal userPrincipal = new UserPrincipal(user.getId(), user.getEmail(), authorities);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
    @Override
    public String getPassword() {
        return null;
    }
    @Override
    public String getName() {
        return String.valueOf(id);
    }
}