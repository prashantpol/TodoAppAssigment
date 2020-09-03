package com.example.todoassigmentapp.Filter;

import android.widget.Filter;

import com.example.todoassigmentapp.Adapter.TodoAdapter;
import com.example.todoassigmentapp.Model.TodoModel;

import java.util.ArrayList;
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


            if(charSequence != null && charSequence.length() > 0)
            {
                //CHANGE TO UPPER for case Insentive Search
                charSequence=charSequence.toString().toUpperCase();
                 List<TodoModel> filtereddata=new ArrayList<TodoModel>();
                 for (int i=0;i<todoModels.size();i++)
                {
                    charSequence=charSequence.toString().replace(" ","");
                    //Filter here and create new list
                    if(((todoModels.get(i).getTitle().replace(" ",""))).toUpperCase().contains(charSequence))
                    {
                        filtereddata.add(todoModels.get(i));
                    }

                }

                results.values=filtereddata;
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
        //REFRESH Recyclerview
        adapter.notifyDataSetChanged();
    }
}
