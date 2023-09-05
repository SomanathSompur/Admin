package com.softwiz.admin.service;

import com.softwiz.admin.dto.LoanDTO;
import com.softwiz.admin.entity.Loan;
import com.softwiz.admin.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLoanService {
    @Autowired
    private LoanRepository loanRepository;
    public List<Loan> getAllOngoingLoans()  {
        return loanRepository.findByReturnedFalse();
    }

    public List<Loan> getAllPastLoans()  {
        return loanRepository.findByStatus(Loan.LoanStatus.PAST);
    }

    /*Create loan Details*/
    public Loan saveLoan(LoanDTO loanDTO) {
        Loan loanDetails = new Loan();
        loanDetails.setIssueDate(loanDTO.getIssueDate());
        loanDetails.setDueDate(loanDTO.getDueDate());
        loanDetails.setReturned(loanDTO.getReturned());
        loanDetails.setReturnDate(loanDTO.getReturnDate());
//      loanDetails.setStatus(loanDTO.getStatus());
        return loanRepository.save(loanDetails);
    }

}
