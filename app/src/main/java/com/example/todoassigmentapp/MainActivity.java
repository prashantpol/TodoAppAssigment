package com.example.todoassigmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.todoassigmentapp.Adapter.TodoAdapter;
import com.example.todoassigmentapp.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
     List<TodoModel> todoModelList;
    RecyclerView recyclerView;
    TodoAdapter todoAdapter;
    Button btnnext,btnprevious;
    RadioGroup radioGroup;
    RadioButton radall,radcomplete,radincomplete;
    int currentPage=0;
    int totalpages=0;
    int lastpage=0;
    PaginationTodo getitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview_todo);
        btnnext=findViewById(R.id.btnnext);
        btnprevious=findViewById(R.id.btnprevious);
        radall=findViewById(R.id.radall);
        radcomplete=findViewById(R.id.radcomplete);
        radincomplete=findViewById(R.id.radincomplete);
        radioGroup=findViewById(R.id.radiogrp);

        LoadTodo();

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentPage=currentPage+1;
                todoAdapter = new TodoAdapter(getitems.GetTodoList(currentPage), MainActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                recyclerView.setAdapter(todoAdapter);
                EnableDisableButton();

            }
        });


        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage=currentPage-1;
                todoAdapter = new TodoAdapter(getitems.GetTodoList(currentPage), MainActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                recyclerView.setAdapter(todoAdapter);
                EnableDisableButton();

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkdId) {
                if(checkdId==R.id.radall)
                {
                    currentPage=0;

                    generateDataList(todoModelList,0);
                }
                else if(checkdId==R.id.radcomplete)
                {
                    currentPage=0;

                    generateDataList(todoModelList,1);
                }
                else if(checkdId==R.id.radincomplete)
                {
                    currentPage=0;

                    generateDataList(todoModelList,2);
                }
            }
        });

    }
    public void  EnableDisableButton()
    {
        if(currentPage==0)
        {
            btnprevious.setEnabled(false);
            btnnext.setEnabled(true);
        }
        else if(currentPage==totalpages)
        {
            btnnext.setEnabled(false);
            btnprevious.setEnabled(true);
        }
        else if(currentPage>=1 && currentPage<=totalpages)
        {
            btnprevious.setEnabled(true);
            btnnext.setEnabled(true);
        }

    }


    public  void LoadTodo()
    {

        /*Create handle for the RetrofitInstance interface*/
        TodoInterface service= RetrofitTodoServices.getRetrofitInstance().create(TodoInterface.class);

        Call<List<TodoModel>> call = service.LoadTodoList();
        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                 if(response.body()!=null)
                {
                    todoModelList=response.body();
                    for(int i=0;i<todoModelList.size();i++)
                    {
                        todoModelList.get(i).setExpanded(false);
                    }

                    generateDataList(todoModelList,0);
                }

            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                 Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<TodoModel> todos,int checkedItem) {

        List<TodoModel> filteredTodos=new ArrayList<>();
        filteredTodos.clear();
        if(checkedItem==0) // If All RadioButton is selected
        {

            filteredTodos=todos;
        }
        else if(checkedItem==1) // If Complete RadioButton is selected
        {
            for(int i=0;i<=todos.size()-1;i++)
            {
                if(todos.get(i).getCompleted()==true)
                {
                    filteredTodos.add(todos.get(i));
                }
            }
        }
        else if(checkedItem==2) // If Incomplete RadioButton is selected
        {
            for(int i=0;i<=todos.size()-1;i++)
            {
                if(todos.get(i).getCompleted()==false)
                {
                    filteredTodos.add(todos.get(i));
                }
            }
        }


        getitems=new PaginationTodo(filteredTodos);
        totalpages=getitems.totalPages-1;
        todoAdapter = new TodoAdapter(getitems.GetTodoList(currentPage), this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(todoAdapter);

    }
}