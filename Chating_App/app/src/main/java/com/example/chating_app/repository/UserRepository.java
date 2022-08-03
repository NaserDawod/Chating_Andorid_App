package com.example.chating_app.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.chating_app.Data.AppDB;
import com.example.chating_app.Data.Contact;
import com.example.chating_app.Data.UserDao;
import com.example.chating_app.Data.UserWithContacts;
import com.example.chating_app.MyApplication;
import com.example.chating_app.User_Token;
import com.example.chating_app.api.ContactsAPI;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private ContactsAPI api;
    private ContactListData contactListData;
    private static UserRepository repository;

    public UserRepository(){
        AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, User_Token.username)
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        api = new ContactsAPI();
        contactListData = new ContactListData();
    }

    public static UserRepository getInstance() {
        if (repository == null) {
            repository = new UserRepository();
        }
        return repository;
    }

    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData(){
            super();
            UserWithContacts conts = userDao.getContacts(User_Token.username);
            if (conts != null) {
                setValue(conts.contacts);
            }
            new Thread(() -> {
                api.getContacts(User_Token.token, contactListData);
            }).start();
        }
    }
    public MutableLiveData<List<Contact>> getAll(){
        return contactListData;
    }

    public void addContact(String username, String name, String server){
        if (userDao.getContact(username) != null) { return; }
        Contact cont = new Contact(username, name, User_Token.username, server, null, null);
        List<Contact> contacts = contactListData.getValue();
        contacts.add(cont);
        contactListData.setValue(contacts);
        userDao.insertContact(cont);
        Runnable runnable = () -> api.addContact(username, name, server, User_Token.token);
        new Thread(runnable).start();
//        api.addContact(username, name, server, User_Token.token);
    }

    public void addAndroid(String username, String token) {
        api.addAndroid(username, token);
    }

    public void refresh() {
        new Thread(() -> {
            api.getContacts(User_Token.token, contactListData);
        }).start();
    }
}
