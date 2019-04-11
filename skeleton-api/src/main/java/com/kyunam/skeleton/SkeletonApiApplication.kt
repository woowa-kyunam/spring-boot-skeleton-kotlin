package com.kyunam.skeleton

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SkeletonApiApplication

fun main(args: Array<String>) {
    runApplication<SkeletonApiApplication>(*args)
}
