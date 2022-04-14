package ru.ssau.reviewzor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ssau.reviewzor.domain.ERole;
import ru.ssau.reviewzor.domain.Role;
import ru.ssau.reviewzor.domain.User;
import ru.ssau.reviewzor.pojo.JwtResponse;
import ru.ssau.reviewzor.pojo.LoginRequest;
import ru.ssau.reviewzor.pojo.MessageResponse;
import ru.ssau.reviewzor.pojo.SignupRequest;
import ru.ssau.reviewzor.repo.RoleRepo;
import ru.ssau.reviewzor.repo.UserRepo;
import ru.ssau.reviewzor.utils.JwtUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse authUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getSurname(),
                roles
        );
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        if (Boolean.TRUE.equals(userRepo.existsByUsername(signupRequest.getUsername()))) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is exists"));
        }
        final User user = SignupRequest.parseSignupRequestToUser(signupRequest);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRoles(getRoles(signupRequest.getRoles()));
        userRepo.saveAndFlush(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

    private Set<Role> getRoles(final Set<String> roles) {
        final Set<Role> rolesRes = new HashSet<>();
        if (roles == null) {
            addRole(rolesRes, ERole.ROLE_USER);
        } else {
            throw new RuntimeException("Not implemented for this app");
            /*
            roles.forEach(r -> {
                switch (r) {
                    case "admin":
                        addRole(rolesRes, ERole.ROLE_ADMIN);
                    case "moder":
                        addRole(rolesRes, ERole.ROLE_MODERATOR);
                    case "user":
                        addRole(rolesRes, ERole.ROLE_USER);
                }
            });*/
        }
        return rolesRes;
    }

    private void addRole(final Set<Role> roles, final ERole role) {
        final Role userRole = roleRepo
                .findByName(role)
                .orElseThrow(() -> new IllegalStateException("Error: Role \"" + role.getName() + "\" is not found"));
        roles.add(userRole);
    }
}
