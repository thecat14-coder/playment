package com.gateway.android.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile EvidenceDao _evidenceDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `pending_evidence` (`id` TEXT NOT NULL, `rawNotification` TEXT NOT NULL, `amount` INTEGER NOT NULL, `utr` TEXT, `rrn` TEXT, `senderVpa` TEXT, `senderName` TEXT, `upiApp` TEXT NOT NULL, `bank` TEXT, `notificationPackage` TEXT NOT NULL, `notificationTimestamp` INTEGER NOT NULL, `parserVersion` TEXT NOT NULL, `nonce` TEXT NOT NULL, `signature` TEXT NOT NULL, `retryCount` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2f9eb963ced20c7560358d1078a440d7')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `pending_evidence`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPendingEvidence = new HashMap<String, TableInfo.Column>(16);
        _columnsPendingEvidence.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("rawNotification", new TableInfo.Column("rawNotification", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("utr", new TableInfo.Column("utr", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("rrn", new TableInfo.Column("rrn", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("senderVpa", new TableInfo.Column("senderVpa", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("senderName", new TableInfo.Column("senderName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("upiApp", new TableInfo.Column("upiApp", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("bank", new TableInfo.Column("bank", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("notificationPackage", new TableInfo.Column("notificationPackage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("notificationTimestamp", new TableInfo.Column("notificationTimestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("parserVersion", new TableInfo.Column("parserVersion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("nonce", new TableInfo.Column("nonce", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("signature", new TableInfo.Column("signature", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("retryCount", new TableInfo.Column("retryCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingEvidence.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPendingEvidence = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPendingEvidence = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPendingEvidence = new TableInfo("pending_evidence", _columnsPendingEvidence, _foreignKeysPendingEvidence, _indicesPendingEvidence);
        final TableInfo _existingPendingEvidence = TableInfo.read(db, "pending_evidence");
        if (!_infoPendingEvidence.equals(_existingPendingEvidence)) {
          return new RoomOpenHelper.ValidationResult(false, "pending_evidence(com.gateway.android.data.db.PendingEvidence).\n"
                  + " Expected:\n" + _infoPendingEvidence + "\n"
                  + " Found:\n" + _existingPendingEvidence);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "2f9eb963ced20c7560358d1078a440d7", "a6b6fa641ba8ba6736eb06dd616ca232");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "pending_evidence");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `pending_evidence`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(EvidenceDao.class, EvidenceDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public EvidenceDao evidenceDao() {
    if (_evidenceDao != null) {
      return _evidenceDao;
    } else {
      synchronized(this) {
        if(_evidenceDao == null) {
          _evidenceDao = new EvidenceDao_Impl(this);
        }
        return _evidenceDao;
      }
    }
  }
}
