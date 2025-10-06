/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.booking.dao.specification;

import com.smsmode.booking.embeddable.ContactEmbeddable_;
import com.smsmode.booking.enumeration.BookingStatusEnum;
import com.smsmode.booking.enumeration.BookingTypeEnum;
import com.smsmode.booking.model.BookingModel;
import com.smsmode.booking.model.BookingModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 24 Jul 2025</p>
 */
public class BookingSpecification {

    public static Specification<BookingModel> withIdEqual(String bookingId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(BookingModel_.id), bookingId);
    }

    public static Specification<BookingModel> withParentIdEqual(String bookingId) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(bookingId)) {
                return criteriaBuilder.conjunction();
            } else {
                Join<BookingModel, BookingModel> join = root.join(BookingModel_.parent);
                return criteriaBuilder.equal(join.get(BookingModel_.id), bookingId);
            }
        };
    }

    public static Specification<BookingModel> withReferenceLike(String reference) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(reference) ? criteriaBuilder.conjunction() : criteriaBuilder
                        .like(criteriaBuilder.lower(root.get(BookingModel_.reference)), "%" + reference + "%");
    }

    public static Specification<BookingModel> withContactNameLike(String contactName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(contactName) ? criteriaBuilder.conjunction() :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(BookingModel_.contact)
                                .get(ContactEmbeddable_.name)), "%" + contactName.toLowerCase() + "%");
    }

    public static Specification<BookingModel> withParentContactNameLike(String contactName) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(contactName)) {
                return cb.conjunction();
            }
            Join<BookingModel, BookingModel> parentJoin = root.join(BookingModel_.parent);
            return cb.like(cb.lower(parentJoin.get(BookingModel_.contact).get(ContactEmbeddable_.name)),
                    "%" + contactName.toLowerCase() + "%");
        };
    }

    public static Specification<BookingModel> withStatusesIn(BookingStatusEnum[] statuses) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(statuses) || statuses.length == 0) {
                return cb.conjunction();
            }
            return root.get(BookingModel_.status).in((Object[]) statuses);
        };
    }

    public static Specification<BookingModel> withTypesIn(BookingTypeEnum[] types) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(types) || types.length == 0) {
                return cb.conjunction();
            }
            return root.get(BookingModel_.type).in((Object[]) types);
        };
    }

    public static Specification<BookingModel> withDateRangeOverlap(LocalDate from, LocalDate to) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(from) || ObjectUtils.isEmpty(to)) {
                return cb.conjunction();
            }
            return cb.and(
                    cb.lessThanOrEqualTo(root.get(BookingModel_.checkinDate), to),
                    cb.greaterThanOrEqualTo(root.get(BookingModel_.checkoutDate), from)
            );
        };
    }

    public static Specification<BookingModel> withTypeEqual(BookingTypeEnum type) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(BookingModel_.type), type);
    }

    public static Specification<BookingModel> withCheckedInDateEqual(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(BookingModel_.checkinDate), date);
    }

    public static Specification<BookingModel> withCheckinDateLessThanOrEqual(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction(); // Always true if date is null
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("checkinDate"), date);
        };
    }

    public static Specification<BookingModel> withCheckOutDateEqual(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("checkoutDate"), date);
        };
    }

    public static Specification<BookingModel> withCheckinBeforeOrEqualAndCheckoutAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                    criteriaBuilder.lessThanOrEqualTo(root.get("checkinDate"), date),
                    criteriaBuilder.greaterThan(root.get("checkoutDate"), date)
            );
        };
    }

    public static Specification<BookingModel> withDateCreationToday(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(
                    root.get("createdAt"),
                    date.atStartOfDay(),              // start of today
                    date.plusDays(1).atStartOfDay()   // start of tomorrow (exclusive)
            );
        };
    }

}
