package com.smsmode.task.dao.specification;

import com.smsmode.task.model.CategoryModel;
import com.smsmode.task.model.CategoryModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class CategorySpecification {
    public static Specification<CategoryModel> withIdEqual(String categoryId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(CategoryModel_.id), categoryId);
    }

    public static Specification<CategoryModel> withNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(name) ? criteriaBuilder.conjunction() : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(CategoryModel_.name)),
                        "%" + name.toLowerCase() + "%"
                );
    }
}
