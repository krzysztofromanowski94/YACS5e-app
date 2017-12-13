package com.ptpthingers.synchronization;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DBInstance {
    private static CharacterDatabase characterDatabase;

    public static CharacterDatabase getHook(Context context) {
        if (characterDatabase == null) {
            characterDatabase = Room.databaseBuilder(context.getApplicationContext(), CharacterDatabase.class, "yacs5e").build();
        }
        return characterDatabase;
    }
}
