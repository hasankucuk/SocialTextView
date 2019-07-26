package com.hasankucuk.socialtextview.model


enum class LinkedType(val value: Int) {
    TEXT(0),
    HASHTAG(2),
    MENTION(4),
    URL(8),
    EMAIL(16),
    PHONE(32),
    HIGHLITH(64);

    companion object {
        fun getType(value: Int) = values().firstOrNull() { it.value == value } ?: TEXT
    }


}

