package com.example.proyectosegundo.models;

import java.util.List;

public class User {

    private String fullName;
    private String email;
    private String phone;
    private String address;
    private List<String> favoritos;  // Lista de favoritos del usuario

    // Constructor vacío requerido para Firebase
    public User() {
    }

    // Constructor para crear un usuario con datos
    public User(String fullName, String email, String phone, String address, List<String> favoritos) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.favoritos = favoritos;
    }

    // Métodos getter y setter
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<String> favoritos) {
        this.favoritos = favoritos;
    }
}


