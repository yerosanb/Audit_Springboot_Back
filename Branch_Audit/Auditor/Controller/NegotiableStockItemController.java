package com.afr.fms.Branch_Audit.Auditor.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.Auditor.Service.NegotiableStockItemService;
import com.afr.fms.Branch_Audit.Entity.NegotiableStockItem;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/auditor/negotiable_stock_item")
// @PreAuthorize("hasRole('COMPILER_BFA')")
public class NegotiableStockItemController {

	@Autowired
	private NegotiableStockItemService negotiableStockItemService;

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> createNegotiableStockItem(@RequestBody NegotiableStockItem negotiableStockItem,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "create_or_update_ISM_finding")) {
		try {
			if (negotiableStockItem.getId() != null) {
				negotiableStockItemService.updateNegotiableStockItemCategory(negotiableStockItem);
			} else {
				negotiableStockItemService.createNegotiableStockItem(negotiableStockItem);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }

	}

	@GetMapping("/negotiable_stock_item")
	public ResponseEntity<List<NegotiableStockItem>> getNegotiableStockItem(
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {
			return new ResponseEntity<>(negotiableStockItemService.getNegotiableStockItem(), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@GetMapping("/negotiable_stock_item/{category}")

	public ResponseEntity<List<NegotiableStockItem>> getNegotiableStockItemByCategory(@PathVariable String category,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {
			return new ResponseEntity<>(negotiableStockItemService.getNegotiableStockItemByCategory(category),
					HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/delete")
	public ResponseEntity<HttpStatus> deleteSingle(@RequestBody NegotiableStockItem negotiableStockItem,
			HttpServletRequest request) {
		try {
			negotiableStockItemService.deleteNegotiableStockItem(negotiableStockItem);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
