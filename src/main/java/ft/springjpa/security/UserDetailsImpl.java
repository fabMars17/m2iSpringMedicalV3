package ft.springjpa.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ft.springjpa.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
/*
*   @author
*   @param
*
*
* */

public class UserDetailsImpl implements UserDetails {

    private User user;
    public UserDetailsImpl(User user){
        this.user = user;
    }

    public String getLogname() {
        return user.getName();
    }

    public String getLogRole() {
        return user.getRoles();
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRoles()));
        return authorities;
    }

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final Role role : user.getRoles())
            authorities.add(new SimpleGrantedAuthority(role.getTitre()));
        return authorities;
    }*/

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
    public String getUsername() {
        return user.getEmail();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

}
