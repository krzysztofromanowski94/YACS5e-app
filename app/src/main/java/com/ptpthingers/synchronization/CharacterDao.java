package com.ptpthingers.synchronization;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM characters")
    List<CharacterEntity> getAllCharacters();

    @Query("SELECT uuid FROM characters")
    List<String> getAllUuid();

    @Query("SELECT * FROM characters WHERE uuid = :uuid")
    CharacterEntity getCharacter(String uuid);

    @Insert
    void insertCharacter(CharacterEntity characterEntity);

    @Query("DELETE FROM characters")
    void truncate();
}
