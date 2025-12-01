package com.example.Conflict.Tracker.web;

import com.example.Conflict.Tracker.dto.ConflictCreateUpdateDTO;
import com.example.Conflict.Tracker.dto.ConflictResponseDTO;
import com.example.Conflict.Tracker.service.ConflictService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conflicts")
public class ConflictController {
    private final ConflictService conflictService;

    public ConflictController(ConflictService conflictService) {
        this.conflictService = conflictService;
    }

    // GET /api/v1/conflicts?status=ACTIVE (status es opcional)
    @GetMapping
    public List<ConflictResponseDTO> list(@RequestParam(required = false) String status) {
        return conflictService.findAll(status);
    }

    // GET /api/v1/conflicts/{id}
    @GetMapping("/{id}")
    public ConflictResponseDTO getById(@PathVariable Long id) {
        return conflictService.findById(id);
    }

    // POST /api/v1/conflicts
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConflictResponseDTO create(@RequestBody @Valid ConflictCreateUpdateDTO dto) {
        return conflictService.create(dto);
    }

    // PUT /api/v1/conflicts/{id}
    @PutMapping("/{id}")
    public ConflictResponseDTO update(@PathVariable Long id,
                                      @RequestBody @Valid ConflictCreateUpdateDTO dto) {
        return conflictService.update(id, dto);
    }

    // DELETE /api/v1/conflicts/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        conflictService.delete(id);
    }
}
