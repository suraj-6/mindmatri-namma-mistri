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
import com.nammamistri.app.data.model.MaterialLog;
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
public final class MaterialLogDao_Impl implements MaterialLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MaterialLog> __insertionAdapterOfMaterialLog;

  private final EntityDeletionOrUpdateAdapter<MaterialLog> __deletionAdapterOfMaterialLog;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public MaterialLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMaterialLog = new EntityInsertionAdapter<MaterialLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `material_logs` (`id`,`siteId`,`bricks`,`cementBags`,`sandLoads`,`wallLength`,`wallWidth`,`wallHeight`,`wallThickness`,`calculatedOn`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MaterialLog entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSiteId());
        statement.bindLong(3, entity.getBricks());
        statement.bindLong(4, entity.getCementBags());
        statement.bindDouble(5, entity.getSandLoads());
        statement.bindDouble(6, entity.getWallLength());
        statement.bindDouble(7, entity.getWallWidth());
        statement.bindDouble(8, entity.getWallHeight());
        statement.bindString(9, entity.getWallThickness());
        statement.bindLong(10, entity.getCalculatedOn());
      }
    };
    this.__deletionAdapterOfMaterialLog = new EntityDeletionOrUpdateAdapter<MaterialLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `material_logs` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MaterialLog entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM material_logs WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MaterialLog materialLog,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMaterialLog.insertAndReturnId(materialLog);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final MaterialLog materialLog,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMaterialLog.handle(materialLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
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
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<MaterialLog>> getLogsBySite(final long siteId) {
    final String _sql = "SELECT * FROM material_logs WHERE siteId = ? ORDER BY calculatedOn DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, siteId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"material_logs"}, false, new Callable<List<MaterialLog>>() {
      @Override
      @Nullable
      public List<MaterialLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSiteId = CursorUtil.getColumnIndexOrThrow(_cursor, "siteId");
          final int _cursorIndexOfBricks = CursorUtil.getColumnIndexOrThrow(_cursor, "bricks");
          final int _cursorIndexOfCementBags = CursorUtil.getColumnIndexOrThrow(_cursor, "cementBags");
          final int _cursorIndexOfSandLoads = CursorUtil.getColumnIndexOrThrow(_cursor, "sandLoads");
          final int _cursorIndexOfWallLength = CursorUtil.getColumnIndexOrThrow(_cursor, "wallLength");
          final int _cursorIndexOfWallWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "wallWidth");
          final int _cursorIndexOfWallHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "wallHeight");
          final int _cursorIndexOfWallThickness = CursorUtil.getColumnIndexOrThrow(_cursor, "wallThickness");
          final int _cursorIndexOfCalculatedOn = CursorUtil.getColumnIndexOrThrow(_cursor, "calculatedOn");
          final List<MaterialLog> _result = new ArrayList<MaterialLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MaterialLog _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSiteId;
            _tmpSiteId = _cursor.getLong(_cursorIndexOfSiteId);
            final int _tmpBricks;
            _tmpBricks = _cursor.getInt(_cursorIndexOfBricks);
            final int _tmpCementBags;
            _tmpCementBags = _cursor.getInt(_cursorIndexOfCementBags);
            final double _tmpSandLoads;
            _tmpSandLoads = _cursor.getDouble(_cursorIndexOfSandLoads);
            final double _tmpWallLength;
            _tmpWallLength = _cursor.getDouble(_cursorIndexOfWallLength);
            final double _tmpWallWidth;
            _tmpWallWidth = _cursor.getDouble(_cursorIndexOfWallWidth);
            final double _tmpWallHeight;
            _tmpWallHeight = _cursor.getDouble(_cursorIndexOfWallHeight);
            final String _tmpWallThickness;
            _tmpWallThickness = _cursor.getString(_cursorIndexOfWallThickness);
            final long _tmpCalculatedOn;
            _tmpCalculatedOn = _cursor.getLong(_cursorIndexOfCalculatedOn);
            _item = new MaterialLog(_tmpId,_tmpSiteId,_tmpBricks,_tmpCementBags,_tmpSandLoads,_tmpWallLength,_tmpWallWidth,_tmpWallHeight,_tmpWallThickness,_tmpCalculatedOn);
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
  public Object getLogById(final long id, final Continuation<? super MaterialLog> $completion) {
    final String _sql = "SELECT * FROM material_logs WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MaterialLog>() {
      @Override
      @Nullable
      public MaterialLog call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSiteId = CursorUtil.getColumnIndexOrThrow(_cursor, "siteId");
          final int _cursorIndexOfBricks = CursorUtil.getColumnIndexOrThrow(_cursor, "bricks");
          final int _cursorIndexOfCementBags = CursorUtil.getColumnIndexOrThrow(_cursor, "cementBags");
          final int _cursorIndexOfSandLoads = CursorUtil.getColumnIndexOrThrow(_cursor, "sandLoads");
          final int _cursorIndexOfWallLength = CursorUtil.getColumnIndexOrThrow(_cursor, "wallLength");
          final int _cursorIndexOfWallWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "wallWidth");
          final int _cursorIndexOfWallHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "wallHeight");
          final int _cursorIndexOfWallThickness = CursorUtil.getColumnIndexOrThrow(_cursor, "wallThickness");
          final int _cursorIndexOfCalculatedOn = CursorUtil.getColumnIndexOrThrow(_cursor, "calculatedOn");
          final MaterialLog _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSiteId;
            _tmpSiteId = _cursor.getLong(_cursorIndexOfSiteId);
            final int _tmpBricks;
            _tmpBricks = _cursor.getInt(_cursorIndexOfBricks);
            final int _tmpCementBags;
            _tmpCementBags = _cursor.getInt(_cursorIndexOfCementBags);
            final double _tmpSandLoads;
            _tmpSandLoads = _cursor.getDouble(_cursorIndexOfSandLoads);
            final double _tmpWallLength;
            _tmpWallLength = _cursor.getDouble(_cursorIndexOfWallLength);
            final double _tmpWallWidth;
            _tmpWallWidth = _cursor.getDouble(_cursorIndexOfWallWidth);
            final double _tmpWallHeight;
            _tmpWallHeight = _cursor.getDouble(_cursorIndexOfWallHeight);
            final String _tmpWallThickness;
            _tmpWallThickness = _cursor.getString(_cursorIndexOfWallThickness);
            final long _tmpCalculatedOn;
            _tmpCalculatedOn = _cursor.getLong(_cursorIndexOfCalculatedOn);
            _result = new MaterialLog(_tmpId,_tmpSiteId,_tmpBricks,_tmpCementBags,_tmpSandLoads,_tmpWallLength,_tmpWallWidth,_tmpWallHeight,_tmpWallThickness,_tmpCalculatedOn);
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
  public Object getTotalBricksBySite(final long siteId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(bricks) FROM material_logs WHERE siteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, siteId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public Object getTotalCementBagsBySite(final long siteId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(cementBags) FROM material_logs WHERE siteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, siteId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public Object getTotalSandLoadsBySite(final long siteId,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(sandLoads) FROM material_logs WHERE siteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, siteId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
