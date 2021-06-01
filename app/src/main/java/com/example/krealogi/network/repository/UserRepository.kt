package com.example.krealogi.network.repository

import com.example.krealogi.datamodel.UserModel
import com.example.krealogi.network.database.DatabaseRequestManagers
import com.example.krealogi.network.database.UserTable

class UserRepository(private val userService: UserService,
                     private val room: DatabaseRequestManagers) {

    suspend fun getUserGithub(req: UserRequest): ArrayList<UserModel> =
        userService.getUsers(req)

    suspend fun searchUserGithub(req: SearchRequest): SearchResponse =
            userService.searchUsers(req)

    suspend fun searchHistory() : List<UserTable> = room.getDaoUser().getList()

    suspend fun deleteHistoryByID(id: Int){
        return room.getDaoUser().deleteById(id)
    }

    suspend fun insertHistory(item: UserTable) {
        return room.getDaoUser().insert(item)
    }

    suspend fun countHistory() : Int {
        return room.getDaoUser().getRowCount()

    }

}