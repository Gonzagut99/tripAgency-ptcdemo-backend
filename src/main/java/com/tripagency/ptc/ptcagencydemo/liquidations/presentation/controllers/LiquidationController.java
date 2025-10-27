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
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddPaymentCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.AddTourServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.CreateLiquidationCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddAdditionalServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddFlightServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddHotelServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddPaymentCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddTourServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.CreateLiquidationCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.LiquidationPaginatedQuery;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.handlers.LiquidationPaginatedQueryHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DPaymentMethod;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddAdditionalServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddFlightServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddHotelServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddPaymentDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddTourServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.CreateLiquidationDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.PaginatedLiquidationRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/liquidations")
@Tag(name = "Liquidations", description = "Liquidation management endpoints")
public class LiquidationController {

    private final CreateLiquidationCommandHandler createLiquidationCommandHandler;
    private final LiquidationPaginatedQueryHandler liquidationPaginatedQueryHandler;
    private final AddPaymentCommandHandler addPaymentCommandHandler;
    private final AddFlightServiceCommandHandler addFlightServiceCommandHandler;
    private final AddHotelServiceCommandHandler addHotelServiceCommandHandler;
    private final AddTourServiceCommandHandler addTourServiceCommandHandler;
    private final AddAdditionalServiceCommandHandler addAdditionalServiceCommandHandler;

    public LiquidationController(
            CreateLiquidationCommandHandler createLiquidationCommandHandler,
            LiquidationPaginatedQueryHandler liquidationPaginatedQueryHandler,
            AddPaymentCommandHandler addPaymentCommandHandler,
            AddFlightServiceCommandHandler addFlightServiceCommandHandler,
            AddHotelServiceCommandHandler addHotelServiceCommandHandler,
            AddTourServiceCommandHandler addTourServiceCommandHandler,
            AddAdditionalServiceCommandHandler addAdditionalServiceCommandHandler) {
        this.createLiquidationCommandHandler = createLiquidationCommandHandler;
        this.liquidationPaginatedQueryHandler = liquidationPaginatedQueryHandler;
        this.addPaymentCommandHandler = addPaymentCommandHandler;
        this.addFlightServiceCommandHandler = addFlightServiceCommandHandler;
        this.addHotelServiceCommandHandler = addHotelServiceCommandHandler;
        this.addTourServiceCommandHandler = addTourServiceCommandHandler;
        this.addAdditionalServiceCommandHandler = addAdditionalServiceCommandHandler;
    }

    @PostMapping
    @Operation(summary = "Create a new liquidation")
    public ResponseEntity<DLiquidation> createLiquidation(@Valid @RequestBody CreateLiquidationDto dto) {
        CreateLiquidationCommand command = new CreateLiquidationCommand(
                dto.getCurrencyRate(),
                dto.getPaymentDeadline(),
                dto.getCompanion(),
                dto.getCustomerId().toString(),
                dto.getStaffId().toString());

        DLiquidation liquidation = createLiquidationCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(liquidation);
    }

    @GetMapping("/paginados")
    @Operation(summary = "Get paginated liquidations")
    public Page<DLiquidation> getPaginatedLiquidations(@ModelAttribute PaginatedLiquidationRequestDto requestDto) {
        requestDto.normalizePageNumber();
        LiquidationPaginatedQuery query = new LiquidationPaginatedQuery(requestDto);
        return liquidationPaginatedQueryHandler.execute(query);
    }

    @PostMapping("/{liquidationId}/payments")
    @Operation(summary = "Add payment to liquidation")
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
    @Operation(summary = "Add flight service to liquidation")
    public ResponseEntity<DLiquidation> addFlightService(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddFlightServiceDto dto) {
        AddFlightServiceCommand command = new AddFlightServiceCommand(liquidationId, dto);
        DLiquidation liquidation = addFlightServiceCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }

    @PostMapping("/{liquidationId}/hotel-services")
    @Operation(summary = "Add hotel service to liquidation")
    public ResponseEntity<DLiquidation> addHotelService(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddHotelServiceDto dto) {
        AddHotelServiceCommand command = new AddHotelServiceCommand(liquidationId, dto);
        DLiquidation liquidation = addHotelServiceCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }

    @PostMapping("/{liquidationId}/tour-services")
    @Operation(summary = "Add tour service to liquidation")
    public ResponseEntity<DLiquidation> addTourService(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddTourServiceDto dto) {
        AddTourServiceCommand command = new AddTourServiceCommand(liquidationId, dto);
        DLiquidation liquidation = addTourServiceCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }

    @PostMapping("/{liquidationId}/additional-services")
    @Operation(summary = "Add additional service to liquidation")
    public ResponseEntity<DLiquidation> addAdditionalService(
            @PathVariable Long liquidationId,
            @Valid @RequestBody AddAdditionalServiceDto dto) {
        AddAdditionalServiceCommand command = new AddAdditionalServiceCommand(liquidationId, dto);
        DLiquidation liquidation = addAdditionalServiceCommandHandler.execute(command);
        return ResponseEntity.ok(liquidation);
    }
}
