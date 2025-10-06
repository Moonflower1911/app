package com.smsmode.invoice.service.impl;

import com.smsmode.invoice.dao.service.AccountClassDaoService;
import com.smsmode.invoice.dao.specification.AccountClassSpecification;
import com.smsmode.invoice.enumeration.AccountClassEnumeration;
import com.smsmode.invoice.model.AccountClassModel;
import com.smsmode.invoice.service.DataLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderServiceImpl implements DataLoaderService, CommandLineRunner {

    private final AccountClassDaoService accountClassDaoService;

    @Override
    public void createSystemCriticalAccountClasses() {
        for (AccountClassEnumeration enumValue : AccountClassEnumeration.values()) {
            String displayName = enumValue.toLowerCaseName();
            log.debug("Will check if AccountClass <{}> exists in database ...", displayName);

            if (accountClassDaoService.existsBy(AccountClassSpecification.withNameEqual(displayName))) {
                log.info("AccountClass <{}> already exists", displayName);
            } else {
                log.info("AccountClass <{}> doesn't exist, will create it", displayName);
                AccountClassModel model = new AccountClassModel();
                model.setName(displayName);
                if (enumValue.equals(AccountClassEnumeration.REVENUE)) {
                    model.setDescription("Income generated from business operations including room revenue, service charges, and other operating income sources.");
                } else if (enumValue.equals(AccountClassEnumeration.TAX)) {
                    model.setDescription("For all taxes collected on behalf of government entities. This class is used for generating specific tax reports for financial remittance.");
                } else if (enumValue.equals(AccountClassEnumeration.PAYMENT)) {
                    model.setDescription("Transaction that settle a guest's bill, including deposits. This class covers cash, credit cards, and bank transfers.");
                }else if (enumValue.equals(AccountClassEnumeration.PACKAGE)) {
                    model.setDescription("Bundled income before it's broken down into its revenue components (e.g., room and breakfast). It has a net-zero financial effect and is used for internal auditing, often hidden from the guest's bill.");
                }
                model.setSystemCritical(true);

                model = accountClassDaoService.save(model);
                log.info("AccountClass <{}> created with Id: {}", displayName, model.getId());
            }
        }
    }

    @Override
    public void run(String... args) {
        this.createSystemCriticalAccountClasses();
    }
}
