package com.ptpthingers.synchronization;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM characters")
    List<CharacterEntity> getAllCharacters();

    @Query("SELECT uuid FROM characters")
    List<String> getAllUuid();

    @Query("SELECT uuid FROM characters WHERE to_delete = FALSE")
    List<String> getAllUuidNotToDelete();

    @Query("SELECT * FROM characters WHERE uuid = :uuid")
    CharacterEntity getCharacter(String uuid);

    @Query("SELECT COUNT(*) FROM characters")
    Integer count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(CharacterEntity characterEntity);

    @Query("UPDATE characters SET uuid = :newUuid WHERE uuid = :oldUuid")
    void updateUuid(String oldUuid, String newUuid);

    @Query("UPDATE characters SET to_delete = TRUE where uuid = :uuid")
    void setToDelete(String uuid);

    @Query("UPDATE characters SET to_delete = FALSE where uuid = :uuid")
    void unsetToDelete(String uuid);


    @Query("DELETE FROM characters")
    void truncate();
}
