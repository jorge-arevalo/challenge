package com.bank.management.challenge.infrastructure.rest;

import com.bank.management.challenge.domain.models.output.ReportDto;
import com.bank.management.challenge.domain.ports.services.IReportService;
import com.bank.management.challenge.infrastructure.rest.output.FormatOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to manage the reports.
 *
 * @author jorge-arevalo
 */
@RestController
@Validated
@RequestMapping("/v1/reports")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Reports operations")
public class ReportController {

  private final IReportService reportService;

  @GetMapping
  @Operation(description = "Movements report by dates")
  public ResponseEntity<FormatOutput<List<ReportDto>>> findMovementByDate(
      @RequestParam(value = "initialDate") String initialDate,
      @RequestParam(value = "finalDate") String finalDate,
      @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName) {
    log.info("Find movements by dates");
    HttpStatus status = HttpStatus.OK;
    FormatOutput<List<ReportDto>> output = new FormatOutput<>();
    output.setData(reportService.findByMovementDateBetween(initialDate, finalDate, customerName));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

}
