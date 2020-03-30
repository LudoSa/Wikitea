package com.example.wikitea.Tables.Favourite;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {
        @Insert
        void insert(Favorite favourite);

        @Update
        void update(Favorite favourite);

        @Delete
        void delete(Favorite favourite);

        @Query("DELETE FROM favourite_table")
        void deleteAllFavourites();

        @Query("SELECT * FROM favourite_table")
        LiveData<List<Favorite>> getAllFavourites();

}
