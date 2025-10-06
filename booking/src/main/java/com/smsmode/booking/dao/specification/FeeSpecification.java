/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.dao.specification;

import com.smsmode.booking.model.BookingModel;
import com.smsmode.booking.model.BookingModel_;
import com.smsmode.booking.model.FeeModel;
import com.smsmode.booking.model.FeeModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Aug 2025</p>
 */
public class FeeSpecification {

    public static Specification<FeeModel> withBookingIdEqual(String bookingId) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(bookingId)) {
                return criteriaBuilder.conjunction();
            } else {
                Join<FeeModel, BookingModel> join = root.join(FeeModel_.booking);
                return criteriaBuilder.equal(join.get(BookingModel_.id), bookingId);
            }
        };
    }

    public static Specification<FeeModel> withBookingIdIn(Set<String> bookingIds) {
        return (root, query, criteriaBuilder) -> {
            if (bookingIds == null || bookingIds.isEmpty()) {
                return criteriaBuilder.conjunction(); // no filter
            } else {
                Join<FeeModel, BookingModel> join = root.join(FeeModel_.booking);
                return join.get(BookingModel_.id).in(bookingIds);
            }
        };
    }

    public static Specification<FeeModel> withIdEqual(String feeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(FeeModel_.id), feeId);
    }


}
