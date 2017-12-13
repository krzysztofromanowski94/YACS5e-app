package com.ptpthingers.synchronization;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CharacterEntity.class}, version = 1, exportSchema = false)
public abstract class CharacterDatabase extends RoomDatabase {
    public abstract CharacterDao characterDao();
}
