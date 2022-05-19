package com.macluczak.todoapp

import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {

    @GET("/users")
    suspend fun getUsers(): Response<List<User>>

    @GET("/todos")
    suspend fun getTodos(): Response<List<Todo>>

    @GET("/comments")
    suspend fun  getComments(): Response<List<Comment>>

    @GET("/posts")
    suspend fun getPost(): Response<List<Post>>
}