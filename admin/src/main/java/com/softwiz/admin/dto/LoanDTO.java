package com.softwiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LoanDTO {

    private LocalDate issueDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private Boolean returned;               //  private Status loanStatus;

    private Double lateFee;

    /*  @Enumerated(EnumType.STRING)
        private Loan.LoanStatus status;
        public enum LoanStatus{
        ONGOING, COMPLETED, CANCELLED
       }  */

    @Override
    public String toString() {
        return "LoanDTO{" +
                "issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", returned=" + returned +
                ", lateFee=" + lateFee +
                '}';
    }
}