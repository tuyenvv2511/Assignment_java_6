package com.example.demo_java6_asm.controller;

import com.example.demo_java6_asm.dto.request.KhachHangRequest;
import com.example.demo_java6_asm.dto.response.KhachHangResponse;
import com.example.demo_java6_asm.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<KhachHangResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(khachHangService.search(keyword, pageable));
        }
        return ResponseEntity.ok(khachHangService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHangResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(khachHangService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KhachHangResponse> create(@Valid @RequestBody KhachHangRequest request) {
        return new ResponseEntity<>(khachHangService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KhachHangResponse> update(@PathVariable Integer id,
            @Valid @RequestBody KhachHangRequest request) {
        return ResponseEntity.ok(khachHangService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        khachHangService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
