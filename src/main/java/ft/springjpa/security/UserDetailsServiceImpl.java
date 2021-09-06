package ft.springjpa.security;

import ft.springjpa.entities.User;
import ft.springjpa.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userRepository.findByEmail(username);
        System.out.println(user);
        System.out.println(username);
        if(user == null) {
            throw new UsernameNotFoundException("No user named " + username);
        } else {
            return new UserDetailsImpl(user);
        }
    }
}
