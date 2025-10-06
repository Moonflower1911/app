/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.enumeration;

import java.util.Arrays;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 12 Jun 2025</p>
 */
public enum AmenityEnum {
    ALL_DAY_CHECKIN(1),
    AIR_CONDITIONING(2),
    BATHTUB(3),
    BEACH_VIEW(4),
    BUSINESS_SERVICES(5),
    COFFEE_MAKER(6),
    DESK(7),
    DRY_CLEANING(8),
    FITNESS_CENTER(9),
    FREE_INTERNET(10),
    FREEZER(11),
    GARDEN(12),
    HAIR_DRYER(13),
    HOT_TUB(14),
    INDOOR_POOL(15),
    LAKE_FRONT(16),
    LIFT(17),
    MEALS_INCLUDED(18),
    MOUNTAIN_VIEW(19),
    OCEAN_VIEW(20),
    PAID_PARKING(21),
    PRIVATE_BEACH_AREA(22),
    ROOM_WITH_A_VIEW(23),
    SHARED_WASHERS(24),
    SKI_IN_SKI_OUT(25),
    SMOKE_ALARMS(26),
    SWIMMING_POOL(27),
    TOWN(28),
    WASHING_MACHINE(29),
    WHEELCHAIR_ACCESS(30),
    ACTIVITIES_OLDER_CHILDREN(31),
    ALL_INCLUSIVE(32),
    BBQ_PICNIC_AREA(33),
    BED_AND_BREAKFAST(34),
    CABLE_TELEVISION(35),
    CONCIERGE(36),
    DISHWASHER(37),
    DRYER(38),
    FREE_BREAKFAST(39),
    FREE_LOCAL_CALLS(40),
    FRIDGE(41),
    GOLF_COURSE_FRONT(42),
    HANGERS(43),
    HOUSEKEEPING(44),
    IRONING_BOARD(45),
    LAKE_VIEW(46),
    LOCKERS_STORAGE(47),
    MEETING_ROOM(48),
    NEAR_OCEAN(49),
    OUTDOOR_DINING_AREA(50),
    PAID_PARKING_ON_PREMISES(51),
    RESORT(52),
    RURAL(53),
    SHOWER(54),
    SKI_OUT(55),
    SOFA_BED(56),
    TELEVISION(57),
    VALET_PARKING(58),
    WATER_FRONT(59),
    ACTIVITIES_YOUNG_CHILDREN(60),
    BABY_COAT(61),
    BEACH(62),
    BICYCLE(63),
    CAR_RENTAL(64),
    CREDIT_CARDS_ACCEPTED(65),
    DOORMAN(66),
    EARLY_ARRIVAL(67),
    FREE_CANCELATION(68),
    FREE_PARKING(69),
    GAME_ROOM(70),
    GOLF_COURSE_VIEW(71),
    HEATING(72),
    IN_PERSON_CHECK_IN(73),
    KIDS_ACTIVITIES(74),
    LATE_ARRIVAL(75),
    LOYALTY_REWARDS_AVAILABLE(76),
    MICROWAVE(77),
    NON_SMOKING(78),
    OUTDOOR_FURNITURE(79),
    PARKING_AVAILABLE(80),
    RIVER(81),
    SHAMPOO(82),
    SHUTTLE(83),
    SMART_TV(84),
    STAIRS_ELEVATOR(85),
    TENNIS_COURT(86),
    VILLAGE(87),
    WATER_SPORTS(88),
    ADJOINING_ROOMS(89),
    BATH(90),
    BEACH_FRONT(91),
    BREAKFAST(92),
    CLOTHES_IRON(93),
    DEDICATED_WORKSPACE(94),
    DOWNTOWN(95),
    ELDER_ACCESS(96),
    FREE_HOUSEKEEPING(97),
    FREE_WIFI(98),
    GAMES(99),
    GYM(100),
    HIGH_CHAIR(101),
    INDOOR_FIREPLACE(102),
    LAKE(103),
    LATE_CHECK_OUT(104),
    MASSAGE(105),
    MOUNTAIN(106),
    OCEAN_FRONT(107),
    OVEN(108),
    PETS_ALLOWED(109),
    ROOM_SERVICE(110),
    SHARED_DRYER(111),
    SKI_IN(112),
    SMART_LOCK(113),
    STROLLER_PARKING(114),
    TERRACE(115),
    WASHER(116),
    WATER_VIEW(117);
    private final int code;

    AmenityEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AmenityEnum fromCode(int code) {
        return Arrays.stream(values()).filter(a -> a.code == code).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown code " + code));
    }

}
