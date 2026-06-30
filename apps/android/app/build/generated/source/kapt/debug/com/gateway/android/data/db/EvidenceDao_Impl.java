package com.gateway.android.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EvidenceDao_Impl implements EvidenceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PendingEvidence> __insertionAdapterOfPendingEvidence;

  private final EntityDeletionOrUpdateAdapter<PendingEvidence> __deletionAdapterOfPendingEvidence;

  private final SharedSQLiteStatement __preparedStmtOfIncrementRetry;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public EvidenceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPendingEvidence = new EntityInsertionAdapter<PendingEvidence>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pending_evidence` (`id`,`rawNotification`,`amount`,`utr`,`rrn`,`senderVpa`,`senderName`,`upiApp`,`bank`,`notificationPackage`,`notificationTimestamp`,`parserVersion`,`nonce`,`signature`,`retryCount`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PendingEvidence entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getRawNotification() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getRawNotification());
        }
        statement.bindLong(3, entity.getAmount());
        if (entity.getUtr() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUtr());
        }
        if (entity.getRrn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getRrn());
        }
        if (entity.getSenderVpa() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSenderVpa());
        }
        if (entity.getSenderName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getSenderName());
        }
        if (entity.getUpiApp() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getUpiApp());
        }
        if (entity.getBank() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getBank());
        }
        if (entity.getNotificationPackage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNotificationPackage());
        }
        statement.bindLong(11, entity.getNotificationTimestamp());
        if (entity.getParserVersion() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getParserVersion());
        }
        if (entity.getNonce() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getNonce());
        }
        if (entity.getSignature() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getSignature());
        }
        statement.bindLong(15, entity.getRetryCount());
        statement.bindLong(16, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfPendingEvidence = new EntityDeletionOrUpdateAdapter<PendingEvidence>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pending_evidence` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PendingEvidence entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
    this.__preparedStmtOfIncrementRetry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pending_evidence SET retryCount = retryCount + 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pending_evidence WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PendingEvidence evidence,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPendingEvidence.insert(evidence);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PendingEvidence evidence,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPendingEvidence.handle(evidence);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementRetry(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementRetry.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementRetry.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PendingEvidence>> getAll() {
    final String _sql = "SELECT * FROM pending_evidence ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pending_evidence"}, new Callable<List<PendingEvidence>>() {
      @Override
      @NonNull
      public List<PendingEvidence> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRawNotification = CursorUtil.getColumnIndexOrThrow(_cursor, "rawNotification");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfUtr = CursorUtil.getColumnIndexOrThrow(_cursor, "utr");
          final int _cursorIndexOfRrn = CursorUtil.getColumnIndexOrThrow(_cursor, "rrn");
          final int _cursorIndexOfSenderVpa = CursorUtil.getColumnIndexOrThrow(_cursor, "senderVpa");
          final int _cursorIndexOfSenderName = CursorUtil.getColumnIndexOrThrow(_cursor, "senderName");
          final int _cursorIndexOfUpiApp = CursorUtil.getColumnIndexOrThrow(_cursor, "upiApp");
          final int _cursorIndexOfBank = CursorUtil.getColumnIndexOrThrow(_cursor, "bank");
          final int _cursorIndexOfNotificationPackage = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationPackage");
          final int _cursorIndexOfNotificationTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationTimestamp");
          final int _cursorIndexOfParserVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "parserVersion");
          final int _cursorIndexOfNonce = CursorUtil.getColumnIndexOrThrow(_cursor, "nonce");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<PendingEvidence> _result = new ArrayList<PendingEvidence>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PendingEvidence _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpRawNotification;
            if (_cursor.isNull(_cursorIndexOfRawNotification)) {
              _tmpRawNotification = null;
            } else {
              _tmpRawNotification = _cursor.getString(_cursorIndexOfRawNotification);
            }
            final int _tmpAmount;
            _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
            final String _tmpUtr;
            if (_cursor.isNull(_cursorIndexOfUtr)) {
              _tmpUtr = null;
            } else {
              _tmpUtr = _cursor.getString(_cursorIndexOfUtr);
            }
            final String _tmpRrn;
            if (_cursor.isNull(_cursorIndexOfRrn)) {
              _tmpRrn = null;
            } else {
              _tmpRrn = _cursor.getString(_cursorIndexOfRrn);
            }
            final String _tmpSenderVpa;
            if (_cursor.isNull(_cursorIndexOfSenderVpa)) {
              _tmpSenderVpa = null;
            } else {
              _tmpSenderVpa = _cursor.getString(_cursorIndexOfSenderVpa);
            }
            final String _tmpSenderName;
            if (_cursor.isNull(_cursorIndexOfSenderName)) {
              _tmpSenderName = null;
            } else {
              _tmpSenderName = _cursor.getString(_cursorIndexOfSenderName);
            }
            final String _tmpUpiApp;
            if (_cursor.isNull(_cursorIndexOfUpiApp)) {
              _tmpUpiApp = null;
            } else {
              _tmpUpiApp = _cursor.getString(_cursorIndexOfUpiApp);
            }
            final String _tmpBank;
            if (_cursor.isNull(_cursorIndexOfBank)) {
              _tmpBank = null;
            } else {
              _tmpBank = _cursor.getString(_cursorIndexOfBank);
            }
            final String _tmpNotificationPackage;
            if (_cursor.isNull(_cursorIndexOfNotificationPackage)) {
              _tmpNotificationPackage = null;
            } else {
              _tmpNotificationPackage = _cursor.getString(_cursorIndexOfNotificationPackage);
            }
            final long _tmpNotificationTimestamp;
            _tmpNotificationTimestamp = _cursor.getLong(_cursorIndexOfNotificationTimestamp);
            final String _tmpParserVersion;
            if (_cursor.isNull(_cursorIndexOfParserVersion)) {
              _tmpParserVersion = null;
            } else {
              _tmpParserVersion = _cursor.getString(_cursorIndexOfParserVersion);
            }
            final String _tmpNonce;
            if (_cursor.isNull(_cursorIndexOfNonce)) {
              _tmpNonce = null;
            } else {
              _tmpNonce = _cursor.getString(_cursorIndexOfNonce);
            }
            final String _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new PendingEvidence(_tmpId,_tmpRawNotification,_tmpAmount,_tmpUtr,_tmpRrn,_tmpSenderVpa,_tmpSenderName,_tmpUpiApp,_tmpBank,_tmpNotificationPackage,_tmpNotificationTimestamp,_tmpParserVersion,_tmpNonce,_tmpSignature,_tmpRetryCount,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPendingRetries(final Continuation<? super List<PendingEvidence>> $completion) {
    final String _sql = "SELECT * FROM pending_evidence WHERE retryCount < 3 ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PendingEvidence>>() {
      @Override
      @NonNull
      public List<PendingEvidence> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRawNotification = CursorUtil.getColumnIndexOrThrow(_cursor, "rawNotification");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfUtr = CursorUtil.getColumnIndexOrThrow(_cursor, "utr");
          final int _cursorIndexOfRrn = CursorUtil.getColumnIndexOrThrow(_cursor, "rrn");
          final int _cursorIndexOfSenderVpa = CursorUtil.getColumnIndexOrThrow(_cursor, "senderVpa");
          final int _cursorIndexOfSenderName = CursorUtil.getColumnIndexOrThrow(_cursor, "senderName");
          final int _cursorIndexOfUpiApp = CursorUtil.getColumnIndexOrThrow(_cursor, "upiApp");
          final int _cursorIndexOfBank = CursorUtil.getColumnIndexOrThrow(_cursor, "bank");
          final int _cursorIndexOfNotificationPackage = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationPackage");
          final int _cursorIndexOfNotificationTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationTimestamp");
          final int _cursorIndexOfParserVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "parserVersion");
          final int _cursorIndexOfNonce = CursorUtil.getColumnIndexOrThrow(_cursor, "nonce");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<PendingEvidence> _result = new ArrayList<PendingEvidence>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PendingEvidence _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpRawNotification;
            if (_cursor.isNull(_cursorIndexOfRawNotification)) {
              _tmpRawNotification = null;
            } else {
              _tmpRawNotification = _cursor.getString(_cursorIndexOfRawNotification);
            }
            final int _tmpAmount;
            _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
            final String _tmpUtr;
            if (_cursor.isNull(_cursorIndexOfUtr)) {
              _tmpUtr = null;
            } else {
              _tmpUtr = _cursor.getString(_cursorIndexOfUtr);
            }
            final String _tmpRrn;
            if (_cursor.isNull(_cursorIndexOfRrn)) {
              _tmpRrn = null;
            } else {
              _tmpRrn = _cursor.getString(_cursorIndexOfRrn);
            }
            final String _tmpSenderVpa;
            if (_cursor.isNull(_cursorIndexOfSenderVpa)) {
              _tmpSenderVpa = null;
            } else {
              _tmpSenderVpa = _cursor.getString(_cursorIndexOfSenderVpa);
            }
            final String _tmpSenderName;
            if (_cursor.isNull(_cursorIndexOfSenderName)) {
              _tmpSenderName = null;
            } else {
              _tmpSenderName = _cursor.getString(_cursorIndexOfSenderName);
            }
            final String _tmpUpiApp;
            if (_cursor.isNull(_cursorIndexOfUpiApp)) {
              _tmpUpiApp = null;
            } else {
              _tmpUpiApp = _cursor.getString(_cursorIndexOfUpiApp);
            }
            final String _tmpBank;
            if (_cursor.isNull(_cursorIndexOfBank)) {
              _tmpBank = null;
            } else {
              _tmpBank = _cursor.getString(_cursorIndexOfBank);
            }
            final String _tmpNotificationPackage;
            if (_cursor.isNull(_cursorIndexOfNotificationPackage)) {
              _tmpNotificationPackage = null;
            } else {
              _tmpNotificationPackage = _cursor.getString(_cursorIndexOfNotificationPackage);
            }
            final long _tmpNotificationTimestamp;
            _tmpNotificationTimestamp = _cursor.getLong(_cursorIndexOfNotificationTimestamp);
            final String _tmpParserVersion;
            if (_cursor.isNull(_cursorIndexOfParserVersion)) {
              _tmpParserVersion = null;
            } else {
              _tmpParserVersion = _cursor.getString(_cursorIndexOfParserVersion);
            }
            final String _tmpNonce;
            if (_cursor.isNull(_cursorIndexOfNonce)) {
              _tmpNonce = null;
            } else {
              _tmpNonce = _cursor.getString(_cursorIndexOfNonce);
            }
            final String _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new PendingEvidence(_tmpId,_tmpRawNotification,_tmpAmount,_tmpUtr,_tmpRrn,_tmpSenderVpa,_tmpSenderName,_tmpUpiApp,_tmpBank,_tmpNotificationPackage,_tmpNotificationTimestamp,_tmpParserVersion,_tmpNonce,_tmpSignature,_tmpRetryCount,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
