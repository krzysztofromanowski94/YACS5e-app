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

    public static List<String> getUuidList(String username) {
        try {
            return new GetUuidList().execute(username).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetUuidList extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... strings) {
            return DBInstance.getHook().characterDao().getAllUuid(strings[0]);
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

    public static void setToDelete(String uuid) {
        try {
            new SetToDelete().execute(uuid).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class SetToDelete extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            DBInstance.getHook().characterDao().setToDelete(strings[0]);
            return null;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static void unsetToDelete(String uuid) {
        try {
            new UnsetToDelete().execute(uuid).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class UnsetToDelete extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            DBInstance.getHook().characterDao().unsetToDelete(strings[0]);
            return null;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static void setOwnerForAllOrphans(String owner_login) {
        new SetOwnerForAllOrphans().execute(owner_login);
    }

    private static class SetOwnerForAllOrphans extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            DBInstance.getHook().characterDao().setOwnerForAllOrphans(strings[0]);
            return null;
        }
    }
}
