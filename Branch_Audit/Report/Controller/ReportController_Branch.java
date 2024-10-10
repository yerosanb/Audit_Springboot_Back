package com.afr.fms.Branch_Audit.Report.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.Report.Model.ReportBranchRequest;
import com.afr.fms.Branch_Audit.Report.Service.ReportService_Branch;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/report/")
public class ReportController_Branch {

	@Autowired
	ReportService_Branch reportService;

	@PostMapping("fetchReport")
	public ResponseEntity<?> fetchReportAtmCard(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportAtmCard(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getBranches")
	public ResponseEntity<?> getBranches() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.getBranches());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getRegions")
	public ResponseEntity<?> getRegions() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.getRegions());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @PostMapping("getFindings")
	// public ResponseEntity<?> getFindings(@RequestBody String type) {
	// try {
	// return ResponseEntity
	// .status(HttpStatus.OK)
	// .body(reportService.getFindings(type));
	// } catch (Exception e) {
	// e.printStackTrace();
	// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	// }

	// @PostMapping("getFindings")
	// public ResponseEntity<?> getFindings(
	// @RequestBody String type
	// ) {
	// try {
	// return ResponseEntity
	// .status(HttpStatus.OK)
	// .body(reportService.getFindings(type));
	// } catch (Exception e) {
	// e.printStackTrace();
	// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	// }

