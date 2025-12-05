package com.example.demo_java6_asm.controller;

import com.example.demo_java6_asm.dto.request.SanPhamRequest;
import com.example.demo_java6_asm.dto.response.SanPhamResponse;
import com.example.demo_java6_asm.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<SanPhamResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return ResponseEntity.ok(sanPhamService.search(keyword, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(sanPhamService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SanPhamResponse> create(@Valid @RequestBody SanPhamRequest request) {
        return new ResponseEntity<>(sanPhamService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SanPhamResponse> update(@PathVariable Integer id,
            @Valid @RequestBody SanPhamRequest request) {
        return ResponseEntity.ok(sanPhamService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
