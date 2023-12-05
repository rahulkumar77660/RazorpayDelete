package com.example.razorpaydeleteupdate

data class Item(
    val active: Boolean,
    val amount: Int,
    val created_at: Int,
    val currency: String,
    val description: String,
    val hsn_code: Any,
    val id: String,
    val name: String,
    val sac_code: Any,
    val tax_group_id: Any,
    val tax_id: Any,
    val tax_inclusive: Boolean,
    val tax_rate: Any,
    val type: String,
    val unit: Any,
    val unit_amount: Int
)