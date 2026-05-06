package uk.abhishek.moneymanager.service;


import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.abhishek.moneymanager.dto.AuthDTO;
import uk.abhishek.moneymanager.dto.ProfileDTO;
import uk.abhishek.moneymanager.entity.ProfileEntity;
import uk.abhishek.moneymanager.repository.ProfileRepository;
import uk.abhishek.moneymanager.util.JwtUtil;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
//    private  final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${app.activation.url}")
    private String activationURL;
//    @Transactional
    public ProfileDTO registerProfile(ProfileDTO profileDTO)
    {

        return (profileDTO);
//        ProfileEntity newProfile = toEntity(profileDTO);
//        newProfile.setActivationToken(UUID.randomUUID().toString());
//        newProfile = profileRepository.saveAndFlush(newProfile);
//        return toDTO( newProfile);
        //send activation link
//
//        String activationLink = activationURL+"/api/v1.0/activate?token=" + newProfile.getActivationToken();
//        String  subject = "Activate your Money Manager account";
//        String body = "Click on the following link to activate your account:" + activationLink;
////        emailService.sendEmail(newProfile.getEmail(), subject,body);
////        try {
////            emailService.sendEmail(newProfile.getEmail(), subject, body);
////        } catch (Exception e) {
////            System.out.println("Email failed but ignored: " + e.getMessage());
////        }



    }

    //helper function
    public ProfileEntity toEntity(ProfileDTO profileDTO)
    {
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(passwordEncoder.encode(profileDTO.getPassword()))
                .profileImageUrl(profileDTO.getProfileImageUrl())
//                .createdAt(profileDTO.getCreatedAt())
//                .updatedAt(profileDTO.getUpdatedAt())
                .build();

    }
    //helper function
    public ProfileDTO toDTO(ProfileEntity profileEntity)
    {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
//                .createdAt(profileEntity.getCreatedAt())
//                .updatedAt(profileEntity.getUpdatedAt())
                .build();

    }

    // helps to activate the account
    public String activateProfile(String activationToken)
    {
     ProfileEntity profileEntity=  profileRepository.findByActivationToken(activationToken).orElseThrow(()->new RuntimeException("Invalid Token"));
     profileEntity.setIsActive(true);
     profileRepository.save(profileEntity);
     return "Profile activated successfully";
    }

    // checks and return the boolean  value to know whether the account is activated or not !
    public boolean isAccountActive(String email)
    {
        return profileRepository.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);
    }

    // this is very important later we gonna use this function to save the id of the current user while saving any category,income or anything
    public ProfileEntity getCurrentProfile()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return profileRepository.findByEmail(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("Profile not found with email:"+authentication.getName()));
    }

    public ProfileDTO getPublicProfile(String email)
    {
        ProfileEntity currentUser = null;
        if(email == null)
        {
            currentUser = getCurrentProfile();
        }
        else
        {
            currentUser = profileRepository.findByEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("Profile not found with email"+email));
        }
        return  ProfileDTO.builder()
                .id(currentUser.getId())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .profileImageUrl(currentUser.getProfileImageUrl())
//                .createdAt(currentUser.getCreatedAt())
//                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }

    // very important used for the login and generation of the jwt token
    public Map<String, Object> authenticateAndGenerateToken(AuthDTO authDTO) {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(),authDTO.getPassword()));
            //Generate JWT token
          String token = jwtUtil.generateToken(authDTO.getEmail());
            return Map.of(
                    "token",token,
                    "user",getPublicProfile(authDTO.getEmail())
            );
        }
        catch (Exception e){
            throw new RuntimeException("Invalid email or password");
        }
    }
}
