package com.example.todoassigmentapp.Filter;

import android.widget.Filter;

import com.example.todoassigmentapp.Adapter.TodoAdapter;
import com.example.todoassigmentapp.Model.TodoModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FilterTodoList  extends Filter {

    List<TodoModel> todoModels;
    TodoAdapter adapter;
    public FilterTodoList(List<TodoModel> todoModels, TodoAdapter adapter)
    {
        this.todoModels=todoModels;
        this.adapter=adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(todoModels.size()>0)
        {

            //CHECK CONSTRAINT VALIDITY
            if(charSequence != null && charSequence.length() > 0)
            {
                //CHANGE TO UPPER
                charSequence=charSequence.toString().toUpperCase();
                 List<TodoModel> filtereddata=new ArrayList<TodoModel>();
                 for (int i=0;i<todoModels.size();i++)
                {
                    charSequence=charSequence.toString().replace(" ","");


                    //CHECK
                    if(((todoModels.get(i).getTitle().replace(" ","")).replace(".","")).replace("-","").toUpperCase().contains(charSequence))
                    {

                        filtereddata.add(todoModels.get(i));
                    }



                }

                HashSet<TodoModel> listToSet = new HashSet<TodoModel>(filtereddata);

                //Creating Arraylist without duplicate values
                List<TodoModel> listWithoutDuplicates = new ArrayList<TodoModel>(listToSet);

                results.count=listWithoutDuplicates.size();
                results.values=listWithoutDuplicates;
            }else
            {
                results.count=todoModels.size();
                results.values=todoModels;

            }

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.todoModelList= (List<TodoModel>) filterResults.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
