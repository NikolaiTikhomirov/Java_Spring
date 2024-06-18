package ru.gb.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

  @GetMapping
  public ResponseEntity<String> resource() {
    return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, "key=value").body("Resource");
  }

  @GetMapping("/auth")
  public String auth() {
    return "Authorized";
  }

  @GetMapping("/user")
//  @PreAuthorize(")
  public String user() {
    return "User";
  }

  @GetMapping("/admin")
//  @Secured("admin")
  public String admin() {
    return "Admin";
  }

}
