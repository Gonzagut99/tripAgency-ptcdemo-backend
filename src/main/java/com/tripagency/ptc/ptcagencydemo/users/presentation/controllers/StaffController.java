package com.tripagency.ptc.ptcagencydemo.users.presentation.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.GeneralException;
import com.tripagency.ptc.ptcagencydemo.general.presentation.controllers.BaseV1Controller;
import com.tripagency.ptc.ptcagencydemo.general.presentation.exception.ErrorBody;
import com.tripagency.ptc.ptcagencydemo.users.application.commands.CreateStaffCommand;
import com.tripagency.ptc.ptcagencydemo.users.application.commands.CreateUserWithStaffCommand;
import com.tripagency.ptc.ptcagencydemo.users.application.commands.handlers.CreateStaffCommandHandler;
import com.tripagency.ptc.ptcagencydemo.users.application.commands.handlers.CreateUserWithStaffCommandHandler;
import com.tripagency.ptc.ptcagencydemo.users.application.queries.GetStaffByIdQuery;
import com.tripagency.ptc.ptcagencydemo.users.application.queries.GetStaffByRoleQuery;
import com.tripagency.ptc.ptcagencydemo.users.application.queries.StaffPaginatedQuery;
import com.tripagency.ptc.ptcagencydemo.users.application.queries.handlers.GetStaffByIdQueryHandler;
import com.tripagency.ptc.ptcagencydemo.users.application.queries.handlers.GetStaffByRoleQueryHandler;
import com.tripagency.ptc.ptcagencydemo.users.application.queries.handlers.StaffPaginatedQueryHandler;
import com.tripagency.ptc.ptcagencydemo.users.domain.entities.DStaff;
import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DRoles;
import com.tripagency.ptc.ptcagencydemo.users.presentation.dto.CreateStaffDto;
import com.tripagency.ptc.ptcagencydemo.users.presentation.dto.CreateUserWithStaffDto;
import com.tripagency.ptc.ptcagencydemo.users.presentation.dto.PaginatedStaffRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/staff")
@Tag(name = "Staff", description = "Endpoints para el manejo de personal")
public class StaffController extends BaseV1Controller {

    private final CreateStaffCommandHandler createStaffCommandHandler;
    private final CreateUserWithStaffCommandHandler createUserWithStaffCommandHandler;
    private final GetStaffByIdQueryHandler getStaffByIdQueryHandler;
    private final StaffPaginatedQueryHandler staffPaginatedQueryHandler;
    private final GetStaffByRoleQueryHandler getStaffByRoleQueryHandler;

    public StaffController(CreateStaffCommandHandler createStaffCommandHandler,
                          CreateUserWithStaffCommandHandler createUserWithStaffCommandHandler,
                          GetStaffByIdQueryHandler getStaffByIdQueryHandler,
                          StaffPaginatedQueryHandler staffPaginatedQueryHandler,
                          GetStaffByRoleQueryHandler getStaffByRoleQueryHandler) {
        this.createStaffCommandHandler = createStaffCommandHandler;
        this.createUserWithStaffCommandHandler = createUserWithStaffCommandHandler;
        this.getStaffByIdQueryHandler = getStaffByIdQueryHandler;
        this.staffPaginatedQueryHandler = staffPaginatedQueryHandler;
        this.getStaffByRoleQueryHandler = getStaffByRoleQueryHandler;
    }

    @PostMapping("")
    @Operation(
        summary = "Crear un nuevo staff",
        description = "Crea un nuevo miembro del personal con la información proporcionada (requiere userId existente).",
        responses = {
            @ApiResponse(responseCode = "200", description = "Staff creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DStaff.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorBody.class)))
        }
    )
    public DStaff createStaff(@Valid @RequestBody CreateStaffDto dto) {
        return createStaffCommandHandler.execute(new CreateStaffCommand(dto));
    }

    @PostMapping("/with-user")
    @Operation(
        summary = "Crear un nuevo usuario con staff",
        description = "Crea un nuevo usuario y su staff asociado en una sola operación (relación 1:1).",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario y staff creados exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DStaff.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorBody.class)))
        }
    )
    public DStaff createUserWithStaff(@Valid @RequestBody CreateUserWithStaffDto dto) {
        return createUserWithStaffCommandHandler.execute(new CreateUserWithStaffCommand(dto));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener staff por ID",
        description = "Obtiene un miembro del personal específico por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Staff encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Staff no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorBody.class)))
        }
    )
    public DStaff getStaffById(@PathVariable Long id) {
        return getStaffByIdQueryHandler.execute(new GetStaffByIdQuery(id));
    }

    @GetMapping("/paginados")
    @Operation(
        summary = "Obtener staff paginado",
        description = "Obtiene una lista paginada de personal según la configuración proporcionada.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista paginada de staff obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorBody.class)))
        }
    )
    public Page<DStaff> getPaginatedStaff(@ModelAttribute PaginatedStaffRequestDto requestDto) throws GeneralException {
        requestDto.normalizePageNumber();
        StaffPaginatedQuery query = new StaffPaginatedQuery(requestDto);
        return staffPaginatedQueryHandler.execute(query);
    }

    @GetMapping("/by-role/{role}")
    @Operation(
        summary = "Obtener staff por rol",
        description = "Obtiene una lista de personal filtrada por rol.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de staff obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Rol inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorBody.class)))
        }
    )
    public List<DStaff> getStaffByRole(@PathVariable String role) {
        DRoles dRole = DRoles.valueOf(role.toUpperCase());
        return getStaffByRoleQueryHandler.execute(new GetStaffByRoleQuery(dRole));
    }
}
