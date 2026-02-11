package com.example.Conflict.Tracker.web;

import com.example.Conflict.Tracker.dto.ConflictCreateUpdateDTO;
import com.example.Conflict.Tracker.model.ConflictStatus;
import com.example.Conflict.Tracker.service.ConflictService;
import com.example.Conflict.Tracker.dto.ConflictResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/conflicts")
public class ConflictWebController {

    private final ConflictService conflictService;

    public ConflictWebController(ConflictService conflictService) {
        this.conflictService = conflictService;
    }

    // LISTADO (GET)
    @GetMapping
    public String list(Model model) {
        model.addAttribute("conflicts", conflictService.findAll(""));
        // metodo conflict service
        return "conflicts/list";
    }

    // FORM (GET)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("conflict", new ConflictCreateUpdateDTO());
        model.addAttribute("statuses", ConflictStatus.values());
        return "conflicts/create";
    }

    // CREAR (POST)
    @PostMapping
    public String create(
            @Valid @ModelAttribute("conflict") ConflictCreateUpdateDTO dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", ConflictStatus.values());
            return "conflicts/create";
        }

        conflictService.create(dto);

        return "redirect:/web/conflicts?created";
    }
    //Delete
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        conflictService.delete(id);
        return "redirect:/web/conflicts?deleted";
    }
}
