package com.example.sbs.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {
    private List<Todo> todos;
    private View.OnClickListener onBtnDeleteClicked;
    private View.OnClickListener onBtnDetailClicked;

    private View.OnClickListener onBtnShowModifyClicked;
    private View.OnClickListener onBtnModifyClicked;
    private View.OnClickListener onBtnCancelModifyClicked;

    public TodoAdapter(List<Todo> todos, View.OnClickListener onBtnDeleteClicked, View.OnClickListener onBtnDetailClicked, View.OnClickListener onBtnShowModifyClicked, View.OnClickListener onBtnModifyClicked, View.OnClickListener onBtnCancelModifyClicked) {
        this.todos = todos;
        this.onBtnDeleteClicked = onBtnDeleteClicked;
        this.onBtnDetailClicked = onBtnDetailClicked;

        this.onBtnShowModifyClicked = onBtnShowModifyClicked;
        this.onBtnModifyClicked = onBtnModifyClicked;
        this.onBtnCancelModifyClicked = onBtnCancelModifyClicked;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int i) {
        Log.d("A1", "getItem");
        return todos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return todos.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.textViewId = convertView.findViewById(R.id.item_todo__textViewId);
            viewHolder.textViewTitle = convertView.findViewById(R.id.item_todo__textViewTitle);
            viewHolder.btnDelete = convertView.findViewById(R.id.item_todo__btnDelete);
            viewHolder.btnDelete.setOnClickListener(onBtnDeleteClicked);

            viewHolder.btnDetail = convertView.findViewById(R.id.item_todo__btnDetail);
            viewHolder.btnDetail.setOnClickListener(onBtnDetailClicked);
            viewHolder.textViewId.setOnClickListener(onBtnDetailClicked);
            viewHolder.textViewTitle.setOnClickListener(onBtnDetailClicked);

            viewHolder.btnShowModify = convertView.findViewById(R.id.item_todo__btnShowModify);
            viewHolder.btnShowModify.setOnClickListener(onBtnShowModifyClicked);

            viewHolder.btnModify = convertView.findViewById(R.id.item_todo__btnModify);
            viewHolder.btnModify.setOnClickListener(onBtnModifyClicked);

            viewHolder.btnCancelModify = convertView.findViewById(R.id.item_todo__btnCancelModify);
            viewHolder.btnCancelModify.setOnClickListener(onBtnCancelModifyClicked);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Todo todo = todos.get(position);

        viewHolder.textViewId.setText(todo.getId() + "");
        viewHolder.textViewTitle.setText(todo.getTitle());

        viewHolder.btnDelete.setTag(position);

        viewHolder.btnDetail.setTag(position);
        viewHolder.textViewId.setTag(position);
        viewHolder.textViewTitle.setTag(position);

        viewHolder.btnShowModify.setTag(position);
        viewHolder.btnModify.setTag(position);
        viewHolder.btnCancelModify.setTag(position);

        return convertView;
    }

    static class ViewHolder {
        TextView textViewId;
        TextView textViewTitle;
        TextView btnDelete;
        TextView btnDetail;

        TextView btnShowModify;
        TextView btnModify;
        TextView btnCancelModify;
    }
}