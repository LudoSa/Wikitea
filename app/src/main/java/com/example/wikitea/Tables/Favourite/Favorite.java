package com.example.wikitea.Tables.Favourite;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_table")
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    private int idFavorite;

    private String title;

    private String description;

    private String origin ;



    public Favorite(String title, String description, String origin)
    {
        this.title = title;
        this.description = description;
        this.origin = origin;
    }

    //Setter

    public void setIdFavorite(int idTea) {
        this.idFavorite = idTea;
    }

    public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
    }

    //GETTERS
    public int getIdFavorite() {
        return idFavorite;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOrigin() {
        return origin;
    }
}
