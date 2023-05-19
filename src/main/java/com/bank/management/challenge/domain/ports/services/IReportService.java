package com.bank.management.challenge.domain.ports.services;

import com.bank.management.challenge.domain.models.output.ReportDto;
import java.util.List;

/**
 * Service interface for the reports.
 *
 * @author jorge-arevalo
 */
public interface IReportService {

  List<ReportDto> findByMovementDateBetween(String initialDate, String finalDate,
      String customerName);

}
