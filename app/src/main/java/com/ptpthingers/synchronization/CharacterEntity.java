package com.ptpthingers.synchronization;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.protobuf.ByteString;
import com.ptpthingers.yacs5e_app.TCharacter;
import com.ptpthingers.yacs5e_app.TTalk;


@Entity(tableName = "characters")
public class CharacterEntity {
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    @NonNull
    private String uuid;
    @ColumnInfo(name = "last_sync")
    private long lastSync;
    @ColumnInfo(name = "last_mod")
    private long lastMod;
    @ColumnInfo(name = "owner_login")
    private String ownerLogin;
    @ColumnInfo(name = "data")
    private String data;
    @ColumnInfo(name = "to_delete")
    private boolean toDelete;

    public CharacterEntity(@NonNull String uuid, String data) {
        this.data = data;
        this.uuid = uuid;
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
                        .setBlob(ByteString.copyFromUtf8(data))
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
