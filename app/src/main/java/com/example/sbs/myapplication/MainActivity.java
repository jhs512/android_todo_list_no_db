package com.example.sbs.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText editTextTodo;
    private List<Todo> todos;
    private int todosLastId;
    private TodoAdapter todoAdapter;

    private void addTodo(String newTodoTitle) {
        Todo newTodo = new Todo(++todosLastId, newTodoTitle, false);
        todos.add(0, newTodo);
        todoAdapter.notifyDataSetChanged();
    }

    private void deleteTodo(int index) {
        todos.remove(index);
        todoAdapter.notifyDataSetChanged();
    }

    private void deleteAllTodos() {
        todos.clear();
        todoAdapter.notifyDataSetChanged();
    }

    private void makeTestData() {
        for (int i = 0; i < 2; i++) {
            addTodo("할일 " + (i + 1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("할일 리스트");

        todos = new ArrayList<>();
        todosLastId = 0;

        editTextTodo = findViewById(R.id.activity_main__editTextTodo);

        ListView listViewTodo = findViewById(R.id.main_activity__listViewTodo);

        View.OnClickListener onBtnDeleteClicked = view -> {
            final int indexToDelete = (int) view.getTag();

            DialogInterface.OnClickListener onClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteTodo(indexToDelete);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            };

            new AlertDialog.Builder(this)
                    .setMessage("삭제하시겠습니까?")
                    .setPositiveButton("예", onClickListener)
                    .setNegativeButton("아니오", onClickListener)
                    .show();
        };

        View.OnClickListener onBtnDetailClicked = view -> {
            final int indexToDetail = (int) view.getTag();
            Intent intent = new Intent(this, DetailActivity.class);
            Todo todo = todos.get(indexToDetail);
            intent.putExtra("todoId", todo.getId());
            intent.putExtra("todoTitle", todo.getTitle());
            startActivity(intent);
        };

        View.OnClickListener onBtnShowModifyClicked = view -> {
            int articleIndex = (int)view.getTag();
            todos.get(articleIndex).setView__modifyMode(true);

            View itemView = (View) view.getParent();

            TextView textViewTitle = itemView.findViewById(R.id.item_todo__textViewTitle);
            textViewTitle.setVisibility(View.GONE);

            itemView.findViewById(R.id.item_todo__btnShowModify).setVisibility(View.GONE);
            itemView.findViewById(R.id.item_todo__btnDelete).setVisibility(View.GONE);
            itemView.findViewById(R.id.item_todo__btnDetail).setVisibility(View.GONE);

            EditText editTextTitle = itemView.findViewById(R.id.item_todo__editTextTitle);
            editTextTitle.setText(textViewTitle.getText().toString().trim());
            editTextTitle.setVisibility(View.VISIBLE);

            itemView.findViewById(R.id.item_todo__btnCancelModify).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.item_todo__btnModify).setVisibility(View.VISIBLE);
        };

        View.OnClickListener onBtnModifyClicked = view -> {
            int todoIndex = (int)view.getTag();
            todos.get(todoIndex).setView__modifyMode(false);

            View itemView = (View) view.getParent();

            TextView textViewTitle = itemView.findViewById(R.id.item_todo__textViewTitle);
            EditText editTextTitle = itemView.findViewById(R.id.item_todo__editTextTitle);

            if ( editTextTitle.getText().toString().trim().length() == 0 ) {
                Toast.makeText(this, "할일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                editTextTitle.requestFocus();

                return;
            }

            String newTitle = editTextTitle.getText().toString().trim();

            textViewTitle.setText(newTitle);
            todos.get(todoIndex).setTitle(newTitle);

            textViewTitle.setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.item_todo__btnShowModify).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.item_todo__btnDelete).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.item_todo__btnDetail).setVisibility(View.VISIBLE);

            editTextTitle.setVisibility(View.GONE);
            itemView.findViewById(R.id.item_todo__btnCancelModify).setVisibility(View.GONE);
            itemView.findViewById(R.id.item_todo__btnModify).setVisibility(View.GONE);
        };

        View.OnClickListener onBtnCancelModifyClicked = view -> {
            int articleIndex = (int)view.getTag();
            todos.get(articleIndex).setView__modifyMode(false);

            View itemView = (View) view.getParent();

            itemView.findViewById(R.id.item_todo__textViewTitle).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.item_todo__btnShowModify).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.item_todo__btnDelete).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.item_todo__btnDetail).setVisibility(View.VISIBLE);

            itemView.findViewById(R.id.item_todo__editTextTitle).setVisibility(View.GONE);
            itemView.findViewById(R.id.item_todo__btnCancelModify).setVisibility(View.GONE);
            itemView.findViewById(R.id.item_todo__btnModify).setVisibility(View.GONE);
        };

        todoAdapter = new TodoAdapter(todos, onBtnDeleteClicked, onBtnDetailClicked, onBtnShowModifyClicked, onBtnModifyClicked, onBtnCancelModifyClicked);

        listViewTodo.setAdapter(todoAdapter);

        makeTestData(); // 임시
    }

    public void btnAddTodoClicked(View view) {
        String newTodo = editTextTodo.getText().toString().trim();
        editTextTodo.setText(newTodo);

        if (newTodo.length() == 0) {
            Toast.makeText(this, "할일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            editTextTodo.requestFocus();

            return;
        }

        addTodo(newTodo);

        editTextTodo.setText("");
        editTextTodo.requestFocus();
    }

    public void btnDeleteAllTodosClicked(View view) {
        if (todos.size() == 0) {
            Toast.makeText(this, "할일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        DialogInterface.OnClickListener onClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    deleteAllTodos();
                    Toast.makeText(this, "모든 할일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        new AlertDialog.Builder(this)
                .setMessage("정말 전부 삭제하시겠습니까?")
                .setPositiveButton("예", onClickListener)
                .setNegativeButton("아니오", onClickListener)
                .show();
    }

    public void btnFinishAppClicked(View view) {
        finish();
    }
}