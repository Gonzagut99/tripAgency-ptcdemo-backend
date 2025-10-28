package com.tripagency.ptc.ptcagencydemo.liquidations.presentation.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddAdditionalServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddFlightServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddHotelServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddIncidencyCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddPaymentCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddTourServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.CreateLiquidationCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddAdditionalServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddFlightServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddHotelServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddIncidencyCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddPaymentCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddTourServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.CreateLiquidationCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.GetLiquidationByIdQuery;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.GetLiquidationsByCustomerQuery;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.GetLiquidationsByStatusQuery;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.LiquidationPaginatedQuery;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.handlers.GetLiquidationByIdQueryHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.handlers.GetLiquidationsByCustomerQueryHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.handlers.GetLiquidationsByStatusQueryHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.handlers.LiquidationPaginatedQueryHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DLiquidationStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DPaymentMethod;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddAdditionalServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddFlightServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddHotelServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddIncidencyDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddPaymentDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddTourServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.CreateLiquidationDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.LiquidationWithDetailsDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.PaginatedLiquidationRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/liquidations")
@Tag(name = "Liquidaciones", description = "Endpoints de gestión de liquidaciones")
public class LiquidationController {

    private final CreateLiquidationCommandHandler createLiquidationCommandHandler;
    private final LiquidationPaginatedQueryHandler liquidationPaginatedQueryHandler;
    private final GetLiquidationByIdQueryHandler getLiquidationByIdQueryHandler;
    private final GetLiquidationsByCustomerQueryHandler getLiquidationsByCustomerQueryHandler;
    private final GetLiquidationsByStatusQueryHandler getLiquidationsByStatusQueryHandler;
    private final AddPaymentCommandHandler addPaymentCommandHandler;
    private final AddFlightServiceCommandHandler addFlightServiceCommandHandler;
    private final AddHotelServiceCommandHandler addHotelServiceCommandHandler;
    private final AddTourServiceCommandHandler addTourServiceCommandHandler;
    private final AddAdditionalServiceCommandHandler addAdditionalServiceCommandHandler;
    private final AddIncidencyCommandHandler addIncidencyCommandHandler;

