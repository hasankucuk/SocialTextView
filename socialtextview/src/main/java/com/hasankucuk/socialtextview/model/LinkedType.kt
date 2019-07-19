package com.hasankucuk.socialtextview.model

enum class LinkedType(private val value: Int) {
    HASHTAG(2),
    MENTION(4),
    URL(8),
    EMAIL(16),
    PHONE(32),
}