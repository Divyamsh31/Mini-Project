package com.ats.service;

import com.ats.dto.*;
import com.ats.model.User;
import com.ats.model.enums.UserRole;
import com.ats.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class AuthService {

    private final UserRepository userRepository;
    private final com.ats.repository.RecruiterProfileRepository recruiterProfileRepository;

    @org.springframework.beans.factory.annotation.Value("${app.auth.admin-code}")
    private String adminAccessCode;

    public AuthResponse register(RegisterRequest request) {
        UserRole role;
        try {
            role = UserRole.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role. Must be JOBSEEKER, ADMIN or RECRUITER");
        }

        if (userRepository.existsByEmailAndRole(request.getEmail(), role)) {
            throw new IllegalArgumentException("You are already registered as a " + role + " with this email.");
        }

        // Validate Access Code only for ADMIN roles during signup
        if (role == UserRole.ADMIN) {
            if (request.getAccessCode() == null || !request.getAccessCode().equals(adminAccessCode)) {
                throw new IllegalArgumentException("Invalid or missing Admin Access Code");
            }
        }



        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(role)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        UserRole requestedRole;
        try {
            requestedRole = UserRole.valueOf(request.getRole().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid role requested: " + request.getRole());
        }

        // Fetch user matching both email AND the requested role
        User user = userRepository.findByEmailAndRole(request.getEmail(), requestedRole)
                .orElseThrow(() -> new IllegalArgumentException("No account found for " + request.getEmail() + " as a " + requestedRole));
        
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid password for your " + requestedRole + " account");
        }

        // Admin Access Code Validation
        if (user.getRole() == UserRole.ADMIN) {
            if (request.getAccessCode() == null || !request.getAccessCode().equals(adminAccessCode)) {
                throw new IllegalArgumentException("Invalid Admin Access Code");
            }
        }


        // Set activity status
        user.setStatus("ACTIVE");
        userRepository.save(user);

        boolean profileCompleted = true;
        if (user.getRole() == UserRole.RECRUITER) {
            profileCompleted = recruiterProfileRepository.findByRecruiterId(user.getId()).isPresent();
        }

        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .profileCompleted(profileCompleted)
                .build();
    }


    public void logout(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setStatus("INACTIVE");
            userRepository.save(user);
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public com.ats.model.RecruiterProfile updateProfile(Long userId, java.util.Map<String, String> profileData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));
        
        if (user.getRole() != UserRole.RECRUITER) {
            throw new IllegalArgumentException("Only recruiters can have profiles");
        }


        com.ats.model.RecruiterProfile profile = recruiterProfileRepository.findByRecruiterId(userId)
                .orElse(new com.ats.model.RecruiterProfile());
        
        profile.setRecruiter(user);
        profile.setCompanyName(profileData.get("companyName"));
        profile.setDesignation(profileData.get("designation"));
        profile.setCompanyDescription(profileData.get("companyDescription"));
        profile.setLocation(profileData.get("location"));
        
        return recruiterProfileRepository.save(profile);
    }

}
