package com.smsmode.invoice.service.impl;

import com.smsmode.invoice.dao.service.AccountClassDaoService;
import com.smsmode.invoice.dao.service.LedgerGroupDaoService;
import com.smsmode.invoice.dao.service.PostingAccountsDaoService;
import com.smsmode.invoice.dao.service.VatRuleDaoService;
import com.smsmode.invoice.dao.specification.AccountClassSpecification;
import com.smsmode.invoice.dao.specification.LedgerGroupSpecification;
import com.smsmode.invoice.dao.specification.PostingAccountsSpecification;
import com.smsmode.invoice.dao.specification.VatRuleSpecification;
import com.smsmode.invoice.mapper.PostingAccountsMapper;
import com.smsmode.invoice.model.AccountClassModel;
import com.smsmode.invoice.model.LedgerGroupModel;
import com.smsmode.invoice.model.PostingAccountModel;
import com.smsmode.invoice.model.VatRuleModel;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsGetResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPatchResource;
import com.smsmode.invoice.resource.postingaccount.PostingAccountsPostResource;
import com.smsmode.invoice.service.PostingAccountsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingAccountsServiceImpl implements PostingAccountsService {

    private final PostingAccountsMapper postingAccountsMapper;
    private final PostingAccountsDaoService postingAccountsDaoService;
    private final AccountClassDaoService accountClassDaoService;
    private final LedgerGroupDaoService ledgerGroupDaoService;
    private final VatRuleDaoService vatRuleDaoService;

    @Override
    @Transactional
    public ResponseEntity<PostingAccountsGetResource> create(PostingAccountsPostResource postResource) {
        log.debug("Creating new PostingAccount with code {}", postResource.getCode());

        PostingAccountModel model = postingAccountsMapper.postResourceToModel(postResource);

        AccountClassModel accountClass = accountClassDaoService.findOneBy(
                AccountClassSpecification.withNameEqual("revenue"));
        model.setAccountClass(accountClass);

        if (!ObjectUtils.isEmpty(postResource.getLedgerGroupId())) {
            LedgerGroupModel group = ledgerGroupDaoService.findOneBy(
                    LedgerGroupSpecification.withIdEqual(postResource.getLedgerGroupId()));
            model.setLedgerGroup(group);
        }

        if (!ObjectUtils.isEmpty(postResource.getSubLedgerGroupId())) {
            LedgerGroupModel subgroup = ledgerGroupDaoService.findOneBy(
                    LedgerGroupSpecification.withIdEqual(postResource.getSubLedgerGroupId()));
            model.setSubLedgerGroup(subgroup);
        }

        if (!ObjectUtils.isEmpty(postResource.getVatRuleId())) {
            VatRuleModel vatRule = vatRuleDaoService.findOneBy(VatRuleSpecification.withIdEqual(postResource.getVatRuleId()));
            model.setVatRule(vatRule);
        }

        model = postingAccountsDaoService.save(model);
        PostingAccountsGetResource getResource = postingAccountsMapper.modelToGetResource(model);

        return ResponseEntity.created(URI.create("")).body(getResource);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PostingAccountsGetResource> retrieveById(String postingAccountId) {
        PostingAccountModel model =
                postingAccountsDaoService.findOneBy(PostingAccountsSpecification.withIdEqual(postingAccountId));
        return ResponseEntity.ok(postingAccountsMapper.modelToGetResource(model));
    }

    @Override
    public ResponseEntity<Page<PostingAccountsGetResource>> retrieveAllByPage(String search, String accountClass, Boolean enabled, Pageable pageable) {
        Specification<PostingAccountModel> specification = Specification
                .where(PostingAccountsSpecification.withNameLike(search).or(PostingAccountsSpecification.withCodeLike(search)))
                .and(PostingAccountsSpecification.withAccountClassEqual(accountClass))
                .and(PostingAccountsSpecification.withEnabled(enabled));

        Page<PostingAccountModel> postingAccounts = postingAccountsDaoService.findAllBy(specification, pageable);

        return ResponseEntity.ok(postingAccounts.map(postingAccountsMapper::modelToGetResource));
    }

    @Override
    @Transactional
    public ResponseEntity<PostingAccountsGetResource> updateById(String postingAccountId,
                                                                 PostingAccountsPatchResource patchResource) {
        PostingAccountModel model =
                postingAccountsDaoService.findOneBy(PostingAccountsSpecification.withIdEqual(postingAccountId));

        model = postingAccountsMapper.patchResourceToModel(patchResource, model);

        if (patchResource.getLedgerGroupId() != null) {
            if (!ObjectUtils.isEmpty(patchResource.getLedgerGroupId())) {
                LedgerGroupModel group = ledgerGroupDaoService.findOneBy(
                        LedgerGroupSpecification.withIdEqual(patchResource.getLedgerGroupId()));
                model.setLedgerGroup(group);
            } else {
                model.setLedgerGroup(null);
            }
        }

        if (patchResource.getSubLedgerGroupId() != null) {
            if (!ObjectUtils.isEmpty(patchResource.getSubLedgerGroupId())) {
                LedgerGroupModel subgroup = ledgerGroupDaoService.findOneBy(
                        LedgerGroupSpecification.withIdEqual(patchResource.getSubLedgerGroupId()));
                model.setSubLedgerGroup(subgroup);
            } else {
                model.setSubLedgerGroup(null);
            }
        }

        if (patchResource.getVatRuleId() != null) {
            if (!ObjectUtils.isEmpty(patchResource.getVatRuleId())) {
                VatRuleModel vatRule = vatRuleDaoService.findOneBy(VatRuleSpecification.withIdEqual(patchResource.getVatRuleId()));
                model.setVatRule(vatRule);
            } else {
                model.setVatRule(null);
            }
        }
        model = postingAccountsDaoService.save(model);
        return ResponseEntity.ok(postingAccountsMapper.modelToGetResource(model));
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeById(String postingAccountId) {
        PostingAccountModel model =
                postingAccountsDaoService.findOneBy(PostingAccountsSpecification.withIdEqual(postingAccountId));
        postingAccountsDaoService.delete(model);
        return ResponseEntity.noContent().build();
    }
}
