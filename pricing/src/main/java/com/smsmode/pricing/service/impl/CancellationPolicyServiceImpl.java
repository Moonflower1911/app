/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.service.impl;

import com.smsmode.pricing.dao.service.CancellationPolicyDaoService;
import com.smsmode.pricing.dao.specification.CancellationPolicySpecification;
import com.smsmode.pricing.mapper.CancellationPolicyMapper;
import com.smsmode.pricing.model.CancellationPolicyModel;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyGetResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPatchResource;
import com.smsmode.pricing.resource.cancellationpolicy.CancellationPolicyPostResource;
import com.smsmode.pricing.service.CancellationPolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 16 Sep 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CancellationPolicyServiceImpl implements CancellationPolicyService {

    private final CancellationPolicyDaoService cancellationPolicyDaoService;
    private final CancellationPolicyMapper cancellationPolicyMapper;

    @Override
    public ResponseEntity<Page<CancellationPolicyGetResource>> retrieveAll(String search, Boolean enabled, Pageable pageable) {

        Specification<CancellationPolicyModel> specification = Specification.where(
                        CancellationPolicySpecification.withNameLike(search)
                                .or(CancellationPolicySpecification.withCodeLike(search)))
                .and(CancellationPolicySpecification.withEnabled(enabled));

        Page<CancellationPolicyModel> cancellationPolicyModels = cancellationPolicyDaoService.findAll(specification, pageable);
        return ResponseEntity.ok(cancellationPolicyModels.map(cancellationPolicyMapper::modelToGetResource));
    }

    @Override
    public ResponseEntity<CancellationPolicyGetResource> retrieveById(String cancellationPolicyId) {
        CancellationPolicyModel cancellationPolicyModel = cancellationPolicyDaoService.findOneBy(CancellationPolicySpecification.withId(cancellationPolicyId));
        return ResponseEntity.ok(cancellationPolicyMapper.modelToGetResource(cancellationPolicyModel));
    }

    @Override
    public ResponseEntity<CancellationPolicyGetResource> create(CancellationPolicyPostResource cancellationPolicyPostResource) {
        CancellationPolicyModel cancellationPolicyModel = cancellationPolicyMapper.postResourceToModel(cancellationPolicyPostResource);
        cancellationPolicyModel = cancellationPolicyDaoService.save(cancellationPolicyModel);
        return ResponseEntity.created(URI.create("")).body(cancellationPolicyMapper.modelToGetResource(cancellationPolicyModel));
    }

    @Override
    public ResponseEntity<CancellationPolicyGetResource> updateById(String cancellationPolicyId, CancellationPolicyPatchResource cancellationPolicyPatchResource) {
        CancellationPolicyModel cancellationPolicyModel = cancellationPolicyDaoService.findOneBy(CancellationPolicySpecification.withId(cancellationPolicyId));
        cancellationPolicyModel = cancellationPolicyMapper.patchResourceToModel(cancellationPolicyPatchResource, cancellationPolicyModel);
        cancellationPolicyModel = cancellationPolicyDaoService.save(cancellationPolicyModel);
        return ResponseEntity.ok(cancellationPolicyMapper.modelToGetResource(cancellationPolicyModel));
    }

    @Override
    public ResponseEntity<Void> removeById(String cancellationPolicyId) {
        cancellationPolicyDaoService.deleteBy(CancellationPolicySpecification.withId(cancellationPolicyId));
        return ResponseEntity.noContent().build();
    }
}
