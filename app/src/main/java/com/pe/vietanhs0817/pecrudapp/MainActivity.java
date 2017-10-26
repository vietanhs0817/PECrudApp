package com.pe.vietanhs0817.pecrudapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pe.vietanhs0817.pecrudapp.adapter.UserAdapter;
import com.pe.vietanhs0817.pecrudapp.models.Response;
import com.pe.vietanhs0817.pecrudapp.models.User;
import com.pe.vietanhs0817.pecrudapp.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtName;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGetAll = (Button) findViewById(R.id.btn_get_all);
        Button btnFind = (Button) findViewById(R.id.btn_find);
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        Button btnUpdate = (Button) findViewById(R.id.btn_update);
        Button btnCreate = (Button) findViewById(R.id.btn_create);

        txtEmail =(EditText) findViewById(R.id.txt_email);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        txtName = (EditText) findViewById(R.id.txt_name);

        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<User> userList = new HttpRequestUserList().execute().get();
                    ListView listView = (ListView) findViewById(R.id.ls_data);
                    listView.setAdapter(new UserAdapter(MainActivity.this, userList));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if("".equals(txtEmail.getText().toString().trim())){
                        return;
                    }
                    List<User> userList = new ArrayList<User>();
                    User user = new HttpRequestFindUser().execute(txtEmail.getText().toString()).get();
                    if(user != null){
                        userList.add(user);
                    }else{
                        Toast.makeText(MainActivity.this,"User not found",Toast.LENGTH_SHORT).show();
                    }

                    ListView listView = (ListView) findViewById(R.id.ls_data);
                    listView.setAdapter(new UserAdapter(MainActivity.this, userList));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(txtPassword.getText().toString().trim()) || "".equals(txtEmail.getText().toString().trim()) || "".equals(txtName.getText().toString().trim())){
                    return;
                }
                try {

                    User user = new User();
                    user.setEmail(txtEmail.getText().toString().trim());
                    user.setFullName(txtName.getText().toString().trim());
                    user.setPassword(txtPassword.getText().toString().trim());
                    Response response = new HttpRequestCreateUser().execute(user).get();
                    if(response.isStatus()){
                        List<User> userList = new HttpRequestUserList().execute().get();
                        ListView listView = (ListView) findViewById(R.id.ls_data);
                        listView.setAdapter(new UserAdapter(MainActivity.this, userList));
                        Toast.makeText(MainActivity.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(txtPassword.getText().toString().trim()) || "".equals(txtEmail.getText().toString().trim()) || "".equals(txtName.getText().toString().trim())){
                    return;
                }
                try {
                    User user = new User();
                    user.setEmail(txtEmail.getText().toString().trim());
                    user.setFullName(txtName.getText().toString().trim());
                    user.setPassword(txtPassword.getText().toString().trim());
                    Response response = new HttpRequestUpdateUser().execute(user).get();
                    if(response.isStatus()){
                        List<User> userList = new HttpRequestUserList().execute().get();
                        ListView listView = (ListView) findViewById(R.id.ls_data);
                        listView.setAdapter(new UserAdapter(MainActivity.this, userList));
                        Toast.makeText(MainActivity.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if("".equals(txtEmail.getText().toString().trim())){
                        return;
                    }
                    Response response = new HttpRequestDeleteUser().execute(txtEmail.getText().toString().trim()).get();
                    if(response.isStatus()){
                        List<User> userList = new HttpRequestUserList().execute().get();
                        ListView listView = (ListView) findViewById(R.id.ls_data);
                        listView.setAdapter(new UserAdapter(MainActivity.this, userList));

                        Toast.makeText(MainActivity.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    private class HttpRequestDeleteUser extends AsyncTask<String, Void, Response> {

        @Override
        protected Response doInBackground(String... params) {
            UserModel userModel = new UserModel();
            return userModel.deleteUser(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
        }

    }

    private class HttpRequestUpdateUser extends AsyncTask<User, Void, Response> {

        @Override
        protected Response doInBackground(User... params) {
            UserModel userModel = new UserModel();
            return userModel.updateUser(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
        }

    }


    private class HttpRequestCreateUser extends AsyncTask<User, Void, Response> {

        @Override
        protected Response doInBackground(User... params) {
            UserModel userModel = new UserModel();
            return userModel.createUser(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
        }

    }


    private class HttpRequestFindUser extends AsyncTask<String, Void, User> {

        public HttpRequestFindUser(){
            progressDialog = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected User doInBackground(String... params) {
            UserModel userModel = new UserModel();
            System.out.println("EMAIL FIND: " + params[0]);
            return userModel.findUser(params[0]);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(User user) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }

    }


    private class HttpRequestUserList extends AsyncTask<Void, Void, List<User>> {

        public HttpRequestUserList(){
            progressDialog = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            UserModel userModel = new UserModel();
            return userModel.getUserList();
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(List<User> users) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }
}
