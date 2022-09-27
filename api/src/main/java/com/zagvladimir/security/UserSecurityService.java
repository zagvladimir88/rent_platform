package com.zagvladimir.security;


import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.User;
import com.zagvladimir.repository.RoleRepository;
import com.zagvladimir.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            /*Find user in DB*/
            Optional<User> searchResult = userRepository.findByUserLogin(username);

            if (searchResult.isPresent()) {
                User user = searchResult.get();

                /*We are creating Spring Security User object*/

                return new org.springframework.security.core.userdetails.User(
                        user.getUserLogin(),
                        user.getUserPassword(),
//                        ["ROLE_USER", "ROLE_ADMIN"]
                        AuthorityUtils.commaSeparatedStringToAuthorityList(
                                roleRepository.findRolesByUserid(user.getId())
                                        .stream()
                                        .map(Role::getName)
                                        //.map(SystemRoles::name)
                                        .collect(Collectors.joining(","))
                        )
                );
            } else {
                throw new UsernameNotFoundException(String.format("No user found with login '%s'.", username));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }
}
