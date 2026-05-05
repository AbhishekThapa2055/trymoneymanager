package uk.abhishek.moneymanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.abhishek.moneymanager.dto.AuthDTO;
import uk.abhishek.moneymanager.dto.ProfileDTO;
import uk.abhishek.moneymanager.service.ProfileService;

import java.util.Map;

@RestController
@RequiredArgsConstructor

public class ProfileController {
    private final ProfileService profileService;
    @PostMapping("/register")
    public ResponseEntity<String> registerProfile(@RequestBody ProfileDTO profileDTO)
    {
        System.out.println("STEP 1: Controller hit");
        ProfileDTO registeredProfile = profileService.registerProfile(profileDTO);
        System.out.println("STEP 2: After service");
//        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token)
    {
       String message =  profileService.activateProfile(token);

       return ResponseEntity.ok(message);

    }
    @PostMapping("/login")
    public  ResponseEntity<Map<String,Object>> login(@RequestBody AuthDTO authDTO){
        try
        {
            if(!profileService.isAccountActive(authDTO.getEmail()))
            {
                return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message","Account is not active.Please activate your account"));
            }
          Map<String,Object> response =   profileService.authenticateAndGenerateToken(authDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }

    }


}
