package com.debugeando.test.best_travel.api.controllers;

import com.debugeando.test.best_travel.infraestructure.abstract_services.ModifyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "user")
@AllArgsConstructor
@Tag(name = "User")
public class AppUserController {

    private final ModifyUserService modifyUserService;

    @Operation(summary = "Enabled or Disabled user")
    @PatchMapping(path = "enabled-or-disabled")
    public ResponseEntity<Map<String,Boolean>> enabledOrDisable(@RequestParam String username){
        return ResponseEntity.ok(this.modifyUserService.enable(username));
    }

    @Operation(summary = "Add role user")
    @PatchMapping(path = "add-role")
    public ResponseEntity<Map<String, List<String>>> add_role(@RequestParam String username, @RequestParam String role){
        return ResponseEntity.ok(this.modifyUserService.addRole(username, role));
    }

    @Operation(summary = "Remove role user")
    @PatchMapping(path = "remove-role")
    public ResponseEntity<Map<String, List<String>>> remove_role(@RequestParam String username, @RequestParam String role){
        return ResponseEntity.ok(this.modifyUserService.removeRole(username, role));
    }
}
