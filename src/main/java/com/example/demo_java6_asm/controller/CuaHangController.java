package com.example.demo_java6_asm.controller;

import com.example.demo_java6_asm.dto.request.CuaHangRequest;
import com.example.demo_java6_asm.dto.response.CuaHangResponse;
import com.example.demo_java6_asm.service.CuaHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cua-hang")
public class CuaHangController {

    @Autowired
    private CuaHangService cuaHangService;

    @GetMapping
    public ResponseEntity<List<CuaHangResponse>> getAll() {
        return ResponseEntity.ok(cuaHangService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuaHangResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cuaHangService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CuaHangResponse> create(@Valid @RequestBody CuaHangRequest request) {
        return new ResponseEntity<>(cuaHangService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CuaHangResponse> update(@PathVariable Integer id,
            @Valid @RequestBody CuaHangRequest request) {
        return ResponseEntity.ok(cuaHangService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cuaHangService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
