package com.shang.datastore

import com.shang.model.ConfigurationBean

fun ConfigurationBean.toProto(): ConfigurationProto {
    return ConfigurationProto.newBuilder()
        .addAllChangeKeys(this.changeKeys)
        .setImages(this.images.toProto())
        .build()
}

fun ConfigurationBean.Images.toProto(): ConfigurationProto.Images {
    return ConfigurationProto.Images.newBuilder()
        .addAllBackdropSizes(this.backdropSizes)
        .setBaseUrl(this.baseUrl)
        .addAllLogoSizes(this.logoSizes)
        .addAllPosterSizes(this.posterSizes)
        .addAllProfileSizes(this.profileSizes)
        .setSecureBaseUrl(this.secureBaseUrl)
        .addAllStillSizes(this.stillSizes)
        .build()
}
