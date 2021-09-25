package com.lixan.fajardo.incomeandexpensetracker.data.models

data class User(
     val firstName: String,
     val createdOn: String,
     val email: String,
     val lastName: String,
     val id : String
) {
     companion object {
          fun empty() : User {
               return User(
                    "",
                    "",
                    "",
                    "",
                    ""
               )
          }
     }
}