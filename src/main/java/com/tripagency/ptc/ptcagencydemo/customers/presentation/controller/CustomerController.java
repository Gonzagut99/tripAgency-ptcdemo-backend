package com.tripagency.ptc.ptcagencydemo.customers.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripagency.ptc.ptcagencydemo.customers.application.commands.CreateCustomerCommand;
import com.tripagency.ptc.ptcagencydemo.customers.application.commands.handlers.CreateCustomerCommandHandler;
import com.tripagency.ptc.ptcagencydemo.customers.application.queries.CustomerPaginatedQuery;
import com.tripagency.ptc.ptcagencydemo.customers.application.queries.handlers.CustomerPaginatedQueryHandler;
import com.tripagency.ptc.ptcagencydemo.customers.domain.entities.DCustomer;
import com.tripagency.ptc.ptcagencydemo.customers.presentation.dto.CreateCustomerDto;
import com.tripagency.ptc.ptcagencydemo.customers.presentation.dto.PaginatedCustomerRequestDto;
import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.GeneralException;
import com.tripagency.ptc.ptcagencydemo.general.presentation.controllers.BaseV1Controller;
import com.tripagency.ptc.ptcagencydemo.general.presentation.exception.ErrorBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para el manejo de clientes")
public class CustomerController extends BaseV1Controller {

    private final CustomerPaginatedQueryHandler customerPaginatedQueryHandler;
    private final CreateCustomerCommandHandler createCustomerCommandHandler;

    @Autowired
    public CustomerController(CustomerPaginatedQueryHandler customerPaginatedQueryHandler, CreateCustomerCommandHandler createCustomerCommandHandler) {
        this.customerPaginatedQueryHandler = customerPaginatedQueryHandler;
        this.createCustomerCommandHandler = createCustomerCommandHandler;
    }

    @GetMapping("/paginados")
    @Operation(summary = "Obtener clientes paginados", description = "Obtiene una lista paginada de clientes según la configuración proporcionada.", responses = {
        @ApiResponse(responseCode = "200", description = "Lista paginada de clientes obtenida exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorBody.class))),
    })
    public Page<DCustomer> getPaginatedCustomers(@ModelAttribute PaginatedCustomerRequestDto requestDto) throws GeneralException {
        requestDto.normalizePageNumber();
        CustomerPaginatedQuery query = new CustomerPaginatedQuery(requestDto);
        return customerPaginatedQueryHandler.execute(query);
    }

    @PostMapping("")
    public DCustomer saveCustomers(@RequestBody CreateCustomerDto  entity) {
        DCustomer createdCustomer = createCustomerCommandHandler.execute(new CreateCustomerCommand(entity));
        return createdCustomer;
    }


}
