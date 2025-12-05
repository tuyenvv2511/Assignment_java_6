package com.example.demo_java6_asm.controller;

import com.example.demo_java6_asm.dto.request.NhanVienRequest;
import com.example.demo_java6_asm.dto.response.NhanVienResponse;
import com.example.demo_java6_asm.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nhan-vien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<NhanVienResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(nhanVienService.search(keyword, pageable));
        }
        return ResponseEntity.ok(nhanVienService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVienResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhanVienService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NhanVienResponse> create(@Valid @RequestBody NhanVienRequest request) {
        return new ResponseEntity<>(nhanVienService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NhanVienResponse> update(@PathVariable Integer id,
            @Valid @RequestBody NhanVienRequest request) {
        return ResponseEntity.ok(nhanVienService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        nhanVienService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