	@GetMapping("getDiscrepancies")
	public ResponseEntity<?> getDiscrepancies() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.getDiscrepancies());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getControllableExpenseTypes")
	public ResponseEntity<?> getControllableExpenseTypes() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.getControllableExpenseTypes());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getSuspenseAccountTypeOptions")
	public ResponseEntity<?> getSuspenseAccountTypeOptions() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.getSuspenseAccountTypeOptions());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("getFindings")
	public ResponseEntity<?> getFindings(@RequestBody String type) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.getFindings(type));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportAbnormalBalance")
	public ResponseEntity<?> fetchReportAbnormalBalance(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportAbnormalBalance(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//////////////////////

	@PostMapping("fetchReportIncompleteDocument")
	public ResponseEntity<?> fetchReportIncompleteDocument(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(reportService.fetchReportIncompleteDocument(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportControllableExpense")
	public ResponseEntity<?> fetchReportControllableExpense(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(reportService.fetchReportControllableExpense(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportAssetLiability")
	public ResponseEntity<?> fetchReportAssetLiability(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportAssetLiability(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportDormantInactiveAccount")
	public ResponseEntity<?> fetchReportDormantInactiveAccount(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(reportService.fetchReportDormantInactiveAccount(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportSuspenseAccount")
	public ResponseEntity<?> fetchReportSuspenseAccount(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportSuspenseAccount(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportOperation")
	public ResponseEntity<?> fetchReportOperation(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportOperation(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PostMapping("fetchReportObservation")
	public ResponseEntity<?> fetchReportObservation(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportObservation(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("fetchReportMemorandum")
	public ResponseEntity<?> fetchReportMemorandum(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportMemorandum(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportNegotiable")
	public ResponseEntity<?> fetchReportNegotiable(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportNegotiable(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportLong")
	public ResponseEntity<?> fetchReportLong(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportLong(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportLoanAdvance")
	public ResponseEntity<?> fetchReportLoanAdvance(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportLoanAdvance(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportCashCount")
	public ResponseEntity<?> fetchReportCashCount(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportCashCount(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportCashManagement")
	public ResponseEntity<?> fetchReportCashManagement(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportCashManagement(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("fetchReportCashExcessOrShortage")
	public ResponseEntity<?> fetchReportCashExcessOrShortage(@RequestBody ReportBranchRequest report_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(reportService.fetchReportCashExcessOrShortage(report_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("check_check")
	public List<MonthlySalesData> fetchReportCashExcessOrShortage() {
		List<MonthlySalesData> salesDataList = new ArrayList<>();
		salesDataList.add(new MonthlySalesData("Jan", "Electronics", 120000));
		salesDataList.add(new MonthlySalesData("Jan", "Clothing", 80000));
		salesDataList.add(new MonthlySalesData("Jan", "Home Decor", 50000));

		salesDataList.add(new MonthlySalesData("Feb", "Electronics", 130000));
		salesDataList.add(new MonthlySalesData("Feb", "Clothing", 85000));
		salesDataList.add(new MonthlySalesData("Feb", "Home Decor", 52000));

		salesDataList.add(new MonthlySalesData("Mar", "Electronics", 125000));
		salesDataList.add(new MonthlySalesData("Mar", "Clothing", 82000));
		salesDataList.add(new MonthlySalesData("Mar", "Home Decor", 51000));

		salesDataList.add(new MonthlySalesData("Apr", "Electronics", 135000));
		salesDataList.add(new MonthlySalesData("Apr", "Clothing", 87000));
		salesDataList.add(new MonthlySalesData("Apr", "Home Decor", 53000));

		salesDataList.add(new MonthlySalesData("May", "Electronics", 140000));
		salesDataList.add(new MonthlySalesData("May", "Clothing", 90000));
		salesDataList.add(new MonthlySalesData("May", "Home Decor", 55000));

		salesDataList.add(new MonthlySalesData("Jun", "Electronics", 145000));
		salesDataList.add(new MonthlySalesData("Jun", "Clothing", 92000));
		salesDataList.add(new MonthlySalesData("Jun", "Home Decor", 56000));

		List<Mm2> mm2 = new ArrayList<>();

		return salesDataList;
		// try {
		// System.out.println("controller");
		// return
		// ResponseEntity.status(HttpStatus.OK).body(reportService.fetchReportCashExcessOrShortage(report_request));
		// } catch (Exception e) {
		// e.printStackTrace();
		// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		// }
	}

	private List<Mm2> mm2 = new ArrayList<>();

	@GetMapping("check_check2")
	public List<Mm2> fetchReportCashExcessOrShortage2() {
		return mm2;
	}

	private List<TimeSeriesData> timeSeriesData = new ArrayList<>();

	@GetMapping("timeseries")
	public List<TimeSeriesData> getContinuousTimeSeries() {
		return timeSeriesData;
	}

	@Scheduled(fixedRate = 1000) // Run every 1000 milliseconds (1 second)
	public void generateRandomData() {
		LocalDateTime timestamp = LocalDateTime.now();
		double value = random.nextDouble() * 100;

		timeSeriesData.add(new TimeSeriesData(timestamp, value));

		// Limit the data to keep the example simple
		if (timeSeriesData.size() > 100) {
			timeSeriesData.remove(0);
		}
	}

	@Scheduled(fixedRate = 1000) // Runs every 1000 milliseconds (1 second)
	public void updateValues() {
		// Clear the existing values
		mm2.clear();

		// Add new random values
		mm2.add(new Mm2("cat1", generateRandomNumber()));
		mm2.add(new Mm2("cat2", generateRandomNumber()));
		mm2.add(new Mm2("cat2", generateRandomNumber()));
		mm2.add(new Mm2("cat3", generateRandomNumber()));
	}

	private int generateRandomNumber() {
		// Generate a random number between 10 and 20
		return new Random().nextInt(11) + 10;
	}

	/////// ACTUAL DASHBOARD COMPONENTS ///////
	private List<TotalsForStatChart> statData = new ArrayList<>();
	private List<TotalsForTimeSeriesModuleSpecific> totalsForTimeSeriesModuleSpecific = new ArrayList<>();
	private List<UnrectifiedForAllModules> unrectifiedForAllModules = new ArrayList<>();
	private Random random = new Random();

	@GetMapping("getTotalsForStatChart")
	public List<TotalsForStatChart> getTotalsForStatChart() {
		return statData;
	}

	@GetMapping("getTotalsForTimeSeriesIs")
	public List<TotalsForTimeSeriesModuleSpecific> getTotalsForTimeSeriesIs() {
		return totalsForTimeSeriesModuleSpecific;
	}

	@GetMapping("getUnrectifiedForAllModules")
	public List<UnrectifiedForAllModules> getUnrectifiedForAllModules() {
		return unrectifiedForAllModules;
	}

	
	// @Scheduled(fixedRate = 1000)
	// public void generateRandomAuditData() {
	// statData.add(
	// new TotalsForStatChart(
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000));

	// totalsForTimeSeriesModuleSpecific.add(
	// new TotalsForTimeSeriesModuleSpecific(
	// LocalDateTime.now(),
	// new Random().nextDouble(1001) + 9000,
	// LocalDateTime.now(),
	// new Random().nextDouble(1001) + 8000,
	// LocalDateTime.now(),
	// new Random().nextDouble(1001) + 7000,
	// LocalDateTime.now(),
	// new Random().nextDouble(1001) + 6000));

	// unrectifiedForAllModules.clear();

	// unrectifiedForAllModules.add(
	// new UnrectifiedForAllModules("IS",
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000));
	// unrectifiedForAllModules.add(
	// new UnrectifiedForAllModules("INSPECTION",
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000));
	// unrectifiedForAllModules.add(
	// new UnrectifiedForAllModules("BRANCH FINANCIAL",
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000));
	// unrectifiedForAllModules.add(
	// new UnrectifiedForAllModules("MANAGEMENT",
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000,
	// new Random().nextDouble(10001) + 1000));

	// if (statData.size() > 10) {
	// statData.remove(0);
	// }
	// if (unrectifiedForAllModules.size() > 10) {
	// unrectifiedForAllModules.remove(0);
	// }
	// if (totalsForTimeSeriesModuleSpecific.size() > 120) {
	// totalsForTimeSeriesModuleSpecific.remove(0);
	// }
	// }
}
