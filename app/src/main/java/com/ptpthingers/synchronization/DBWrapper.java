package com.ptpthingers.synchronization;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class DBWrapper {

    public static void setContext(Context context) {
        DBInstance.getHook(context);
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static List<String> getUuidList() {
        try {
            return new GetUuidList().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetUuidList extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            return DBInstance.getHook().characterDao().getAllUuid();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static CharacterEntity getCharEntity(String uuid) {
        try {
            return new GetCharacter().execute(uuid).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetCharacter extends AsyncTask<String, Void, CharacterEntity> {
        @Override
        protected CharacterEntity doInBackground(String... strings) {
            if (strings.length != 1) {
                return null;
            }
            return DBInstance.getHook().characterDao().getCharacter(strings[0]);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static void insertCharEntity(CharacterEntity characterEntity) {
        new InsertCharacter().execute(characterEntity);
    }

    private static class InsertCharacter extends AsyncTask<CharacterEntity, Void, Void> {
        @Override
        protected Void doInBackground(CharacterEntity... characterEntities) {
            if (characterEntities.length != 1) {
                return null;
            }
            DBInstance.getHook().characterDao().insertCharacter(characterEntities[0]);
            return null;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static void truncate() {
        new Truncate().execute();
    }

    private static class Truncate extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            DBInstance.getHook().characterDao().truncate();
            return null;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static List<CharacterEntity> getCharEntityList() {
        try {
            return new GetList().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetList extends AsyncTask<Void, Void, List<CharacterEntity>> {
        @Override
        protected List<CharacterEntity> doInBackground(Void... voids) {
            return DBInstance.getHook().characterDao().getAllCharacters();
        }
    }

    // ToDo usuń na podstawie uuid
    // ToDo dodaj cofnij usuń na podstawie uuid
    // ToDo jeśtli check toDelete - nie zwracaj w getUuidList
}
