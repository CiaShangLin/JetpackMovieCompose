package com.shang.datastore.mapper

import com.shang.datastore.ConfigurationProto
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

fun ConfigurationProto.toModel(): ConfigurationBean {
    return ConfigurationBean(
        changeKeys = this.changeKeysList,
        images = this.images.toModel(),
    )
}

fun ConfigurationProto.Images.toModel(): ConfigurationBean.Images {
    return ConfigurationBean.Images(
        backdropSizes = this.backdropSizesList,
        baseUrl = this.baseUrl,
        logoSizes = this.logoSizesList,
        posterSizes = this.posterSizesList,
        profileSizes = this.profileSizesList,
        secureBaseUrl = this.secureBaseUrl,
        stillSizes = this.stillSizesList,
    )
}
