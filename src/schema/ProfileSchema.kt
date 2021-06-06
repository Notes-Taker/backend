package com.notes.schema

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.notes.models.User
import com.notes.services.ProfileService

fun SchemaBuilder.profileSchema(profileService: ProfileService) {
    query("getProfile") {
        resolver { ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                profileService.getProfile(userId)
            } catch (e: Exception) {
                null
            }
        }
    }
}
