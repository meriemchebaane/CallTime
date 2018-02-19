package com.example.user.calltime2;

/**
 * Created by chebaane on 05/11/2017.
 */

public class Contact {
    public String name;
    public String numero;
    public String id;

    //Constructors

    //Default constructor
    public Contact(){

    }

    public Contact(String name, String numero, String id) {
        this.name = name;
        this.numero = numero;
        this.id = id;
    }

    //Getters and Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNumero() {
        return numero;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "Name='" + name + '\'' +
                ", Number='" + numero + '\'' +
                ", ID='" + id +
                '}';
    }
}
