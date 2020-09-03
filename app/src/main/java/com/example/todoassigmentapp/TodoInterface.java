package com.example.todoassigmentapp;

import com.example.todoassigmentapp.Model.TodoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TodoInterface {
    @GET("todos/")
    Call<List<TodoModel>> LoadTodoList();//Id is BusinessID
}
