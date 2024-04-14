package com.fairybow.caloriediary.tools

const val CM_IN_FACTOR = 2.54
const val FEET_IN_FACTOR = 12.0
const val FEET_CM_FACTOR = 30.48
const val KG_LB_FACTOR = 2.20462262
const val ST_KG_FACTOR = 6.35029317
const val ST_LB_FACTOR = 14
//const val KC_KJ_FACTOR = 4.184

const val AUSTRALIA = "AU"
const val CANADA = "CA"
const val EGYPT = "EG"
const val INDIA = "IN"
const val IRELAND = "IE"
const val LEBANON = "LB"
const val LIBERIA = "LR"
const val MYANMAR = "MM"
const val SOUTH_AFRICA = "ZA"
const val SOUTH_SUDAN = "SS"
const val SUDAN = "SD"
const val SYRIA = "SY"
const val UK = "GB"
const val US = "US"

const val UNIT_CM = "cm"
const val UNIT_FT = "ft"
const val UNIT_IN = "in"
const val UNIT_KG = "kg"
const val UNIT_LB = "lb"
const val UNIT_ST = "st"
const val UNIT_RATE_KC = "kcal/day"
//const val UNIT_RATE_KJ = "kJ/day"

const val MSJ_KG_FACTOR = 10.0
const val MSJ_CM_FACTOR = 6.25
const val MSJ_AGE_FACTOR = 5.0
const val MSJ_F_ADJ = -161.0
const val MSJ_M_ADJ = 5.0

const val DF_DOUBLE = "#,###.##"
const val DF_INT = "#,###"

fun usesImperial(countryCode: String): Boolean {
    return countryCode == LIBERIA ||
            countryCode == MYANMAR ||
            countryCode == US
}

fun usesImperialCommonly(countryCode: String): Boolean {
    return usesImperial(countryCode) ||
            countryCode == AUSTRALIA ||
            countryCode == CANADA ||
            countryCode == INDIA ||
            countryCode == IRELAND ||
            countryCode == SOUTH_AFRICA ||
            countryCode == UK
}
