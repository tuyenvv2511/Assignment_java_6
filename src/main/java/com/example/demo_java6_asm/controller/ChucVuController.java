package com.example.demo_java6_asm.controller;

import com.example.demo_java6_asm.dto.request.ChucVuRequest;
import com.example.demo_java6_asm.dto.response.ChucVuResponse;
import com.example.demo_java6_asm.service.ChucVuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chuc-vu")
public class ChucVuController {

    @Autowired
    private ChucVuService chucVuService;

    @GetMapping
    public ResponseEntity<List<ChucVuResponse>> getAll() {
        return ResponseEntity.ok(chucVuService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChucVuResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(chucVuService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChucVuResponse> create(@Valid @RequestBody ChucVuRequest request) {
        return new ResponseEntity<>(chucVuService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChucVuResponse> update(@PathVariable Integer id, @Valid @RequestBody ChucVuRequest request) {
        return ResponseEntity.ok(chucVuService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        chucVuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
