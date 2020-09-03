package com.example.todoassigmentapp;

import com.example.todoassigmentapp.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class PaginationTodo {
    List<TodoModel> todoModels;
    List<TodoModel> newtodoModels=new ArrayList<>();
    int totalPages,itemsPerpage,itemsRemaining,lastpageno,totalitems;
    public PaginationTodo(List<TodoModel> todoModels)
    {
        this.todoModels=todoModels;
        totalitems=todoModels.size();
        itemsPerpage=5;
        totalPages=totalitems/itemsPerpage;
        itemsRemaining=totalPages%totalitems;
        lastpageno=totalitems/itemsPerpage;

    }

    public List<TodoModel> GetTodoList(int currentPage)
    {
    newtodoModels.clear();
    int startItem=currentPage*itemsPerpage;
    if(currentPage==lastpageno && itemsRemaining>0)
    {
        for(int i=startItem;i<startItem+itemsRemaining;i++)
        {
            newtodoModels.add(todoModels.get(i));
        }


    }
    else
    {
        for(int i=startItem;i<startItem+itemsPerpage;i++)
        {
            newtodoModels.add(todoModels.get(i));
        }
    }
    return newtodoModels;

    }
}
