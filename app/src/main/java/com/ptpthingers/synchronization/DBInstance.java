package com.ptpthingers.synchronization;

import android.arch.persistence.room.Room;
import android.content.Context;

class DBInstance {
    private static CharacterDatabase characterDatabase;

    static CharacterDatabase getHook(Context context) {
        if (characterDatabase == null) {
            characterDatabase = Room.databaseBuilder(context, CharacterDatabase.class, "yacs5e").build();
        }
        return characterDatabase;
    }

    static CharacterDatabase getHook() {
        if (characterDatabase == null) {
            return null;
        }
        return characterDatabase;
    }
}
