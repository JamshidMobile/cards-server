package jtoir.uz.lib.data.model

import jtoir.uz.lib.utils.Constants

enum class RoleModel {
    ADMIN,MODERATOR,CLIENT
}

fun String.getRoleByString(): RoleModel{
    return when(this){
        Constants.Role.ADMIN -> RoleModel.ADMIN
        Constants.Role.MODERATOR -> RoleModel.MODERATOR
        else -> {RoleModel.CLIENT}
    }
}

fun RoleModel.getStringByRole(): String{
    return when(this){
        RoleModel.ADMIN -> Constants.Role.ADMIN
        RoleModel.MODERATOR -> Constants.Role.MODERATOR
        else -> {
            Constants.Role.CLIENT
        }
    }
}