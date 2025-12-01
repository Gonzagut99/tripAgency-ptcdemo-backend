package com.tripagency.ptc.ptcagencydemo.liquidations.presentation.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivateAdditionalServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivateFlightBookingCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivateHotelBookingCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivateIncidencyCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivateLiquidationCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivatePaymentCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.DeactivateTourCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateAdditionalServiceCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateFlightBookingCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateHotelBookingCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateIncidencyCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdatePaymentCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.UpdateTourCommand;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddAdditionalServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddFlightServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddHotelServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddIncidencyCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddPaymentCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.AddTourServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.CreateLiquidationCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.DeactivateAdditionalServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.DeactivateFlightBookingCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.DeactivateHotelBookingCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.DeactivateIncidencyCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.DeactivateLiquidationCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.DeactivatePaymentCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.DeactivateTourCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.UpdateAdditionalServiceCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.UpdateFlightBookingCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.UpdateHotelBookingCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.UpdateIncidencyCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.UpdatePaymentCommandHandler;
import com.tripagency.ptc.ptcagencydemo.liquidations.application.commands.handlers.UpdateTourCommandHandler;
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
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.AdditionalServices;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.FlightBooking;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.HotelBooking;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Incidency;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Liquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Payment;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Tour;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddAdditionalServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddFlightServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddHotelServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddIncidencyDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddPaymentDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.AddTourServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.CreateLiquidationDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.LiquidationWithDetailsDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.PaginatedLiquidationRequestDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateAdditionalServiceDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateFlightBookingDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateHotelBookingDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateIncidencyDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdatePaymentDto;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdateTourDto;

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
    private final UpdateTourCommandHandler updateTourCommandHandler;
    private final UpdateHotelBookingCommandHandler updateHotelBookingCommandHandler;
    private final UpdateFlightBookingCommandHandler updateFlightBookingCommandHandler;
    private final UpdateAdditionalServiceCommandHandler updateAdditionalServiceCommandHandler;
    private final UpdatePaymentCommandHandler updatePaymentCommandHandler;
    private final UpdateIncidencyCommandHandler updateIncidencyCommandHandler;
    private final DeactivateLiquidationCommandHandler deactivateLiquidationCommandHandler;
    private final DeactivateTourCommandHandler deactivateTourCommandHandler;
    private final DeactivateHotelBookingCommandHandler deactivateHotelBookingCommandHandler;
    private final DeactivateFlightBookingCommandHandler deactivateFlightBookingCommandHandler;
    private final DeactivateAdditionalServiceCommandHandler deactivateAdditionalServiceCommandHandler;
    private final DeactivatePaymentCommandHandler deactivatePaymentCommandHandler;
    private final DeactivateIncidencyCommandHandler deactivateIncidencyCommandHandler;

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
            AddIncidencyCommandHandler addIncidencyCommandHandler,
            UpdateTourCommandHandler updateTourCommandHandler,
            UpdateHotelBookingCommandHandler updateHotelBookingCommandHandler,
            UpdateFlightBookingCommandHandler updateFlightBookingCommandHandler,
            UpdateAdditionalServiceCommandHandler updateAdditionalServiceCommandHandler,
            UpdatePaymentCommandHandler updatePaymentCommandHandler,
            UpdateIncidencyCommandHandler updateIncidencyCommandHandler,
            DeactivateLiquidationCommandHandler deactivateLiquidationCommandHandler,
            DeactivateTourCommandHandler deactivateTourCommandHandler,
            DeactivateHotelBookingCommandHandler deactivateHotelBookingCommandHandler,
            DeactivateFlightBookingCommandHandler deactivateFlightBookingCommandHandler,
            DeactivateAdditionalServiceCommandHandler deactivateAdditionalServiceCommandHandler,
            DeactivatePaymentCommandHandler deactivatePaymentCommandHandler,
            DeactivateIncidencyCommandHandler deactivateIncidencyCommandHandler) {
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
        this.updateTourCommandHandler = updateTourCommandHandler;
        this.updateHotelBookingCommandHandler = updateHotelBookingCommandHandler;
        this.updateFlightBookingCommandHandler = updateFlightBookingCommandHandler;
        this.updateAdditionalServiceCommandHandler = updateAdditionalServiceCommandHandler;
        this.updatePaymentCommandHandler = updatePaymentCommandHandler;
        this.updateIncidencyCommandHandler = updateIncidencyCommandHandler;
        this.deactivateLiquidationCommandHandler = deactivateLiquidationCommandHandler;
        this.deactivateTourCommandHandler = deactivateTourCommandHandler;
        this.deactivateHotelBookingCommandHandler = deactivateHotelBookingCommandHandler;
        this.deactivateFlightBookingCommandHandler = deactivateFlightBookingCommandHandler;
        this.deactivateAdditionalServiceCommandHandler = deactivateAdditionalServiceCommandHandler;
        this.deactivatePaymentCommandHandler = deactivatePaymentCommandHandler;
        this.deactivateIncidencyCommandHandler = deactivateIncidencyCommandHandler;
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

    // ==================== UPDATE ENDPOINTS ====================

    @PutMapping("/{liquidationId}/tour-services/{tourServiceId}/tours/{tourId}")
    @Operation(summary = "Actualizar un tour específico")
    public ResponseEntity<Tour> updateTour(
            @PathVariable Long liquidationId,
            @PathVariable Long tourServiceId,
            @PathVariable Long tourId,
            @Valid @RequestBody UpdateTourDto dto) {
        UpdateTourCommand command = new UpdateTourCommand(liquidationId, tourServiceId, tourId, dto);
        Tour updatedTour = updateTourCommandHandler.execute(command);
        return ResponseEntity.ok(updatedTour);
    }

    @PutMapping("/{liquidationId}/hotel-services/{hotelServiceId}/bookings/{hotelBookingId}")
    @Operation(summary = "Actualizar una reserva de hotel específica")
    public ResponseEntity<HotelBooking> updateHotelBooking(
            @PathVariable Long liquidationId,
            @PathVariable Long hotelServiceId,
            @PathVariable Long hotelBookingId,
            @Valid @RequestBody UpdateHotelBookingDto dto) {
        UpdateHotelBookingCommand command = new UpdateHotelBookingCommand(liquidationId, hotelServiceId, hotelBookingId, dto);
        HotelBooking updatedHotelBooking = updateHotelBookingCommandHandler.execute(command);
        return ResponseEntity.ok(updatedHotelBooking);
    }

    @PutMapping("/{liquidationId}/flight-services/{flightServiceId}/bookings/{flightBookingId}")
    @Operation(summary = "Actualizar una reserva de vuelo específica")
    public ResponseEntity<FlightBooking> updateFlightBooking(
            @PathVariable Long liquidationId,
            @PathVariable Long flightServiceId,
            @PathVariable Long flightBookingId,
            @Valid @RequestBody UpdateFlightBookingDto dto) {
        UpdateFlightBookingCommand command = new UpdateFlightBookingCommand(liquidationId, flightServiceId, flightBookingId, dto);
        FlightBooking updatedFlightBooking = updateFlightBookingCommandHandler.execute(command);
        return ResponseEntity.ok(updatedFlightBooking);
    }

    @PutMapping("/{liquidationId}/additional-services/{additionalServiceId}")
    @Operation(summary = "Actualizar un servicio adicional")
    public ResponseEntity<AdditionalServices> updateAdditionalService(
            @PathVariable Long liquidationId,
            @PathVariable Long additionalServiceId,
            @Valid @RequestBody UpdateAdditionalServiceDto dto) {
        UpdateAdditionalServiceCommand command = new UpdateAdditionalServiceCommand(liquidationId, additionalServiceId, dto);
        AdditionalServices updatedAdditionalService = updateAdditionalServiceCommandHandler.execute(command);
        return ResponseEntity.ok(updatedAdditionalService);
    }

    @PutMapping("/{liquidationId}/payments/{paymentId}")
    @Operation(summary = "Actualizar un pago")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long liquidationId,
            @PathVariable Long paymentId,
            @Valid @RequestBody UpdatePaymentDto dto) {
        UpdatePaymentCommand command = new UpdatePaymentCommand(liquidationId, paymentId, dto);
        Payment updatedPayment = updatePaymentCommandHandler.execute(command);
        return ResponseEntity.ok(updatedPayment);
    }

    @PutMapping("/{liquidationId}/incidencies/{incidencyId}")
    @Operation(summary = "Actualizar una incidencia")
    public ResponseEntity<Incidency> updateIncidency(
            @PathVariable Long liquidationId,
            @PathVariable Long incidencyId,
            @Valid @RequestBody UpdateIncidencyDto dto) {
        UpdateIncidencyCommand command = new UpdateIncidencyCommand(liquidationId, incidencyId, dto);
        Incidency updatedIncidency = updateIncidencyCommandHandler.execute(command);
        return ResponseEntity.ok(updatedIncidency);
    }

    // ============== DELETE (Soft Delete) Endpoints ==============

    @DeleteMapping("/{liquidationId}")
    @Operation(summary = "Desactivar (soft delete) una liquidación")
    public ResponseEntity<Liquidation> deactivateLiquidation(@PathVariable Long liquidationId) {
        DeactivateLiquidationCommand command = new DeactivateLiquidationCommand(liquidationId);
        Liquidation result = deactivateLiquidationCommandHandler.execute(command);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{liquidationId}/tour-services/{tourServiceId}/tours/{tourId}")
    @Operation(summary = "Desactivar (soft delete) un tour")
    public ResponseEntity<Tour> deactivateTour(
            @PathVariable Long liquidationId,
            @PathVariable Long tourServiceId,
            @PathVariable Long tourId) {
        DeactivateTourCommand command = new DeactivateTourCommand(liquidationId, tourServiceId, tourId);
        Tour result = deactivateTourCommandHandler.execute(command);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{liquidationId}/hotel-services/{hotelServiceId}/bookings/{hotelBookingId}")
    @Operation(summary = "Desactivar (soft delete) una reserva de hotel")
    public ResponseEntity<HotelBooking> deactivateHotelBooking(
            @PathVariable Long liquidationId,
            @PathVariable Long hotelServiceId,
            @PathVariable Long hotelBookingId) {
        DeactivateHotelBookingCommand command = new DeactivateHotelBookingCommand(liquidationId, hotelServiceId, hotelBookingId);
        HotelBooking result = deactivateHotelBookingCommandHandler.execute(command);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{liquidationId}/flight-services/{flightServiceId}/bookings/{flightBookingId}")
    @Operation(summary = "Desactivar (soft delete) una reserva de vuelo")
    public ResponseEntity<FlightBooking> deactivateFlightBooking(
            @PathVariable Long liquidationId,
            @PathVariable Long flightServiceId,
            @PathVariable Long flightBookingId) {
        DeactivateFlightBookingCommand command = new DeactivateFlightBookingCommand(liquidationId, flightServiceId, flightBookingId);
        FlightBooking result = deactivateFlightBookingCommandHandler.execute(command);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{liquidationId}/additional-services/{additionalServiceId}")
    @Operation(summary = "Desactivar (soft delete) un servicio adicional")
    public ResponseEntity<AdditionalServices> deactivateAdditionalService(
            @PathVariable Long liquidationId,
            @PathVariable Long additionalServiceId) {
        DeactivateAdditionalServiceCommand command = new DeactivateAdditionalServiceCommand(liquidationId, additionalServiceId);
        AdditionalServices result = deactivateAdditionalServiceCommandHandler.execute(command);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{liquidationId}/payments/{paymentId}")
    @Operation(summary = "Desactivar (soft delete) un pago")
    public ResponseEntity<Payment> deactivatePayment(
            @PathVariable Long liquidationId,
            @PathVariable Long paymentId) {
        DeactivatePaymentCommand command = new DeactivatePaymentCommand(liquidationId, paymentId);
        Payment result = deactivatePaymentCommandHandler.execute(command);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{liquidationId}/incidencies/{incidencyId}")
    @Operation(summary = "Desactivar (soft delete) una incidencia")
    public ResponseEntity<Incidency> deactivateIncidency(
            @PathVariable Long liquidationId,
            @PathVariable Long incidencyId) {
        DeactivateIncidencyCommand command = new DeactivateIncidencyCommand(liquidationId, incidencyId);
        Incidency result = deactivateIncidencyCommandHandler.execute(command);
        return ResponseEntity.ok(result);
    }
}
