package com.ptpthingers.synchronization;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM characters WHERE owner_login = :ownerLogin")
    List<CharacterEntity> getAllCharacters(String ownerLogin);

    @Query("SELECT uuid FROM characters WHERE to_delete = 0 AND owner_login = :ownerLogin ORDER BY last_mod DESC")
    List<String> getAllUuid(String ownerLogin);

    @Query("SELECT * FROM characters WHERE uuid = :uuid")
    CharacterEntity getCharacter(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(CharacterEntity characterEntity);

    @Query("UPDATE characters SET uuid = :newUuid WHERE uuid = :oldUuid")
    void updateUuid(String oldUuid, String newUuid);

    @Query("UPDATE characters SET to_delete = 1 where uuid = :uuid")
    void setToDelete(String uuid);

    @Query("UPDATE characters SET to_delete = 0 where uuid = :uuid")
    void unsetToDelete(String uuid);

    @Query("DELETE FROM characters WHERE uuid = :uuid")
    void deleteCharacterEntity(String uuid);

    @Query("DELETE FROM characters")
    void truncate();

    @Query("UPDATE characters SET owner_login= :ownerLogin WHERE owner_login=''")
    void setOwnerForAllOrphans(String ownerLogin);
}
