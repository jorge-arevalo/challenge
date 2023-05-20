package com.bank.management.challenge.unit.infrastructure.rest;

import com.bank.management.challenge.domain.models.output.ReportDto;
import com.bank.management.challenge.domain.ports.services.IReportService;
import com.bank.management.challenge.infrastructure.rest.ReportController;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

/**
 * Unit tests for ReportController.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class ReportControllerTest {

  @InjectMocks
  ReportController reportController;

  @Mock
  IReportService reportService;

  private ReportDto reportDto;

  private String initialDate;

  private String finalDate;

  private String customerName;

  @BeforeEach
  void initData() {
    initialDate = "01/01/2021";
    finalDate = "31/01/2021";
    customerName = "Customer Name";

    reportDto = ReportDto.builder()
        .movementDate("01/01/2021")
        .customerName(customerName)
        .accountNumber("1234567890")
        .accountType("savings")
        .initialBalance(BigDecimal.TEN)
        .status(Boolean.TRUE)
        .movementValue(BigDecimal.ONE)
        .balance(BigDecimal.TEN.subtract(BigDecimal.ONE))
        .build();

    log.info("ReportControllerTest.initData() - initDate: {}", initialDate);
    log.info("ReportControllerTest.initData() - finalDate: {}", finalDate);
    log.info("ReportControllerTest.initData() - customerName: {}", customerName);
    log.info("ReportControllerTest.initData() - reportDto: {}", reportDto);
  }

  @Test
  void findMovementByDateTest() {
    Mockito.when(reportService.findByMovementDateBetween(initialDate, finalDate, customerName))
        .thenReturn(Collections.singletonList(reportDto));
    var response = reportController.findMovementByDate(initialDate, finalDate, customerName);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(1, Objects.requireNonNull(response.getBody()).getData().size());
    Assertions.assertEquals(reportDto, Objects.requireNonNull(response.getBody()).getData().get(0));
  }

}