    public LiquidationController(
            CreateLiquidationCommandHandler createLiquidationCommandHandler,
            LiquidationPaginatedQueryHandler liquidationPaginatedQueryHandler,
            GetLiquidationByIdQueryHandler getLiquidationByIdQueryHandler,
            GetLiquidationsByCustomerQueryHandler getLiquidationsByCustomerQueryHandler,
            GetLiquidationsByStatusQueryHandler getLiquidationsByStatusQueryHandler,
            AddPaymentCommandHandler addPaymentCommandHandler,
            AddFlightServiceCommandHandler addFlightServiceCommandHandler,
            AddHotelServiceCommandHandler addHotelServiceCommandHandler,
            AddTourServiceCommandHandler addTourServiceCommandHandler,
            AddAdditionalServiceCommandHandler addAdditionalServiceCommandHandler,
            AddIncidencyCommandHandler addIncidencyCommandHandler) {
        this.createLiquidationCommandHandler = createLiquidationCommandHandler;
        this.liquidationPaginatedQueryHandler = liquidationPaginatedQueryHandler;
        this.getLiquidationByIdQueryHandler = getLiquidationByIdQueryHandler;
        this.getLiquidationsByCustomerQueryHandler = getLiquidationsByCustomerQueryHandler;
        this.getLiquidationsByStatusQueryHandler = getLiquidationsByStatusQueryHandler;
        this.addPaymentCommandHandler = addPaymentCommandHandler;
        this.addFlightServiceCommandHandler = addFlightServiceCommandHandler;
        this.addHotelServiceCommandHandler = addHotelServiceCommandHandler;
        this.addTourServiceCommandHandler = addTourServiceCommandHandler;
        this.addAdditionalServiceCommandHandler = addAdditionalServiceCommandHandler;
        this.addIncidencyCommandHandler = addIncidencyCommandHandler;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva liquidación")
    public ResponseEntity<DLiquidation> createLiquidation(@Valid @RequestBody CreateLiquidationDto dto) {
        CreateLiquidationCommand command = new CreateLiquidationCommand(
                dto.getCurrencyRate(),
                dto.getPaymentDeadline(),
                dto.getCompanion(),
                dto.getCustomerId(),
                dto.getStaffId());

        DLiquidation liquidation = createLiquidationCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(liquidation);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Obtener liquidaciones paginadas")
    public Page<LiquidationWithDetailsDto> getPaginatedLiquidations(@ModelAttribute PaginatedLiquidationRequestDto requestDto) {
        requestDto.normalizePageNumber();
        LiquidationPaginatedQuery query = new LiquidationPaginatedQuery(requestDto);
        return liquidationPaginatedQueryHandler.execute(query);
    }

    @GetMapping("/{liquidationId}")
    @Operation(summary = "Obtener liquidación por ID")
    public ResponseEntity<LiquidationWithDetailsDto> getLiquidationById(@PathVariable Long liquidationId) {
        GetLiquidationByIdQuery query = new GetLiquidationByIdQuery(liquidationId);
        return getLiquidationByIdQueryHandler.execute(query)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Obtener liquidaciones paginadas por ID de cliente")
    public Page<LiquidationWithDetailsDto> getLiquidationsByCustomer(
            @PathVariable Long customerId,
            @ModelAttribute PaginatedLiquidationRequestDto requestDto) {
        requestDto.normalizePageNumber();
        GetLiquidationsByCustomerQuery query = new GetLiquidationsByCustomerQuery(customerId, requestDto);
        return getLiquidationsByCustomerQueryHandler.execute(query);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Obtener liquidaciones paginadas por estado")
    public Page<LiquidationWithDetailsDto> getLiquidationsByStatus(
            @PathVariable DLiquidationStatus status,
            @ModelAttribute PaginatedLiquidationRequestDto requestDto) {
        requestDto.normalizePageNumber();
        GetLiquidationsByStatusQuery query = new GetLiquidationsByStatusQuery(status, requestDto);
        return getLiquidationsByStatusQueryHandler.execute(query);
    }

    @PostMapping("/{liquidationId}/payments")
    @Operation(summary = "Agregar pago a la liquidación")
    public ResponseEntity<DLiquidation> addPayment(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddPaymentDto dto) {
        AddPaymentCommand command = new AddPaymentCommand(
                liquidationId,
                DPaymentMethod.valueOf(dto.getPaymentMethod()),
                dto.getAmount());

        DLiquidation liquidation = addPaymentCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }

    @PostMapping("/{liquidationId}/flight-services")
    @Operation(summary = "Agregar servicio de vuelo a la liquidación")
    public ResponseEntity<DLiquidation> addFlightService(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddFlightServiceDto dto) {
        AddFlightServiceCommand command = new AddFlightServiceCommand(liquidationId, dto);
        DLiquidation liquidation = addFlightServiceCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }

    @PostMapping("/{liquidationId}/hotel-services")
    @Operation(summary = "Agregar servicio de hotel a la liquidación")
    public ResponseEntity<DLiquidation> addHotelService(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddHotelServiceDto dto) {
        AddHotelServiceCommand command = new AddHotelServiceCommand(liquidationId, dto);
        DLiquidation liquidation = addHotelServiceCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }

    @PostMapping("/{liquidationId}/tour-services")
    @Operation(summary = "Agregar servicio de tour a la liquidación")
    public ResponseEntity<DLiquidation> addTourService(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddTourServiceDto dto) {
        AddTourServiceCommand command = new AddTourServiceCommand(liquidationId, dto);
        DLiquidation liquidation = addTourServiceCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }

    @PostMapping("/{liquidationId}/additional-services")
    @Operation(summary = "Agregar servicio adicional a la liquidación")
    public ResponseEntity<DLiquidation> addAdditionalService(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddAdditionalServiceDto dto) {
        AddAdditionalServiceCommand command = new AddAdditionalServiceCommand(liquidationId, dto);
        DLiquidation liquidation = addAdditionalServiceCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }

    @PostMapping("/{liquidationId}/incidencies")
    @Operation(summary = "Agregar incidencia a la liquidación")
    public ResponseEntity<DLiquidation> addIncidency(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddIncidencyDto dto) {
        AddIncidencyCommand command = new AddIncidencyCommand(liquidationId, dto);
        DLiquidation liquidation = addIncidencyCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }
}
