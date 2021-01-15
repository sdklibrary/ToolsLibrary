package com.pretty.library.tools.extend

import com.google.android.material.tabs.TabLayout

fun TabLayout.onSelectState(tabSelect: MyTabLayoutSelect.() -> Unit) {
    tabSelect.invoke(MyTabLayoutSelect(this))
}

class MyTabLayoutSelect(tab: TabLayout) {

    private var tabReselected: (TabLayout.Tab.() -> Unit)? = null

    private var tabUnselected: (TabLayout.Tab.() -> Unit)? = null

    private var tabSelected: (TabLayout.Tab.() -> Unit)? = null

    fun onTabReselected(reselected: (TabLayout.Tab.() -> Unit)) {
        this.tabReselected = reselected
    }

    fun onTabUnselected(unselected: (TabLayout.Tab.() -> Unit)) {
        this.tabUnselected = unselected
    }

    fun onTabSelected(selected: (TabLayout.Tab.() -> Unit)) {
        this.tabSelected = selected
    }

    init {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab) {
                tabReselected?.invoke(p0)
            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
                tabUnselected?.invoke(p0)
            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                tabSelected?.invoke(p0)
            }

        })
    }
}