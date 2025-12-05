package com.example.demo_java6_asm.controller;

import com.example.demo_java6_asm.dto.request.HoaDonRequest;
import com.example.demo_java6_asm.dto.response.HoaDonResponse;
import com.example.demo_java6_asm.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping
    public ResponseEntity<Page<HoaDonResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(hoaDonService.search(keyword, pageable));
        }
        return ResponseEntity.ok(hoaDonService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDonResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(hoaDonService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<HoaDonResponse> create(@RequestBody HoaDonRequest request) {
        return ResponseEntity.ok(hoaDonService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<HoaDonResponse> update(@PathVariable Integer id, @RequestBody HoaDonRequest request) {
        return ResponseEntity.ok(hoaDonService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        hoaDonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
