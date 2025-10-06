package com.smsmode.invoice.model;

import com.smsmode.invoice.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "N_POSTING_ACCOUNTS")
public class PostingAccountModel extends AbstractBaseModel {

    @NotBlank
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Code must contain only letters and numbers (no spaces or special characters).")
    @Column(name = "CODE", nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_CLASS_ID", nullable = false)
    private AccountClassModel accountClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEDGER_GROUP_ID")
    private LedgerGroupModel ledgerGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_LEDGER_SUBGROUP_ID")
    private LedgerGroupModel subLedgerGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VAT_RULE_ID")
    private VatRuleModel vatRule;

}
