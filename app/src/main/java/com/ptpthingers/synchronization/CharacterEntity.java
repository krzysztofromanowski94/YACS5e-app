package com.ptpthingers.synchronization;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.protobuf.ByteString;
import com.ptpthingers.yacs5e_app.TCharacter;
import com.ptpthingers.yacs5e_app.TTalk;


@Entity(tableName = "characters", primaryKeys = {"uuid", "owner_login"})
public class CharacterEntity {
    @ColumnInfo(name = "uuid")
    @NonNull
    private String uuid;

    @ColumnInfo(name = "owner_login")
    @NonNull
    private String ownerLogin;

    @ColumnInfo(name = "last_sync")
    private long lastSync;

    @ColumnInfo(name = "last_mod")
    private long lastMod;

    @ColumnInfo(name = "data")
    private String data;

    @ColumnInfo(name = "to_delete")
    private boolean toDelete;

    public CharacterEntity(@NonNull String uuid, @NonNull String ownerLogin, String data) {
        this.uuid = uuid;
        this.ownerLogin = ownerLogin;
        this.data = data;
        this.toDelete = false;
        lastMod = System.currentTimeMillis() / 1000L;
    }

    public String getUuid() {
        return uuid;
    }

    void setUuid(String uuid) {
        this.uuid = uuid;
    }

    long getLastSync() {
        return lastSync;
    }

    void setLastSync(long lastSync) {
        this.lastSync = lastSync;
    }

    public long getLastMod() {
        return lastMod;
    }

    void setLastMod(long lastMod) {
        this.lastMod = lastMod;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public ByteString getDataByteString() {
        return ByteString.copyFromUtf8(data);
    }

    TTalk toSyncTTalk() {
        lastSync = System.currentTimeMillis() / 1000L;
        return TTalk.newBuilder()
                .setCharacter(TCharacter.newBuilder()
                        .setUuid(uuid)
                        .setBlob(data)
                        .setLastSync(lastSync)
                        .setLastMod(lastMod))
                .build();
    }

    @Override
    public String toString() {
        return "CharacterEntity{" +
                "uuid='" + uuid + '\'' +
                ", lastSync=" + lastSync +
                ", lastMod=" + lastMod +
                ", ownerLogin='" + ownerLogin + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
