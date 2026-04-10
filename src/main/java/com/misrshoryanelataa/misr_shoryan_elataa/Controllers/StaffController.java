package com.misrshoryanelataa.misr_shoryan_elataa.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.misrshoryanelataa.misr_shoryan_elataa.Dtos.LoginDto;
import com.misrshoryanelataa.misr_shoryan_elataa.Security.LoginRateLimiter;
import com.misrshoryanelataa.misr_shoryan_elataa.Services.StaffService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private LoginRateLimiter rateLimiter;
@PostMapping("/login")
public ResponseEntity<String> login(
        @RequestBody LoginDto loginRequest,
        HttpServletRequest request) {

    String ip = request.getRemoteAddr();
    String key = ip;

    if (rateLimiter.isBlocked(key)) {
        long minutes = rateLimiter.remainingBlockMinutes(key);
        return ResponseEntity.status(429)
                .body("تم الحظر مؤقتاً. حاول بعد " + minutes + " دقيقة.");
    }

    ResponseEntity<String> result = staffService.login(
            loginRequest.getOfficialEmail(),
            loginRequest.getPassword()
    );


    if (result.getStatusCode().is2xxSuccessful()) {
        rateLimiter.reset(key);
        return result;
    }

  
    rateLimiter.recordFailure(key);

    if (rateLimiter.isBlocked(key)) {
        return ResponseEntity.status(429)
                .body("تم الحظر بعد 3 محاولات. حاول بعد "+getBlockTime(key, request)+"دقيقه");
    }

    int remaining = rateLimiter.remainingAttempts(key);

    return ResponseEntity.badRequest()
            .body(result.getBody() + " | تبقى لك " + remaining + " محاولة.");
}
@GetMapping("/login/block-time-seconds")
public ResponseEntity<Long> getBlockTimeSeconds(
        @RequestParam String email,
        HttpServletRequest request) {

    String ip = request.getRemoteAddr();
    String key = ip;

    return ResponseEntity.ok(rateLimiter.remainingBlockSeconds(key));
}
@GetMapping("/login/block-time")
public long getBlockTime(
        @RequestParam String email,
        HttpServletRequest request) {

    String ip = request.getRemoteAddr();
    String key = ip;

    return  rateLimiter.remainingBlockMinutes(key);
}



}
