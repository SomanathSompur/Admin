package com.softwiz.admin.repository;

import com.softwiz.admin.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByReturnedFalse();                       //Query to get ongoing loans
    List<Loan> findByStatus(Loan.LoanStatus status);        //Query to get oan by status
}
