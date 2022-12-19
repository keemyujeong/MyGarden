package com.kyjsoft.tp09plant

class SearchFragmentRecyclerItem {
    @JvmField
    var title: String? = null
    @JvmField
    var imgUrl: String? = null

    constructor(title: String?, imgUrl: String?) {
        this.title = title
        this.imgUrl = imgUrl
    }

    constructor() {}
}