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
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nammamistri.app.data.model.Calculation;
import com.nammamistri.app.data.model.CalculationType;
import com.nammamistri.app.data.model.Material;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CalculationDao_Impl implements CalculationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Calculation> __insertionAdapterOfCalculation;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<Material> __insertionAdapterOfMaterial;

  private final EntityDeletionOrUpdateAdapter<Calculation> __deletionAdapterOfCalculation;

  private final EntityDeletionOrUpdateAdapter<Calculation> __updateAdapterOfCalculation;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCalculationById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMaterialsByCalculationId;

  public CalculationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCalculation = new EntityInsertionAdapter<Calculation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `calculations` (`id`,`projectName`,`calculationType`,`length`,`width`,`height`,`area`,`quantity`,`unit`,`materialCost`,`laborCost`,`totalCost`,`notes`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Calculation entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getProjectName());
        final String _tmp = __converters.fromCalculationType(entity.getCalculationType());
        statement.bindString(3, _tmp);
        statement.bindDouble(4, entity.getLength());
        statement.bindDouble(5, entity.getWidth());
        statement.bindDouble(6, entity.getHeight());
        statement.bindDouble(7, entity.getArea());
        statement.bindDouble(8, entity.getQuantity());
        statement.bindString(9, entity.getUnit());
        statement.bindDouble(10, entity.getMaterialCost());
        statement.bindDouble(11, entity.getLaborCost());
        statement.bindDouble(12, entity.getTotalCost());
        statement.bindString(13, entity.getNotes());
        statement.bindLong(14, entity.getCreatedAt());
        statement.bindLong(15, entity.getUpdatedAt());
      }
    };
    this.__insertionAdapterOfMaterial = new EntityInsertionAdapter<Material>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `materials` (`id`,`calculationId`,`name`,`quantity`,`unit`,`rate`,`amount`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Material entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCalculationId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getQuantity());
        statement.bindString(5, entity.getUnit());
        statement.bindDouble(6, entity.getRate());
        statement.bindDouble(7, entity.getAmount());
      }
    };
    this.__deletionAdapterOfCalculation = new EntityDeletionOrUpdateAdapter<Calculation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `calculations` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Calculation entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCalculation = new EntityDeletionOrUpdateAdapter<Calculation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `calculations` SET `id` = ?,`projectName` = ?,`calculationType` = ?,`length` = ?,`width` = ?,`height` = ?,`area` = ?,`quantity` = ?,`unit` = ?,`materialCost` = ?,`laborCost` = ?,`totalCost` = ?,`notes` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Calculation entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getProjectName());
        final String _tmp = __converters.fromCalculationType(entity.getCalculationType());
        statement.bindString(3, _tmp);
        statement.bindDouble(4, entity.getLength());
        statement.bindDouble(5, entity.getWidth());
        statement.bindDouble(6, entity.getHeight());
        statement.bindDouble(7, entity.getArea());
        statement.bindDouble(8, entity.getQuantity());
        statement.bindString(9, entity.getUnit());
        statement.bindDouble(10, entity.getMaterialCost());
        statement.bindDouble(11, entity.getLaborCost());
        statement.bindDouble(12, entity.getTotalCost());
        statement.bindString(13, entity.getNotes());
        statement.bindLong(14, entity.getCreatedAt());
        statement.bindLong(15, entity.getUpdatedAt());
        statement.bindLong(16, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteCalculationById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM calculations WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteMaterialsByCalculationId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM materials WHERE calculationId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertCalculation(final Calculation calculation,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCalculation.insertAndReturnId(calculation);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertMaterials(final List<Material> materials,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMaterial.insert(materials);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCalculation(final Calculation calculation,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCalculation.handle(calculation);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCalculation(final Calculation calculation,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCalculation.handle(calculation);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCalculationWithMaterials(final Calculation calculation,
      final List<Material> materials, final Continuation<? super Long> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> CalculationDao.DefaultImpls.insertCalculationWithMaterials(CalculationDao_Impl.this, calculation, materials, __cont), $completion);
  }

  @Override
  public Object deleteCalculationById(final long calculationId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCalculationById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, calculationId);
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
          __preparedStmtOfDeleteCalculationById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMaterialsByCalculationId(final long calculationId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMaterialsByCalculationId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, calculationId);
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
          __preparedStmtOfDeleteMaterialsByCalculationId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Calculation>> getAllCalculations() {
    final String _sql = "SELECT * FROM calculations ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"calculations"}, new Callable<List<Calculation>>() {
      @Override
      @NonNull
      public List<Calculation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfCalculationType = CursorUtil.getColumnIndexOrThrow(_cursor, "calculationType");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfMaterialCost = CursorUtil.getColumnIndexOrThrow(_cursor, "materialCost");
          final int _cursorIndexOfLaborCost = CursorUtil.getColumnIndexOrThrow(_cursor, "laborCost");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Calculation> _result = new ArrayList<Calculation>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Calculation _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final CalculationType _tmpCalculationType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCalculationType);
            _tmpCalculationType = __converters.toCalculationType(_tmp);
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpArea;
            _tmpArea = _cursor.getDouble(_cursorIndexOfArea);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpMaterialCost;
            _tmpMaterialCost = _cursor.getDouble(_cursorIndexOfMaterialCost);
            final double _tmpLaborCost;
            _tmpLaborCost = _cursor.getDouble(_cursorIndexOfLaborCost);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Calculation(_tmpId,_tmpProjectName,_tmpCalculationType,_tmpLength,_tmpWidth,_tmpHeight,_tmpArea,_tmpQuantity,_tmpUnit,_tmpMaterialCost,_tmpLaborCost,_tmpTotalCost,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public LiveData<List<Calculation>> getAllCalculationsLiveData() {
    final String _sql = "SELECT * FROM calculations ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"calculations"}, false, new Callable<List<Calculation>>() {
      @Override
      @Nullable
      public List<Calculation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfCalculationType = CursorUtil.getColumnIndexOrThrow(_cursor, "calculationType");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfMaterialCost = CursorUtil.getColumnIndexOrThrow(_cursor, "materialCost");
          final int _cursorIndexOfLaborCost = CursorUtil.getColumnIndexOrThrow(_cursor, "laborCost");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Calculation> _result = new ArrayList<Calculation>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Calculation _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final CalculationType _tmpCalculationType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCalculationType);
            _tmpCalculationType = __converters.toCalculationType(_tmp);
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpArea;
            _tmpArea = _cursor.getDouble(_cursorIndexOfArea);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpMaterialCost;
            _tmpMaterialCost = _cursor.getDouble(_cursorIndexOfMaterialCost);
            final double _tmpLaborCost;
            _tmpLaborCost = _cursor.getDouble(_cursorIndexOfLaborCost);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Calculation(_tmpId,_tmpProjectName,_tmpCalculationType,_tmpLength,_tmpWidth,_tmpHeight,_tmpArea,_tmpQuantity,_tmpUnit,_tmpMaterialCost,_tmpLaborCost,_tmpTotalCost,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getCalculationById(final long id,
      final Continuation<? super Calculation> $completion) {
    final String _sql = "SELECT * FROM calculations WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Calculation>() {
      @Override
      @Nullable
      public Calculation call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfCalculationType = CursorUtil.getColumnIndexOrThrow(_cursor, "calculationType");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfMaterialCost = CursorUtil.getColumnIndexOrThrow(_cursor, "materialCost");
          final int _cursorIndexOfLaborCost = CursorUtil.getColumnIndexOrThrow(_cursor, "laborCost");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final Calculation _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final CalculationType _tmpCalculationType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCalculationType);
            _tmpCalculationType = __converters.toCalculationType(_tmp);
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpArea;
            _tmpArea = _cursor.getDouble(_cursorIndexOfArea);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpMaterialCost;
            _tmpMaterialCost = _cursor.getDouble(_cursorIndexOfMaterialCost);
            final double _tmpLaborCost;
            _tmpLaborCost = _cursor.getDouble(_cursorIndexOfLaborCost);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new Calculation(_tmpId,_tmpProjectName,_tmpCalculationType,_tmpLength,_tmpWidth,_tmpHeight,_tmpArea,_tmpQuantity,_tmpUnit,_tmpMaterialCost,_tmpLaborCost,_tmpTotalCost,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public LiveData<Calculation> getCalculationByIdLiveData(final long id) {
    final String _sql = "SELECT * FROM calculations WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"calculations"}, false, new Callable<Calculation>() {
      @Override
      @Nullable
      public Calculation call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfCalculationType = CursorUtil.getColumnIndexOrThrow(_cursor, "calculationType");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfMaterialCost = CursorUtil.getColumnIndexOrThrow(_cursor, "materialCost");
          final int _cursorIndexOfLaborCost = CursorUtil.getColumnIndexOrThrow(_cursor, "laborCost");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final Calculation _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final CalculationType _tmpCalculationType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCalculationType);
            _tmpCalculationType = __converters.toCalculationType(_tmp);
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpArea;
            _tmpArea = _cursor.getDouble(_cursorIndexOfArea);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpMaterialCost;
            _tmpMaterialCost = _cursor.getDouble(_cursorIndexOfMaterialCost);
            final double _tmpLaborCost;
            _tmpLaborCost = _cursor.getDouble(_cursorIndexOfLaborCost);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new Calculation(_tmpId,_tmpProjectName,_tmpCalculationType,_tmpLength,_tmpWidth,_tmpHeight,_tmpArea,_tmpQuantity,_tmpUnit,_tmpMaterialCost,_tmpLaborCost,_tmpTotalCost,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Calculation>> getCalculationsByProject(final String projectName) {
    final String _sql = "SELECT * FROM calculations WHERE projectName = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectName);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"calculations"}, new Callable<List<Calculation>>() {
      @Override
      @NonNull
      public List<Calculation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfCalculationType = CursorUtil.getColumnIndexOrThrow(_cursor, "calculationType");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfMaterialCost = CursorUtil.getColumnIndexOrThrow(_cursor, "materialCost");
          final int _cursorIndexOfLaborCost = CursorUtil.getColumnIndexOrThrow(_cursor, "laborCost");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Calculation> _result = new ArrayList<Calculation>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Calculation _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final CalculationType _tmpCalculationType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCalculationType);
            _tmpCalculationType = __converters.toCalculationType(_tmp);
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpArea;
            _tmpArea = _cursor.getDouble(_cursorIndexOfArea);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpMaterialCost;
            _tmpMaterialCost = _cursor.getDouble(_cursorIndexOfMaterialCost);
            final double _tmpLaborCost;
            _tmpLaborCost = _cursor.getDouble(_cursorIndexOfLaborCost);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Calculation(_tmpId,_tmpProjectName,_tmpCalculationType,_tmpLength,_tmpWidth,_tmpHeight,_tmpArea,_tmpQuantity,_tmpUnit,_tmpMaterialCost,_tmpLaborCost,_tmpTotalCost,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Calculation>> getCalculationsByType(final CalculationType type) {
    final String _sql = "SELECT * FROM calculations WHERE calculationType = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromCalculationType(type);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"calculations"}, new Callable<List<Calculation>>() {
      @Override
      @NonNull
      public List<Calculation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "projectName");
          final int _cursorIndexOfCalculationType = CursorUtil.getColumnIndexOrThrow(_cursor, "calculationType");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfMaterialCost = CursorUtil.getColumnIndexOrThrow(_cursor, "materialCost");
          final int _cursorIndexOfLaborCost = CursorUtil.getColumnIndexOrThrow(_cursor, "laborCost");
          final int _cursorIndexOfTotalCost = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCost");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Calculation> _result = new ArrayList<Calculation>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Calculation _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpProjectName;
            _tmpProjectName = _cursor.getString(_cursorIndexOfProjectName);
            final CalculationType _tmpCalculationType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCalculationType);
            _tmpCalculationType = __converters.toCalculationType(_tmp_1);
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpArea;
            _tmpArea = _cursor.getDouble(_cursorIndexOfArea);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpMaterialCost;
            _tmpMaterialCost = _cursor.getDouble(_cursorIndexOfMaterialCost);
            final double _tmpLaborCost;
            _tmpLaborCost = _cursor.getDouble(_cursorIndexOfLaborCost);
            final double _tmpTotalCost;
            _tmpTotalCost = _cursor.getDouble(_cursorIndexOfTotalCost);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Calculation(_tmpId,_tmpProjectName,_tmpCalculationType,_tmpLength,_tmpWidth,_tmpHeight,_tmpArea,_tmpQuantity,_tmpUnit,_tmpMaterialCost,_tmpLaborCost,_tmpTotalCost,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Material>> getMaterialsByCalculationId(final long calculationId) {
    final String _sql = "SELECT * FROM materials WHERE calculationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, calculationId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"materials"}, new Callable<List<Material>>() {
      @Override
      @NonNull
      public List<Material> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCalculationId = CursorUtil.getColumnIndexOrThrow(_cursor, "calculationId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final List<Material> _result = new ArrayList<Material>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Material _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCalculationId;
            _tmpCalculationId = _cursor.getLong(_cursorIndexOfCalculationId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final double _tmpRate;
            _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            _item = new Material(_tmpId,_tmpCalculationId,_tmpName,_tmpQuantity,_tmpUnit,_tmpRate,_tmpAmount);
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
  public Object getTotalCostByProject(final String projectName,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(totalCost) FROM calculations WHERE projectName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectName);
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

  @Override
  public Flow<List<String>> getAllProjectNames() {
    final String _sql = "SELECT DISTINCT projectName FROM calculations ORDER BY projectName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"calculations"}, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
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
  public Object getCalculationCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM calculations";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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
