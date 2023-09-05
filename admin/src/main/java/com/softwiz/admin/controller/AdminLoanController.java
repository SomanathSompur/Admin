package com.softwiz.admin.controller;

import com.softwiz.admin.dto.LoanDTO;
import com.softwiz.admin.entity.Loan;
import com.softwiz.admin.service.AdminLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/loans")
public class AdminLoanController {
    @Autowired
    private AdminLoanService adminLoanService;

    @GetMapping("/loans/ongoing")
    public ResponseEntity<List<Loan>> getOngoingLoans(){
        List<Loan> ongoingLoans = adminLoanService.getAllOngoingLoans();
        return ResponseEntity.ok(ongoingLoans);
    }

    @GetMapping("/loans/past")
    public ResponseEntity<List<Loan>> getPastLoans(){
        List<Loan> pastLoans = adminLoanService.getAllPastLoans();
        return ResponseEntity.ok(pastLoans);
    }
    @PostMapping("/loanDetails")
    public Loan createBook(@RequestBody LoanDTO loanDTO) {

        return adminLoanService.saveLoan(loanDTO);
    }

}
