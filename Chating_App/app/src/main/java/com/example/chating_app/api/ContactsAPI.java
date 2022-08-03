package com.example.chating_app.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.chating_app.Data.AppDB;
import com.example.chating_app.Data.Contact;
import com.example.chating_app.Data.Message;
import com.example.chating_app.Data.UserDao;
import com.example.chating_app.MyApplication;
import com.example.chating_app.R;
import com.example.chating_app.User_Token;
import com.example.chating_app.api_entities.Api_Contact;
import com.example.chating_app.api_entities.Api_Device;
import com.example.chating_app.api_entities.Api_User;
import com.example.chating_app.api_entities.Temp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {

    Retrofit retrofit;
    WebAPI webServiceAPI;
//    MutableLiveData<String> token2;
    public ContactsAPI() {

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebAPI.class);
    }

    public void login(String username, String password, MutableLiveData<String> token) {
        Api_User temp = new Api_User(username, password);
        Call<String> call = webServiceAPI.login(temp);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                token.setValue(response.body());
                Log.i("ContactsAPI", "hi");
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void register(String username, String name, String password, MutableLiveData<String> token) {
        Api_User temp = new Api_User(username, name, password);
        Call<String> call = webServiceAPI.register(temp);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                token.setValue(response.body());
                Log.i("ContactsAPI", token.getValue());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void getContacts(String token, MutableLiveData<List<Contact>> contacts) {
        Call<List<Contact>> call = webServiceAPI.getContacts(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts2 = response.body();
                contacts.setValue(response.body());
                AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, User_Token.username)
                        .allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                if (contacts2 != null) {
                    for (Contact c : contacts2) {
                        if (userDao.getContact(c.contname) == null) {
                            userDao.insertContact(c);
                        } else {
                            userDao.updateContact(c);
                        }
                    }
                }
                Log.i("ContactsAPI", token);
            }
            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });
    }

    public void addContact(String userID, String name, String server, String token) {
        Api_Contact cont  = new Api_Contact(userID, name, server);
        Call<String> call =  webServiceAPI.addContact(cont, token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ret = response.body();
                Log.i("ContactsAPI", token);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void getContact(String ContID, String token) {
        Call<Contact> call = webServiceAPI.getContact(ContID, token);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                Contact contact = response.body();
                Log.i("ContactsAPI", token);
            }
            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
            }
        });
    }

    public void getMessages(String ContID, MutableLiveData<List<Message>> msg, String token) {
        Call<List<Message>> call = webServiceAPI.getMessages(ContID, token);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                msg.setValue(messages);
                Log.i("ContactsAPI", token);
            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    public void addMessage(String contID,String content, String token) {
        Temp cont = new Temp(content);
        Call<String> call =  webServiceAPI.addMessage(contID, cont, token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ret = response.body();
                Log.i("ContactsAPI", token);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void addAndroid(String username, String token) {
        Api_Device dev = new Api_Device(username, token);
        Call<String> call =  webServiceAPI.addAndroid(dev);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ret = response.body();
                Log.i("ContactsAPI", token);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}
