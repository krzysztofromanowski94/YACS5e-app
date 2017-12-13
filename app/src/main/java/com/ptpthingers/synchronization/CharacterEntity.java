package com.ptpthingers.synchronization;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.protobuf.ByteString;

import java.util.UUID;


@Entity(tableName = "characters")
public class CharacterEntity {
    public CharacterEntity(String data) {
        super();
        this.data = data;
        uuid = UUID.randomUUID().toString();
        lastMod = System.currentTimeMillis() / 1000L;
    }

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getLastSync() {
        return lastSync;
    }

    public void setLastSync(long lastSync) {
        this.lastSync = lastSync;
    }

    public long getLastMod() {
        return lastMod;
    }

    public void setLastMod(long lastMod) {
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

    public ByteString getDataByteString() {
        return ByteString.copyFromUtf8(data);
    }

    public void setData(String data) {
        this.data = data;
    }
}
