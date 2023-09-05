package com.softwiz.admin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private Boolean returned;          //  private Status loanStatus;

    private Double lateFee;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    public enum LoanStatus{
        ONGOING,
        PAST
    }
}