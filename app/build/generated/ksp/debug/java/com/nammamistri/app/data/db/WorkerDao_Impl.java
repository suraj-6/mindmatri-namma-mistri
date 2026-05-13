package com.nammamistri.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nammamistri.app.data.model.Worker;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkerDao_Impl implements WorkerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Worker> __insertionAdapterOfWorker;

  private final EntityDeletionOrUpdateAdapter<Worker> __deletionAdapterOfWorker;

  private final EntityDeletionOrUpdateAdapter<Worker> __updateAdapterOfWorker;

  private final SharedSQLiteStatement __preparedStmtOfDeleteWorkerById;

  public WorkerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorker = new EntityInsertionAdapter<Worker>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `workers` (`id`,`siteId`,`name`,`dailyWage`,`phoneNumber`,`joiningDate`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Worker entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSiteId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDailyWage());
        statement.bindString(5, entity.getPhoneNumber());
        statement.bindLong(6, entity.getJoiningDate());
      }
    };
    this.__deletionAdapterOfWorker = new EntityDeletionOrUpdateAdapter<Worker>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `workers` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Worker entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWorker = new EntityDeletionOrUpdateAdapter<Worker>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workers` SET `id` = ?,`siteId` = ?,`name` = ?,`dailyWage` = ?,`phoneNumber` = ?,`joiningDate` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Worker entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSiteId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDailyWage());
        statement.bindString(5, entity.getPhoneNumber());
        statement.bindLong(6, entity.getJoiningDate());
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteWorkerById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM workers WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWorker(final Worker worker, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWorker.insertAndReturnId(worker);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWorker(final Worker worker, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorker.handle(worker);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWorker(final Worker worker, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorker.handle(worker);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWorkerById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteWorkerById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteWorkerById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Worker>> getWorkersBySite(final long siteId) {
    final String _sql = "SELECT * FROM workers WHERE siteId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, siteId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workers"}, false, new Callable<List<Worker>>() {
      @Override
      @Nullable
      public List<Worker> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSiteId = CursorUtil.getColumnIndexOrThrow(_cursor, "siteId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final List<Worker> _result = new ArrayList<Worker>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Worker _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSiteId;
            _tmpSiteId = _cursor.getLong(_cursorIndexOfSiteId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final long _tmpJoiningDate;
            _tmpJoiningDate = _cursor.getLong(_cursorIndexOfJoiningDate);
            _item = new Worker(_tmpId,_tmpSiteId,_tmpName,_tmpDailyWage,_tmpPhoneNumber,_tmpJoiningDate);
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
  public Object getWorkerById(final long id, final Continuation<? super Worker> $completion) {
    final String _sql = "SELECT * FROM workers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Worker>() {
      @Override
      @Nullable
      public Worker call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSiteId = CursorUtil.getColumnIndexOrThrow(_cursor, "siteId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final Worker _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSiteId;
            _tmpSiteId = _cursor.getLong(_cursorIndexOfSiteId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final long _tmpJoiningDate;
            _tmpJoiningDate = _cursor.getLong(_cursorIndexOfJoiningDate);
            _result = new Worker(_tmpId,_tmpSiteId,_tmpName,_tmpDailyWage,_tmpPhoneNumber,_tmpJoiningDate);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<Worker> getWorkerByIdLiveData(final long id) {
    final String _sql = "SELECT * FROM workers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workers"}, false, new Callable<Worker>() {
      @Override
      @Nullable
      public Worker call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSiteId = CursorUtil.getColumnIndexOrThrow(_cursor, "siteId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDailyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyWage");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final Worker _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSiteId;
            _tmpSiteId = _cursor.getLong(_cursorIndexOfSiteId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpDailyWage;
            _tmpDailyWage = _cursor.getDouble(_cursorIndexOfDailyWage);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final long _tmpJoiningDate;
            _tmpJoiningDate = _cursor.getLong(_cursorIndexOfJoiningDate);
            _result = new Worker(_tmpId,_tmpSiteId,_tmpName,_tmpDailyWage,_tmpPhoneNumber,_tmpJoiningDate);
          } else {
            _result = null;
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
  public Object getWorkerCountBySite(final long siteId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM workers WHERE siteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, siteId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
