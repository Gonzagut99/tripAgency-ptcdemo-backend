package com.tripagency.ptc.ptcagencydemo.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tripagency.ptc.ptcagencydemo.users.application.commands.CreateUserWithStaffCommand;
import com.tripagency.ptc.ptcagencydemo.users.application.commands.handlers.CreateUserWithStaffCommandHandler;
import com.tripagency.ptc.ptcagencydemo.users.application.queries.StaffPaginatedQuery;
import com.tripagency.ptc.ptcagencydemo.users.application.queries.handlers.StaffPaginatedQueryHandler;
import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DStaff;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DRoles;
import com.tripagency.ptc.ptcagencydemo.users.presentation.dto.CreateUserWithStaffDto;
import com.tripagency.ptc.ptcagencydemo.users.presentation.dto.PaginatedStaffRequestDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class HomeController {

    private final StaffPaginatedQueryHandler staffPaginatedQueryHandler;
    private final CreateUserWithStaffCommandHandler createUserWithStaffCommandHandler;

    public HomeController(
            StaffPaginatedQueryHandler staffPaginatedQueryHandler,
            CreateUserWithStaffCommandHandler createUserWithStaffCommandHandler) {
        this.staffPaginatedQueryHandler = staffPaginatedQueryHandler;
        this.createUserWithStaffCommandHandler = createUserWithStaffCommandHandler;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        PaginatedStaffRequestDto requestDto = new PaginatedStaffRequestDto(page, size);
        
        StaffPaginatedQuery query = new StaffPaginatedQuery(requestDto);
        Page<DStaff> staffPage = staffPaginatedQueryHandler.execute(query);
        
        model.addAttribute("staffPage", staffPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", staffPage.getTotalPages());
        model.addAttribute("createUserDto", new CreateUserWithStaffDto());
        model.addAttribute("roles", DRoles.values());
        model.addAttribute("currencies", DCurrency.values());
        
        return "home";
    }

    @PostMapping("/users/create")
    public String createUser(
            @Valid @ModelAttribute("createUserDto") CreateUserWithStaffDto dto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error en los datos del formulario");
            return "redirect:/";
        }

        try {
            CreateUserWithStaffCommand command = new CreateUserWithStaffCommand(dto);
            
            createUserWithStaffCommandHandler.execute(command);
            redirectAttributes.addFlashAttribute("success", "Usuario creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear usuario: " + e.getMessage());
        }

        return "redirect:/";
    }
}
