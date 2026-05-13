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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nammamistri.app.data.model.WageEntry;
import java.lang.Class;
import java.lang.Double;
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
public final class WageEntryDao_Impl implements WageEntryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WageEntry> __insertionAdapterOfWageEntry;

  private final EntityDeletionOrUpdateAdapter<WageEntry> __deletionAdapterOfWageEntry;

  private final EntityDeletionOrUpdateAdapter<WageEntry> __updateAdapterOfWageEntry;

  public WageEntryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWageEntry = new EntityInsertionAdapter<WageEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `wage_entries` (`id`,`workerId`,`date`,`isPresent`,`advancePayment`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WageEntry entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWorkerId());
        statement.bindLong(3, entity.getDate());
        final int _tmp = entity.isPresent() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindDouble(5, entity.getAdvancePayment());
        statement.bindString(6, entity.getNotes());
      }
    };
    this.__deletionAdapterOfWageEntry = new EntityDeletionOrUpdateAdapter<WageEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `wage_entries` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WageEntry entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWageEntry = new EntityDeletionOrUpdateAdapter<WageEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `wage_entries` SET `id` = ?,`workerId` = ?,`date` = ?,`isPresent` = ?,`advancePayment` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WageEntry entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWorkerId());
        statement.bindLong(3, entity.getDate());
        final int _tmp = entity.isPresent() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindDouble(5, entity.getAdvancePayment());
        statement.bindString(6, entity.getNotes());
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insertEntry(final WageEntry wageEntry,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWageEntry.insertAndReturnId(wageEntry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEntry(final WageEntry wageEntry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWageEntry.handle(wageEntry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEntry(final WageEntry wageEntry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWageEntry.handle(wageEntry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<WageEntry>> getEntriesByWorker(final long workerId) {
    final String _sql = "SELECT * FROM wage_entries WHERE workerId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workerId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"wage_entries"}, false, new Callable<List<WageEntry>>() {
      @Override
      @Nullable
      public List<WageEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsPresent = CursorUtil.getColumnIndexOrThrow(_cursor, "isPresent");
          final int _cursorIndexOfAdvancePayment = CursorUtil.getColumnIndexOrThrow(_cursor, "advancePayment");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<WageEntry> _result = new ArrayList<WageEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WageEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkerId;
            _tmpWorkerId = _cursor.getLong(_cursorIndexOfWorkerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsPresent;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPresent);
            _tmpIsPresent = _tmp != 0;
            final double _tmpAdvancePayment;
            _tmpAdvancePayment = _cursor.getDouble(_cursorIndexOfAdvancePayment);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new WageEntry(_tmpId,_tmpWorkerId,_tmpDate,_tmpIsPresent,_tmpAdvancePayment,_tmpNotes);
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
  public Object getEntriesByWorkerSync(final long workerId,
      final Continuation<? super List<WageEntry>> $completion) {
    final String _sql = "SELECT * FROM wage_entries WHERE workerId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<WageEntry>>() {
      @Override
      @NonNull
      public List<WageEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsPresent = CursorUtil.getColumnIndexOrThrow(_cursor, "isPresent");
          final int _cursorIndexOfAdvancePayment = CursorUtil.getColumnIndexOrThrow(_cursor, "advancePayment");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<WageEntry> _result = new ArrayList<WageEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WageEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkerId;
            _tmpWorkerId = _cursor.getLong(_cursorIndexOfWorkerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsPresent;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPresent);
            _tmpIsPresent = _tmp != 0;
            final double _tmpAdvancePayment;
            _tmpAdvancePayment = _cursor.getDouble(_cursorIndexOfAdvancePayment);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new WageEntry(_tmpId,_tmpWorkerId,_tmpDate,_tmpIsPresent,_tmpAdvancePayment,_tmpNotes);
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

  @Override
  public Object getTotalAdvanceByWorker(final long workerId,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT COALESCE(SUM(advancePayment), 0.0) FROM wage_entries WHERE workerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
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
  public Object getTotalDaysPresent(final long workerId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM wage_entries WHERE workerId = ? AND isPresent = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workerId);
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

  @Override
  public Object getEntryByWorkerAndDate(final long workerId, final long date,
      final Continuation<? super WageEntry> $completion) {
    final String _sql = "SELECT * FROM wage_entries WHERE workerId = ? AND date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workerId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, date);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WageEntry>() {
      @Override
      @Nullable
      public WageEntry call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsPresent = CursorUtil.getColumnIndexOrThrow(_cursor, "isPresent");
          final int _cursorIndexOfAdvancePayment = CursorUtil.getColumnIndexOrThrow(_cursor, "advancePayment");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final WageEntry _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkerId;
            _tmpWorkerId = _cursor.getLong(_cursorIndexOfWorkerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsPresent;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPresent);
            _tmpIsPresent = _tmp != 0;
            final double _tmpAdvancePayment;
            _tmpAdvancePayment = _cursor.getDouble(_cursorIndexOfAdvancePayment);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _result = new WageEntry(_tmpId,_tmpWorkerId,_tmpDate,_tmpIsPresent,_tmpAdvancePayment,_tmpNotes);
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
  public LiveData<List<WageEntry>> getEntriesInDateRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM wage_entries WHERE date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return __db.getInvalidationTracker().createLiveData(new String[] {"wage_entries"}, false, new Callable<List<WageEntry>>() {
      @Override
      @Nullable
      public List<WageEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkerId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsPresent = CursorUtil.getColumnIndexOrThrow(_cursor, "isPresent");
          final int _cursorIndexOfAdvancePayment = CursorUtil.getColumnIndexOrThrow(_cursor, "advancePayment");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<WageEntry> _result = new ArrayList<WageEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WageEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkerId;
            _tmpWorkerId = _cursor.getLong(_cursorIndexOfWorkerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsPresent;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPresent);
            _tmpIsPresent = _tmp != 0;
            final double _tmpAdvancePayment;
            _tmpAdvancePayment = _cursor.getDouble(_cursorIndexOfAdvancePayment);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new WageEntry(_tmpId,_tmpWorkerId,_tmpDate,_tmpIsPresent,_tmpAdvancePayment,_tmpNotes);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
