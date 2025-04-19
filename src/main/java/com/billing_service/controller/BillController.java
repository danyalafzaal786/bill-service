package com.billing_service.controller;

import com.billing_service.dto.CalculateRequest;
import com.billing_service.dto.CalculateResponse;
import com.billing_service.service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping(value = "calculate")
    public ResponseEntity<CalculateResponse> calculate(@RequestBody CalculateRequest request) {
        return ResponseEntity.ok(billService.calculateNetPayableAmount(request));
    }
}
