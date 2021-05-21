package com.pretty.library.tools.extend

fun Boolean.isTrue(block: Boolean.() -> Unit): Boolean {
    if (this)
        block.invoke(this)
    return this
}

fun Boolean.isFalse(block: Boolean.() -> Unit): Boolean {
    if (!this)
        block.invoke(this)
    return this
}