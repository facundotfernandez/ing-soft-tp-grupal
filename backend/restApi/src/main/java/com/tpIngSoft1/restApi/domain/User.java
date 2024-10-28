package com.tpIngSoft1.restApi.domain;

import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {
    private String name;
    private String email;
    private String id;
    private String token;
    private String userName;
    private String lastName;
    private String password;
    private String direction;
    private String genre;
    private String photoPerfil;

    public User( String name, String email, String id, String token, String userName, String lastName, String password, String direction, String genre, String photoPerfil) {
        this.name= name;
        this.email= email;
        this.id= id;
        this.token= token;
        this.userName= userName;
        this.lastName= lastName;
        this.password= password;
        this.direction= direction;
        this.genre= genre;
        this.photoPerfil= photoPerfil;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.userName;
    }
}
